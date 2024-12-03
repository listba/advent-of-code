//> using dep com.lihaoyi::os-lib:0.11.3
//> using dep co.fs2::fs2-core::3.11.0
//> using dep co.fs2::fs2-io::3.11.0
import cats.effect.ExitCode
import cats.effect.IOApp
import cats.ApplicativeThrow

import cats.syntax.all._
import cats.effect.IO
import cats.effect.Concurrent
import fs2.io.file.{Files, Path}
import fs2.text.utf8Decode
import fs2.{Chunk, Stream}

object Day01 extends IOApp.Simple {
  val run: IO[Unit] = for {
    (listA, listB) <- parse[IO](Path("../../input/day-01/input-1.txt"))
    part1Result    =  part1(listA, listB)
    _              <- IO.println(s"Part 1: $part1Result")
    part2Result    =  part2(listA, listB)
    _              <- IO.println(s"Part 2: $part2Result")
  } yield ExitCode.Success
}

def parse[F[_]: Files: Concurrent](path: Path): F[(List[Int], List[Int])] =
  Files[F]
    .readAll(path)
    .through(utf8Decode)
    .through(fs2.text.lines)
    .map(_.split("\\s+").map(_.toInt).toList)
    .flatMap {
        case a::b::Nil => Stream.emit(a -> b)
        case x         => Stream.raiseError(new Exception(s"Failed to parse $x"))
    }.compile.toList.map(_.unzip)
    
def part1(listA: List[Int], listB: List[Int]): Int =
  listA.sorted.zip(listB.sorted).map{ case (l, r) => (l-r).abs}.sum

def part2(listA: List[Int], listB: List[Int]): Int = {
  val lookup = listB.groupBy(identity).mapValues(_.length)
  listA.map(k => k * lookup.getOrElse(k,0)).sum
}
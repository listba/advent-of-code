

final class day$minus01$_ {
def args = day$minus01_sc.args$
def scriptPath = """day-01.sc"""
/*<script>*/
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

// import scala.util.matching.Regex

// val inputPath = os.pwd / os.up / os.up / "input" / "day-01" / "input-1.txt"
// val input = os.read.lines(inputPath)

// object Pair {
  
//   private val regex: Regex = """(\d+)\s+(\d+)""".r
  
//   def unapply(s: String): Option[(Int, Int)] =
//     s match {
//       case regex(a, b) => Some((a.toInt, b.toInt))
//       case _ => None
//     }
// }

// def parse(input: os.Path): (Seq[Int], Seq[Int]) =
//   os.read.lines(input)
//          .map{ case Pair(a,b) => a -> b }
//          .unzip


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
    
    // for {
    //   x <- parseFileToIntPairs[IO](???).compile
    // } yield ???

object Day01 extends IOApp.Simple {
  val run: IO[Unit] = for {
    (listA, listB) <- parse[IO](Path("../../input/day-01/input-1.txt"))
    part1Result    =  part1(listA, listB)
    _              <- IO.println(s"Part 1: $part1Result")
    //part2Result    <- part2(listA, listB)
  } yield ExitCode.Success
}

def part1( listA: List[Int], listB: List[Int]): String =
  listA.sorted.zip(listB.sorted).map{ case (l, r) => (l-r).abs}.sum.toString

/*</script>*/ /*<generated>*//*</generated>*/
}

object day$minus01_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new day$minus01$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export day$minus01_sc.script as `day-01`


//> using dep "org.typelevel::cats-core:2.12.0"
//> using dep "org.typelevel::cats-effect:3.5.7"
//> using dep co.fs2::fs2-core::3.11.0
//> using dep co.fs2::fs2-io::3.11.0
//> using dep com.monovore::decline:2.4.1
//> using dep com.monovore::decline-effect::2.4.1

package aoc

import cats.syntax.all._
import cats.ApplicativeThrow
import cats.effect.{Concurrent, ExitCode, IO, IOApp}
import cats.effect.Concurrent
import fs2.Stream
import fs2.io.file.{Files, Path}
import com.monovore.decline._
import com.monovore.decline.effect._


class Result[A](val message: String, val value: A)
object Result {
  def apply[A](message: String, value: A) = new Result[A](message, value)
  def apply[A](value: A) = new Result[A](s"Result: $value", value)
}

trait Day[F[_], O] {
  val day: Int
  type Parsed
  def parse(input: Stream[F,String]): F[Parsed]

  def part1(data: Parsed): F[Result[O]]

  def part2(data: Parsed): F[Result[O]]
}

def readFile[F[_]: Files: Concurrent](path: Path): Stream[F,String] =
  Files[F]
    .readAll(path)
    .through(fs2.text.utf8.decode)
    .through(fs2.text.lines)


val day: Opts[Int] = Opts.argument[Int](metavar="1-25")

val file: Opts[String] = 
  Opts.option[String]( "file", "The name of the input file to use.", short = "f" ).withDefault("input.txt")

val days: Seq[Day[IO, ?]] = 
  Day01[IO] :: 
  Day02[IO] :: 
  Day03[IO] :: 
  Day04[IO] :: 
  Nil

def findDay[F[_]: ApplicativeThrow](dayNum: Int) = days.find(_.day == dayNum) match
  case None      =>  ApplicativeThrow[F].raiseError(new IllegalArgumentException(s"Day $dayNum not found"))
  case Some(day) => ApplicativeThrow[F].pure(day)


object AOCApp extends CommandIOApp(
  name = "aoc",
  header = "cli for aoc 2024",
  version = "1.0.0"
) {

  override def main: Opts[IO[ExitCode]] = (day, file).mapN { (d, f) =>
    for {
      _      <- IO.println(s"Running day $d with file $f")
      path   =  Path(s"../input/day-$d/$f")
      day    <- findDay[IO](d)
      parsed <- day.parse(readFile[IO](path))
      part1Result <- day.part1(parsed)
      part2Result <- day.part2(parsed)
      _      <- IO.println(s"Part 1: ${part1Result.message}")
      _      <- IO.println(s"Part 2: ${part2Result.message}")
    } yield ExitCode.Success
  }
}
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

object Day02 extends IOApp.Simple {
  val run: IO[Unit] = for {
    _       <- IO.println("Day 02")
    results <- parse02[IO](Path("../../input/day-02/input-1.txt"))
    valid   =  results.filter(validate)
    _       <- IO.println(s"Valid Reports: ${valid.length}")
    validD  =  results.filter(validateWithDrop)
    _       <- IO.println(s"Valid Reports w Dampener: ${validD.length}")
  } yield ExitCode.Success
}

def parse02[F[_]: Files: Concurrent](path: Path): F[List[List[Int]]] =
  Files[F]
    .readAll(path)
    .through(utf8Decode)
    .through(fs2.text.lines)
    .map(_.split("\\s+").map(_.toInt).toList)
    .compile.toList
    
def validate(report: List[Int]): Boolean = 
  val diff = report.sliding(2).map{ case a::b::Nil => (a-b) }.toList
  diff.forall(d => d >= 1 && d <=3) ||
  diff.forall(d => d <= -1 && d >= -3)

def incDiff(a:Int,b:Int): Int = b - a
def decDiff(a:Int,b:Int): Int = a - b

def validateWithDrop(report: List[Int]): Boolean =
  val dampenedReports = Range.inclusive(0,report.length-1).map(i => report.take(i) ++ report.drop(i+1))
  validate(report) || dampenedReports.exists(validate)
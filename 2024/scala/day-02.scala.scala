package aoc

import cats.syntax.all._
import cats.effect.Concurrent
import fs2.Stream

object Day02 {
  def apply[F[_]: Concurrent](): Day[F,Int] = new Day[F,Int] {
    val day = 2
    type Parsed = List[List[Int]]

    private def validate(report: List[Int]): Boolean = 
      val diff = report.sliding(2).map{ 
        case a::b::Nil => (a-b) 
        case _ => ??? // meh
      }.toList
      diff.forall(d => d >= 1 && d <=3) ||
      diff.forall(d => d <= -1 && d >= -3)

    private def incDiff(a:Int,b:Int): Int = b - a
    private def decDiff(a:Int,b:Int): Int = a - b

    private def validateWithDrop(report: List[Int]): Boolean =
      val dampenedReports = Range.inclusive(0,report.length-1).map(i => report.take(i) ++ report.drop(i+1))
      validate(report) || dampenedReports.exists(validate)

    def parse(input: Stream[F,String]): F[Parsed] =
      input.map(_.split("\\s+").map(_.toInt).toList)
           .compile.toList

    def part1(data: Parsed): F[Result[Int]] =
      Concurrent[F].pure(Result[Int](data.filter(validate).length))
      

    def part2(data: Parsed): F[Result[Int]] =
      Concurrent[F].pure(Result[Int](data.filter(validateWithDrop).length))
  }
}
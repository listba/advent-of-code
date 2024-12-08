package aoc

import cats.syntax.all._
import cats.effect.Concurrent
import fs2.Stream


object Day01 {
  def apply[F[_]: Concurrent]: Day[F,Int] = new Day[F,Int] {
    val day = 1
    type Parsed = (List[Int], List[Int])

    def parse(input: Stream[F,String]): F[Parsed] =
      input.map(_.split("\\s+").map(_.toInt).toList)
      .flatMap {
        case a::b::Nil => Stream.emit(a -> b)
        case x         => Stream.raiseError(new Exception(s"Failed to parse $x"))
      }.compile.toList.map(_.unzip)

    def part1(data: Parsed): F[Result[Int]] =
      val res = data._1.sorted.zip(data._2.sorted).map{ case (l, r) => (l-r).abs}.sum
      Concurrent[F].pure(Result[Int](res))
      

    def part2(data: Parsed): F[Result[Int]] =
      val lookup = data._2.groupBy(identity).view.mapValues(_.length)
      val res = data._1.map(k => k * lookup.getOrElse(k,0)).sum
      Concurrent[F].pure(Result[Int](res))
  }
}
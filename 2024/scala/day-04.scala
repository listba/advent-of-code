package aoc

import cats.syntax.all._
import cats.effect.Concurrent
import fs2.Stream


object Day04 {
  def apply[F[_]: Concurrent](): Day[F,Int] = new Day[F,Int] {
    val day = 4
    type Parsed = List[String]

    def parse(input: Stream[F,String]): F[Parsed] =
      input.compile.toList

    def part1(data: Parsed): F[Result[Int]] =
      Concurrent[F].pure(Result[Int](0))
      

    def part2(data: Parsed): F[Result[Int]] =
      Concurrent[F].pure(Result[Int](0))
  }
}
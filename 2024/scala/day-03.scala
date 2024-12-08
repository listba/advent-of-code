package aoc

import cats.syntax.all._
import cats.effect.Concurrent
import fs2.Stream
import scala.annotation.tailrec

sealed trait Cmd {
  val idx: Int
}

case class Do(idx: Int) extends Cmd 
case class Dont(idx: Int) extends Cmd

case class Mult(a: Int, b: Int, idx: Int) extends Cmd {
  val compute = a*b;
}

object Parser { 
  private val MultPat = raw"mul\((\d+),(\d+)\)".r
  private val DoPat = raw"do\(\)".r
  private val DontPat = raw"don't\(\)".r

  private def mults(line: String): List[Mult] =
    MultPat.findAllMatchIn(line)
           .map( m => Mult(m.group(1).toInt, m.group(2).toInt, m.start))
           .toList

  private def dos(line: String): List[Do] =
    DoPat.findAllMatchIn(line)
           .map( m => Do(m.start))
           .toList
  
  private def donts(line: String): List[Dont] =
    DontPat.findAllMatchIn(line)
           .map( m => Dont(m.start))
           .toList

  def parseLine(line: String): List[Cmd] = 
    (mults(line) ++ dos(line) ++ donts(line)).sortBy(_.idx)
}

object Day03 {
  def apply[F[_]: Concurrent]: Day[F,Int] = new Day[F,Int] {
    val day = 3
    type Parsed = List[Cmd]

    def parse(input: Stream[F,String]): F[Parsed] =
      input.map(Parser.parseLine)
           .compile.toList.map(_.flatten)

    def part1(cmds: Parsed): F[Result[Int]] =
      Concurrent[F].pure(Result[Int](cmds.collect{ case Mult(a,b,_) => a*b}.sum))
      

    def part2(data: Parsed): F[Result[Int]] =
      @tailrec
      def go(cmds: List[Cmd], acc: Int, on: Boolean): Int = cmds match
        case Nil                     => acc
        case Do(_)       :: xs       => go(xs, acc, true)
        case Dont(_)     :: xs       => go(xs, acc, false)
        case Mult(a,b,_) :: xs if on => go(xs, acc + a*b, on)
        case Mult(a,b,_) :: xs       => go(xs, acc, on)
      Concurrent[F].pure(Result[Int](go(data, 0, true)))
  }
}
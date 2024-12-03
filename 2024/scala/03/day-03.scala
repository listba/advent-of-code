//> using dep com.lihaoyi::os-lib:0.11.3
//> using dep co.fs2::fs2-core::3.11.0
//> using dep co.fs2::fs2-io::3.11.0
//> using dep org.typelevel::cats-parse::1.0.0

import cats.syntax.all._
import cats.effect.{Concurrent,ExitCode,IO,IOApp}
import fs2.io.file.{Files, Path}
import fs2.text.utf8Decode
import fs2.{Chunk, Stream}
import scala.util.matching.Regex
import scala.annotation.tailrec

object Day03 extends IOApp.Simple {
  val run: IO[Unit] = for {
    _        <- IO.println("Day 03")
    cmds     <- parse03[IO](Path("../../input/day-03/input.txt"))
    multsRes =  computeMults(cmds)
    _        <- IO.println(s"Mults Only: $multsRes")
    pgrmRes  =  compute(cmds)
    _        <- IO.println(s"Mults Only: $pgrmRes")
  } yield ExitCode.Success
}

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

def parse03[F[_]: Files: Concurrent](path: Path): F[List[Cmd]] =
  Files[F]
    .readAll(path)
    .through(utf8Decode)
    .through(fs2.text.lines)
    .map(Parser.parseLine)
    .compile.toList.map(_.flatten)

def computeMults(cmds: List[Cmd]): Int = cmds.collect{ case Mult(a,b,_) => a*b}.sum

def compute(allCmds: List[Cmd]): Int =
  @tailrec
  def go(cmds: List[Cmd], acc: Int, on: Boolean): Int = cmds match
    case Nil                     => acc
    case Do(_)       :: xs       => go(xs, acc, true)
    case Dont(_)     :: xs       => go(xs, acc, false)
    case Mult(a,b,_) :: xs if on => go(xs, acc + a*b, on)
    case Mult(a,b,_) :: xs       => go(xs, acc, on)
  go(allCmds, 0, true)
    
  
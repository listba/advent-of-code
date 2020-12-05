package aoc2020.solutions

import scala.io.Source
import fastparse._
import fastparse.Parsed.Failure
import aoc2020.day
import aoc2020.Utils.btwn
import fastparse.NoWhitespace._

object day02 extends day {
  val day = 2

  def CHAR[_:P] = CharIn("A-Za-z").!.map(_.toCharArray().head)
  def DIGITS[_:P] = CharsWhileIn("0-9",1).!.map(_.toInt)
  def RULE[_:P] = P(DIGITS ~~ "-" ~~ DIGITS ~~ " " ~~ CHAR ~~ ":" ~~ " ")
  
  def p1 = {
    def PASS_P1[_:P] = P(RULE ~~ CharsWhileIn("a-zA-Z").! ~~ End)
    val input = Source.fromFile("../resources/day-02/input.txt").getLines.toList
    val valid = input.map(line => parse(line,PASS_P1(_)))
                     .collect { 
                         case Parsed.Success((min,max,c,pass),_) if btwn(pass.count(_ == c),min,max) => ()
                     }
                    .length
    s"There are $valid passwords"
  }

  def p2 = {
    def PASS_P2[_:P] = for {
        (f,s,c) <- RULE
        _       <- CHAR.rep(f-1,max=f-1)
        r       <- CHAR
        _       <- CHAR.rep(s-f-1,max=s-f-1)
        _       <- if (r == c) CharPred(_ != c) else CharPred(_ == c)
        _       <- CHAR.rep ~~ End
    } yield ()
    val input = Source.fromFile("../resources/day-02/input.txt").getLines.toList
    val valid = input.map(line => parse(line,PASS_P2(_)))
                     .collect { case Parsed.Success(_,i) => () }
                     .length
    s"There are $valid valid passwords"
  }
  
}
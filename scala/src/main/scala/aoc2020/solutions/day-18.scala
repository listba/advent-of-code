package aoc2020.solutions

import aoc2020.day
import scala.io.Source
import cats.parse.{Parser1 => P1, Parser => P}


object day18 extends day {
    val day = 18
    
    sealed trait Op
    case object Addition extends Op
    case object Multiplication extends Op


    val digits = P.charIn('0' to '9').rep1.string.map(BigInt(_))
    val ws   = P.char(' ')

    val ADD  = ws.soft *> P.char('+').as[Op](Addition) <* ws
    val MULT = ws.soft *> P.char('*').as[Op](Multiplication) <* ws

    val OP = P.oneOf1(ADD::MULT::Nil)
    
    val exp : P1[BigInt] = {
        val recurse = P.defer1(exp)

        val parened = P.char('(') *> recurse <* P.char(')')
        val term = P.oneOf1(parened :: digits :: Nil)

        (term ~ (OP ~ term).rep).map {
            case (lhs,chunks) => chunks.foldLeft(lhs) {
                case (lhs, (op, rhs)) => op match {
                    case Addition => lhs + rhs
                    case Multiplication => lhs * rhs
                }
            }
        }        
    }

    def p1: String = {
        Source.fromFile("../resources/day-18/input.txt")
              .getLines
              .map(exp.parseAll(_))
              .collect{case Right(v) => v}
              .sum.toString()
    }

    val exp2 : P1[BigInt] = {
        val recurse = P.defer1(exp2)

        val parened = P.char('(') *> recurse <* P.char(')')
        val term = P.oneOf1(parened :: digits :: Nil)

        val add = (term ~ (ADD ~ term).rep).map {
            case (lhs,chunks) => chunks.foldLeft(lhs) {
                case (lhs, (_, rhs)) => lhs + rhs
            }
        } 
        
        (add ~ (MULT ~ add).rep).map {
            case (lhs,chunks) => chunks.foldLeft(lhs) {
                case (lhs, (_, rhs)) => lhs * rhs
            }
        } 
    }

    def p2: String = {
        Source.fromFile("../resources/day-18/input.txt")
              .getLines
              .map(exp2.parseAll(_))
              .collect{case Right(v) => v}
              .sum.toString()
    }
}
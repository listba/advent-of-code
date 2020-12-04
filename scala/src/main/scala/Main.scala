package aoc2020

import solutions._

trait day {
    def p1 : Unit
    def p2 : Unit
}

object Main extends App {
  List(day02,day04).map(d => { d.p1; d.p2 })
}
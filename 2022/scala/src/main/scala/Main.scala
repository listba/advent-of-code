package aoc2021

import solutions._

trait day {
  val day : Int
  def p1 : String 
  def p2 : String
}

@main def hello: Unit = 
  List().map(d => {
    println(
      s"""------------ Day ${d.day} Part 1 ------------ 
         |Result: ${d.p1}
         |
         |------------ Day ${d.day} Part 2 ------------ 
         |Result: ${d.p2}
         |""".stripMargin )
  })
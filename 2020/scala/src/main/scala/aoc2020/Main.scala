package aoc2020

import solutions._

trait day {
    val day : Int
    def p1 : String
    def p2 : String
}

case class Person(name:String, age: Int, ssn: Option[String] = None)

object Person_ {
  def unapply(s:Person) = Some((s.name,s.age))
}

object Main extends App {
  List(day02,day04,day05,day18).map(d => { 
    println(
      s"""------------ Day ${d.day} Part 1 ------------ 
         |Result: ${d.p1}
         |
         |------------ Day ${d.day} Part 2 ------------ 
         |Result: ${d.p2}
         |""".stripMargin )
  })

  Person("fred", 22) match {
    case Person_(name,age) => println(s"Matched $name [$age]")
  }
}
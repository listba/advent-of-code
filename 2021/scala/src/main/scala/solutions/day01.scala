package aoc2021.solutions

import aoc2021.day

import scala.io.Source

object day01 extends day {
    val day = 1

    val input = Source.fromFile("../resources/day-01/input.txt").getLines.toList.map(_.toInt)

    def checkNums(xs: List[Int]): String = xs.sliding(2).foldLeft(0){
        case (c, a::b::Nil) if a < b => c+1
        case (c, _) => c
    }.toString

    def p1 = checkNums(input)

    def p2 = checkNums(input.sliding(3).map(_.sum).toList)
}

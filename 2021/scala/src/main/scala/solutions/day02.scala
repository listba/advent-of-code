package aoc2021.solutions

import aoc2021.day

import scala.io.Source

object day02 extends day {
    val day = 2

    val input = Source.fromFile("../resources/day-02/input.txt").getLines.toList.map(_.split(' ') match {
        case Array(cmd,x) => (cmd,x.toInt)
    })
    def p1 = input.foldLeft((0,0)){
        case ((h,d), ("forward", x)) => (h+x,d)
        case ((h,d), ("up", x))      => (h,d-x)
        case ((h,d), ("down", x))    => (h,d+x)
    } match { case (h,d) => s"${h*d}" }
    
    def p2 = input.foldLeft((0,0,0)){
        case ((h,d,a), ("forward", x)) => (h+x,d+(a*x),a)
        case ((h,d,a), ("up", x))      => (h,d,a-x)
        case ((h,d,a), ("down", x))    => (h,d,a+x)
    } match { case (h,d,a) => s"${h*d}" }
}

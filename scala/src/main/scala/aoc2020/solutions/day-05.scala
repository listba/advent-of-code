package aoc2020.solutions

import scala.io.Source
import aoc2020.day

object day05 extends day { 
    val day = 5

    val tickets = (for {
        line  <- Source.fromFile("../resources/day-05/input.txt").getLines()
        row   =  line.take(7).map{ case 'F' => "0"; case 'B' => "1"; }.mkString
        col   =  line.drop(7).take(3).map{ case 'L' => "0"; case 'R' => "1"; }.mkString 
    } yield (Integer.parseInt(row,2) * 8) + Integer.parseInt(col,2)).toList

    def p1 = {
        s"${tickets.max}"
    }

    def p2 = {
        val sorted = tickets.sorted
        sorted.foldLeft((Option.empty[Int],sorted.head)){ 
            case ((s, prev), cur) => if (cur-prev == 2) (Some(cur-1),cur) else (s,cur) 
        } match { case (Some(seat),_) => s"Your seat is $seat"; case _ => "no seat found"}
    }
}
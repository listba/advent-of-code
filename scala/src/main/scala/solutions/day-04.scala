package aoc2020.solutions

import scala.io.Source
import fastparse._
import fastparse.Parsed.Failure
import fastparse.NoWhitespace._
import aoc2020.day
import aoc2020.Utils.btwn
import fastparse.Parsed.Success


object day04 extends day { 

    trait MMUnit
    case object CM extends MMUnit
    case object IN extends MMUnit
    def requiredFields(pp: Map[String,Any]) = List("byr","iyr","eyr","hgt","hcl","ecl","pid").foldLeft(true){case (r,k) => r && pp.contains(k) }

    def space1[_:P] = CharIn("\n ")
    def DIGITS[_:P]:P[Int] = CharsWhileIn("0-9").!.map(_.toInt)
    def DIGITS[_:P](min:Int, max:Int):P[Int] = DIGITS.filter(btwn(_,min,max))
    def HEXCLR[_:P] = P("#"~~CharIn("a-f0-9").rep(6,max=6)).!
    def MMUNIT[_:P] = P("cm").map(_ => CM) | P("in").map(_ => IN)
    def key[_:P](k:String) = P(k.! ~~ ":")
    def byr[_:P] = P(key("byr") ~~ DIGITS(1920,2002))
    def iyr[_:P] = P(key("iyr") ~~ DIGITS(2010,2020))
    def eyr[_:P] = P(key("eyr") ~~ DIGITS(2020,2030))
    def hgt[_:P] = P(key("hgt") ~~ (DIGITS ~~ MMUNIT).filter{ 
        case (i,CM) => btwn(i,150,193)
        case (i,IN) => btwn(i,59,76)
        case _ => false
    })
    def hcl[_:P] = P(key("hcl") ~~ HEXCLR)
    def ecl[_:P] = P(key("ecl") ~~ ("amb"|"blu"|"brn"|"gry"|"grn"|"hzl"|"oth").!)
    def pid[_:P] = P(key("pid") ~~ CharIn("0-9").rep(9,max=9).!)
    def cid[_:P] = P(key("cid") ~~ CharsWhileIn("0-9a-z").!)

    def p1 = {
        def ppField[_:P] = P(CharsWhileIn("a-z").! ~~ ":" ~~ CharsWhileIn("a-z0-9#").!)
        def pp[_:P] = P(ppField.rep(1,sep=space1)).map(_.toMap)

        val input = Source.fromFile("../resources/day-04/input.txt").getLines.mkString("\n").split("\n\n")
        val valid = input.map(parse(_,pp(_))).collect{ case Success(pp, index) if requiredFields(pp) => pp }
        println(s"Day04 P1: Found ${valid.length} passports")
    }

    def p2 = {
        def ppField[_:P] = P(byr | iyr | eyr | hgt | hcl | ecl | pid | cid)
        def pp[_:P] = P(ppField.rep(1,sep=space1)).map(_.toMap)

        val input = Source.fromFile("../resources/day-04/input.txt").getLines.mkString("\n").split("\n\n")
        val valid = input.map(parse(_,pp(_))).collect{ case Success(pp, index) if requiredFields(pp) => pp }
        println(s"Day04 P2: Found ${valid.length} passports")
    }
}
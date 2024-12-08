package aoc

import cats.syntax.all._
import cats.effect.Concurrent
import fs2.Stream


case class GridItem[A](value: A, row: Int, col: Int) {
  def distanceFrom(gi: GridItem[A]): (Int, Int) = 
    (this.row - gi.row) -> (this.col - gi.col)
}


class Grid[A](input: IndexedSeq[IndexedSeq[A]]){

  def get(row: Int, col: Int): Option[A] = input.lift(row).flatMap(_.lift(col))

  def getWhen(predicate: A => Boolean)(row: Int, col: Int): Option[GridItem[A]] = 
    get(row, col).filter(predicate).map(GridItem(_,row,col))

  def findAll(a:A): Seq[GridItem[A]] = for {
    row <- (0 to input.length-1)
    col <- (0 to input(row).length-1)
    if input(row)(col) == a
   } yield GridItem(a, row, col)

   def itemsFrom(gi: GridItem[A], distances: List[(Int, Int)]): List[GridItem[A]] = for {
      (r,c) <- distances.map(_ |+| (gi.row, gi.col))
      if r >= 0 && r < input.length && c >= 0 && c < input(r).length
     } yield GridItem(input(r)(c), r, c)

   val diagonals = List((1,1), (-1,-1), (1,-1), (-1,1))
   val cardinal = List((1,0), (-1,0), (0,1), (0,-1))
   
   def neighbors(gi: GridItem[A]): List[GridItem[A]] = 
    itemsFrom(gi, cardinal++diagonals)
}

object Day04 {
  def apply[F[_]: Concurrent]: Day[F,Int] = new Day[F,Int] {
    val day = 4
    type Parsed = Grid[Char]

    def parse(input: Stream[F,String]): F[Parsed] =
      input.map(_.toCharArray().toIndexedSeq)
           .compile.toList.map(x => new Grid(x.toIndexedSeq))

    def part1(grid: Parsed): F[Result[Int]] =
      val xmas = for {
        x       <- grid.findAll('X')
        m       <- grid.neighbors(x).filter(_.value == 'M')
        (dR,dC) =  m.distanceFrom(x)
        a       <- grid.getWhen(_ == 'A')(m.row+dR, m.col+dC)
        s       <- grid.getWhen(_ == 'S')(a.row+dR, a.col+dC)
      } yield (x, m, a, s)

      Concurrent[F].pure(Result[Int](xmas.size))

    def part2(grid: Parsed): F[Result[Int]] =
      val x_mas = for {
        a <- grid.findAll('A')
        lr = grid.itemsFrom(a, (-1,-1)::(1,1)::Nil).map(_.value).toSet
        rl = grid.itemsFrom(a, (-1,1)::(1,-1)::Nil).map(_.value).toSet
        exp = Set('M', 'S')
        if lr == exp && rl == exp
      } yield a
      Concurrent[F].pure(Result[Int](x_mas.size))
  }
}
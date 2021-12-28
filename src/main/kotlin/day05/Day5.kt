package day05

import Coordinate
import PuzzleData

class Line(private val start: Coordinate, private val end: Coordinate) {

    fun coordinates(): List<Coordinate> {
        val xOp = steppingOperator(start.x, end.x)
        val yOp = steppingOperator(start.y, end.y)
        return steps(listOf(), start, end, xOp, yOp)
    }

    private fun steppingOperator(n1: Int, n2: Int): (Int) -> Int =
        if (n1 == n2) { n: Int -> n }
        else if (n1 < n2) { n: Int -> n + 1 }
        else { n: Int -> n - 1 }

    private fun steps(
        acc: List<Coordinate>, current: Coordinate, end: Coordinate, xOp: (Int) -> Int, yOp: (Int) -> Int
    ): List<Coordinate> {
        return if (current == end) acc + current
        else steps(acc + current, Coordinate(xOp(current.x), yOp(current.y)), end, xOp, yOp)
    }

    override fun toString(): String = "$start -> $end"
}

object Day5 : Runnable {

    private val lines = PuzzleData.load("/day05/day5.txt") { parseLines(it) }

    fun countCoordinatesInTwoOrMoreLines(): Int =
        lines.flatMap { it.coordinates() }.groupingBy { it }.eachCount().filter { it.value > 1 }.size

    private fun parseLines(lines: List<String>): List<Line> =
        lines.map{
            val coordinateStrings = it.split(" -> ")
            val x = coordinateStrings[0].split(",").map{ n -> n.toInt() }
            val y = coordinateStrings[1].split(",").map{ n -> n.toInt() }
            Line(Coordinate(x[0], x[1]), Coordinate(y[0], y[1]))
        }

    override fun run() {
        println("Day 5, number of coordinates in two or more lines (with diagonals): ${countCoordinatesInTwoOrMoreLines()}")
    }
}
import day01.Day01
import day02.Day02
import day03.Day03
import day04.Day4
import day05.Day5
import day06.Day06
import day07.Day07
import day08.Day8
import day09.Day09
import day10.Day10
import day11.Day11
import day12.Day12
import day13.Day13
import day14.Day14
import day15.Day15
import day16.Day16
import day17.Day17
import day18.Day18
import day19.Day19
import day20.Day20
import day21.Day21
import day22.Day22
import day23.Day23
import day24.Day24
import day25.Day25
import java.io.File

data class Coordinate(val x: Int, val y: Int) {

    private fun north(): Coordinate = Coordinate(x + 1, y)
    private fun east(): Coordinate = Coordinate(x, y + 1)
    private fun south(): Coordinate = Coordinate(x - 1, y)
    private fun west(): Coordinate = Coordinate(x, y - 1)
    fun directNeighbours(): Set<Coordinate> = setOf(north(), east(), south(), west())

    private fun northWest(): Coordinate = Coordinate(x + 1, y - 1)
    private fun northEast(): Coordinate = Coordinate(x + 1, y + 1)
    private fun southEast(): Coordinate = Coordinate(x - 1, y + 1)
    private fun southWest(): Coordinate = Coordinate(x - 1, y - 1)
    fun allNeighbours(): Set<Coordinate> = setOf(
        north(), east(), south(), west(), northWest(), northEast(), southEast(), southWest()
    )
}

fun main() = listOf(
    Day01, Day02, Day03, Day4, Day5, Day06, Day07, Day8, Day09, Day10,
    Day11, Day12, Day13, Day14, Day15, Day16, Day17, Day18, Day19, Day20,
    Day21, Day22, Day23, Day24, Day25
).forEach { it.run() }

object PuzzleData {

    fun <T> load(resourceLocation: String, transform: (lines: List<String>) -> T): T {
        val resourceUrl = javaClass.getResource(resourceLocation)
            ?: throw Error("Error loading resource: $resourceLocation")
        val lines = File(resourceUrl.path).reader().readLines()
        return transform(lines)
    }
}

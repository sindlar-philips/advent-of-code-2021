import day01.Day1
import day02.Day2
import day03.Day3
import day04.Day4
import day05.Day5
import day06.Day6
import day07.Day7
import day08.Day8
import day09.Day9
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
    Day1, Day2, Day3, Day4, Day5, Day6, Day7, Day8, Day9, Day10,
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

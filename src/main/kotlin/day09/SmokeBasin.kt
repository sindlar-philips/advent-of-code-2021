package day09

import PuzzleData
import day04.Coordinate

object SmokeBasin : Runnable {

    private val heights = PuzzleData.load("/day09/heightmap.txt") { parse(it) }

    fun sumOfRiskLevels(): Int =
        lowPoints().sumOf { heights[it]!! + 1 }

    fun productOfThreeLargestBasinSizes(): Int =
        lowPoints().asSequence().map { getBasin(it) }
            .sortedByDescending { it.size }.take(3).map { it.size }.reduce { a, b -> a * b }

    private fun getBasin(coordinate: Coordinate): Set<Coordinate> {
        fun determineBasin(lastAdded: List<Coordinate>, basin: List<Coordinate>): List<Coordinate> {
            val newNeighbours = lastAdded.flatMap { basinNeighbours(it) }.filterNot { basin.contains(it) }
            return if (newNeighbours.isEmpty()) basin + coordinate
            else determineBasin(newNeighbours, basin + newNeighbours)
        }
        return determineBasin(listOf(coordinate), listOf(coordinate)).toSet()
    }

    private fun basinNeighbours(coordinate: Coordinate): List<Coordinate> =
        coordinate.directNeighbours().filter { heights.containsKey(it) && heights[it]!! < 9 }

    private fun lowPoints(): List<Coordinate> = heights.keys.filter { it.isLowPoint() }

    private fun Coordinate.isLowPoint(): Boolean =
        this.directNeighbours().mapNotNull { day09.SmokeBasin.heights[it] }.all { day09.SmokeBasin.heights[this]!! < it }

    private fun parse(lines: List<String>): Map<Coordinate, Int> = lines.mapIndexed { x, heights ->
        heights.chunked(1).mapIndexed { y, height -> Pair(Coordinate(x, y), height.toInt()) }
    }.flatten().toMap()

    override fun run() {
        println("Day 9, sum of risk levels: ${sumOfRiskLevels()}")
        println("Day 9, product of three largest basin sizes: ${productOfThreeLargestBasinSizes()}")
    }
}
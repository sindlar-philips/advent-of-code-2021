package day11

import Coordinate
import PuzzleData

object Day11 {

    private val grid = PuzzleData.load("/day11/day11.txt") { parse(it) }

    fun countFlashesAfter100Steps(): Int {
        fun countFlashes(steps: Int, grid: Map<Coordinate, Int>, acc: Int): Int {
            if (steps == 0) return acc
            val updated = step(grid)
            val flashes = updated.filterValues { it == 0 }.count()
            return countFlashes(steps - 1, updated, acc + flashes)
        }
        return countFlashes(100, grid, 0)
    }

    fun findFirstSynchronizedFlashStep(): Int {
        fun search(grid: Map<Coordinate, Int>, n: Int): Int =
            if (grid.all { (_, v) -> v == 0 }) n
            else search(step(grid), n + 1)
        return search(grid, 0)
    }

    private fun step(grid: Map<Coordinate, Int>): Map<Coordinate, Int> {
        val energyIncreased: Map<Coordinate, Int> = grid.mapValues { (_, v) -> v + 1 }
        val afterFlash = flash(energyIncreased, setOf())
        return afterFlash.mapValues { (_, v) -> if (v > 9) 0 else v }
    }

    private fun flash(grid: Map<Coordinate, Int>, haveFlashed: Set<Coordinate>): Map<Coordinate, Int> {
        val nowFlashing = grid.filter { it.value > 9 }.keys.filterNot { haveFlashed.contains(it) }
        if (nowFlashing.isEmpty()) return grid
        val energized = nowFlashing.flatMap { it.allNeighbours() }.filter { grid.containsKey(it) }
        val extraEnergy = energized.groupingBy { it }.eachCount()
        val afterFlash = grid.mapValues { (k, v) -> v + (extraEnergy[k] ?: 0) }
        return flash(afterFlash, haveFlashed + nowFlashing)
    }

    private fun parse(lines: List<String>): Map<Coordinate, Int> = lines.mapIndexed { x, energyLevels ->
        energyLevels.chunked(1).mapIndexed { y, level -> Pair(Coordinate(x, y), level.toInt()) }
    }.flatten().toMap()
}
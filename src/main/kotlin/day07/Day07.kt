package day07

import PuzzleData
import kotlin.math.abs

object Day07 : Runnable {

    private val positions = PuzzleData.load("/day07/day07.txt") { parsePositions(it) }

    fun minimumFuelRequired(): Int = minimumFuelRequired(false)

    fun minimumFuelRequiredWeighted(): Int = minimumFuelRequired(true)

    private fun minimumFuelRequired(weighted: Boolean): Int {
        val min = positions.minOrNull() ?: throw Error("Expecting position")
        val max = positions.maxOrNull() ?: throw Error("Expecting position")
        val f = if (weighted) { steps: Int -> weightedSteps(steps, 1, 0) }
        else { steps: Int -> steps }
        return (min..max).minOfOrNull { target -> calculateFuel(target, f) }
            ?: throw Error("Expecting value!")
    }

    private fun calculateFuel(target: Int, f: (Int) -> Int): Int {
        return positions.sumOf { f(abs(it - target)) }
    }

    private fun weightedSteps(steps: Int, weight: Int, acc: Int): Int =
        if (steps == 0) acc
        else weightedSteps(steps - 1, weight + 1, acc + weight)

    private fun parsePositions(lines: List<String>): List<Int> =
        lines[0].split(",").map { it.toInt() }

    override fun run() {
        println("Day 7, minimum fuel required: ${minimumFuelRequired()}")
        println("Day 7, minimum fuel required (weighted): ${minimumFuelRequiredWeighted()}")
    }
}
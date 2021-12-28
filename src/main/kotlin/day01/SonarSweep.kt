package day01

import PuzzleData

object SonarSweep : Runnable {

    private val measurements = PuzzleData.load("/day01/sonar-sweep-report.txt") { parseMeasurements(it) }

    fun countIncreases(): Int =
        // Initializing "previous" with first element in list because it is not counted
        countIncreases(measurements, measurements[0], 0)

    fun countIncreasesWindowed(): Int {
        val windowedMeasurements = measurements.windowed(3, 1).map{ it.sum() }
        return countIncreases(windowedMeasurements, windowedMeasurements[0], 0)
    }

    private fun countIncreases(measurements: List<Int>, previous: Int, count: Int): Int {
        if (measurements.isEmpty()) return count

        val current = measurements[0]
        val tail = measurements.drop(1)
        val updatedCount = if (current > previous) count + 1 else count
        return countIncreases(tail, current, updatedCount)
    }

    private fun parseMeasurements(lines: List<String>): List<Int> =
        lines.map { l -> l.toInt() }

    override fun run() {
        println("Day 1, number of increases: ${countIncreases()}")
        println("Day 1, number of increases (windowed): ${countIncreasesWindowed()}")
    }
}
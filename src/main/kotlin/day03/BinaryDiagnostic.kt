package day03

import PuzzleData

object BinaryDiagnostic : Runnable {

    private val binaryNumbers = PuzzleData.load("/day03/diagnostic-report.txt") { parseBinaryNumbers(it) }

    fun calculatePowerConsumption(): Int {
        val gammaRate = getGammaRate(binaryNumbers)
        val epsilonRate = invertBits(gammaRate)
        return bitsToInt(gammaRate) * bitsToInt(epsilonRate)
    }

    private fun getGammaRate(binaryNumbers: List<List<Int>>): List<Int> {
        val sumOfBits: List<Int> = binaryNumbers.reduce { l1, l2 -> l1.mapIndexed { index, i -> i + l2[index] } }
        val toMostFrequentBit: (Int) -> Int = { n: Int -> if (n.toDouble() >= binaryNumbers.size / 2.0) 1 else 0 }
        return sumOfBits.map { toMostFrequentBit(it) }
    }

    private fun invertBits(binaryNumber: List<Int>): List<Int> = binaryNumber.map { n -> if (n == 0) 1 else 0 }

    private fun bitsToInt(l: List<Int>): Int = l.fold("") { a, b -> a + b }.toInt(2)

    fun calculateLifeSupportRating(): Int {
        val oxygenGeneratorRating = getOxygenGeneratorRating()
        val co2ScrubberRating = getCo2ScrubberRating()
        return bitsToInt(oxygenGeneratorRating) * bitsToInt(co2ScrubberRating)
    }

    private fun getOxygenGeneratorRating(): List<Int> =
        getInputForLifeSupportRating({ getGammaRate(it) }, binaryNumbers, 0)

    private fun getCo2ScrubberRating(): List<Int> =
        getInputForLifeSupportRating({ invertBits(getGammaRate(it)) }, binaryNumbers, 0)

    private fun getInputForLifeSupportRating(
        rate: (l: List<List<Int>>) -> List<Int>, binaryNumbers: List<List<Int>>, position: Int
    ): List<Int> {
        if (binaryNumbers.size == 1) return binaryNumbers[0] else if (binaryNumbers.isEmpty()) throw Error("Not expecting empty list here!")
        val rating = rate(binaryNumbers)
        val filteredNumbers = binaryNumbers.filter { it[position] == rating[position] }
        return getInputForLifeSupportRating(rate, filteredNumbers, position + 1)
    }

    private fun parseBinaryNumbers(lines: List<String>): List<List<Int>> =
        lines.map { s -> s.chunked(1).map { c -> c.toInt() }.toList() }

    override fun run() {
        println("Day 3, power consumption: ${calculatePowerConsumption()}")
        println("Day 3, life support rating: ${calculateLifeSupportRating()}")
    }
}
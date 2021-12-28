package day08

import PuzzleData

data class Displays(val signals: List<String>, val outputs: List<String>)

object Day8 : Runnable {

    private val displays = PuzzleData.load("/day08/day08.txt") { parseDisplays(it) }

    fun countUniqueOutputs(): Int = displays.map {
        it.outputs.filter { o -> o.length == 2 || o.length == 3 || o.length == 4 || o.length == 7 }
    }.sumOf { it.size }

    fun fixWiringAndSumValues(): Int = displays.map { digit ->
        val wiring = determineWiring(digit.signals)
        val fixedOutputs = digit.outputs.map { output ->
            fixWiring(output, wiring)
        }
        fixedOutputs.map { getDisplayValue(it) }.reduce { a, b -> a + b }
    }.sumOf { it.toInt() }

    private fun fixWiring(signal: String, mapping: Map<String, String>): String = signal.toCharArray().map {
        mapping[it.toString()] ?: throw Error("Expecting mapping!")
    }.sorted().reduce { a, b -> a + b }

    internal fun String.minusChars(that: String): String =
        String(this.toCharArray().filterNot { that.toCharArray().contains(it) }.toCharArray())

    internal fun determineWiring(signals: List<String>): Map<String, String> {
        val one = signals.single { it.length == 2 }
        val four = signals.single { it.length == 4 }
        val seven = signals.single { it.length == 3 }
        val eight = signals.single { it.length == 7 }
        val a = seven.minusChars(one)
        val (e, g) = determineEG(signals, four, a)
        val nine = determineNine(signals, e)
        val (c, d, f) = determineCDF(signals, nine, one)
        val b = eight.minusChars(a + c + d + e + f + g)
        return mapOf(a to "a", b to "b", c to "c", d to "d", e to "e", f to "f", g to "g")
    }

    private fun determineEG(signals: List<String>, four: String, a: String): Pair<String, String> {
        val zeroSixOrNine = signals.filter { it.length == 6 }
        val eOrG = zeroSixOrNine.map { it.minusChars(four).minusChars(a) }
        val g = eOrG.single { it.length == 1 }
        val e = eOrG.first { it.length == 2 }.minusChars(g)
        return Pair(e, g)
    }

    private fun determineNine(signals: List<String>, e: String): String {
        val zeroSixOrNine = signals.filter { it.length == 6 }
        return zeroSixOrNine.filterNot { it.contains(e) }.single()
    }

    private fun determineCDF(signals: List<String>, nine: String, one: String): Triple<String, String, String> {
        val zeroSixOrNine = signals.filter { it.length == 6 }
        val zeroOrSix = zeroSixOrNine.map { it }.filterNot { it == nine }
        val cOrD = zeroOrSix[0].minusChars(zeroOrSix[1]) + zeroOrSix[1].minusChars(zeroOrSix[0])
        val d = cOrD.minusChars(one)
        val c = cOrD.minusChars(d)
        val f = one.minusChars(c)
        return Triple(c, d, f)
    }

    private fun getDisplayValue(signal: String): String = when (signal) {
        "abcefg" -> "0"
        "cf" -> "1"
        "acdeg" -> "2"
        "acdfg" -> "3"
        "bcdf" -> "4"
        "abdfg" -> "5"
        "abdefg" -> "6"
        "acf" -> "7"
        "abcdefg" -> "8"
        "abcdfg" -> "9"
        else -> throw Error("Unknown signal: $signal!")
    }

    private fun normalize(signal: String) = String(signal.toCharArray().sorted().toCharArray())

    internal fun parseDisplays(lines: List<String>): List<Displays> =
        lines.map { it.split(" | ") }.map { signalsAndOutputs ->
            val signals: List<String> = signalsAndOutputs[0].split(" ").map { normalize(it) }
            val outputs: List<String> = signalsAndOutputs[1].split(" ").map { normalize(it) }
            Displays(signals, outputs)
        }

    override fun run() {
        println("Day 8, count unique outputs: ${countUniqueOutputs()}")
        println("Day 8, summed values after fixing wiring: ${fixWiringAndSumValues()}")
    }
}
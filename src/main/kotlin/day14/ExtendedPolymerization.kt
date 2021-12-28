package day14

import PuzzleData
import java.util.stream.Collectors

object ExtendedPolymerization : Runnable {

    private val polymer = PuzzleData.load("/day14/polymer-template.txt") { parsePolymer(it) }
    private val rules = PuzzleData.load("/day14/insertion-rules.txt") { parseRules(it) }

    /**
     * Creates new polymer strings according to the production rules,
     * counts the number of times individual elements occur,
     * and returns the difference between max. and min. occurrence.
     */
    fun createPolymerAndCount(steps: Int): Int {
        fun apply(step: Int, polymer: String): String =
            if (step == 0) polymer else apply(step - 1, applyRules(polymer))

        val finalPolymer = apply(steps, polymer)
        val elementCount = finalPolymer.groupingBy { it }.eachCount()
        return elementCount.maxOf { it.value } - elementCount.minOf { it.value }
    }

    /**
     * Applies the production rules by iterating over the given string, looking at pairs of elements (window size = 2)
     * and moving along one element at a time (step size = 1). For every pair, the mapping function returns the first
     * element of the pair plus the output for that pair according to its production rule. Then all such
     * strings are concatenated, and finally the last element of the input polymer is attached at the end.
     *
     * Example:
     *   input: NNCB
     *   rules: NN -> C, NC -> B, CB -> H
     *   first application step:
     *     pairs:   (N,N), (N,C), (C,B)
     *     mapping: N+C    N+B    C+H
     *     concatenate and add last character: NCNBCHB
     */
    private fun applyRules(polymer: String): String =
        polymer.chunked(1).windowed(2, 1).parallelStream().map { s ->
            val output = rules[s[0] + s[1]]!!
            s[0] + output
        }.collect(Collectors.toList()).reduce { s1, s2 -> s1 + s2 } + polymer.last()

    /**
     * This function does not create the actual polymer strings, but just counts how frequently
     * specific pairs occur in the string. For this a map from pair to occurrence is used.
     * An evolution function produces a new map from a given map, based on the production rules.
     * Pitfall is that a specific pair could be produced from different sources; see below merge function.
     * Also in this case we count the first element from every pair for the final outcome,
     * and add the last character of the polymer string separately.
     */
    fun justCount(steps: Int): Long {
        fun evolve(steps: Int, map: Map<String, Long>): Map<String, Long> =
            if (steps == 0) map else {
                val evolved = map.flatMap { (key, value) ->
                    val pair1 = key[0] + rules[key]!!
                    val pair2 = rules[key]!! + key[1]
                    listOf(pair1 to value, pair2 to value)
                }
                evolve(steps - 1, merge(evolved))
            }

        val initial = merge(polymer.chunked(1).windowed(2, 1).map { it[0] + it[1] to 1L })
        val evolved = evolve(steps, initial)
        val pairs = evolved.map { (k: String, v: Long) -> k.first().toString() to v } +
                (polymer.last().toString() to 1L)
        val final = merge(pairs)
        return final.maxOf { it.value } - final.minOf { it.value }
    }

    /**
     * This function reflects that specific pairs can originate from different sources. For example, if we have
     * a pair (A,A) and a pair (A,B) along with production rules AA -> C and AB -> C, then we get two occurrences
     * of AC in the output. Therefore, we cannot simply associate elements from the input map into a new map,
     * but need to merge these different occurrences together before creating the new map.
     */
    private fun merge(pairs: List<Pair<String, Long>>): Map<String, Long> =
        pairs.groupBy { it.first }.map { (k: String, pairs: List<Pair<String, Long>>) ->
            k to pairs.map { it.second }.reduce { v1, v2 -> v1 + v2 }
        }.toMap()

    private fun parseRules(lines: List<String>): Map<String, String> =
        lines.associate {
            val parts = it.split(" -> ")
            parts[0] to parts[1]
        }

    private fun parsePolymer(lines: List<String>): String = lines.single()

    override fun run() {
        println("Day 14, create polymer and count: ${createPolymerAndCount(10)}")
        println("Day 14, just count: ${justCount(40)}")
    }
}
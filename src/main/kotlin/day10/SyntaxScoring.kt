package day10

import PuzzleData

class InvalidCharacterException(val invalidCharacter: Char) : Exception("Invalid character: $invalidCharacter")

object SyntaxScoring : Runnable {

    private val lines = PuzzleData.load("/day10/navigation-subsystem.txt") { parse(it) }
    private val validChars = setOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))

    fun sumFirstIllegalChars(): Int =
        lines.mapNotNull {
            try {
                complete(it, listOf())
                null
            } catch (e: InvalidCharacterException) {
                e.invalidCharacter
            }
        }.map {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw InvalidCharacterException(it)
            }
        }.sum()

    fun getMiddleCompletionScore(): Long {
        val completionStrings = lines.mapNotNull {
            try {
                complete(it, listOf())
            } catch (_: InvalidCharacterException) {
                null
            }
        }
        val scores = completionStrings.map { scoreCompletionString(it, 0) }.sorted()
        return scores[(scores.size - 1) / 2]
    }

    private fun complete(chars: List<Char>, activeScope: List<Char>): List<Char> =
        if (chars.isEmpty()) activeScope.reversed().map { getClosing(it) }
        else {
            val current = chars.first()
            if (activeScope.isEmpty()) {
                if (isClosing(current)) throw InvalidCharacterException(current)
                else complete(chars.drop(1), activeScope + current)
            } else if (isClosing(current)) {
                if (!isValidPair(activeScope.last(), current)) throw InvalidCharacterException(current)
                else complete(chars.drop(1), activeScope.dropLast(1))
            } else {
                complete(chars.drop(1), activeScope + current)
            }
        }

    private fun scoreCompletionString(chars: List<Char>, current: Long): Long =
        if (chars.isEmpty()) current
        else {
            val charScore = when (chars.first()) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> throw Error("Unexpected character: ${chars.first()}")
            }
            scoreCompletionString(chars.drop(1), (current * 5) + charScore)
        }

    private fun getClosing(char: Char): Char = validChars.filter { it.first == char }.map { it.second }.single()

    private fun isClosing(char: Char): Boolean = validChars.any { it.second == char }

    private fun isValidPair(some: Char, other: Char): Boolean = validChars.contains(Pair(some, other))

    private fun parse(lines: List<String>): List<List<Char>> = lines.map { it.toCharArray().asList() }

    override fun run() {
        println("Day 10, sum of first illegal characters: ${sumFirstIllegalChars()}")
        println("Day 10, middle completion score: ${getMiddleCompletionScore()}")
    }
}
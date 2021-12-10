object Day10 {

    private val lines = PuzzleData.load("/day10.txt") { parse(it) }
    private val validChars = setOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))

    fun sumFirstIllegalChars(): Int {
        val map = lines.mapNotNull { findFirstIllegal(it, listOf()) }
            .map {
                when (it) {
                    ')' -> 3
                    ']' -> 57
                    '}' -> 1197
                    '>' -> 25137
                    else -> throw Error("Unexpected character!")
                }
            }
        return map.sum()
    }

    fun getMiddleCompletionScore(): Long {
        val incompleteStrings = lines.filter { findFirstIllegal(it, listOf()) == null }
        val completionStrings = incompleteStrings.map { findCompletion(it, listOf(), listOf()) }
        val scores = completionStrings.map { scoreCompletionString(it, 0) }.sorted()
        return scores[(scores.size - 1) / 2]
    }

    private fun findFirstIllegal(chars: List<Char>, activeScope: List<Char>): Char? =
        if (chars.isEmpty()) null
        else {
            val current = chars.first()
            if (activeScope.isEmpty()) {
                if (isClosing(current)) current
                else findFirstIllegal(chars.drop(1), activeScope + current)
            } else if (isClosing(current)) {
                if (!isValidPair(activeScope.last(), current)) current
                else findFirstIllegal(chars.drop(1), activeScope.dropLast(1))
            } else {
                findFirstIllegal(chars.drop(1), activeScope + current)
            }
        }

    private fun findCompletion(chars: List<Char>, activeScope: List<Char>, completion: List<Char>): List<Char> =
        if (chars.isEmpty()) completion
        else {
            val current = chars.last()
            if (activeScope.isEmpty()) {
                if (isOpening(current))
                    findCompletion(chars.dropLast(1), activeScope, completion + getClosing(current))
                else findCompletion(chars.dropLast(1), listOf(current) + activeScope, completion)
            } else {
                if (isValidPair(current, activeScope.first()))
                    findCompletion(chars.dropLast(1), activeScope.drop(1), completion)
                else findCompletion(chars.dropLast(1), listOf(current) + activeScope, completion)
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

    private fun isOpening(char: Char): Boolean = validChars.any { it.first == char }

    private fun isClosing(char: Char): Boolean = validChars.any { it.second == char }

    private fun isValidPair(some: Char, other: Char): Boolean = validChars.contains(Pair(some, other))

    private fun parse(lines: List<String>): List<List<Char>> = lines.map { it.toCharArray().asList() }
}
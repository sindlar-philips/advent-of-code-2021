import kotlin.math.ceil
import kotlin.math.floor

object Day18 {

    private val data = PuzzleData.load("/day18.txt") { parse(it) }

    interface SnailFishNumber {
        fun magnitude(): Long
    }

    data class SnailFishLiteral(val value: Long) : SnailFishNumber {
        override fun magnitude(): Long = value
    }

    data class SnailFishPair(
        val left: SnailFishNumber,
        val right: SnailFishNumber
    ) : SnailFishNumber {
        override fun magnitude(): Long = 3 * left.magnitude() + 2 * right.magnitude()
    }

    fun doHomeworkPart1(): Long {
        val summed = snailFishSum(data)
        val number = parseSnailFishNumber(summed)
        return number.magnitude()
    }

    fun doHomeworkPart2(): Long {
        val snailFishNumbers = data.flatMap { n1 ->
            data.mapNotNull { n2 ->
                if (n1 != n2) {
                    val summed = snailFishSum(listOf(n1, n2))
                    parseSnailFishNumber(summed)
                } else null
            }
        }
        return snailFishNumbers.maxOf { it.magnitude() }
    }

    internal fun parseSnailFishNumber(string: String): SnailFishNumber {
        if (string.length == 1) return SnailFishLiteral(string.toLong())
        val pairs = splitSnailFishNumber(string)
        return SnailFishPair(parseSnailFishNumber(pairs.first), parseSnailFishNumber(pairs.second))
    }

    internal fun splitSnailFishNumber(string: String): Pair<String, String> {
        tailrec fun splitIt(string: String, nesting: Int, acc: String): Pair<String, String> =
            if (nesting == 0 && acc.isNotEmpty()) {
                if (string.startsWith(",")) Pair(acc, string.drop(1))
                else Pair(acc, string)
            } else {
                when (val first = string.first()) {
                    '[' -> splitIt(string.drop(1), nesting + 1, acc + first)
                    ']' -> splitIt(string.drop(1), nesting - 1, acc + first)
                    else -> splitIt(string.drop(1), nesting, acc + first)
                }
            }

        val subString = string.substring(1, string.length - 1)
        return splitIt(subString, 0, "")
    }

    internal fun reduce(number: String): String {
        val exploded = explode(number, "", 0)
        if (exploded != number) return reduce(exploded)
        val split = split(exploded)
        if (split != exploded) return reduce(split)
        return split
    }

    internal fun explode(right: String, left: String, depth: Int): String {
        if (right.isEmpty()) return left
        val pairRegex = "^([\\d]+),([\\d]+).*".toRegex()
        val matches = right.matches(pairRegex)
        return if (matches && depth > 4) {
            val (pairLeft, pairRight) = pairRegex.find(right)!!.destructured
            val rightRemaining = right.drop(pairLeft.length + pairRight.length + 1)
            val leftExploded = explodeLeft(left, pairLeft.toInt())
            val rightExploded = explodeRight(rightRemaining, pairRight.toInt())
            leftExploded.dropLast(1) + "0" + rightExploded.drop(1)
        } else if (right.first() == '[') explode(right.drop(1), left + right.first(), depth + 1)
        else if (right.first() == ']') explode(right.drop(1), left + right.first(), depth - 1)
        else explode(right.drop(1), left + right.first(), depth)
    }

    internal fun explodeLeft(left: String, value: Int): String {
        val numberRegex = "([\\d]+)".toRegex()
        val matchResult = numberRegex.findAll(left).toList()
        if (matchResult.isEmpty()) return left
        val lastMatch = matchResult.last().value
        val exploded = lastMatch.toInt() + value
        val index = left.lastIndexOf(lastMatch)
        return left.substring(0, index) + exploded + left.substring(index + lastMatch.length)
    }

    internal fun explodeRight(right: String, value: Int): String {
        val numberRegex = "([\\d]+)".toRegex()
        val matchResult = numberRegex.findAll(right).toList()
        if (matchResult.isEmpty()) return right
        val firstMatch = matchResult.first().value
        val exploded = firstMatch.toInt() + value
        val index = right.indexOf(firstMatch)
        return right.substring(0, index) + exploded + right.substring(index + firstMatch.length)
    }

    internal fun split(number: String): String {
        val numberRegex = "\\d\\d".toRegex()
        val matchResult = numberRegex.findAll(number).toList()
        if (matchResult.isEmpty()) return number
        val firstMatch = matchResult.first().value
        val value = firstMatch.toDouble()
        val left = floor(value / 2).toLong()
        val right = ceil(value / 2).toLong()
        val split = "[${left.toInt()},${right.toInt()}]"
        val index = number.indexOf(firstMatch)
        return number.substring(0, index) + split + number.substring(index + firstMatch.length)
    }

    internal fun snailFishSum(numbers: List<String>): String =
        numbers.reduce { n1, n2 -> reduce("[${reduce(n1)},${reduce(n2)}]") }

    private fun parse(lines: List<String>): List<String> = lines
}
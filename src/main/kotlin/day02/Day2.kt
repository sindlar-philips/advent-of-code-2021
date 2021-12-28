package day02

import PuzzleData

object Day2 : Runnable {

    private val commands = PuzzleData.load("/day02/day2.txt") { parseCommands(it) }

    fun determinePosition(): Pair<Int, Int> =
        determinePosition(commands, Triple(0, 0, 0))

    private fun determinePosition(
        commands: List<Pair<String, Int>>, currentPosition: Triple<Int, Int, Int>
    ): Pair<Int, Int> {
        if (commands.isEmpty()) return Pair(currentPosition.first, currentPosition.second)
        val (direction, units) = commands[0]
        val tail = commands.drop(1)
        return determinePosition(tail, updatePosition(currentPosition, direction, units))
    }

    private fun updatePosition(
        currentPosition: Triple<Int, Int, Int>, direction: String, units: Int
    ): Triple<Int, Int, Int> {
        val horizontal = currentPosition.first
        val depth = currentPosition.second
        val aim = currentPosition.third
        return when (direction) {
            "up" -> Triple(horizontal, depth, aim - units)
            "down" -> Triple(horizontal, depth, aim + units)
            "forward" -> Triple(horizontal + units, depth + (units * aim), aim)
            else -> throw NotImplementedError("No support for direction: $direction")
        }
    }

    private fun parseCommands(lines: List<String>): List<Pair<String, Int>> =
        lines.map { l ->
            val split = l.split(" ")
            val direction = split[0]
            val units = split[1].toInt()
            Pair(direction, units)
        }

    override fun run() {
        println("Day 2, position (with aim): ${determinePosition()}")
    }
}
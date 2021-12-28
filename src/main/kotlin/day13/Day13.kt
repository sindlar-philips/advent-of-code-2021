package day13

import PuzzleData
import day04.Coordinate

typealias FoldingInstruction = (dots: Set<Coordinate>) -> Set<Coordinate>

object Day13 : Runnable {

    private val dots = PuzzleData.load("/day13/day13-dots.txt") { parseDots(it) }
    private val instructions = PuzzleData.load("/day13/day13-instructions.txt") { parseInstructions(it) }

    fun countVisibleDotsAfterFirstFold(): Int =
        instructions.first()(dots).count()

    fun foldAndPrint() {
        val folded = instructions.fold(dots) { dots, instruction -> instruction(dots) }
        print(folded)
    }

    private fun print(dots: Set<Coordinate>) {
        for (y in 0..dots.maxOf { it.y }) {
            for (x in 0..dots.maxOf { it.x }) {
                print(if (dots.contains(Coordinate(x, y))) "#" else " ")
            }
            print("\n")
        }
    }

    private fun verticalFold(dots: Set<Coordinate>, x: Int): Set<Coordinate> {
        val left = dots.filter { it.x < x }
        val rightFolded = dots.filter { it.x > x }.map { Coordinate(it.x - ((it.x - x) * 2), it.y) }
        return left.plus(rightFolded).toSet()
    }

    private fun horizontalFold(dots: Set<Coordinate>, y: Int): Set<Coordinate> {
        val top = dots.filter { it.y < y }
        val bottomFolded = dots.filter { it.y > y }.map { Coordinate(it.x, it.y - ((it.y - y) * 2)) }
        return top.plus(bottomFolded).toSet()
    }

    private fun parseDots(lines: List<String>): Set<Coordinate> =
        lines.map {
            val split = it.split(",")
            Coordinate(split[0].toInt(), split[1].toInt())
        }.toSet()

    private fun parseInstructions(lines: List<String>): List<FoldingInstruction> =
        lines.map {
            val parts = it.substringAfter("fold along ").split("=")
            val axis = parts[0]
            val line = parts[1].toInt()
            when (axis) {
                "x" -> { dots: Set<Coordinate> -> verticalFold(dots, line) }
                "y" -> { dots: Set<Coordinate> -> horizontalFold(dots, line) }
                else -> throw IllegalArgumentException("Not a folding instruction: $it")
            }
        }

    override fun run() {
        println("Day 13, visible dots after first fold: ${countVisibleDotsAfterFirstFold()}")
        println("Day 13, visible dots after folding (printed):")
        foldAndPrint()
    }
}
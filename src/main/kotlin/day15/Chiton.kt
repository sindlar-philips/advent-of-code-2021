package day15

import PuzzleData
import day04.Coordinate
import java.util.*
import kotlin.math.abs

class DeadEndException : Exception("Dead end!")

data class Node<T>(
    val value: T, val parent: Node<T>?, val cost: Int, val estimation: Int, val f: Int = cost + estimation
)

class AStar<T>(
    private val estimate: (t: T) -> Int,
    private val getNextValuesAndCost: (current: T) -> List<Pair<T, Int>>
) {

    private val open = PriorityQueue<Node<T>> { n1, n2 -> (n1.f).compareTo(n2.f) }
    private val closed = mutableSetOf<T>()

    fun search(start: T, goal: T): Node<T> {
        initialize(start)
        while (open.isNotEmpty()) {
            val bestCandidate = open.minByOrNull { it.f }!!
            if (bestCandidate.value == goal)
                return bestCandidate
            close(bestCandidate)
        }
        throw DeadEndException()
    }

    private fun close(candidate: Node<T>) {
        open.remove(candidate)
        closed.add(candidate.value)
        val openOptions = getNextValuesAndCost(candidate.value).filterNot { closed.contains(it.first) }
        openOptions.forEach { (nextValue, cost) ->
            val next = Node(nextValue, candidate, candidate.cost + cost, estimate(nextValue))
            val existing = open.firstOrNull { it.value == nextValue }
            if (existing == null)
                open.add(next)
            else if (next.cost < existing.cost) {
                open.remove(existing)
                open.add(next)
            }
        }
    }

    private fun initialize(start: T) {
        open.clear()
        closed.clear()
        val startingNode = Node(start, null, 0, estimate(start))
        open.add(startingNode)
    }
}

object Chiton : Runnable {

    private val riskLevels = PuzzleData.load("/day15/risk-levels.txt") { parse(it) }

    fun leastRiskyPath(): Int = findLeastRiskyPath(riskLevels)

    fun leastRiskyPathExtended(): Int = findLeastRiskyPath(extendedRiskLevels())

    private fun findLeastRiskyPath(riskLevels: Map<Coordinate, Int>): Int {
        val start = Coordinate(0, 0)
        val goal = Coordinate(riskLevels.maxOf { it.key.x }, riskLevels.maxOf { it.key.y })
        val estimate = { c: Coordinate -> manhattanDistance(c, goal) }
        val getNextValuesAndCost = { c: Coordinate ->
            c.directNeighbours().filter { riskLevels.containsKey(it) }
                .map { Pair(it, riskLevels[it]!!) }
        }
        val aStar = AStar(estimate, getNextValuesAndCost)
        return aStar.search(start, goal).cost
    }

    private fun manhattanDistance(current: Coordinate, goal: Coordinate): Int =
        abs(current.x - goal.x) + abs(current.y - goal.y)

    private fun parse(lines: List<String>): Map<Coordinate, Int> = lines.mapIndexed { x, riskLevels ->
        riskLevels.chunked(1).mapIndexed { y, level -> Pair(Coordinate(x, y), level.toInt()) }
    }.flatten().toMap()

    private fun extendedRiskLevels(): Map<Coordinate, Int> {
        val times = 5
        val xMax = riskLevels.maxOf { it.key.x }
        val yMax = riskLevels.maxOf { it.key.y }
        return riskLevels.flatMap { (c, r) ->
            (0 until times).flatMap { xFactor ->
                (0 until times).map { yFactor ->
                    val updatedRisk = r + xFactor + yFactor
                    Coordinate(c.x + xFactor * (xMax + 1), c.y + yFactor * (yMax + 1)) to
                            if (updatedRisk > 9) updatedRisk % 9 else updatedRisk
                }
            }
        }.toMap()
    }

    override fun run() {
        println("Day 15, least risky path: ${leastRiskyPath()}")
        println("Day 15, least risky path (extended): ${leastRiskyPathExtended()}")
    }
}
package day15

import PuzzleData
import day04.Coordinate
import java.util.*
import kotlin.math.abs

class DeadEndException : Exception("Dead end!")

data class Node(val coordinate: Coordinate, val parent: Node?, val g: Int, val h: Int, val f: Int = g + h)

object Chiton : Runnable {

    private val riskLevels = PuzzleData.load("/day15/risk-levels.txt") { parse(it) }

    fun leastRiskyPath(): Int {
        val start = Coordinate(0, 0)
        val goal = Coordinate(riskLevels.maxOf { it.key.x }, riskLevels.maxOf { it.key.y })
        return aStar(start, goal, riskLevels).g
    }

    fun leastRiskyPathExtended(): Int {
        val extendedRiskLevels = extendRiskLevels(5)
        val start = Coordinate(0, 0)
        val goal = Coordinate(extendedRiskLevels.maxOf { it.key.x }, extendedRiskLevels.maxOf { it.key.y })
        return aStar(start, goal, extendedRiskLevels).g
    }

    private fun aStar(start: Coordinate, goal: Coordinate, riskLevels: Map<Coordinate, Int>): Node {
        val open = PriorityQueue<Node> { n1, n2 -> (n1.f).compareTo(n2.f) }
        open.add(Node(start, null, 0, h(start, goal)))
        val closed = mutableSetOf<Coordinate>()
        while (open.isNotEmpty()) {
            val bestCandidate = open.minByOrNull { it.f }!!
            if (bestCandidate.coordinate == goal) return bestCandidate
            open.remove(bestCandidate)
            closed.add(bestCandidate.coordinate)
            val neighbours = bestCandidate.coordinate.directNeighbours().filter { riskLevels.containsKey(it) }
            neighbours.forEach { nb ->
                if (!closed.contains(nb)) {
                    val new = Node(nb, bestCandidate, bestCandidate.g + riskLevels[nb]!!, h(nb, goal))
                    val old = open.firstOrNull { old -> old.coordinate == nb }
                    if (old == null) open.add(new)
                    else {
                        if (new.g < old.g) {
                            open.remove(old)
                            open.add(new)
                        }
                    }
                }
            }
        }
        throw DeadEndException()
    }

    private fun h(current: Coordinate, goal: Coordinate): Int = abs(current.x - goal.x) + abs(current.y - goal.y)

    private fun parse(lines: List<String>): Map<Coordinate, Int> = lines.mapIndexed { x, riskLevels ->
        riskLevels.chunked(1).mapIndexed { y, level -> Pair(Coordinate(x, y), level.toInt()) }
    }.flatten().toMap()

    private fun extendRiskLevels(times: Int): Map<Coordinate, Int> {
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
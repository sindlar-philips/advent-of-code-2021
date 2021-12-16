import kotlin.math.abs

object Day15 {

    private val riskLevels = PuzzleData.load("/day15.txt") { parse(it) }

    class DeadEndException : Exception("Dead end!")

    data class Node(val coordinate: Coordinate, val parent: Node?, val g: Int, val h: Int, val f: Int = g + h)

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
        val open = mutableSetOf(Node(start, null, 0, h(start, goal)))
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
                    val existing = open.filter { old -> old.coordinate == nb }
                    if (existing.isEmpty()) open.add(new)
                    else {
                        val old = existing.single()
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
}
package day23

import Coordinate
import kotlin.math.max
import kotlin.math.min

object Day23 {

    class DeadEndException : Exception("Dead end!")

    data class Node(val state: State, val parent: Node?, val cost: Int, val estimation: Int)

    fun getStates(node: Node): List<State> {
        return if (node.parent == null) listOf(node.state)
        else getStates(node.parent) + listOf(node.state)
    }

    abstract class Amphipod(val energy: Int, val roomNumber: Int) {
        override fun equals(other: Any?): Boolean {
            return if (other == null) false else this::class == other::class
        }

        override fun toString(): String = this.javaClass.simpleName
        override fun hashCode(): Int {
            var result = energy
            result = 31 * result + roomNumber
            return result
        }
    }

    class A : Amphipod(1, 1)
    class B : Amphipod(10, 2)
    class C : Amphipod(100, 3)
    class D : Amphipod(1000, 4)

    data class State(val board: Map<Coordinate, Amphipod?>) {

        override fun toString(): String =
            board.filterValues { it != null }.map { (c, a) -> "(${c.x}, ${c.y}) -> $a" }.joinToString(", ")

        /**
         * Returns an evaluation of the state (lower number is better) for the benefit of A*.
         * The evaluation is calculated as follows:
         *   (total number of room cells MINUS number of amphipods in the correct location)
         *     * (number of amphipods in rooms MINUS number of amphipods in the correct location PLUS one)
         * This evaluation gives preference to states that have more amphipods in the correct location, and
         * favors states that have amphipods outside of rooms over those where they are in the wrong room.
         */
        fun evaluate(): Int {
            fun countAmphipodsInPlace(l: List<Amphipod>, roomNumber: Int, acc: Int): Int =
                if (l.isEmpty() || l.first().roomNumber != roomNumber) acc
                else countAmphipodsInPlace(l.drop(1), roomNumber, acc + 1)
            return (1..4).sumOf { n ->
                val room = room(n).toList()
                val amphipodsFromBottom = room.sortedByDescending { (c, _) -> c.y }.mapNotNull { (_, v) -> v }
                val amphipodsInPlace = countAmphipodsInPlace(amphipodsFromBottom, n, 0)
                (room.size - amphipodsInPlace) * (amphipodsFromBottom.size - amphipodsInPlace + 1)
            }
        }

        /**
         * Returns possible moves as a pair of the resulting states and the cost of the move (i.e. the energy spent
         * by the moving amphipod. There are two sorts of moves, both of which require the amphipod to have a free path:
         *   - Move by an amphipod that is the "topmost" amphipod inside a room. Such a move is restricted in the sense
         *     that it cannot end on a "door" cell, i.e. just outside a room.
         *   - Move by an amphipod that is in the hall. Such a move can only be done when the amphipod is able to move
         *     into its own room, and that room is not inhabited by any amphipods that belong in another room.
         */
        fun moves(): List<Pair<State, Int>> {
            val moveableAmphipodsInRooms = getMoveableAmphipodsInRooms()
            val possibleTargetsInHall = hall().filterValues { it == null }.keys
            val toHall = moveableAmphipodsInRooms.flatMap { (current, amphipod) ->
                possibleTargetsInHall.mapNotNull { target ->
                    moveAmphipod(current, amphipod, target)
                }
            }
            val amphipodsInHall = hall().mapNotNull { (k, v) -> v?.let { k to v } }.toMap()
            val toRoom = amphipodsInHall.mapNotNull { (current, amphipod) ->
                val amphipodRoom = room(amphipod.roomNumber)
                val lowestFreeCell = amphipodRoom.filterValues { it == null }.maxByOrNull { (c, _) -> c.y }?.key
                if (lowestFreeCell == null) null
                else {
                    val hasRoomSameAmphipods = amphipodRoom.filterKeys { it.y > lowestFreeCell.y }.all { (_, v) ->
                        v!!.roomNumber == amphipod.roomNumber
                    }
                    if (!hasRoomSameAmphipods) null
                    else moveAmphipod(current, amphipod, lowestFreeCell)
                }
            }
            return toHall + toRoom
        }

        fun print() {
            val minX = board.keys.minOf { it.x }
            val maxX = board.keys.maxOf { it.x }
            val minY = board.keys.minOf { it.y }
            val maxY = board.keys.maxOf { it.y }
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    val key = Coordinate(x, y)
                    val value = if (!board.containsKey(key)) " " else board[key] ?: "."
                    print(value)
                }
                print("\n")
            }
            println()
        }

        private fun moveAmphipod(current: Coordinate, amphipod: Amphipod, target: Coordinate): Pair<State, Int>? {
            val cost = cost(amphipod, current, target)
            return if (cost == null) null
            else {
                val updatedMap = board.map { (c, v) ->
                    when (c) {
                        current -> c to null
                        target -> c to amphipod
                        else -> c to v
                    }
                }.toMap()
                Pair(State(updatedMap), cost)
            }
        }

        private fun cost(amphipod: Amphipod, current: Coordinate, target: Coordinate): Int? {
            val path = getPath(current, target)
            return if (!path.all { isFree(it) }) null
            else path.size * amphipod.energy
        }

        private fun getPath(start: Coordinate, end: Coordinate): List<Coordinate> {
            val minX = min(start.x, end.x)
            val maxX = max(start.x, end.x)
            val minY = min(start.y, end.y)
            val maxY = max(start.y, end.y)
            val xs = (minX..maxX).map { x -> Coordinate(x, minY) }
            val ys = (minY..maxY).map { y ->
                if (end.y > start.x) Coordinate(end.x, y) else Coordinate(start.x, y)
            }
            return (xs + ys).toSet().filterNot { it == start }
        }

        private fun isFree(coordinate: Coordinate) = board[coordinate] == null

        private fun getMoveableAmphipodsInRooms(): Map<Coordinate, Amphipod> =
            (1..4).flatMap { n ->
                val amphipods = room(n).filterValues { it != null }
                val moveableAmphipods = amphipods.filter { (coordinate, amphipod) ->
                    val onTop = room(n).filterKeys { it.y < coordinate.y }.all { (_, v) -> v == null }
                    if (!onTop) false
                    else {
                        amphipod!!.roomNumber != n || room(n).filterKeys { it.y > coordinate.y }
                            .any { (_, otherAmphipod) -> amphipod != otherAmphipod }
                    }
                }
                moveableAmphipods.mapNotNull { (k, v) -> v?.let { k to v } }
            }.toMap()

        private fun door(): Map<Coordinate, Amphipod?> =
            board.filterKeys { it.y == 0 && listOf(2, 4, 6, 8).contains(it.x) }

        private fun hall(): Map<Coordinate, Amphipod?> = board.filterKeys { it.y == 0 }.minus(door().keys)
        private fun room(room: Int): Map<Coordinate, Amphipod?> = board.filterKeys { it.x == room * 2 && it.y > 0 }
    }

    fun minimumEnergyForGroupingAmphipods(printSolutions: Boolean = false): Int {
        val start = State(initialMap())
        val goal = State(targetMap())
        val solution = aStar(start, goal)
        if (printSolutions) getStates(solution).forEach { it.print() }
        return solution.cost
    }

    private fun aStar(start: State, goal: State): Node {
        val open = mutableSetOf(Node(start, null, 0, start.evaluate()))
        val closed = mutableSetOf<State>()
        while (open.isNotEmpty()) {
            val bestCandidate = open.minByOrNull { it.cost + it.estimation }!!
            if (bestCandidate.state == goal) return bestCandidate
            open.remove(bestCandidate)
            closed.add(bestCandidate.state)
            val nextStates = bestCandidate.state.moves()
            nextStates.forEach { (ns, c) ->
                if (!closed.contains(ns)) {
                    val new = Node(ns, bestCandidate, bestCandidate.cost + c, ns.evaluate())
                    val existing = open.filter { old -> old.state == ns }
                    if (existing.isEmpty()) open.add(new)
                    else {
                        val old = existing.single()
                        if (new.cost < old.cost) {
                            open.remove(old)
                            open.add(new)
                        }
                    }
                }
            }
        }
        throw DeadEndException()
    }

    /**
     * #############
     * #...........#
     * ###C#D#A#B###
     *   #D#C#B#A#
     *   #D#B#A#C#
     *   #B#A#D#C#
     *   #########
     */
    private fun initialMap(): Map<Coordinate, Amphipod?> = mapOf(
        // Hallway
        Coordinate(0, 0) to null,
        Coordinate(1, 0) to null,
        Coordinate(2, 0) to null,
        Coordinate(3, 0) to null,
        Coordinate(4, 0) to null,
        Coordinate(5, 0) to null,
        Coordinate(6, 0) to null,
        Coordinate(7, 0) to null,
        Coordinate(8, 0) to null,
        Coordinate(9, 0) to null,
        Coordinate(10, 0) to null,
        // Room 1
        Coordinate(2, 1) to C(),
        Coordinate(2, 2) to D(),
        Coordinate(2, 3) to D(),
        Coordinate(2, 4) to B(),
        // Room 2
        Coordinate(4, 1) to D(),
        Coordinate(4, 2) to C(),
        Coordinate(4, 3) to B(),
        Coordinate(4, 4) to A(),
        // Room 3
        Coordinate(6, 1) to A(),
        Coordinate(6, 2) to B(),
        Coordinate(6, 3) to A(),
        Coordinate(6, 4) to D(),
        // Room 4
        Coordinate(8, 1) to B(),
        Coordinate(8, 2) to A(),
        Coordinate(8, 3) to C(),
        Coordinate(8, 4) to C()
    )

    /**
     * #############
     * #...........#
     * ###A#B#C#D###
     *   #A#B#C#D#
     *   #A#B#C#D#
     *   #A#B#C#D#
     *   #########
     */
    private fun targetMap(): Map<Coordinate, Amphipod?> = mapOf(
        // Hallway
        Coordinate(0, 0) to null,
        Coordinate(1, 0) to null,
        Coordinate(2, 0) to null,
        Coordinate(3, 0) to null,
        Coordinate(4, 0) to null,
        Coordinate(5, 0) to null,
        Coordinate(6, 0) to null,
        Coordinate(7, 0) to null,
        Coordinate(8, 0) to null,
        Coordinate(9, 0) to null,
        Coordinate(10, 0) to null,
        // Room 1
        Coordinate(2, 1) to A(),
        Coordinate(2, 2) to A(),
        Coordinate(2, 3) to A(),
        Coordinate(2, 4) to A(),
        // Room 2
        Coordinate(4, 1) to B(),
        Coordinate(4, 2) to B(),
        Coordinate(4, 3) to B(),
        Coordinate(4, 4) to B(),
        // Room 3
        Coordinate(6, 1) to C(),
        Coordinate(6, 2) to C(),
        Coordinate(6, 3) to C(),
        Coordinate(6, 4) to C(),
        // Room 4
        Coordinate(8, 1) to D(),
        Coordinate(8, 2) to D(),
        Coordinate(8, 3) to D(),
        Coordinate(8, 4) to D()
    )
}
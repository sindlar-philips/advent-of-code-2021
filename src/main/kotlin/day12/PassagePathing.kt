package day12

import PuzzleData
import java.util.stream.Collectors

data class Cave(private val name: String) {
    fun isEnd(): Boolean = name == "end"
    fun isLarge(): Boolean = !isSmall()
    fun isSmall(): Boolean = name.lowercase() == name
    fun isStart(): Boolean = name == "start"
    override fun toString(): String = name
}

data class Connection(val from: Cave, val to: Cave) {
    fun follows(other: Connection): Boolean = from == other.to
}

data class Path(val connections: List<Connection>) {

    fun accepts(connection: Connection): Boolean =
        if (!connection.follows(connections.last())) false
        else if (connection.to.isStart() || connection.from.isEnd()) false
        else if (connection.to.isEnd() || connection.to.isLarge()) true
        else {
            val visitsToSmallCaves = visitedCaves().filter { it.isSmall() }.groupingBy { it }.eachCount()
            !visitsToSmallCaves.containsKey(connection.to) ||
                    visitsToSmallCaves.values.none { it > 1 }
        }

    fun ended(): Boolean = connections.last().to.isEnd()

    private fun visitedCaves(): List<Cave> =
        connections.flatMap { if (it.from.isStart()) listOf(it.from, it.to) else listOf(it.to) }

    override fun toString() = "${visitedCaves()}"
}

object PassagePathing : Runnable {

    private val connections = PuzzleData.load("/day12/caves-map.txt") { parse(it) }

    fun countPaths(): Int {
        val starts = connections.filter { it.from.isStart() }
        val paths = starts.parallelStream().map { findPaths(it) }.collect(Collectors.toList()).flatten()
        return paths.count()
    }

    private fun findPaths(start: Connection): List<Path> {
        fun search(paths: List<Path>): List<Path> =
            if (paths.all { it.ended() }) paths
            else {
                val newPaths = paths.filterNot { it.ended() }.flatMap { path ->
                    connections.filter { path.accepts(it) }.map { connection ->
                        Path(path.connections + connection)
                    }
                }
                search(paths.filter { it.ended() } + newPaths)
            }
        return search(listOf(Path(listOf(start))))
    }

    private fun parse(lines: List<String>): Set<Connection> = lines.flatMap {
        val split: List<String> = it.split("-")
        val cave1 = Cave(split[0])
        val cave2 = Cave(split[1])
        listOf(Connection(cave1, cave2), Connection(cave2, cave1))
    }.toSet()

    override fun run() {
        println("Day 12, number of paths through cave system (new rules): ${countPaths()}")
    }
}
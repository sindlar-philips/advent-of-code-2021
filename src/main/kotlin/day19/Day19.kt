package day19

import PuzzleData
import kotlin.math.abs

data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    fun xRotation(): Coordinate3D = Coordinate3D(x, -z, y)
    fun yRotation(): Coordinate3D = Coordinate3D(-z, y, x)
    fun zRotation(): Coordinate3D = Coordinate3D(-y, x, z)
}

data class Scanner(val id: Int, val position: Coordinate3D, val beacons: List<Coordinate3D>) {
    private fun xRotation(): Scanner = Scanner(id, position, beacons.map { it.xRotation() })
    private fun yRotation(): Scanner = Scanner(id, position, beacons.map { it.yRotation() })
    private fun zRotation(): Scanner = Scanner(id, position, beacons.map { it.zRotation() })

    fun allOrientations(): Set<Scanner> {
        val xRotations = listOf(this, xRotation(), xRotation().xRotation(), xRotation().xRotation().xRotation())
        val xyRotations = xRotations.flatMap {
            listOf(it, it.yRotation(), it.yRotation().yRotation(), it.yRotation().yRotation().yRotation())
        }
        val xyzRotations = xyRotations.flatMap {
            listOf(it, it.zRotation(), it.zRotation().zRotation(), it.zRotation().zRotation().zRotation())
        }
        return xyzRotations.toSet()
    }
}

object Day19 : Runnable {

    internal val scanners = PuzzleData.load("/day19/day19.txt") { parse(it) }

    fun countBeacons(): Int {
        val normalizedScanners = getNormalizedScanners()
        val beacons = normalizedScanners.flatMap { it.beacons }.toSet()
        return beacons.count()
    }

    fun manhattanDistance(): Int {
        val scannerPositions = getNormalizedScanners().map { it.position }
        val distances: List<Int> = scannerPositions.flatMap { ref ->
            scannerPositions.map { other -> abs(ref.x - other.x) + abs(ref.y - other.y) + abs(ref.z - other.z) }
        }
        return distances.maxOf { it }
    }

    private fun getNormalizedScanners(): Set<Scanner> {
        fun go(open: Set<Scanner>, closed: Set<Scanner>): Set<Scanner> {
            val ignoredIds = open.map { it.id } + closed.map { it.id }
            val remaining = scanners.filterNot { ignoredIds.contains(it.id) }
            if (remaining.isEmpty()) return closed + open
            val candidates = remaining.flatMap { it.allOrientations() }
            val new = open.flatMap { ref -> candidates.mapNotNull { candidate -> normalize(12, ref, candidate) } }
            return if (new.isEmpty()) closed + open
            else go(new.toSet(), closed + open)
        }
        return go(setOf(scanners.first()), setOf())
    }

    private fun relativeBeaconPositions(ref: Coordinate3D, beacons: List<Coordinate3D>): Set<Coordinate3D> =
        beacons.map { Coordinate3D(ref.x - it.x, ref.y - it.y, ref.z - it.z) }.toSet()

    internal fun normalize(minimumMatching: Int, ref: Scanner, other: Scanner): Scanner? {
        val refBeacons =
            ref.beacons.associateWith { r1 -> relativeBeaconPositions(r1, ref.beacons) }
        val otherBeacons =
            other.beacons.associateWith { r1 -> relativeBeaconPositions(r1, other.beacons) }
        val matchingBeacons = refBeacons.map { (beacon, refPos) ->
            val matches =
                otherBeacons.filterValues { otherPos -> refPos.intersect(otherPos).count() >= minimumMatching }.keys
            if (matches.isNotEmpty()) Pair(beacon, matches.single())
            else null
        }.filterNotNull()
        return if (matchingBeacons.isEmpty()) null
        else {
            val (rBeacon, oBeacon) = matchingBeacons.first()
            return offset(rBeacon.x - oBeacon.x, rBeacon.y - oBeacon.y, rBeacon.z - oBeacon.z, other)
        }
    }

    private fun offset(x: Int, y: Int, z: Int, scanner: Scanner): Scanner =
        Scanner(scanner.id, Coordinate3D(x, y, z),
            scanner.beacons.map { Coordinate3D(x + it.x, y + it.y, z + it.z) })

    private fun parse(lines: List<String>): List<Scanner> {
        fun scan(lines: List<String>, i: Int, beacons: List<Coordinate3D>, scanners: List<Scanner>): List<Scanner> {
            return if (lines.isEmpty()) scanners + Scanner(i, Coordinate3D(0, 0, 0), beacons)
            else {
                val line = lines.first()
                if (line == "--- scanner ${i + 1} ---") {
                    val scanner = Scanner(i, Coordinate3D(0, 0, 0), beacons)
                    scan(lines.drop(1), i + 1, listOf(), scanners + scanner)
                } else if (line.startsWith("--- scanner")) {
                    scan(lines.drop(1), i, beacons, scanners)
                } else {
                    val coordinates = line.split(",").map { it.toInt() }
                    val beacon = Coordinate3D(coordinates[0], coordinates[1], coordinates[2])
                    scan(lines.drop(1), i, beacons + beacon, scanners)
                }
            }
        }
        return scan(lines.filterNot { it.isEmpty() }, 0, listOf(), listOf())
    }

    override fun run() {
        println("Day 19, count beacons: ${countBeacons()}")
        println("Day 19, maximum Manhattan distance: ${manhattanDistance()}")
    }
}
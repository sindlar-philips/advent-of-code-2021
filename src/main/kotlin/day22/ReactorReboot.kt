package day22

import PuzzleData
import day19.Coordinate3D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Instruction(val on: Boolean, val cuboid: Cuboid)
data class Cuboid(val x: IntRange, val y: IntRange, val z: IntRange) {

    fun coordinates(): Set<Coordinate3D> =
        x.flatMap { x ->
            y.flatMap { y ->
                z.map { z ->
                    Coordinate3D(x, y, z)
                }
            }
        }.toSet()

    fun intersect(other: Cuboid): Cuboid? {
        val minX = max(x.first, other.x.first)
        val maxX = min(x.last, other.x.last)
        val minY = max(y.first, other.y.first)
        val maxY = min(y.last, other.y.last)
        val minZ = max(z.first, other.z.first)
        val maxZ = min(z.last, other.z.last)
        val cuboidsIntersect = listOf(minX, maxX).all { x.contains(it) && other.x.contains(it) } &&
                listOf(minY, maxY).all { y.contains(it) && other.y.contains(it) } &&
                listOf(minZ, maxZ).all { z.contains(it) && other.z.contains(it) }
        return if (!cuboidsIntersect) null
        else Cuboid(minX..maxX, minY..maxY, minZ..maxZ)
    }

    fun remove(other: Cuboid): Set<Cuboid> {
        val int = intersect(other) ?: return setOf(this)
        val rest = mutableSetOf<Cuboid>()
        if (x.first < int.x.first)
            rest.add(Cuboid(x.first until int.x.first, y, z))
        if (x.last > int.x.last)
            rest.add(Cuboid(int.x.last + 1..x.last, y, z))
        if (y.first < int.y.first)
            rest.add(Cuboid(int.x, y.first until int.y.first, z))
        if (y.last > int.y.last)
            rest.add(Cuboid(int.x, int.y.last + 1..y.last, z))
        if (z.first < int.z.first)
            rest.add(Cuboid(int.x, int.y, z.first until int.z.first))
        if (z.last > int.z.last)
            rest.add(Cuboid(int.x, int.y, int.z.last + 1..z.last))
        return rest.toSet()
    }

    fun volume(): Long {
        val xWidth = abs(x.last - x.first) + 1L
        val yWidth = abs(y.last - y.first) + 1L
        val zWidth = abs(z.last - z.first) + 1L
        return xWidth * yWidth * zWidth
    }
}

object ReactorReboot : Runnable {

    private val instructions = PuzzleData.load("/day22/cuboid-regions.txt") { parse(it) }

    fun countOnCubes(isRestricted: Boolean = false): Long {
        tailrec fun process(instructions: List<Instruction>, onCuboids: List<Cuboid>): List<Cuboid> {
            //println("Processing, ${instructions.size} to go")
            if (instructions.isEmpty()) return onCuboids
            else {
                val instruction = instructions.first()
                if (isRestricted && !isInFiftyFiftyRange(instruction)) return process(instructions.drop(1), onCuboids)
                val result = if (instruction.on) onCuboids + instruction.cuboid
                else off(instruction.cuboid, onCuboids)
                return process(instructions.drop(1), result)
            }
        }
        val onCuboids = process(instructions, listOf())
        val normalized = normalize(onCuboids, listOf())
        return normalized.sumOf { it.volume() }
    }

    private fun normalize(onCuboids: List<Cuboid>, normalized: List<Cuboid>): List<Cuboid> {
        //println("Normalizing, ${onCuboids.size} to go, ${normalized.size} normalized")
        if (onCuboids.isEmpty()) return normalized
        val head = onCuboids.first()
        val tail = onCuboids.drop(1)
        val normalHead = tail.fold(listOf(head)) { headParts: List<Cuboid>, onCuboid: Cuboid ->
            headParts.flatMap { it.remove(onCuboid) }
        }
        return normalize(tail, normalized + normalHead)
    }

    private fun off(cuboid: Cuboid, onCuboids: List<Cuboid>): List<Cuboid> =
        onCuboids.flatMap { it.remove(cuboid) }

    private fun isInFiftyFiftyRange(i: Instruction): Boolean =
        i.cuboid.x.first >= -50 && i.cuboid.y.first >= -50 && i.cuboid.z.first >= -50 &&
                i.cuboid.x.last <= 50 && i.cuboid.y.last <= 50 && i.cuboid.z.last <= 50

    private fun parse(lines: List<String>): List<Instruction> =
        lines.map { line ->
            val on = line.startsWith("on")
            val regex = ".* x=(.+)\\.\\.(.+),y=(.+)\\.\\.(.+),z=(.+)\\.\\.(.+)".toRegex()
            val matchResult = regex.find(line) ?: throw Exception("No match!")
            val (xMin, xMax, yMin, yMax, zMin, zMax) = matchResult.destructured
            val coordinates =
                Cuboid(xMin.toInt()..xMax.toInt(), yMin.toInt()..yMax.toInt(), zMin.toInt()..zMax.toInt())
            Instruction(on, coordinates)
        }

    override fun run() {
        println("Day 22: count ON cubes (restricted): ${countOnCubes(true)}")
        println("Day 22: count ON cubes (full): ${countOnCubes()}")
    }
}
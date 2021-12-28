package day17

import PuzzleData
import day04.Coordinate
import java.util.stream.Collectors

data class Trajectory(val vInit: Velocity, val path: List<Coordinate>)
data class Velocity(val x: Int, val y: Int)

object Day17 : Runnable {

    private val targetArea = PuzzleData.load("/day17/day17.txt") { parseTargetArea(it) }

    fun maxHeight(): Int {
        val trajectories = giveItYourBestShot(0, 20, 0, 250)
        val maxHeight = trajectories.maxOf { t -> t.path.maxOf { c -> c.y } }
        val velocitiesForMaxHeight = trajectories.filter { it.path.any { c -> c.y == maxHeight } }.map { it.vInit }
        println("Velocities reaching max height of $maxHeight: $velocitiesForMaxHeight")
        return maxHeight
    }

    fun countVelocities(): Int {
        val velocities = giveItYourBestShot(0, 100, -250, 250).map { it.vInit }
        val successfulInitialVelocities = velocities.count()
        println("Number of distinct successful velocities: $successfulInitialVelocities")
        println("Velocity with min/max X: ${velocities.minByOrNull { it.x }} / ${velocities.maxByOrNull { it.x }}")
        println("Velocity with min/max Y: ${velocities.minByOrNull { it.y }} / ${velocities.maxByOrNull { it.y }}")
        return successfulInitialVelocities
    }

    private fun giveItYourBestShot(xMin: Int, xMax: Int, yMin: Int, yMax: Int): List<Trajectory> =
        (xMin..xMax).flatMap { vx ->
            (yMin..yMax).toList().parallelStream().map { vy ->
                getTrajectory(Velocity(vx, vy))
            }.filter { bullseye(it.path.last()) }.collect(Collectors.toList())
        }

    private fun getTrajectory(initial: Velocity): Trajectory {
        val start = Coordinate(0, 0)
        tailrec fun getPath(c: Coordinate, v: Velocity, acc: List<Coordinate>): List<Coordinate> =
            if (bullseye(c) || miss(c)) acc + c
            else {
                val (cNext, vNext) = step(c, v)
                getPath(cNext, vNext, acc + c)
            }

        val coordinates = getPath(start, initial, listOf())
        return Trajectory(initial, coordinates)
    }

    private fun bullseye(c: Coordinate): Boolean = targetArea.contains(c)

    private fun miss(c: Coordinate): Boolean = c.y < targetArea.minOf { it.y }

    private fun step(position: Coordinate, velocity: Velocity): Pair<Coordinate, Velocity> {
        val newPosition = Coordinate(position.x + velocity.x, position.y + velocity.y)
        val newVelocity = if (velocity.x > 0) Velocity(velocity.x - 1, velocity.y - 1)
        else if (velocity.x < 0) Velocity(velocity.x + 1, velocity.y - 1)
        else (Velocity(0, velocity.y - 1))
        return Pair(newPosition, newVelocity)
    }

    private fun parseTargetArea(lines: List<String>): Set<Coordinate> {
        val regex = "target area: x=(.+)\\.\\.(.+), y=(.+)\\.\\.(.+)".toRegex()
        val matchResult = regex.find(lines.single()) ?: throw Exception("No match!")
        val (xMin, xMax, yMin, yMax) = matchResult.destructured
        val coordinates = (xMin.toInt()..xMax.toInt()).flatMap { x ->
            (yMin.toInt()..yMax.toInt()).map { y ->
                Coordinate(x, y)
            }
        }
        return coordinates.toSet()
    }

    override fun run() {
        println("Day 17, maximum height")
        maxHeight()
        println("Day 17, number of successful initial velocities")
        countVelocities()
    }
}
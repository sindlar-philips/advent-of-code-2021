import day16.Day16.eval
import day01.Day1
import day02.Day2
import day03.Day3
import day04.Day4
import day05.Day5
import day06.Day6
import day07.Day7
import day08.Day8
import day09.Day9
import day10.Day10
import day11.Day11
import day12.Day12
import day13.Day13
import day14.Day14
import day15.Day15
import day16.Day16
import day17.Day17
import day18.Day18
import day19.Day19
import day20.Day20
import day21.Day21
import day22.Day22
import day23.Day23
import day24.Day24
import day25.Day25
import java.io.File

data class Coordinate(val x: Int, val y: Int) {

    private fun north(): Coordinate = Coordinate(x + 1, y)
    private fun east(): Coordinate = Coordinate(x, y + 1)
    private fun south(): Coordinate = Coordinate(x - 1, y)
    private fun west(): Coordinate = Coordinate(x, y - 1)
    fun directNeighbours(): Set<Coordinate> = setOf(north(), east(), south(), west())

    private fun northWest(): Coordinate = Coordinate(x + 1, y - 1)
    private fun northEast(): Coordinate = Coordinate(x + 1, y + 1)
    private fun southEast(): Coordinate = Coordinate(x - 1, y + 1)
    private fun southWest(): Coordinate = Coordinate(x - 1, y - 1)
    fun allNeighbours(): Set<Coordinate> = setOf(
        north(), east(), south(), west(), northWest(), northEast(), southEast(), southWest()
    )
}

fun main() {

    println("Day 1, number of increases: ${Day1.countIncreases()}")
    println("Day 1, number of increases (windowed): ${Day1.countIncreasesWindowed()}")

    println("Day 2, position (with aim): ${Day2.determinePosition()}")

    println("Day 3, power consumption: ${Day3.calculatePowerConsumption()}")
    println("Day 3, life support rating: ${Day3.calculateLifeSupportRating()}")

    println("Day 4, bingo first: ${Day4.getScoreOfFirstWinningBoard()}")
    println("Day 4, bingo last: ${Day4.getScoreOfLastWinningBoard()}")

    println("Day 5, number of coordinates in two or more lines (with diagonals): ${Day5.countCoordinatesInTwoOrMoreLines()}")

    println("Day 6, number of lanternfish after 80 days: ${Day6.countLanternfishGroups(80)}")
    println("Day 6, number of lanternfish after 256 days: ${Day6.countLanternfishGroups(256)}")

    println("Day 7, minimum fuel required: ${Day7.minimumFuelRequired()}")
    println("Day 7, minimum fuel required (weighted): ${Day7.minimumFuelRequiredWeighted()}")

    println("Day 8, count unique outputs: ${Day8.countUniqueOutputs()}")
    println("Day 8, summed values after fixing wiring: ${Day8.fixWiringAndSumValues()}")

    println("Day 9, sum of risk levels: ${Day9.sumOfRiskLevels()}")
    println("Day 9, product of three largest basin sizes: ${Day9.productOfThreeLargestBasinSizes()}")

    println("Day 10, sum of first illegal characters: ${Day10.sumFirstIllegalChars()}")
    println("Day 10, middle completion score: ${Day10.getMiddleCompletionScore()}")

    println("Day 11, flashes after 100 steps: ${Day11.countFlashesAfter100Steps()}")
    println("Day 11, first synchronized flash step: ${Day11.findFirstSynchronizedFlashStep()}")

    println("Day 12, number of paths through cave system (new rules): ${Day12.countPaths()}")

    println("Day 13, visible dots after first fold: ${Day13.countVisibleDotsAfterFirstFold()}")
    println("Day 13, visible dots after folding (printed):")
    Day13.foldAndPrint()

    println("Day 14, create polymer and count: ${Day14.createPolymerAndCount(10)}")
    println("Day 14, just count: ${Day14.justCount(40)}")

    println("Day 15, least risky path: ${Day15.leastRiskyPath()}")
    println("Day 15, least risky path (extended): ${Day15.leastRiskyPathExtended()}")

    println("Day 16, sum of versions: ${Day16.sumVersions()}")
    println("Day 16, expression: ${Day16.packets.single().eval()}")

    println("Day 17, maximum height")
    Day17.maxHeight()
    println("Day 17, number of successful initial velocities")
    Day17.countVelocities()

    println("Day 18, snailfish homework, part 1: ${Day18.doHomeworkPart1()}")
    println("Day 18, snailfish homework, part 2: ${Day18.doHomeworkPart2()}")

    println("Day 19, count beacons: ${Day19.countBeacons()}")
    println("Day 19, maximum Manhattan distance: ${Day19.manhattanDistance()}")

    println("Day 20, 2x enhanced: ${Day20.enhanceAndCountLitPixels(2)}")
    println("Day 20, 50x enhanced: ${Day20.enhanceAndCountLitPixels(50)}")

    println("Day 21: play dice: ${Day21.playDice(5, 6)}")
    println("Day 21: play quantum dice: ${Day21.quantumDice(5, 6)}")

    println("Day 22: count on cubes (restricted): ${Day22.countOnCubes(true)}")
    // FIXME: very slow
    //println("Day 22: count on cubes: ${day22.Day22.countOnCubes()}")

    println("Day 23, part 1: managed with good old pen & paper, modify input for part 2 to verify")
    println("Day 23, part 2: ${Day23.minimumEnergyForGroupingAmphipods()}")

    println("Day 24, largest model number: ${Day24.findLargestModelNumber()}")
    println("Day 24, smallest model number: ${Day24.findSmallestModelNumber()}")

    println("Day 25, steps for sea cucumber equilibrium: ${Day25.stepsForSeaCucumberEquilibrium()}")
}

object PuzzleData {

    fun <T> load(resourceLocation: String, transform: (lines: List<String>) -> T): T {
        val resourceUrl = javaClass.getResource(resourceLocation)
            ?: throw Error("Error loading resource: $resourceLocation")
        val lines = File(resourceUrl.path).reader().readLines()
        return transform(lines)
    }
}

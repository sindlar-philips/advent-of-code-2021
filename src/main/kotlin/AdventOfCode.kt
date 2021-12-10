import java.io.File

data class Coordinate(val x: Int, val y: Int) {

    fun north(): Coordinate = Coordinate(x + 1, y)
    fun east(): Coordinate = Coordinate(x, y + 1)
    fun south(): Coordinate = Coordinate(x - 1, y)
    fun west(): Coordinate = Coordinate(x, y - 1)
    fun neighbours(): Set<Coordinate> = setOf(north(), east(), south(), west())
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
}

object PuzzleData {

    fun <T> load(resourceLocation: String, transform: (lines: List<String>) -> T): T {
        val resourceUrl = javaClass.getResource(resourceLocation)
            ?: throw Error("Error loading resource: $resourceLocation")
        val lines = File(resourceUrl.path).reader().readLines()
        return transform(lines)
    }
}

import java.io.File

data class Coordinate(val x: Int, val y: Int)

fun main() {

    println("Day 1, number of increases: ${Day1.countIncreases()}")
    println("Day 1, number of increases (windowed): ${Day1.countIncreasesWindowed()}")

    println("Day 2, position (with aim): ${Day2.determinePosition()}")

    println("Day 3, power consumption: ${Day3.calculatePowerConsumption()}")
    println("Day 3, life support rating: ${Day3.calculateLifeSupportRating()}")

    println("Day 4, bingo first: ${Day4.getScoreOfFirstWinningBoard()}")
    println("Day 4, bingo last: ${Day4.getScoreOfLastWinningBoard()}")

    println("Day 5, number of coordinates in two or more lines (with diagonals): ${Day5.countCoordinatesInTwoOrMoreLines()}")
}

object PuzzleData {

    fun <T> load(resourceLocation: String, transform: (lines: List<String>) -> T): T {
        val resourceUrl = javaClass.getResource(resourceLocation)
            ?: throw Error("Error loading resource: $resourceLocation")
        val lines = File(resourceUrl.path).reader().readLines()
        return transform(lines)
    }
}

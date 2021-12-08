import java.io.File

data class Coordinate(val x: Int, val y: Int)

fun main() {

    println("Day 1, number of increases: ${Day1.countIncreases()}")
    println("Day 1, number of increases (windowed): ${Day1.countIncreasesWindowed()}")
}

object PuzzleData {

    fun <T> load(resourceLocation: String, transform: (lines: List<String>) -> T): T {
        val resourceUrl = javaClass.getResource(resourceLocation)
            ?: throw Error("Error loading resource: $resourceLocation")
        val lines = File(resourceUrl.path).reader().readLines()
        return transform(lines)
    }
}

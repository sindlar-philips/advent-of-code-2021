object Day9 {

    private val heights = PuzzleData.load("/day9.txt") { parse(it) }

    fun sumOfRiskLevels(): Int =
        lowPoints().sumOf { heights[it]!! + 1 }

    fun productOfThreeLargestBasinSizes(): Int =
        lowPoints().asSequence().map { getBasin(it) }
            .sortedByDescending { it.size }.take(3).map { it.size }.reduce { a, b -> a * b }

    private fun getBasin(coordinate: Coordinate): Set<Coordinate> {
        val basin = mutableSetOf<Coordinate>()
        fun determineBasin(coordinate: Coordinate) {
            basin.add(coordinate)
            val newNeighbours = basinNeighbours(coordinate).filterNot { basin.contains(it) }
            newNeighbours.forEach{ determineBasin(it) }
        }
        determineBasin(coordinate)
        return basin
    }

    private fun basinNeighbours(coordinate: Coordinate): List<Coordinate> =
        coordinate.directNeighbours().filter { heights.containsKey(it) && heights[it]!! < 9 }

    private fun lowPoints(): List<Coordinate> = heights.keys.filter { it.isLowPoint() }

    private fun Coordinate.isLowPoint(): Boolean =
        this.directNeighbours().mapNotNull { heights[it] }.all { heights[this]!! < it }

    private fun parse(lines: List<String>): Map<Coordinate, Int> = lines.mapIndexed { x, heights ->
        heights.chunked(1).mapIndexed { y, height -> Pair(Coordinate(x, y), height.toInt()) }
    }.flatten().toMap()
}
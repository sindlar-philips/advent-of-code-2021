object Day5  {

    private val lines = PuzzleData.load("/day5.txt") { parseLines(it) }

    class Line(private val start: Coordinate, private val end: Coordinate) {

        fun coordinates(): List<Coordinate> {
            val xOp = steppingOperator(start.x, end.x)
            val yOp = steppingOperator(start.y, end.y)
            return steps(mutableListOf(), start, end, xOp, yOp)
        }

        private fun steppingOperator(n1: Int, n2: Int): (Int) -> Int =
            if (n1 == n2) { n: Int -> n }
            else if (n1 < n2) { n: Int -> n + 1 }
            else { n: Int -> n - 1 }

        private fun steps(
            acc: MutableList<Coordinate>, current: Coordinate, end: Coordinate, xOp: (Int) -> Int, yOp: (Int) -> Int
        ): List<Coordinate> {
            acc.add(current)
            return if (current == end) acc.toList()
            else steps(acc, Coordinate(xOp(current.x), yOp(current.y)), end, xOp, yOp)
        }

        override fun toString(): String = "$start -> $end"
    }

    fun countCoordinatesInTwoOrMoreLines(): Int =
        lines.flatMap { it.coordinates() }.groupingBy { it }.eachCount().filter { it.value > 1 }.size

    private fun parseLines(lines: List<String>): List<Day5.Line> =
        lines.map{
            val coordinateStrings = it.split(" -> ")
            val x = coordinateStrings[0].split(",").map{ n -> n.toInt() }
            val y = coordinateStrings[1].split(",").map{ n -> n.toInt() }
            Day5.Line(Coordinate(x[0], x[1]), Coordinate(y[0], y[1]))
        }
}
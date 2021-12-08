object Day4 {

    private const val boardSize = 5
    private val boards = loadBoards()

    private fun loadBoards(): List<Board> {
        val numbers = PuzzleData.load("/day4-numbers.txt") { parseNumbers(it) }
        return PuzzleData.load("/day4-boards.txt") { parseBoards(it, numbers) }
    }

    class Board(private val state: Map<Coordinate, Int>, numbers: List<Int>) {

        val bingo: Boolean
        val usedNumbers: List<Int>
        private val markedCoordinates: Set<Coordinate>

        init {
            val (c, n, b) = process(numbers, setOf(), listOf())
            markedCoordinates = c
            usedNumbers = n
            bingo = b
        }

        fun sumOfUnmarkedNumbers(): Int =
            state.filterKeys { !markedCoordinates.contains(it) }.values.sum()

        private fun process(
            remainingNumbers: List<Int>, marked: Set<Coordinate>, usedNumbers: List<Int>
        ): Triple<Set<Coordinate>, List<Int>, Boolean> =
            if (bingo(marked)) Triple(marked, usedNumbers, true)
            else if (remainingNumbers.isEmpty()) Triple(marked, usedNumbers, false)
            else {
                val nextNumber = remainingNumbers[0]
                val filterValues = state.filterValues { it == nextNumber }.keys
                process(remainingNumbers.drop(1), marked + filterValues, usedNumbers + nextNumber)
            }

        private fun bingo(coordinates: Set<Coordinate>): Boolean = (0 until boardSize).any { n ->
            coordinates.count { c -> c.x == n } == boardSize || coordinates.count { c -> c.y == n } == boardSize
        }
    }

    fun getScoreOfFirstWinningBoard(): Int =
        getScore(boards.filter { it.bingo }.sortedBy { it.usedNumbers.count() }.first())

    fun getScoreOfLastWinningBoard(): Int =
        getScore(boards.filter { it.bingo }.sortedByDescending { it.usedNumbers.count() }.first())

    private fun getScore(board: Board): Int =
        board.sumOfUnmarkedNumbers() * board.usedNumbers.last()

    private fun parseBoards(lines: List<String>, numbers: List<Int>): List<Board> =
        lines.filter { it.isNotEmpty() }.chunked(boardSize).map { linesToBoardInput(it) }.map { Board(it, numbers) }

    private fun linesToBoardInput(lines: List<String>): Map<Coordinate, Int> {
        val values: List<List<Int>> = lines.map { line ->
            line.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        }
        return values.mapIndexed { x, vs -> vs.mapIndexed { y, v -> Coordinate(x, y) to v } }.flatten().toMap()
    }

    private fun parseNumbers(lines: List<String>): List<Int> = lines[0].split(",").map { it.toInt() }
}

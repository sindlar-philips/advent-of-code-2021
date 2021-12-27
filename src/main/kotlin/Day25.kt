object Day25 {

    private val cucumbers = PuzzleData.load("/day25.txt") { it }

    fun stepsForSeaCucumberEquilibrium(): Int {
        fun search(cucumbers: List<String>, moves: Int): Int {
            val moved = move(cucumbers)
            return if (moved == cucumbers) moves + 1
            else search(moved, moves + 1)
        }
        return search(cucumbers, 0)
    }

    internal fun move(cucumbers: List<String>): List<String> {
        val movedRight = right(cucumbers, '>')
        return right(movedRight.flip(), 'v').flip()
    }

    internal fun List<String>.flip(): List<String> {
        fun flipIt(flipping: List<String>, flipped: List<String>): List<String> =
            if (flipping.first().isEmpty()) flipped
            else {
                val firstCharsFlipped = flipping.map { it.first() }.joinToString("")
                flipIt(flipping.map { it.drop(1) }, flipped + firstCharsFlipped)
            }
        return flipIt(this, listOf())
    }

    internal fun right(cucumbers: List<String>, symbol: Char): List<String> {
        val firstColumnBeforeMove = cucumbers.map { it.first() }
        val firstColumnCopiedToEnd = cucumbers.map { it + it.first() }
        val moved = firstColumnCopiedToEnd.map { it.replace("$symbol.", ".$symbol") }
        val firstColumnAfterMove = moved.map { it.last() }
        return moved.mapIndexed { i, row ->
            val symbolBeforeMove = firstColumnBeforeMove[i]
            val symbolAfterMove = firstColumnAfterMove[i]
            if (symbolBeforeMove == '.') symbolAfterMove.toString() + row.drop(1).dropLast(1)
            else row.dropLast(1)
        }
    }
}
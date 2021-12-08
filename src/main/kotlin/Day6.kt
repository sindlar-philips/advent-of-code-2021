object Day6  {

    private val timers = PuzzleData.load("/day6.txt") { parseTimers(it) }

    fun countLanternfishIndividually(days: Int): Long =
        timers.map { count(days, it) }.reduce { a, b -> a + b }

    private fun count(days: Int, timer: Int): Long = if (days <= timer) 1
    else count(days - timer - 1, 6) + count(days - timer - 1, 8)

    fun countLanternfishGroups(days: Int): Long {
        val initialState: Map<Int, Long> = timers.sorted().groupingBy { it }.eachCount().mapValues { (_, n) -> n.toLong() }
        val endState = progress(initialState, days)
        return endState.values.fold(0) { x, b -> x + b }
    }

    private fun progress(state: Map<Int, Long>, days: Int): Map<Int, Long> =
        if (days == 0) state
        else {
            val updatedState = (0..8).associate {
                when (it) {
                    6 -> it to (state[7] ?: 0) + (state[0] ?: 0)
                    8 -> it to (state[0] ?: 0)
                    else -> it to (state[it + 1] ?: 0)
                }
            }
            progress(updatedState, days - 1)
        }

    private fun parseTimers(lines: List<String>): Sequence<Int> =
        lines[0].split(",").map{ it.toInt() }.asSequence()
}
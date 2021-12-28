package day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day4Test {

    @Test
    fun bingo() {
        assertEquals(4512, Day4.getScoreOfFirstWinningBoard())
        assertEquals(1924, Day4.getScoreOfLastWinningBoard())
    }
}
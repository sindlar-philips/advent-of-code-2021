package day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GiantSquidTest {

    @Test
    fun bingo() {
        assertEquals(4512, GiantSquid.getScoreOfFirstWinningBoard())
        assertEquals(1924, GiantSquid.getScoreOfLastWinningBoard())
    }
}
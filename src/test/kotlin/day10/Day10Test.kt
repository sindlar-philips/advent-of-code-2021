package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day10Test {

    @Test
    fun sumOfFirstIllegalChars() {
        assertEquals(26397, Day10.sumFirstIllegalChars())
    }

    @Test
    fun getMiddleCompletionScore() {
        assertEquals(288957, Day10.getMiddleCompletionScore())
    }
}
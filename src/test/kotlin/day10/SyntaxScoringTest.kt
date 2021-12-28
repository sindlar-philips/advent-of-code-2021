package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SyntaxScoringTest {

    @Test
    fun sumOfFirstIllegalChars() {
        assertEquals(26397, SyntaxScoring.sumFirstIllegalChars())
    }

    @Test
    fun getMiddleCompletionScore() {
        assertEquals(288957, SyntaxScoring.getMiddleCompletionScore())
    }
}
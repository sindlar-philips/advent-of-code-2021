package day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PassagePathingTest {

    @Test
    fun countPaths() {
        assertEquals(36, PassagePathing.countPaths())
    }
}
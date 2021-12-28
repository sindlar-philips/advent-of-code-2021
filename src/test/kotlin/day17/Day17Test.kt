package day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17Test {

    @Test
    fun maxHeight() {
        assertEquals(45, Day17.maxHeight())
    }

    @Test
    fun countVelocities() {
        assertEquals(112, Day17.countVelocities())
    }
}
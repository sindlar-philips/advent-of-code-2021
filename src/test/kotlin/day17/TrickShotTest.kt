package day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TrickShotTest {

    @Test
    fun maxHeight() {
        assertEquals(45, TrickShot.maxHeight())
    }

    @Test
    fun countVelocities() {
        assertEquals(112, TrickShot.countVelocities())
    }
}
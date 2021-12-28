package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SonarSweepTest {

    @Test
    fun countIncreases() {
        assertEquals(7, SonarSweep.countIncreases())
    }

    @Test
    fun countIncreasesWindowed() {
        assertEquals(5, SonarSweep.countIncreasesWindowed())
    }
}
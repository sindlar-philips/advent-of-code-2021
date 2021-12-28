package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day01Test {

    @Test
    fun countIncreases() {
        assertEquals(7, Day01.countIncreases())
    }

    @Test
    fun countIncreasesWindowed() {
        assertEquals(5, Day01.countIncreasesWindowed())
    }
}
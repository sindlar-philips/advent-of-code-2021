package day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {

    @Test
    fun countFlashesAfter100Steps() {
        assertEquals(1656, Day11.countFlashesAfter100Steps())
    }

    @Test
    fun findFirstSynchronizedFlashStep() {
        assertEquals(195, Day11.findFirstSynchronizedFlashStep())
    }
}
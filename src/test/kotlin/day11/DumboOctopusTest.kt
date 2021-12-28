package day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DumboOctopusTest {

    @Test
    fun countFlashesAfter100Steps() {
        assertEquals(1656, DumboOctopus.countFlashesAfter100Steps())
    }

    @Test
    fun findFirstSynchronizedFlashStep() {
        assertEquals(195, DumboOctopus.findFirstSynchronizedFlashStep())
    }
}
package day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TheTreacheryOfWhalesTest {

    @Test
    fun minimumFuelRequired() {
        assertEquals(37, TheTreacheryOfWhales.minimumFuelRequired())
    }

    @Test
    fun minimumFuelRequiredWeighted() {
        assertEquals(168, TheTreacheryOfWhales.minimumFuelRequiredWeighted())
    }
}
package day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day07Test {

    @Test
    fun minimumFuelRequired() {
        assertEquals(37, Day07.minimumFuelRequired())
    }

    @Test
    fun minimumFuelRequiredWeighted() {
        assertEquals(168, Day07.minimumFuelRequiredWeighted())
    }
}
package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day03Test {

    @Test
    fun calculatePowerConsumption() {
        assertEquals(198, Day03.calculatePowerConsumption())
    }

    @Test
    fun calculateLifeSupportRating() {
        assertEquals(230, Day03.calculateLifeSupportRating())
    }
}
package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BinaryDiagnosticTest {

    @Test
    fun calculatePowerConsumption() {
        assertEquals(198, BinaryDiagnostic.calculatePowerConsumption())
    }

    @Test
    fun calculateLifeSupportRating() {
        assertEquals(230, BinaryDiagnostic.calculateLifeSupportRating())
    }
}
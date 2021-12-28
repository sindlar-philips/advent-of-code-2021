package day05

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class HydrothermalVentureTest {

    @Test
    fun coordinatesInTwoOrMoreLines() {
        assertEquals(12, HydrothermalVenture.countCoordinatesInTwoOrMoreLines())
    }
}
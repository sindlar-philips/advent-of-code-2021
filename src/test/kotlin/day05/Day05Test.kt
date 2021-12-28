package day05

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day05Test {

    @Test
    fun coordinatesInTwoOrMoreLines() {
        assertEquals(12, Day5.countCoordinatesInTwoOrMoreLines())
    }
}
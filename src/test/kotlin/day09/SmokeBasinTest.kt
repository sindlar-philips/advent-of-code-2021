package day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SmokeBasinTest {

    @Test
    fun sumOfRiskLevels() {
        assertEquals(15, SmokeBasin.sumOfRiskLevels())
    }

    @Test
    fun productOfthreeLargestBasinSizes() {
        assertEquals(1134, SmokeBasin.productOfThreeLargestBasinSizes())
    }
}
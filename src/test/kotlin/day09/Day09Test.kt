package day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day09Test {

    @Test
    fun sumOfRiskLevels() {
        assertEquals(15, Day09.sumOfRiskLevels())
    }

    @Test
    fun productOfthreeLargestBasinSizes() {
        assertEquals(1134, Day09.productOfThreeLargestBasinSizes())
    }
}
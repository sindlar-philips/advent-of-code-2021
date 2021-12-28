package day20

import Coordinate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20Test {

    @Test
    fun getPixelValue() {
        val pixelValue = Day20.image.getPixelValue(Coordinate(2, 2))
        assertEquals(34, pixelValue)
        assertEquals('1', Day20.algorithm[pixelValue])
    }

    @Test
    fun enhanceAndCountLitPixels() {
        assertEquals(35, Day20.enhanceAndCountLitPixels(2))
        assertEquals(3351, Day20.enhanceAndCountLitPixels(50))
    }
}
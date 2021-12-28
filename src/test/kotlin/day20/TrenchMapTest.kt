package day20

import day04.Coordinate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TrenchMapTest {

    @Test
    fun getPixelValue() {
        val pixelValue = TrenchMap.image.getPixelValue(Coordinate(2, 2))
        assertEquals(34, pixelValue)
        assertEquals('1', TrenchMap.algorithm[pixelValue])
    }

    @Test
    fun enhanceAndCountLitPixels() {
        assertEquals(35, TrenchMap.enhanceAndCountLitPixels(2))
        assertEquals(3351, TrenchMap.enhanceAndCountLitPixels(50))
    }
}
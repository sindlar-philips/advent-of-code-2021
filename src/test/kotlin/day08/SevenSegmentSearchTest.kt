package day08

import day08.SevenSegmentSearch.minusChars
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SevenSegmentSearchTest {

    @Test
    fun countUniqueOutputs() {
        assertEquals(26, SevenSegmentSearch.countUniqueOutputs())
    }

    @Test
    fun minusChars() {
        assertEquals("a", "daef".minusChars("fed"))
    }

    @Test
    fun determineWiring() {
        val data = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
        val inputs = SevenSegmentSearch.parseDisplays(data).first().signals
        val expected = mapOf("d" to "a", "e" to "b", "a" to "c", "f" to "d", "g" to "e", "b" to "f", "c" to "g")
        val actual = SevenSegmentSearch.determineWiring(inputs)
        assertEquals(expected, actual)
    }

    @Test
    fun fixWiringAndSumValues() {
        assertEquals(61229, SevenSegmentSearch.fixWiringAndSumValues())
    }
}
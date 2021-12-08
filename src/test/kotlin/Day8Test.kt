import Day8.minusChars
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8Test {

    @Test
    fun countUniqueOutputs() {
        assertEquals(26, Day8.countUniqueOutputs())
    }

    @Test
    fun minusChars() {
        assertEquals("a", "daef".minusChars("fed"))
    }

    @Test
    fun determineWiring() {
        val data = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
        val inputs = Day8.parseDisplays(data).first().signals
        val expected = mapOf("d" to "a", "e" to "b", "a" to "c", "f" to "d", "g" to "e", "b" to "f", "c" to "g")
        val actual = Day8.determineWiring(inputs)
        assertEquals(expected, actual)
    }

    @Test
    fun fixWiringAndSumValues() {
        assertEquals(61229, Day8.fixWiringAndSumValues())
    }
}
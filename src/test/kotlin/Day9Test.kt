import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day9Test {

    @Test
    fun sumOfRiskLevels() {
        assertEquals(15, Day9.sumOfRiskLevels())
    }

    @Test
    fun productOfthreeLargestBasinSizes() {
        assertEquals(1134, Day9.productOfThreeLargestBasinSizes())
    }
}
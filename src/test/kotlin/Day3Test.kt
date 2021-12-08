import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {

    @Test
    fun calculatePowerConsumption() {
        assertEquals(198, Day3.calculatePowerConsumption())
    }

    @Test
    fun calculateLifeSupportRating() {
        assertEquals(230, Day3.calculateLifeSupportRating())
    }
}
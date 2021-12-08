import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day7Test {

    @Test
    fun minimumFuelRequired() {
        assertEquals(37, Day7.minimumFuelRequired())
    }

    @Test
    fun minimumFuelRequiredWeighted() {
        assertEquals(168, Day7.minimumFuelRequiredWeighted())
    }
}
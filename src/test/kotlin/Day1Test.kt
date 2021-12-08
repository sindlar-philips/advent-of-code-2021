import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {

    @Test
    fun countIncreases() {
        assertEquals(7, Day1.countIncreases())
    }

    @Test
    fun countIncreasesWindowed() {
        assertEquals(5, Day1.countIncreasesWindowed())
    }
}
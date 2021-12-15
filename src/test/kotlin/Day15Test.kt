import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15Test {

    @Test
    fun leastRiskyPath() {
        assertEquals(40, Day15.leastRiskyPath())
    }

    @Test
    fun leastRiskyPathExtended() {
        assertEquals(315, Day15.leastRiskyPathExtended())
    }
}
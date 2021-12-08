import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day2Test {

    @Test
    fun processCommands() {
        assertEquals(Pair(15, 60), Day2.determinePosition())
    }
}
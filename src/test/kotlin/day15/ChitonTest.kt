package day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ChitonTest {

    @Test
    fun leastRiskyPath() {
        assertEquals(40, Chiton.leastRiskyPath())
    }

    @Test
    fun leastRiskyPathExtended() {
        assertEquals(315, Chiton.leastRiskyPathExtended())
    }
}
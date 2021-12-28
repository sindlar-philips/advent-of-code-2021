package day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day02Test {

    @Test
    fun processCommands() {
        assertEquals(Pair(15, 60), Day02.determinePosition())
    }
}
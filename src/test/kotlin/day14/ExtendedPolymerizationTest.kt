package day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class ExtendedPolymerizationTest {

    @Test
    fun createPolymerAndCount10Steps() {
        assertEquals(1588, ExtendedPolymerization.createPolymerAndCount(10))
    }

    @Disabled("Too slow!")
    @Test
    fun createPolymerAndCount40Steps() {
        assertEquals(1588, ExtendedPolymerization.createPolymerAndCount(40))
    }

    @Test
    fun justCount10Steps() {
        assertEquals(1588, ExtendedPolymerization.justCount(10))
    }

    @Test
    fun justCount40Steps() {
        assertEquals(2188189693529L, ExtendedPolymerization.justCount(40))
    }
}
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class Day14Test {

    @Test
    fun createPolymerAndCount10Steps() {
        assertEquals(1588, Day14.createPolymerAndCount(10))
    }

    @Disabled("Too slow!")
    @Test
    fun createPolymerAndCount40Steps() {
        assertEquals(1588, Day14.createPolymerAndCount(40))
    }

    @Test
    fun justCount10Steps() {
        assertEquals(1588, Day14.justCount(10))
    }

    @Test
    fun justCount40Steps() {
        assertEquals(2188189693529L, Day14.justCount(40))
    }
}
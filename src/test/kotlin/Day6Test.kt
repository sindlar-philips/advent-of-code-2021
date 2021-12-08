import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day6Test {

    @Test
    fun run80() {
        assertEquals(5934, Day6.countLanternfishIndividually(80))
    }

    @Disabled("Too slow!")
    @Test
    fun run256() {
        assertEquals(26984457539, Day6.countLanternfishIndividually(256))
    }

    @Test
    fun run80Fast() {
        assertEquals(5934, Day6.countLanternfishGroups(80))
    }

    @Test
    fun run256Fast() {
        assertEquals(26984457539, Day6.countLanternfishGroups(256))
    }
}
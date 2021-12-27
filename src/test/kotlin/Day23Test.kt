import Day23.A
import Day23.B
import Day23.State
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day23Test {

    @Test
    fun amphipodEquals() {
        val someA = A()
        val otherA = A()
        val b = B()
        assertEquals(someA, someA)
        assertEquals(someA, otherA)
        assertNotEquals(someA, b)
    }

    @Test
    fun stateEquals() {
        val someMap = mapOf(Coordinate(0,0) to A(), Coordinate(0,1) to null)
        val otherSameMap = mapOf(Coordinate(0,0) to A(), Coordinate(0,1) to null)
        val otherDifferentMap = mapOf(Coordinate(0,0) to null, Coordinate(0,1) to null)
        assertEquals(State(someMap), State(someMap))
        assertEquals(State(someMap), State(otherSameMap))
        assertNotEquals(State(someMap), State(otherDifferentMap))
    }
}
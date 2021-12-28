package day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class Day22Test {

    @Test
    fun countOnCubesRestricted() {
        assertEquals(474140, Day22.countOnCubes(true))
    }

    @Test
    fun counOnCubes() {
        assertEquals(2758514936282235, Day22.countOnCubes())
    }

    @Test
    fun coordinates() {
        val reference = Cuboid(0..10, 0..10, 0..10)
        assertEquals(1331, reference.coordinates().count())
    }

    @Test
    fun intersect() {
        val reference = Cuboid(0..10, 0..10, 0..10)
        val other1 = Cuboid(10..20, 10..20, 10..20)
        val expected1 = Cuboid(10..10, 10..10, 10..10)
        assertEquals(expected1, reference.intersect(other1))
        assertEquals(expected1, other1.intersect(reference))

        val other2 = Cuboid(-5..5, -5..0, 5..15)
        val expected2 = Cuboid(0..5, 0..0, 5..10)
        assertEquals(expected2, reference.intersect(other2))
        assertEquals(expected2, other2.intersect(reference))

        val other3 = Cuboid(10..20, 10..20, 11..20)
        assertNull(reference.intersect(other3))
        assertNull(other3.intersect(reference))
    }

    @Test
    fun subtract() {
        val reference = Cuboid(0..10, 0..10, 0..10)
        val other1 = Cuboid(2..3, 4..5, 6..7)
        val expected1 = setOf(
            Cuboid(0..1, 0..10, 0..10),
            Cuboid(4..10, 0..10, 0..10),
            Cuboid(2..3, 0..3, 0..10),
            Cuboid(2..3, 6..10, 0..10),
            Cuboid(2..3, 4..5, 0..5),
            Cuboid(2..3, 4..5, 8..10),
        )
        assertEquals(expected1, reference.remove(other1))

        val other2 = Cuboid(10..20, 10..20, 11..20)
        assertEquals(setOf(reference), reference.remove(other2))
    }

    @Test
    fun volume() {
        val reference = Cuboid(0..10, 0..10, 0..10)
        assertEquals(1331, reference.volume())
        val referenceMinus = Cuboid(-20..-10, -20..-10, -20..-10)
        assertEquals(1331, referenceMinus.volume())
    }
}
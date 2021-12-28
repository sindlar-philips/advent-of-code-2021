package day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BeaconScannerTest {

    @Test
    fun allOrientations() {
        val scanner = Scanner(
            1, Coordinate3D(0, 0, 0), listOf(
                Coordinate3D(-1, -1, 1), Coordinate3D(-2, -2, 2), Coordinate3D(-3, -3, 3),
                Coordinate3D(-2, -3, 1), Coordinate3D(5, 6, -4), Coordinate3D(8, 0, 7)
            )
        )

        val allOrientations = scanner.allOrientations()
        val expected1 = Scanner(
            1, Coordinate3D(0, 0, 0), listOf(
                Coordinate3D(1, -1, 1), Coordinate3D(2, -2, 2), Coordinate3D(3, -3, 3),
                Coordinate3D(2, -1, 3), Coordinate3D(-5, 4, -6), Coordinate3D(-8, -7, 0)
            )
        )
        assertTrue(allOrientations.contains(expected1))

        val expected2 = Scanner(
            1, Coordinate3D(0, 0, 0), listOf(
                Coordinate3D(-1, -1, -1), Coordinate3D(-2, -2, -2), Coordinate3D(-3, -3, -3),
                Coordinate3D(-1, -3, -2), Coordinate3D(4, 6, 5), Coordinate3D(-7, 0, 8)
            )
        )
        assertTrue(allOrientations.contains(expected2))

        val expected3 = Scanner(
            1, Coordinate3D(0, 0, 0), listOf(
                Coordinate3D(1, 1, -1), Coordinate3D(2, 2, -2), Coordinate3D(3, 3, -3),
                Coordinate3D(1, 3, -2), Coordinate3D(-4, -6, 5), Coordinate3D(7, 0, 8)
            )
        )
        assertTrue(allOrientations.contains(expected3))

        val expected4 = Scanner(
            1, Coordinate3D(0, 0, 0), listOf(
                Coordinate3D(1, 1, 1), Coordinate3D(2, 2, 2), Coordinate3D(3, 3, 3),
                Coordinate3D(3, 1, 2), Coordinate3D(-6, -4, -5), Coordinate3D(0, 7, -8)
            )
        )
        assertTrue(allOrientations.contains(expected4))
    }

    @Test
    fun normalize1() {
        val scanner1 =
            Scanner(1, Coordinate3D(0, 0, 0), listOf(Coordinate3D(-500, 1000, -1500), Coordinate3D(1501, 0, -500)))
        val scanner2 =
            Scanner(2, Coordinate3D(0, 0, 0), listOf(Coordinate3D(-1000, 1000, -1000), Coordinate3D(1001, 0, 0)))
        val expected = Scanner(2, Coordinate3D(500, 0, -500), scanner1.beacons)
        val actual = BeaconScanner.normalize(2, scanner1, scanner2)
        assertEquals(expected, actual)
    }

    @Test
    fun normalize2() {
        val reference = BeaconScanner.scanners[0]
        val rotations = BeaconScanner.scanners[1].allOrientations()
        val normalized = rotations.mapNotNull { BeaconScanner.normalize(12, reference, it) }.single()
        val actualShared = reference.beacons.intersect(normalized.beacons.toSet())
        val expectedShared = setOf(
            Coordinate3D(-618, -824, -621),
            Coordinate3D(-537, -823, -458),
            Coordinate3D(-447, -329, 318),
            Coordinate3D(404, -588, -901),
            Coordinate3D(544, -627, -890),
            Coordinate3D(528, -643, 409),
            Coordinate3D(-661, -816, -575),
            Coordinate3D(390, -675, -793),
            Coordinate3D(423, -701, 434),
            Coordinate3D(-345, -311, 381),
            Coordinate3D(459, -707, 401),
            Coordinate3D(-485, -357, 347)
        )
        assertEquals(expectedShared, actualShared)
    }

    @Test
    fun countBeacons() {
        assertEquals(79, BeaconScanner.countBeacons())
    }

    @Test
    fun manhattanDistance() {
        assertEquals(3621, BeaconScanner.manhattanDistance())
    }
}
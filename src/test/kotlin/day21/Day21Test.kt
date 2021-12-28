package day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Day21Test {

    @Test
    fun dice() {
        assertEquals(739785, Day21.playDice(4, 8))
    }

    @Test
    fun move() {
        assertEquals(3, Day21.move(1, 2))
        assertEquals(10, Day21.move(1, 9))
        assertEquals(1, Day21.move(1, 10))
        assertEquals(4, Day21.move(1, 33))
    }

    @Test
    fun quantum() {
        assertEquals(BigInteger.valueOf(444356092776315), Day21.quantumDice(4, 8))
    }
}
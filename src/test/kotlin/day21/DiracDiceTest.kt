package day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class DiracDiceTest {

    @Test
    fun dice() {
        assertEquals(739785, DiracDice.playDice(4, 8))
    }

    @Test
    fun move() {
        assertEquals(3, DiracDice.move(1, 2))
        assertEquals(10, DiracDice.move(1, 9))
        assertEquals(1, DiracDice.move(1, 10))
        assertEquals(4, DiracDice.move(1, 33))
    }

    @Test
    fun quantum() {
        assertEquals(BigInteger.valueOf(444356092776315), DiracDice.quantumDice(4, 8))
    }
}
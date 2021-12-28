package day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ArithmeticLogicUnitTest {

    @Test
    fun alu1() {
        val instructions = listOf(Inp("x"), Mul("x", "-1"))
        val vars1 = Alu().runProgram(listOf(3), instructions)
        assertEquals(-3, vars1.x)
        val vars2 = Alu().runProgram(listOf(-6), instructions)
        assertEquals(6, vars2.x)
    }


    @Test
    fun alu2() {
        val instructions = listOf(Inp("z"), Inp("x"), Mul("z", "3"), Eql("z", "x"))
        val vars1 = Alu().runProgram(listOf(3, 9), instructions)
        assertEquals(1, vars1.z)
        val vars2 = Alu().runProgram(listOf(0, 1), instructions)
        assertEquals(0, vars2.z)
    }

    @Test
    fun alu3() {
        val instructions = listOf(
            Inp("w"),
            Add("z", "w"),
            Mod("z", "2"),
            Div("w", "2"),
            Add("y", "w"),
            Mod("y", "2"),
            Div("w", "2"),
            Add("x", "w"),
            Mod("x", "2"),
            Div("w", "2"),
            Mod("w", "2")
        )
        val vars1 = Alu().runProgram(listOf("1101".toInt(2)), instructions)
        assertEquals(1, vars1.z)
        assertEquals(0, vars1.y)
        assertEquals(1, vars1.x)
        assertEquals(1, vars1.w)

        val vars2 = Alu().runProgram(listOf("1000101101".toInt(2)), instructions)
        assertEquals(1, vars2.z)
        assertEquals(0, vars2.y)
        assertEquals(1, vars2.x)
        assertEquals(1, vars2.w)
    }

    @Test
    fun validateLargestModelNumber() {
        val input = listOf(7, 9, 1, 9, 7, 9, 1, 9, 9, 9, 3, 9, 8, 5)
        val alu = Alu()
        val vars = alu.runProgram(input, ArithmeticLogicUnit.monadInstructions)
        assertEquals(0, vars.z)
    }

    @Test
    fun validateSmallestModelNumber() {
        val input = listOf(1, 3, 1, 9, 1, 9, 1, 3, 5, 7, 1, 2, 1, 1)
        val alu = Alu()
        val vars = alu.runProgram(input, ArithmeticLogicUnit.monadInstructions)
        assertEquals(0, vars.z)
    }
}
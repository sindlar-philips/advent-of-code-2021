package day25

import day25.SeaCucumber.flip
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SeaCucumberTest {

    @Test
    fun findEquilibrium() {
        assertEquals(58, SeaCucumber.stepsForSeaCucumberEquilibrium())
    }

    @Test
    fun flip() {
        val cucumbers = listOf(
            "v>.",
            "v>."
        )
        val flipped = cucumbers.flip()
        val expected = listOf(
            "vv",
            ">>",
            ".."
        )
        assertEquals(expected, flipped)
        assertEquals(cucumbers, flipped.flip())
    }

    @Test
    fun right() {
        val cucumbers = listOf("...>>>>>...")
        val oneRight = SeaCucumber.right(cucumbers, '>')
        assertEquals(listOf("...>>>>.>.."), oneRight)
        val twoRight = SeaCucumber.right(oneRight, '>')
        assertEquals(listOf("...>>>.>.>."), twoRight)
    }

    @Test
    fun move() {
        val cucumbers = listOf(
            "...>...",
            ".......",
            "......>",
            "v.....>",
            "......>",
            ".......",
            "..vvv.."
        )
        val afterOneStep = listOf(
            "..vv>..",
            ".......",
            ">......",
            "v.....>",
            ">......",
            ".......",
            "....v.."
        )
        assertEquals(afterOneStep, SeaCucumber.move(cucumbers))
        val afterTwoSteps = listOf(
            "....v>.",
            "..vv...",
            ".>.....",
            "......>",
            "v>.....",
            ".......",
            "......."
        )
        assertEquals(afterTwoSteps, SeaCucumber.move(afterOneStep))
        val afterThreeSteps = listOf(
            "......>",
            "..v.v..",
            "..>v...",
            ">......",
            "..>....",
            "v......",
            "......."
        )
        assertEquals(afterThreeSteps, SeaCucumber.move(afterTwoSteps))
        val afterFourSteps = listOf(
            ">......",
            "..v....",
            "..>.v..",
            ".>.v...",
            "...>...",
            ".......",
            "v......"
        )
        assertEquals(afterFourSteps, SeaCucumber.move(afterThreeSteps))
    }
}
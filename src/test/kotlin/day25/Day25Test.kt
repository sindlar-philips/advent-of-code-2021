package day25

import day25.Day25.flip
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25Test {

    @Test
    fun findEquilibrium() {
        assertEquals(58, Day25.stepsForSeaCucumberEquilibrium())
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
        val oneRight = Day25.right(cucumbers, '>')
        assertEquals(listOf("...>>>>.>.."), oneRight)
        val twoRight = Day25.right(oneRight, '>')
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
        assertEquals(afterOneStep, Day25.move(cucumbers))
        val afterTwoSteps = listOf(
            "....v>.",
            "..vv...",
            ".>.....",
            "......>",
            "v>.....",
            ".......",
            "......."
        )
        assertEquals(afterTwoSteps, Day25.move(afterOneStep))
        val afterThreeSteps = listOf(
            "......>",
            "..v.v..",
            "..>v...",
            ">......",
            "..>....",
            "v......",
            "......."
        )
        assertEquals(afterThreeSteps, Day25.move(afterTwoSteps))
        val afterFourSteps = listOf(
            ">......",
            "..v....",
            "..>.v..",
            ".>.v...",
            "...>...",
            ".......",
            "v......"
        )
        assertEquals(afterFourSteps, Day25.move(afterThreeSteps))
    }
}
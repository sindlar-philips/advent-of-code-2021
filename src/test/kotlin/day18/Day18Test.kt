package day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18Test {

    @Test
    fun reduce() {
        val input = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"
        val expected = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        val actual = Day18.reduce(input)
        assertEquals(expected, actual)
    }

    @Test
    fun explode1() {
        val input = "[[[[[9,8],1],2],3],4]"
        val expected = "[[[[0,9],2],3],4]"
        val actual = Day18.explode(input, "", 0)
        assertEquals(expected, actual)
    }

    @Test
    fun explode2() {
        val input = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"
        val expected = "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"
        val actual = Day18.explode(input, "", 0)
        assertEquals(expected, actual)
    }

    @Test
    fun explodeLeft() {
        val left = "[[4,3],[[7[["
        val actual = Day18.explodeLeft(left, 4)
        val expected = "[[4,3],[[11[["
        assertEquals(expected, actual)
    }

    @Test
    fun explodeRight() {
        val right = "]]2],[4,7]]"
        val actual = Day18.explodeRight(right, 9)
        val expected = "]]11],[4,7]]"
        assertEquals(expected, actual)
    }

    @Test
    fun split() {
        val number = "[[4,5],[11,3]]"
        val actual = Day18.split(number)
        val expected = "[[4,5],[[5,6],3]]"
        assertEquals(expected, actual)
    }

    @Test
    fun snailFishSum1() {
        val numbers = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]")
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", Day18.snailFishSum(numbers))
    }

    @Test
    fun snailFishSum2() {
        val numbers = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]")
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", Day18.snailFishSum(numbers))
    }

    @Test
    fun snailFishSum3() {
        val numbers = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]", "[6,6]")
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", Day18.snailFishSum(numbers))
    }

    @Test
    fun snailFishSum4() {
        val numbers = listOf(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
            "[7,[5,[[3,8],[1,4]]]]",
            "[[2,[2,2]],[8,[8,1]]]",
            "[2,9]",
            "[1,[[[9,3],9],[[9,0],[0,7]]]]",
            "[[[5,[7,4]],7],1]",
            "[[[[4,2],2],6],[8,7]]"
        )
        val n1 = Day18.snailFishSum(listOf(numbers[0], numbers[1]))
        assertEquals("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", n1)
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", Day18.snailFishSum(numbers))
    }

    @Test
    fun doHomeworkPart1() {
        assertEquals(4140, Day18.doHomeworkPart1())
    }

    @Test
    fun parseSnailFishNumber() {
        val left = SnailFishPair(SnailFishLiteral(1), SnailFishLiteral(2))
        val right = SnailFishPair(
            SnailFishPair(SnailFishLiteral(3), SnailFishLiteral(4)),
            SnailFishLiteral(5)
        )
        val expected = SnailFishPair(left, right)
        val actual = Day18.parseSnailFishNumber("[[1,2],[[3,4],5]]")
        assertEquals(expected, actual)
    }

    @Test
    fun splitSnailFishNumber() {
        val number = "[[1,2],[[3,4],5]]"
        val split1 = Day18.splitSnailFishNumber(number)
        assertEquals(Pair("[1,2]", "[[3,4],5]"), split1)
        val split2 = Day18.splitSnailFishNumber(split1.first)
        assertEquals(Pair("1", "2"), split2)
        val split3 = Day18.splitSnailFishNumber(split1.second)
        assertEquals(Pair("[3,4]", "5"), split3)
        val split4 = Day18.splitSnailFishNumber(split3.first)
        assertEquals(Pair("3", "4"), split4)
    }

    @Test
    fun doHomeworkPart2() {
        assertEquals(3993, Day18.doHomeworkPart2())
    }
}
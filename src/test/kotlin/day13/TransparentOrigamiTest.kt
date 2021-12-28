package day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TransparentOrigamiTest {

    @Test
    fun countVisibleDotsAfterFirstFold() {
        assertEquals(17, TransparentOrigami.countVisibleDotsAfterFirstFold())
    }

    @Test
    fun foldAndPrint() {
        TransparentOrigami.foldAndPrint()
    }
}
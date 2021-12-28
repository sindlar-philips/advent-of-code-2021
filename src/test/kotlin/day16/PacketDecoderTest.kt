package day16

import day16.PacketDecoder.getPackets
import day16.PacketDecoder.getVersions
import day16.PacketDecoder.hexToBin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PacketDecoderTest {

    @Test
    fun hexToBin() {
        assertEquals("110100101111111000101000", "D2FE28".hexToBin())
    }

    @Test
    fun getPackets() {
        val bin = "D2FE28".hexToBin()
        val packets = getPackets(bin, listOf())
        val expected = LiteralPacket(6, 2021)
        assertEquals(expected, packets.single())
    }

    @Test
    fun sumVersions1() {
        val bin = "8A004A801A8002F478".hexToBin()
        val packets = getPackets(bin, listOf())
        val versions = getVersions(packets)
        assertEquals(16, versions.sumOf { it })
    }

    @Test
    fun sumVersions2() {
        val bin = "620080001611562C8802118E34".hexToBin()
        val packets = getPackets(bin, listOf())
        val versions = getVersions(packets)
        assertEquals(12, versions.sumOf { it })
    }

    @Test
    fun sumVersions3() {
        val bin = "C0015000016115A2E0802F182340".hexToBin()
        val packets = getPackets(bin, listOf())
        val versions = getVersions(packets)
        assertEquals(23, versions.sumOf { it })
    }

    @Test
    fun sumVersions4() {
        val bin = "A0016C880162017C3686B18A3D4780".hexToBin()
        val packets = getPackets(bin, listOf())
        val versions = getVersions(packets)
        assertEquals(31, versions.sumOf { it })
    }

    @Test
    fun eval() {
        val bin = "38006F45291200".hexToBin()
        val packets = getPackets(bin, listOf())
        assertEquals("lt(10, 20)", packets.single().toString())
        assertEquals(1L, packets.single().eval())
    }
}
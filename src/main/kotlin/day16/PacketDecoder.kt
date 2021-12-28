package day16

import PuzzleData
import kotlin.math.max
import kotlin.math.min

interface Packet {
    val version: Int

    fun eval(): Long =
        when (this) {
            is LiteralPacket -> this.value
            is SumPacket -> this.sub.sumOf { it.eval() }
            is ProductPacket -> this.sub.map { it.eval() }.reduce { a, b -> a * b }
            is MinimumPacket -> this.sub.map { it.eval() }.reduce { a, b -> min(a, b) }
            is MaximumPacket -> this.sub.map { it.eval() }.reduce { a, b -> max(a, b) }
            is GreaterThanPacket -> this.sub.map { it.eval() }.reduce { a, b -> if (a > b) 1 else 0 }
            is LessThanPacket -> this.sub.map { it.eval() }.reduce { a, b -> if (a < b) 1 else 0 }
            is EqualToPacket -> this.sub.map { it.eval() }.reduce { a, b -> if (a == b) 1 else 0 }
            else -> throw Exception("Unknown packet type!")
        }
}

abstract class OperatorPacket(
    override val version: Int,
    open val name: String,
    val sub: List<Packet>
) : Packet {
    override fun toString(): String =
        "$name(${sub.joinToString(", ")})"
}

data class LiteralPacket(override val version: Int, val value: Long) : Packet {
    override fun toString(): String = value.toString()
}

class SumPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "plus", sub)
class ProductPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "times", sub)
class MinimumPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "min", sub)
class MaximumPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "max", sub)
class GreaterThanPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "gt", sub)
class LessThanPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "lt", sub)
class EqualToPacket(version: Int, sub: List<Packet>) : OperatorPacket(version, name = "eq", sub)

object PacketDecoder : Runnable {

    val packets = PuzzleData.load("/day16/hexadecimal-transmission.txt") { parse(it) }

    fun sumVersions(): Int = getVersions(packets).sumOf { it }

    internal fun getVersions(packets: List<Packet>): List<Int> =
        packets.flatMap { packet ->
            when (packet) {
                is LiteralPacket -> listOf(packet.version)
                is OperatorPacket -> listOf(packet.version) + getVersions(packet.sub)
                else -> throw Exception("Unexpected packet: $packet")
            }
        }

    private fun getPacketAndRest(bin: String): Pair<Packet, String> {
        val version = bin.take(3).toInt(2)
        val typeId = bin.drop(3).take(3).toInt(2)
        return when (typeId) {
            4 -> getLiteralPacketAndRest(version, bin.drop(6))
            else -> {
                val lengthTypeId = bin.drop(6).take(1).toInt(2)
                getOperatorPacketAndRest(version, typeId, lengthTypeId, bin.drop(6))
            }
        }
    }

    private fun getLiteralPacketAndRest(version: Int, bin: String): Pair<LiteralPacket, String> {
        fun bitGroups(bin: String, acc: String = ""): String {
            val next = bin.take(5)
            return if (next.startsWith("0")) acc + next.drop(1)
            else bitGroups(bin.drop(5), acc + next.drop(1))
        }

        val groups = bitGroups(bin)
        val numberOfGroups = groups.length / 4
        return Pair(LiteralPacket(version, groups.toLong(2)), bin.drop(numberOfGroups * 5))
    }

    private fun getOperatorPacketAndRest(
        version: Int,
        typeId: Int,
        lengthTypeId: Int,
        bin: String
    ): Pair<OperatorPacket, String> =
        when (lengthTypeId) {
            0 -> {
                val subPacketLength = bin.drop(1).take(15).toInt(2)
                val subPacketString = bin.drop(1 + 15).take(subPacketLength)
                val subPackets = getPackets(subPacketString, listOf())
                Pair(getOperatorPacket(version, typeId, subPackets), bin.drop(1 + 15 + subPacketLength))
            }
            1 -> {
                val numberOfSubPackets = bin.drop(1).take(11).toInt(2)
                fun getSubPacketsAndRest(n: Int, packets: List<Packet>, s: String): Pair<List<Packet>, String> =
                    if (n == 0) Pair(packets, s)
                    else {
                        val (p, r) = getPacketAndRest(s)
                        getSubPacketsAndRest(n - 1, packets + p, r)
                    }
                val (subPackets, rest) = getSubPacketsAndRest(numberOfSubPackets, listOf(), bin.drop(1 + 11))
                Pair(getOperatorPacket(version, typeId, subPackets), rest)
            }
            else -> throw Exception("Unexpected length type ID: $lengthTypeId")
        }

    private fun getOperatorPacket(version: Int, typeId: Int, subPackets: List<Packet>): OperatorPacket =
        when (typeId) {
            0 -> SumPacket(version, subPackets)
            1 -> ProductPacket(version, subPackets)
            2 -> MinimumPacket(version, subPackets)
            3 -> MaximumPacket(version, subPackets)
            5 -> GreaterThanPacket(version, subPackets)
            6 -> LessThanPacket(version, subPackets)
            7 -> EqualToPacket(version, subPackets)
            else -> throw Exception("Unexpected type ID: $typeId")
        }

    internal fun getPackets(bin: String, acc: List<Packet>): List<Packet> =
        if (bin.isEmpty()) acc
        else {
            val (packet, rest) = getPacketAndRest(bin)
            if (rest.indexOfFirst { it == '1' } == -1) acc + packet
            else getPackets(rest, acc + packet)
        }

    internal fun String.hexToBin(): String =
        this.chunked(1).map {
            String.format("%4s", Integer.toBinaryString(it.toInt(16))).replace(" ", "0")
        }.reduce { a, b -> a + b }

    private fun parse(lines: List<String>): List<Packet> {
        val binString = lines.single().hexToBin()
        return getPackets(binString, listOf())
    }

    override fun run() {
        println("Day 16, sum of versions: ${sumVersions()}")
        println("Day 16, expression: ${packets.single().eval()}")
    }
}
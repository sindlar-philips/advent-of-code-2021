package day24

import PuzzleData
import java.util.stream.Collectors

object Day24 {

    internal val monadInstructions = PuzzleData.load("/day24/day24.txt") { parse(it) }

    interface Instruction
    data class Inp(val a: String) : Instruction
    data class Add(val a: String, val b: String) : Instruction
    data class Mul(val a: String, val b: String) : Instruction
    data class Div(val a: String, val b: String) : Instruction
    data class Mod(val a: String, val b: String) : Instruction
    data class Eql(val a: String, val b: String) : Instruction

    data class Vars(val w: Int, val x: Int, val y: Int, val z: Int)

    class Alu(w: Int = 0, x: Int = 0, y: Int = 0, z: Int = 0) {

        private var input = mutableListOf<Int>()
        private val vars = mutableMapOf("w" to w, "x" to x, "y" to y, "z" to z)

        fun runProgram(inputNumbers: List<Int>, instructions: List<Instruction>): Vars {
            input = inputNumbers.toMutableList()
            instructions.forEach { process(it) }
            return Vars(vars["w"]!!, vars["x"]!!, vars["y"]!!, vars["z"]!!)
        }

        private fun process(ins: Instruction) {
            when (ins) {
                is Inp -> vars[ins.a] = input.removeFirst()
                is Add -> vars[ins.a] = vars[ins.a]!! + toInt(ins.b)
                is Mul -> vars[ins.a] = vars[ins.a]!! * toInt(ins.b)
                is Div -> vars[ins.a] = vars[ins.a]!!.floorDiv(toInt(ins.b))
                is Mod -> vars[ins.a] = vars[ins.a]!!.mod(toInt(ins.b))
                is Eql -> vars[ins.a] = if (vars[ins.a]!! == toInt(ins.b)) 1 else 0
                else -> throw Exception("Unexpected instruction: $ins")
            }
        }

        private fun toInt(b: String): Int =
            try {
                b.toInt()
            } catch (_: NumberFormatException) {
                vars[b]!!
            }
    }

    fun findLargestModelNumber(): List<Int> {
        val chunks = monadInstructions.chunked(18)
        val largestNumberSelector = { ns: List<List<Int>> -> ns.maxByOrNull { it.joinToString("") }!! }
        fun search(firstDigit: Int): List<Int> {
            if (firstDigit == 0) throw Exception("No valid model number found!")
            print("Searching for largest model number starting with $firstDigit, number of inputs remaining: ")
            val zToModelNumber = findModelNumber(firstDigit, chunks, largestNumberSelector, mapOf())
            return if (zToModelNumber.containsKey(0)) zToModelNumber[0]!!
            else search(firstDigit - 1)
        }
        return search(9)
    }

    fun findSmallestModelNumber(): List<Int> {
        val chunks = monadInstructions.chunked(18)
        val smallestNumberSelector = { ns: List<List<Int>> -> ns.minByOrNull { it.joinToString("") }!! }
        fun search(firstDigit: Int): List<Int> {
            if (firstDigit == 10) throw Exception("No valid model number found!")
            print("Searching for smallest model number starting with $firstDigit, number of inputs remaining: ")
            val zToModelNumber = findModelNumber(firstDigit, chunks, smallestNumberSelector, mapOf())
            return if (zToModelNumber.containsKey(0)) zToModelNumber[0]!!
            else search(firstDigit + 1)
        }
        return search(1)
    }

    /**
     * Finds model numbers accepted by MONAD, and selects one according to a given selector. The finder applies
     * a simplification based on the insight that the MONAD validation instructions consist of several similar chunks,
     * each of which resets the values of 'x' and 'y', so that the output varies only per values of 'w' (input
     * number) and 'z' (copied over from the previous step). The simplification consists of mapping the 'z' value
     * that is found after applying a chunk of instructions to the model numbers that resulted in this 'z' value,
     * and applying the selection function to only consider relevant model numbers for the next chunk of instructions.
     */
    private fun findModelNumber(
        firstDigit: Int,
        chunks: List<List<Instruction>>,
        selector: (List<List<Int>>) -> List<Int>,
        acc: Map<Int, List<Int>>
    ): Map<Int, List<Int>> {
        val validInputs = (1..9).toList()
        return if (chunks.isEmpty()) {
            print("done!\n")
            acc
        } else if (acc.isEmpty()) {
            print("${chunks.size}, ")
            val chunk = chunks.first()
            val zToPartialModelNumbers = listOf(listOf(firstDigit))
                .groupBy { input -> getZ(0, input.single(), chunk) }
                .mapValues { (_, value) -> selector(value) }
            findModelNumber(firstDigit, chunks.drop(1), selector, zToPartialModelNumbers)
        } else {
            print("${chunks.size}, ")
            val chunk = chunks.first()
            val zToPartialModelNumbers = acc.flatMap { (oldZ, partialModelNumber) ->
                validInputs.parallelStream().map { input ->
                    val newZ = getZ(oldZ, input, chunk)
                    newZ to partialModelNumber + input
                }.collect(Collectors.toList())
            }.groupBy { it.first }.mapValues { (_, pairs) -> pairs.map { it.second } }
                .mapValues { (_, value) -> selector(value) }
            findModelNumber(firstDigit, chunks.drop(1), selector, zToPartialModelNumbers)
        }
    }

    private fun getZ(initialZ: Int, inputNumber: Int, instructions: List<Instruction>): Int {
        val alu = Alu(z = initialZ)
        val vars = alu.runProgram(listOf(inputNumber), instructions)
        return vars.z
    }

    private fun parse(lines: List<String>): List<Instruction> =
        lines.map { line ->
            val regex = "^([^\\s]+)[\\s]([^\\s]+)[\\s]?([^\\s]*)$".toRegex()
            val matchResult = regex.find(line) ?: throw Exception("No match!")
            val (instruction, a, b) = matchResult.destructured
            when (instruction) {
                "inp" -> Inp(a)
                "add" -> Add(a, b)
                "mul" -> Mul(a, b)
                "div" -> Div(a, b)
                "mod" -> Mod(a, b)
                "eql" -> Eql(a, b)
                else -> throw Exception("Unknown instruction: $line")
            }
        }
}
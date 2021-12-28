package day21

import java.lang.Integer.min
import java.math.BigInteger

data class GameState(val position1: Int, val score1: Int, val position2: Int, val score2: Int, val rolls: Int)

object DiracDice : Runnable {

    fun <T> Iterator<T>.asIterable(): Iterable<T> = object : Iterable<T> {
        private val iter = this@asIterable
        override fun iterator() = iter
    }

    fun playDice(start1: Int, start2: Int): Int {
        val final = states(start1, start2, deterministicDie()).last()
        return min(final.score1, final.score2) * final.rolls
    }

    fun quantumDice(start1: Int, start2: Int): BigInteger {
        val initial = mapOf(GameState(start1, 0, start2, 0, 0) to BigInteger.ONE)
        val final = quantumRoll(initial, true)
        println(final)
        return maxOf(final.first, final.second)
    }

    private fun quantumRoll(
        states: Map<GameState, BigInteger>,
        isReadyPlayerOne: Boolean
    ): Pair<BigInteger, BigInteger> {
        return states.map { (state, nStates) ->
            if (state.score1 >= 21) Pair(nStates, BigInteger.ZERO)
            else if (state.score2 >= 21) Pair(BigInteger.ZERO, nStates)
            else {
                quantumDie().map { (roll, nRolls) ->
                    val nStatesUpd = nStates * nRolls
                    if (isReadyPlayerOne) {
                        val position = move(state.position1, roll)
                        val score = state.score1 + position
                        val stateUpd = GameState(position, score, state.position2, state.score2, 0)
                        quantumRoll(mapOf(stateUpd to nStatesUpd), false)
                    } else {
                        val position = move(state.position2, roll)
                        val score = state.score2 + position
                        val stateUpd = GameState(state.position1, state.score1, position, score, 0)
                        quantumRoll(mapOf(stateUpd to nStatesUpd), true)
                    }
                }.reduce { s1, s2 -> Pair(s1.first + s2.first, s1.second + s2.second) }
            }
        }.reduce { s1, s2 -> Pair(s1.first + s2.first, s1.second + s2.second) }
    }

    private fun quantumDie(): Map<Int, BigInteger> =
        mapOf(
            3 to BigInteger.valueOf(1),
            4 to BigInteger.valueOf(3),
            5 to BigInteger.valueOf(6),
            6 to BigInteger.valueOf(7),
            7 to BigInteger.valueOf(6),
            8 to BigInteger.valueOf(3),
            9 to BigInteger.valueOf(1)
        )

    internal fun move(current: Int, add: Int): Int {
        val new = current + add.mod(10)
        return if (new <= 10) new else new.mod(10)
    }

    private fun deterministicDie() = sequence {
        yieldAll(generateSequence(1) { if (it < 100) it + 1 else 1 })
    }.iterator().asIterable()

    private fun states(start1: Int, start2: Int, die: Iterable<Int>): Sequence<GameState> =
        generateSequence(GameState(start1, 0, start2, 0, 0)) { state ->
            if (state.score1 >= 1000 || state.score2 >= 1000) null
            else {
                val position1 = move(state.position1, die.take(3).sum())
                val score1 = state.score1 + position1
                if (score1 >= 1000) GameState(position1, score1, state.position2, state.score2, state.rolls + 3)
                else {
                    val position2 = move(state.position2, die.take(3).sum())
                    val score2 = state.score2 + position2
                    GameState(position1, score1, position2, score2, state.rolls + 6)
                }
            }
        }

    override fun run() {
        println("Day 21: play dice: ${playDice(5, 6)}")
        println("Day 21: play quantum dice: ${quantumDice(5, 6)}")
    }
}
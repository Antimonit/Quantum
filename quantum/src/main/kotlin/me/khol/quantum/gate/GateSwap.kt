package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

object GateSwap : Gate() {

    override val qubits = 2
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO, ZERO, ZERO),
            listOf(ZERO, ZERO, ONE, ZERO),
            listOf(ZERO, ONE, ZERO, ZERO),
            listOf(ZERO, ZERO, ZERO, ONE)
        )
    )
}

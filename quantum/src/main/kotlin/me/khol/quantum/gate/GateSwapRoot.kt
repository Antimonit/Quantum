package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.I
import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

object GateSwapRoot : Gate() {

    override val qubits = 2
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO, ZERO, ZERO),
            listOf(ZERO, (ONE + I) / 2, (ONE - I) / 2, ZERO),
            listOf(ZERO, (ONE - I) / 2, (ONE + I) / 2, ZERO),
            listOf(ZERO, ZERO, ZERO, ONE)
        )
    )
}

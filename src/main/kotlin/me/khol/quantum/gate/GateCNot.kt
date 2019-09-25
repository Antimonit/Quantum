package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

object GateCNot : Gate {

    override val qubits = 2
    override val matrix = Matrix(
        arrayOf(
            arrayOf(ONE, ZERO, ZERO, ZERO),
            arrayOf(ZERO, ONE, ZERO, ZERO),
            arrayOf(ZERO, ZERO, ZERO, ONE),
            arrayOf(ZERO, ZERO, ONE, ZERO)
        )
    )
}
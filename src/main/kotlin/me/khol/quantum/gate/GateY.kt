package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.I
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

object GateY : Gate {

    override val qubits = 1
    override val matrix = Matrix(
        arrayOf(
            arrayOf(ZERO, -I),
            arrayOf(I, ZERO)
        )
    )
}

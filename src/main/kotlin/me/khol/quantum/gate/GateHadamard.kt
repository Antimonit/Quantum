package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Matrix
import kotlin.math.sqrt

object GateHadamard : Gate {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ONE),
            listOf(ONE, -ONE)
        )
    ) * sqrt(0.5)
}

package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

typealias GateToffoli = GateCCNot

object GateCCNot : Gate() {

    override val qubits = 3
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
            listOf(ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO),
            listOf(ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO, ZERO),
            listOf(ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO, ZERO),
            listOf(ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO, ZERO),
            listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO, ZERO),
            listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE),
            listOf(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ONE, ZERO)
        )
    )
}
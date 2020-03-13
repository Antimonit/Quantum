package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Complex.Companion.I
import me.khol.quantum.math.Matrix

/**
 * Phase shift gate with 90° rotation.
 *
 *         | 1       0      |   | 1   0 |
 *     S = |                | = |       |
 *         | 0   e^(i*PI/2) |   | 0   i |
 *
 * @see GateT Phase shift gate with 45° rotation
 * @see GateZ Phase shift gate with 180° rotation
 * @see GatePhase Phase shift gate with custom rotation
 */
object GateS : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO),
            listOf(ZERO, I)
        )
    )
}

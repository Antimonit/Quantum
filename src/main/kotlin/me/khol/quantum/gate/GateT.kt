package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix
import kotlin.math.PI

/**
 * Phase shift gate with 45° rotation.
 *
 *         | 1       0      |
 *     T = |                |
 *         | 0   e^(i*PI/4) |
 *
 * @see GateS Phase shift gate with 90° rotation
 * @see GateZ Phase shift gate with 180° rotation
 * @see GatePhase Phase shift gate with custom rotation
 */
object GateT : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO),
            listOf(ZERO, Complex.fromPolar(PI / 4))
        )
    )
}

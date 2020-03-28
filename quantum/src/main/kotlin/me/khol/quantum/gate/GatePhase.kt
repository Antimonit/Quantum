package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

/**
 * Phase shift gate with custom rotation.
 *
 *         | 1        0      |
 *     R = |                 |
 *         | 0   e^(i*phase) |
 *
 * @see GateT Phase shift gate with 45° rotation
 * @see GateS Phase shift gate with 90° rotation
 * @see GateZ Phase shift gate with 180° rotation
 */
class GatePhase(radians: Double) : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO),
            listOf(ZERO, Complex.fromPolar(radians))
        )
    )
}

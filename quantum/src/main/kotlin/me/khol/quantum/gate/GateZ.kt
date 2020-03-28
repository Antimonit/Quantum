package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

/**
 * Phase shift gate with 180° rotation.
 *
 *         | 1      0     |   | 1   0 |
 *     Z = |              | = |       |
 *         | 0   e^(i*PI) |   | 0  -1 |
 *
 * @see GateX
 * @see GateY
 * @see GateT Phase shift gate with 45° rotation
 * @see GateS Phase shift gate with 90° rotation
 * @see GatePhase Phase shift gate with custom rotation
 */
object GateZ : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(ONE, ZERO),
            listOf(ZERO, -ONE)
        )
    )
}

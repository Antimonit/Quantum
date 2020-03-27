package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

/**
 * Custom rotation about X axis.
 *
 *          | e^(-i*phase/2)         0       |
 *     Rz = |                                |
 *          |        0         e^(i*phase/2) |
 *
 * @see GateRx Custom rotation about X axis.
 * @see GateRy Custom rotation about Y axis.
 */
class GateRz(radians: Double) : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(Complex.fromPolar(-radians / 2), ZERO),
            listOf(ZERO, Complex.fromPolar(radians / 2))
        )
    )
}

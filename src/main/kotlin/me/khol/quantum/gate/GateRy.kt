package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import kotlin.math.cos
import kotlin.math.sin

/**
 * Custom rotation about Y axis.
 *
 *          | cos(phase/2)   -sin(phase/2) |
 *     Rx = |                              |
 *          | sin(phase/2)    cos(phase/2) |
 *
 * @see GateRx Custom rotation about X axis.
 * @see GateRz Custom rotation about Z axis.
 */
class GateRy(radians: Double) : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(Complex(cos(radians / 2)), Complex(-sin(radians / 2))),
            listOf(Complex(sin(radians / 2)), Complex(cos(radians / 2)))
        )
    )
}

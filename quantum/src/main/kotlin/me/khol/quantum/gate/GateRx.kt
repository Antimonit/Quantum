package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import kotlin.math.cos
import kotlin.math.sin

/**
 * Custom rotation about X axis.
 *
 *          |    cos(phase/2)   -i*sin(phase/2) |
 *     Rx = |                                   |
 *          | -i*sin(phase/2)      cos(phase/2) |
 *
 * @see GateRy Custom rotation about Y axis.
 * @see GateRz Custom rotation about Z axis.
 */
class GateRx(radians: Double) : Gate() {

    override val qubits = 1
    override val matrix = Matrix(
        listOf(
            listOf(Complex(re = cos(radians / 2)), Complex(im = -sin(radians / 2))),
            listOf(Complex(im = -sin(radians / 2)), Complex(re = cos(radians / 2)))
        )
    )
}

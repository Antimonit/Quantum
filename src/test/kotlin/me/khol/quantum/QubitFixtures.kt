package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.Complex
import kotlin.math.sqrt

object QubitFixtures {

    // Qubits at the ends of the Z-axis
    val one = ONE
    val zero = ZERO

    // Qubits at the ends of the X-axis
    val plus = Qubit((ZERO.ket + ONE.ket) * sqrt(0.5))
    val minus = Qubit((ZERO.ket - ONE.ket) * sqrt(0.5))

    // Qubits at the ends of the Y-axis
    val right = Qubit((ZERO.ket + ONE.ket * Complex.I) * sqrt(0.5))
    val left = Qubit((ZERO.ket - ONE.ket * Complex.I) * sqrt(0.5))
}

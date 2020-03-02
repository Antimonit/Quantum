package me.khol.quantum.gate

import me.khol.quantum.math.Matrix
import kotlin.math.pow

object GateIdentity : Gate() {

    override val qubits: Int = 1
    override val matrix: Matrix = Matrix.identity(2 pow qubits)
}

// Fake constructor
@Suppress("FunctionName")
fun GateIdentity(qubits: Int): Gate = object : Gate() {
    override val qubits: Int = qubits
    override val matrix: Matrix = Matrix.identity(2 pow qubits)
}

private infix fun Int.pow(exponent: Int): Int {
    return toDouble().pow(exponent).toInt()
}

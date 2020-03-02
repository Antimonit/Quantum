package me.khol.quantum.gate

import me.khol.quantum.math.Matrix

object GateIdentity1 : Gate() {

    override val qubits: Int = 1
    override val matrix: Matrix = Matrix.identity(2 pow qubits)
}
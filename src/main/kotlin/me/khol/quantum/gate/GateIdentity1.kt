package me.khol.quantum.gate

import me.khol.quantum.math.Matrix

object GateIdentity1 : Gate {

    override val qubits = 1
    override val matrix = Matrix.identity(2)
}

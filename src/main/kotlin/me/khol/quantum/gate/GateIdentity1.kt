package me.khol.quantum.gate

object GateIdentity1 : Gate {

    override val qubits = 1
    override val matrix = Gate.identity(qubits)
}

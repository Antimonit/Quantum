package me.khol.quantum

import me.khol.quantum.gate.Gate
import me.khol.quantum.gate.GateIdentity
import me.khol.quantum.gate.withOrder

@DslMarker
annotation class AlgorithmTagMarker

@AlgorithmTagMarker
class Algorithm(private val qubitCount: Int) {

    private val allSteps: MutableList<Gate> = mutableListOf()

    val gate: Gate get() = allSteps.reduce { acc, gate -> gate * acc }

    operator fun Gate.get(vararg qubits: Int) {
        val order = qubits.toList() + List(qubitCount) { it }.filter { it !in qubits }
        allSteps.add(if (this.qubits < qubitCount) {
            this tensor GateIdentity(qubitCount - this.qubits)
        } else {
            this
        }.withOrder(order))
    }
}

fun algorithm(qubitCount: Int, action: Algorithm.() -> Unit): Algorithm {
    return Algorithm(qubitCount).apply {
        action()
    }
}

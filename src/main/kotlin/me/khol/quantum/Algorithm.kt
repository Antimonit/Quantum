package me.khol.quantum

import me.khol.quantum.gate.*

@DslMarker
annotation class AlgorithmTagMarker

@AlgorithmTagMarker
class Algorithm(private val qubitCount: Int) {

    private val allSteps: MutableList<Step> = mutableListOf()

    fun asGate(): Gate = allSteps.map { it.gate }.reduce { acc, gate -> gate * acc }

    fun run(register: Register): Register = allSteps.fold(register) { acc, step -> step.gate * acc }

    operator fun Gate.get(vararg qubits: Int): Step {
        val step = Step(this, qubitCount, *qubits)
        allSteps.add(step)
        return step
    }

    operator fun Step.plus(other: Step): Step {
        val step = Step(this, other)
        allSteps.remove(this)
        allSteps.remove(other)
        allSteps.add(step)
        return step
    }
}

class Step {

    val gate: Gate
    private val qubitsUsed: Set<Int>

    constructor(gate: Gate, qubitCount: Int, vararg qubits: Int) {
        qubitsUsed = qubits.toSet()
        val order = qubits.toList() + List(qubitCount) { it }.filter { it !in qubits }
        this.gate = if (gate.qubits < qubitCount) {
            gate tensor GateIdentity(qubitCount - gate.qubits)
        } else {
            gate
        }.withOrder(order)
    }

    constructor(first: Step, other: Step) {
        val intersect = first.qubitsUsed.intersect(other.qubitsUsed)
        check(intersect.isEmpty()) {
            "Cannot apply a gate to qubit(s) $intersect twice in a single step."
        }
        qubitsUsed = first.qubitsUsed + other.qubitsUsed
        gate = other.gate * first.gate
    }
}

fun algorithm(qubitCount: Int, action: Algorithm.() -> Unit): Algorithm {
    return Algorithm(qubitCount).apply {
        action()
    }
}

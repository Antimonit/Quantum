package me.khol.quantum

import me.khol.quantum.gate.*

@DslMarker
annotation class AlgorithmTagMarker

interface Algorithm {

    operator fun Gate.get(vararg qubits: Int)

    fun step(action: Step.() -> Unit)
}

/**
 * Every gate applied via [Gate.get] or [step] is directly applied to [register].
 */
@AlgorithmTagMarker
class RunnableAlgorithm(private var register: Register) : Algorithm {

    fun resultRegister(): Register = register

    override operator fun Gate.get(vararg qubits: Int) = step { get(*qubits) }

    override fun step(action: Step.() -> Unit) {
        register = Step(register.qubits).apply { action() }.gate * register
    }
}

/**
 * Combines all gates applied via [Gate.get] or [step] into a single gate.
 *
 * Useful for verification of algorithms that they do the same operation as another algorithm
 * or a single gate.
 */
@AlgorithmTagMarker
class PrecomputedAlgorithm(private val qubitCount: Int) : Algorithm {

    private var gate: Gate = GateIdentity(qubitCount)

    fun resultGate(): Gate = gate

    override operator fun Gate.get(vararg qubits: Int) = step { get(*qubits) }

    override fun step(action: Step.() -> Unit) {
        gate *= Step(qubitCount).apply { action() }.gate
    }
}

@AlgorithmTagMarker
class Step(private val qubitCount: Int) {

    var gate = GateIdentity(qubitCount)
    private val qubitsUsed = mutableSetOf<Int>()

    operator fun Gate.get(vararg qubits: Int) {
        val intersect = qubitsUsed.intersect(qubits.toSet())
        check(intersect.isEmpty()) {
            "Cannot apply a gate to qubit(s) $intersect twice in a single step."
        }
        qubitsUsed += qubits.toSet()
        gate *= if (this.qubits < qubitCount) {
            this tensor GateIdentity(qubitCount - this.qubits)
        } else {
            this
        }.withOrder(qubits.toList() + List(qubitCount) { it }.filter { it !in qubits })
    }
}

fun runnableAlgorithm(register: Register, action: RunnableAlgorithm.() -> Unit): Register {
    return RunnableAlgorithm(register).apply {
        action()
    }.resultRegister()
}

fun gateAlgorithm(qubitCount: Int, action: PrecomputedAlgorithm.() -> Unit): Gate {
    return PrecomputedAlgorithm(qubitCount).apply {
        action()
    }.resultGate()
}

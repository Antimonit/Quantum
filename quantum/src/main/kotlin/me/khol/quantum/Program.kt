package me.khol.quantum

import me.khol.quantum.gate.*
import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import kotlin.math.sqrt

@DslMarker
annotation class ProgramTagMarker

interface Program {

    operator fun Gate.get(vararg qubits: Int) = step { get(*qubits) }

    operator fun Gate.get(range: IntRange) = step { get(range) }

    fun step(action: Step.() -> Unit)
}

/**
 * Every gate applied via [Gate.get] or [step] is directly applied to [register].
 */
@ProgramTagMarker
class RunnableProgram(private val qubitCount: Int) : Program {

    var register = Register(List(qubitCount) { Qubit.ZERO })
        private set

    constructor(register: Register) : this(register.qubits) {
        this.register = register
    }

    override fun step(action: Step.() -> Unit) {
        register = Step(qubitCount).apply { action() }.gate * register
    }

    /**
     * Measures qubits specified by [qubitIndices] and collapses the state of the register so that
     * repeated measurement of such qubits will stay the same. This may possibly change the state of
     * other qubits as well in case some of the measured qubits were entangled to unmeasured qubits.
     *
     * Internally performs the following steps:
     * 1) Measures all qubits in the register.
     * 2) Create a mask for every combination of |0> and |1> where the probability of every
     *    combination that is no longer possible to observe (due to entanglement) is zero.
     * 3) Apply the mask to the original register and create a new one where impossible
     *    observation are removed.
     * 4) Scale the remaining probabilities to compensate for the erased combinations.
     *
     * TODO: Verify there are no complex situations where simple scaling of probabilities does not
     * TODO: work. For example a non-fully entangled pair.
     */
    fun measureAndCollapse(vararg qubitIndices: Int): List<Qubit> {
        val measurement = register.measure()
        val erasureMask = measurement.erasureMask(*qubitIndices)
        val erasedMatrix = register.matrix.mapIndexed { row: Int, col: Int, value: Complex ->
            value * erasureMask[row, col]
        }
        val scale = sqrt(erasedMatrix.sumByDouble { it.square })
        register = Register(erasedMatrix / scale)
        return measurement.filterIndexed { index, _ -> index in qubitIndices }
    }

    /**
     * Creates a new Matrix with identical dimensions where each element is mapped
     * by [transform] operation.
     */
    private fun Matrix.mapIndexed(transform: (row: Int, col: Int, value: Complex) -> Complex): Matrix {
        return Matrix(List(rows) { row ->
            List(cols) { col ->
                transform(row, col, this[row, col])
            }
        })
    }

    private fun List<Qubit>.erasureMask(vararg qubitIndices: Int): Matrix {
        val keepProbability = Matrix(List(2) { listOf(Complex.ONE) })
        return foldIndexed(Matrix(1, 1, Complex.ONE)) { index, acc, qubit ->
            acc tensor if (index in qubitIndices) qubit.ket else keepProbability
        }
    }
}

/**
 * Combines all gates applied via [Gate.get] or [step] into a single gate.
 *
 * Useful for verification of programs that they do the same operation as another program
 * or a single gate.
 */
@ProgramTagMarker
class PrecomputedProgram(private val qubitCount: Int) : Program {

    var gate: Gate = GateIdentity(qubitCount)
        private set

    override fun step(action: Step.() -> Unit) {
        gate = Step(qubitCount).apply { action() }.gate * gate
    }
}

@ProgramTagMarker
class Step(private val qubitCount: Int) {

    var gate = GateIdentity(qubitCount)
    private val qubitsUsed = mutableSetOf<Int>()

    operator fun Gate.get(range: IntRange) {
        return get(*range.toList().toIntArray())
    }

    operator fun Gate.get(vararg qubits: Int) {
        val intersect = qubitsUsed.intersect(qubits.toSet())
        check(intersect.isEmpty()) {
            "Cannot apply a gate to qubit(s) $intersect twice in a single step."
        }
        qubitsUsed += qubits.toSet()
        gate = if (this.qubits < qubitCount) {
            this tensor GateIdentity(qubitCount - this.qubits)
        } else {
            this
        }.withOrder(qubits.toList() + List(qubitCount) { it }.filter { it !in qubits }) * gate
    }
}

fun program(qubitCount: Int, action: RunnableProgram.() -> Unit): Register {
    return RunnableProgram(qubitCount).apply {
        action()
    }.register
}

fun program(register: Register, action: RunnableProgram.() -> Unit): Register {
    return RunnableProgram(register).apply {
        action()
    }.register
}

fun gate(qubitCount: Int, action: PrecomputedProgram.() -> Unit): Gate {
    return PrecomputedProgram(qubitCount).apply {
        action()
    }.gate
}

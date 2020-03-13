package me.khol.quantum.gate

import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix

class GateControlled(controlled: Gate) : Gate() {

    override val qubits = controlled.qubits + 1
    override val matrix = Matrix(with(controlled.matrix) {
        List(rows * 2) { row ->
            List(cols * 2) { col ->
                when {
                    row >= rows && col >= cols -> this[row - rows, col - cols]
                    row == col -> ONE
                    else -> ZERO
                }
            }
        }
    })
}
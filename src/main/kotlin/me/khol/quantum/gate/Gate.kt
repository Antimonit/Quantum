package me.khol.quantum.gate

import me.khol.quantum.math.Matrix

interface Gate {

    val qubits: Int
    val matrix: Matrix
}

package me.khol.quantum

object RegisterFixtures {

    // Qubits at the ends of the Z-axis
    val one = Register(QubitFixtures.one)
    val zero = Register(QubitFixtures.zero)

    // Qubits at the ends of the X-axis
    val plus = Register(QubitFixtures.plus)
    val minus = Register(QubitFixtures.minus)

    // Qubits at the ends of the Y-axis
    val right = Register(QubitFixtures.right)
    val left = Register(QubitFixtures.left)
}

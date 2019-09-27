package me.khol.quantum.gate

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GateCNotTest {

    @DisplayName("CNot gate inverts target qubit if control qubit is one.")
    @Test
    fun cNotGateInvertsIfControlIsOne() {
        val input = ONE x ONE
        val matrix = GateCNot.matrix
        val output = matrix * input
        println("input")
        println(input)
        println("output")
        println(output)
    }

    @Test
    fun b() {
        val input = ZERO x ONE
        val matrix = GateCNot.matrix
        val output = matrix * input
        println("input")
        println(input)
        println("output")
        println(output)
    }
}
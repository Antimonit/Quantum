package me.khol.quantum.gate

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Mathematical properties of gates")
class GateTest {

    @DisplayName("Gate should be reversible")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
//        GateS::class, // does not perform full 180° rotation
//        GateT::class, // does not perform full 180° rotation
        GateHadamard::class,
        GateIdentity::class,
        GateCNot::class,
        GateSwap::class
    ])
    fun <T: Gate> isGateReversible(clazz: Class<T>) {
        val gate = clazz.kotlin.objectInstance!!
        val squared = gate * gate
        assertThat(squared, equalTo(GateIdentity(gate.qubits)))
    }

    @DisplayName("Gates should be normal")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateS::class,
        GateT::class,
        GateHadamard::class,
        GateIdentity::class,
        GateCNot::class,
        GateSwap::class
    ])
    fun <T: Gate> isGateNormal(clazz: Class<T>) {
        val gate = clazz.kotlin.objectInstance!!
        val matrix = gate.matrix
        val adjoint = matrix.conjugate().transpose()
        assertThat(matrix * adjoint, equalTo(adjoint * matrix))
    }

    /**
     * All linear operators that correspond to quantum logic gates must be unitary.
     *
     * That is, if a complex matrix U is unitary, then it must be true that inverse of U is
     * equal to conjugate transpose of U.
     *
     * Unitary operators preserve the inner product of two vectors, geometrically
     * preserving the lengths of the vectors and the angle between them:
     */
    @DisplayName("Gates should be unitary")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateS::class,
        GateT::class,
        GateHadamard::class,
        GateIdentity::class,
        GateCNot::class,
        GateSwap::class
    ])
    fun <T: Gate> isGateUnitary(clazz: Class<T>) {
        val gate = clazz.kotlin.objectInstance!!
        val matrix = gate.matrix
        val adjoint = matrix.conjugate().transpose()
        assertThat(matrix * adjoint, equalTo(GateIdentity(gate.qubits).matrix))
        assertThat(adjoint * matrix, equalTo(GateIdentity(gate.qubits).matrix))
    }
}

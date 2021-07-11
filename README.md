# Quantum computing in Kotlin
[![Download](https://maven-badges.herokuapp.com/maven-central/io.github.antimonit/quantum/badge.svg) ](https://search.maven.org/artifact/io.github.antimonit/quantum/1.0.0/jar)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.com/Antimonit/Quantum.svg?branch=master)](https://travis-ci.com/Antimonit/Quantum)
[![Code Coverage](https://codecov.io/gh/Antimonit/Quantum/branch/master/graph/badge.svg)](https://codecov.io/gh/Antimonit/Quantum)

Kotlin framework for writing quantum algorithms using QASM-like syntax.

Because the code is still compiled by Kotlin compiler we are not limited by QASM language features.
We can use classical variables, loops, functions, etc. There is no need for binary-controlled gates
as we can utilize standard `if` condition.

# Examples

## Quantum Teleportation

![Quantum Teleportation](images/Quantum%20Teleportation.svg)

```kotlin
val message = ZERO // or ONE or random()

program(Register(message, ZERO, ZERO)) {
    // Entangle qubits q1 and q2 to form a fully entangled bell state
    Hadamard[2]
    CNot[2, 1]

    // Entangle message/state held by q0 with the rest.
    CNot[0, 1]
    Hadamard[0]

    // Measuring the first two qubits will change the state of the third qubit because
    // of the entanglement. This will teleport the message from the first qubit to the
    // third qubit.
    val (secret, shared) = measureAndCollapse(0, 1)

    // The last qubit can be in one of four superpositions now. We can use qubits
    // measured in the previous step to conditionally apply some gates to put it
    // into one specific superposition.
    if (shared == ONE) X[2]
    if (secret == ONE) Z[2]
}

// At this point the third qubit will be in the same superposition as the message
// defined at the beginning.
```

## Grover's Algorithm

![Grover's Algorithm](images/Grover's%20Algorithm.svg)

```kotlin
val oracle: Gate = oracleGate(ONE, ONE, ZERO)

program(3) {
    // Initialization
    step { H[0]; H[1]; H[2] }

    repeat(2) {
        // Oracle
        oracle[0, 1, 2]

        // Diffusion
        step { H[0]; H[1]; H[2] }
        step { X[0]; X[1]; X[2] }
        step { C(C(Z))[0, 1, 2] }
        step { X[0]; X[1]; X[2] }
        step { H[0]; H[1]; H[2] }
    }

    // Measurement
}.measure() // Returns [ONE, ONE, ZERO] with high probability
```

## Quantum Adder

![Quantum Adder](images/Quantum%20Adder.svg)

```kotlin
// We can even utilize entanglement to calculate two separate additions at the same time.

program(4) {
    // Prepare input
    H[0]
    CNot[0, 1]

    // Encode two combinations of input into an entangled pair.
    // By passing ∣00⟩ + ∣11⟩ / sqrt(2) as input to the adder
    // we are essentially calculating both 0 + 0 and 1 + 1.

    // Full Adder
    CCNot[0, 1, 3]
    CNot[0, 1]
    CCNot[1, 2, 3]
    CNot[1, 2]
    CNot[0, 1]
}

// At this point it is equally likely to observe result of 0 + 0 = 00 and 1 + 1 = 10.
```

# Building blocks

## [Complex numbers](quantum/src/main/kotlin/me/khol/quantum/math/Complex.kt)
<details>
<summary>Details</summary>

Complex numbers can be created either from cartesian coordinates:
```kotlin
Complex(re: Number = 0, im: Number = 0)
```
or from polar coordinates:
```kotlin
Complex.fromPolar(theta: Number, radius: Number = 1)
```
For most commonly used Complex numbers there are three predefined values:
```kotlin
val ONE = Complex(1, 0)
val ZERO = Complex(0, 0)
val I = Complex(0, 1)
```
There are several overloaded operators that allow for easier addition, subtraction, multiplication 
and division of complex numbers.
</details>  

## [Matrices](quantum/src/main/kotlin/me/khol/quantum/math/Matrix.kt)
<details>
<summary>Details</summary>

Matrices can be created from number of rows and cols and 1D array of complex numbers:
```kotlin
Matrix(rows: Int, cols: Int, vararg values: Complex)
```
or directly from 2D array of complex numbers:
```kotlin
Matrix(m: List<List<Complex>>)
```
Identity matrix n×n can be created using:
```kotlin
Matrix.identity(size: Int)
```
There are many overloaded operators that allow for easier *addition* and *subtraction* of two 
matrices, *multiplication* and *division* by a number, complex number or another matrix, 
*conjugate transpose* and *tensor* product. 
</details>  

## [Qubits](quantum/src/main/kotlin/me/khol/quantum/Qubit.kt) 
Qubits are defined by two complex numbers which define their probability amplitudes:
```kotlin
Qubit(alpha: Complex, beta: Complex)
```
Two commonly used qubit states are predefined:
```kotlin
val ZERO = Qubit(Complex.ONE, Complex.ZERO)
val ONE = Qubit(Complex.ZERO, Complex.ONE)
```

Qubits can be converted to *bra* ⟨&phi;∣ or *ket* ∣&phi;⟩ matrices and define *dot*, *cross* 
and *tensor* products.

> Because two qubits may have different alpha and beta values although they represent the same
physical state they can be *normalized* so that two qubits can be programmatically compared.
This cannot be observed in the physical world but is invaluable in testing.

## [Registers](quantum/src/main/kotlin/me/khol/quantum/Register.kt)
Registers are created from a list of qubits:
```kotlin
Register(vararg qubits: Qubit)
```

In contrast to a simple `List<Qubit>` that can only hold independent qubits, a `Register` can
also hold *entangled* qubits, such as *Bell states*. This is crucial for creation of more 
complex algorithms that depend on entanglement.

> Just like single qubits, registers can be *normalized* for testing purposes.

## [Gates](quantum/src/main/kotlin/me/khol/quantum/gate/Gate.kt)
Most of the commonly used gates are already predefined:

| [Identity][_Identity] | [X (Not)][_X] | [Y][_Y] | [Z][_Z] | [S][_S] | [T][_T] |
|-----------------------|---------------|---------|---------|---------|---------|
| ![Identity][Identity] | ![X (Not)][X] | ![Y][Y] | ![Z][Z] | ![S][S] | ![T][T] |

| [Hadamard][_Hadamard] | [Rx][_Rx] | [Ry][_Ry] | [Rz][_Rz] | [Phase][_Phase] |
|-----------------------|-----------|-----------|-----------|-----------------|
| ![Hadamard][Hadamard] | ![Rx][Rx] | ![Ry][Ry] | ![Rz][Rz] | ![Phase][Phase] |

| [CNot (CX)][_CNot] | [Swap][_Swap] | [Square Root of Swap][_SwapRoot] | [Controlled][_Controlled] |
|--------------------|---------------|----------------------------------|---------------------------|
| ![CNot (CX)][CNot] | ![Swap][Swap] | ![Square Root of Swap][SwapRoot] | ![Controlled][Controlled] |

| [Toffoli (CCNot)][_CCNot] | [Fredkin (CSwap)][_CSwap] |
|---------------------------|---------------------------|
| ![Toffoli (CCNot)][CCNot] | ![Fredkin (CSwap)][CSwap] |

[//]: https://alexanderrodin.com/github-latex-markdown/

[//]: <> ( \begin{bmatrix} 1 & 0 \\ 0 & 1 \end{bmatrix} )
[Identity]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%5C%5C%200%20%26%201%20%5Cend%7Bbmatrix%7D
[_Identity]: quantum/src/main/kotlin/me/khol/quantum/gate/GateIdentity.kt
[//]: <> ( \frac{1}{\sqrt{2}} \begin{bmatrix} 1 & \phantom{-}1 \\ 1 & -1 \end{bmatrix} )
[Hadamard]: https://render.githubusercontent.com/render/math?math=%5Cfrac%7B1%7D%7B%5Csqrt%7B2%7D%7D%20%5Cbegin%7Bbmatrix%7D%201%20%26%20%5Cphantom%7B-%7D1%20%5C%5C%201%20%26%20-1%20%5Cend%7Bbmatrix%7D
[_Hadamard]: quantum/src/main/kotlin/me/khol/quantum/gate/GateHadamard.kt
[X//]: <> ( \begin{bmatrix} 0 & 1 \\ 1 & 0 \end{bmatrix} )
[X]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200%20%26%201%20%5C%5C%201%20%26%200%20%5Cend%7Bbmatrix%7D
[_X]: quantum/src/main/kotlin/me/khol/quantum/gate/GateX.kt
[Y//]: <> ( \begin{bmatrix} 0 & -i \\ i & \phantom{-}0 \end{bmatrix} )
[Y]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200%20%26%20-i%20%5C%5C%20i%20%26%20%5Cphantom%7B-%7D0%20%5Cend%7Bbmatrix%7D
[_Y]: quantum/src/main/kotlin/me/khol/quantum/gate/GateY.kt
[Z//]: <> ( \begin{bmatrix} 1 & \phantom{-}0 \\ 0 & -1 \end{bmatrix} )
[Z]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%20%5Cphantom%7B-%7D0%20%5C%5C%200%20%26%20-1%20%5Cend%7Bbmatrix%7D
[_Z]: quantum/src/main/kotlin/me/khol/quantum/gate/GateZ.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 \\ 0 & i \end{bmatrix} )
[S]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%5C%5C%200%20%26%20i%20%5Cend%7Bbmatrix%7D
[_S]: quantum/src/main/kotlin/me/khol/quantum/gate/GateS.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 \\ 0 & e^{i\pi/4} \end{bmatrix} )
[T]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%5C%5C%200%20%26%20e%5E%7Bi%5Cpi%2F4%7D%20%5Cend%7Bbmatrix%7D
[_T]: quantum/src/main/kotlin/me/khol/quantum/gate/GateT.kt
[//]: <>  (  )
[Rx]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%20%5Cphantom%7B-i%20%7Dcos(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%26%20-i%20sin(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%5C%5C%20-i%20sin(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%26%20%5Cphantom%7B-i%20%7Dcos(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%5Cend%7Bbmatrix%7D
[_Rx]: quantum/src/main/kotlin/me/khol/quantum/gate/GateRx.kt
[//]: <>  (  )
[Ry]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%20cos(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%26%20-sin(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%5C%5C%20sin(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%26%20%5Cphantom%7B-%7Dcos(%5Cfrac%7B%5Ctheta%7D%7B2%7D)%20%5Cend%7Bbmatrix%7D
[_Ry]: quantum/src/main/kotlin/me/khol/quantum/gate/GateRy.kt
[//]: <>  ( \begin{bmatrix} e^{-i\frac{\theta}{2}} & 0 \\ 0 & e^{i\frac{\theta}{2}} \end{bmatrix} )
[Rz]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%20e%5E%7B-i%5Cfrac%7B%5Ctheta%7D%7B2%7D%7D%20%26%200%20%5C%5C%200%20%26%20e%5E%7Bi%5Cfrac%7B%5Ctheta%7D%7B2%7D%7D%20%5Cend%7Bbmatrix%7D
[_Rz]: quantum/src/main/kotlin/me/khol/quantum/gate/GateRz.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 \\ 0 & e^{i\theta} \end{bmatrix} )
[Phase]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%5C%5C%200%20%26%20e%5E%7Bi%5Ctheta%7D%20%5Cend%7Bbmatrix%7D
[_Phase]: quantum/src/main/kotlin/me/khol/quantum/gate/GatePhase.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 & 0 & 0 \\ 0 & 0 & 1 & 0 \\ 0 & 1 & 0 & 0 \\ 0 & 0 & 0 & 1 \end{bmatrix} )
[Swap]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%201%20%26%200%20%5C%5C%200%20%26%201%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%201%20%5Cend%7Bbmatrix%7D
[_Swap]: quantum/src/main/kotlin/me/khol/quantum/gate/GateSwap.kt
[//]: <>  (  )
[SwapRoot]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%20%5Cfrac%7B1%7D%7B2%7D(1%2Bi)%20%26%20%5Cfrac%7B1%7D%7B2%7D(1-i)%20%26%200%20%5C%5C%200%20%26%20%5Cfrac%7B1%7D%7B2%7D(1-i)%20%26%20%5Cfrac%7B1%7D%7B2%7D(1%2Bi)%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%201%20%5Cend%7Bbmatrix%7D
[_SwapRoot]: quantum/src/main/kotlin/me/khol/quantum/gate/GateSwapRoot.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 & 0 & 0 \\ 0 & 1 & 0 & 0 \\ 0 & 0 & 0 & 1 \\ 0 & 0 & 1 & 0 \end{bmatrix} )
[CNot]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%201%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%201%20%5C%5C%200%20%26%200%20%26%201%20%26%200%20%5Cend%7Bbmatrix%7D
[_CNot]: quantum/src/main/kotlin/me/khol/quantum/gate/GateCNot.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 & 0 & 0 & 0 & 0 & 0 & 0 \\ 0 & 1 & 0 & 0 & 0 & 0 & 0 & 0 \\ 0 & 0 & 1 & 0 & 0 & 0 & 0 & 0 \\ 0 & 0 & 0 & 1 & 0 & 0 & 0 & 0 \\ 0 & 0 & 0 & 0 & 1 & 0 & 0 & 0 \\ 0 & 0 & 0 & 0 & 0 & 1 & 0 & 0 \\ 0 & 0 & 0 & 0 & 0 & 0 & 0 & 1 \\ 0 & 0 & 0 & 0 & 0 & 0 & 1 & 0 \end{bmatrix} )
[CCNot]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%201%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%201%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%201%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%201%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%201%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%201%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%201%20%26%200%20%5Cend%7Bbmatrix%7D
[_CCNot]: quantum/src/main/kotlin/me/khol/quantum/gate/GateCCNot.kt
[//]: <>  ( \left[\begin{bmatrix} 1 & 0 & 0 & 0 & 0 & 0 & 0 & 0 \\ 0 & 1 & 0 & 0 & 0 & 0 & 0 & 0 \\ 0 & 0 & 1 & 0 & 0 & 0 & 0 & 0 \\ 0 & 0 & 0 & 1 & 0 & 0 & 0 & 0 \\ 0 & 0 & 0 & 0 & 1 & 0 & 0 & 0 \\ 0 & 0 & 0 & 0 & 0 & 0 & 1 & 0 \\ 0 & 0 & 0 & 0 & 0 & 1 & 0 & 0 \\ 0 & 0 & 0 & 0 & 0 & 0 & 0 & 1 \end{bmatrix}\right] )
[CSwap]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%201%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%201%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%201%20%26%200%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%201%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%201%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%201%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%200%20%26%201%20%5Cend%7Bbmatrix%7D
[_CSwap]: quantum/src/main/kotlin/me/khol/quantum/gate/GateCSwap.kt
[//]: <>  ( \begin{bmatrix} 1 & 0 & 0 & 0 \\ 0 & 1 & 0 & 0 \\ 0 & 0 & u_00 & u_01 \\ 0 & 0 & u_10 & u_11 \end{bmatrix} )
[Controlled]: https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%200%20%26%200%20%5C%5C%200%20%26%201%20%26%200%20%26%200%20%5C%5C%200%20%26%200%20%26%20u_00%20%26%20u_01%20%5C%5C%200%20%26%200%20%26%20u_10%20%26%20u_11%20%5Cend%7Bbmatrix%7D
[_Controlled]: quantum/src/main/kotlin/me/khol/quantum/gate/GateControlled.kt

It should be rather straightforward to create custom gates.

Gates can be applied to a register. The size of the gate must match the number of qubits stored by
the register.
```kotlin
GateSwap * Register(Qubit.ONE, Qubit.ZERO) // Register(Qubit.ZERO, Qubit.ONE)
``` 
Gates that act on a single qubit can be also applied to qubits.
```kotlin
GateX * Qubit.ONE // Qubit.ZERO
```

## [Programs](quantum/src/main/kotlin/me/khol/quantum/Program.kt)
Instead of manually applying gates to registers and qubits like this:
```kotlin
val bellState = CNot * Register(Hadamard * ZERO, ZERO)
```
program classes provide a less cluttered and more natural way to combine multiple gates into one,
reorder inputs of a gate, apply gates to registers with different sizes and apply multiple gates 
to a register.

### Precomputed Program
Pre-computes transformations of multiple gates as a standalone gate. As we apply gates 
within the program, their transformation matrices are combined.
It allows us to pre-compute a part of a larger program that is executed repeatedly and 
apply the result gate instead.
    
```kotlin
// Swap gate made using CNot gates 
val swap: Gate = gate(2) {
    CNot[0, 1]
    CNot[1, 0]
    CNot[0, 1]
}
```

### Runnable Program
Applies multiple gates to a register, changing the state of its qubits with each step.

```kotlin
// Fully entangled Bell state (∣00⟩ + ∣11⟩) / sqrt(2)  
val bellState: Register = program(2) {
    Hadamard[0]
    CNot[0, 1]
}
```

Compared to `gate` that does not have any state per se, `program` has a register
that we can measure at any point. 

```kotlin
fun measureAndCollapse(vararg qubitIndices: Int): List<Qubit>
```

Doing so will collapse the state of specified qubits to ∣0⟩ or ∣1⟩ based on their probabilities. 
Other qubits in the register entangled with any of the measured qubits will have their probabilities
updated as well to satisfy constraints imposed by entangled states before the measurement.

# Download

The project is also available as a maven dependency from MavenCentral repository:

```kotlin
implementation("io.github.antimonit:quantum:1.0.0")
```
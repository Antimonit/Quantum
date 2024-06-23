## Brief introduction to quantum computing 

Classical computers, as we know them, work by manipulating bits that hold the value of either 
**zero** or **one**. 

Quantum computers, on the other hand, utilize qubits&mdash;a quantum-world counterpart of bits. 
Qubits also can hold values of **zero** and **one**, but it can also be in a **superposition**
of the two states, meaning that it can be partially in both states at once.

Qubits are typically represented using a pair of complex numbers labeled **&alpha;** and **&beta;**. 
These two values relate to the probabilities at which the given qubit will be measured as **zero** 
or **one**.
 
Typically, we represent the **zero** state as

$`\left\lvert 0 \right\rangle = \begin{bmatrix} 0 \\ 1 \end{bmatrix}`$

the **one** state as

$`\left\lvert 1 \right\rangle = \begin{bmatrix} 1 \\ 0 \end{bmatrix}`$

while **superposed** qubits can be represented as

$`\left\lvert \psi \right\rangle = \begin{bmatrix} \alpha \\ \beta \end{bmatrix}`$

where squares of α and β add up to 1

$`\left|\alpha\right|^2 + \left|\beta\right|^2 = 1`$

It is important to note that even though a qubit can hold any of virtually infinite states, when we
measure it, it always collapses into one of the two basic states&mdash;**zero** or **one**. 

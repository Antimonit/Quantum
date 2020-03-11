# Quantum computing simulator

## Brief introduction to quantum computing 

Classical computers, as we know them, work by manipulating bits that hold the value of either 
**zero** or **one**. 

Quantum computers, on the other hand, utilize qubits&mdash;a quantum-world counterpart of bits. 
Qubits also can hold values of **zero** and **one**, but it can also be in a **superposition**
of the two states, meaning that it can be partially in both states at once.

Qubits are typically represented using a pair of complex numbers labeled **&alpha;** and **&beta;**. 
These two values relate to the probabilities at which the given qubit will be measured as **zero** 
or **one**.
 
Typically we represent the **zero** state as

![\left\lvert 0 \right\rangle = \begin{bmatrix} 0 \\ 1 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cleft%5Clvert%200%20%5Cright%5Crangle%20%3D%20%5Cbegin%7Bbmatrix%7D%200%20%5C%5C%201%20%5Cend%7Bbmatrix%7D),

the **one** state as

![\left\lvert 1 \right\rangle = \begin{bmatrix} 1 \\ 0 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cleft%5Clvert%201%20%5Cright%5Crangle%20%3D%20%5Cbegin%7Bbmatrix%7D%201%20%5C%5C%200%20%5Cend%7Bbmatrix%7D),

while **superposed** qubits can be represented as

![\left\lvert \psi \right\rangle = \begin{bmatrix} \alpha \\ \beta \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cleft%5Clvert%20%5Cphi%20%5Cright%5Crangle%20%3D%20%5Cbegin%7Bbmatrix%7D%20%5Calpha%20%5C%5C%20%5Cbeta%20%5Cend%7Bbmatrix%7D)

where squares of α and β add up to 1

![\left|\alpha\right|^2 + \left|\beta\right|^2 = 1](https://render.githubusercontent.com/render/math?math=%5Cleft%7C%5Calpha%5Cright%7C%5E2%20%2B%20%5Cleft%7C%5Cbeta%5Cright%7C%5E2%20%3D%201)

It is important to note that even though a qubit can hold any of virtually infinite states, when we
measure it, it always collapses into one of the two basic states&mdash;**zero** or **one**. 

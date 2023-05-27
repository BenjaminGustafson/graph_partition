# graph_partition

This is my senior project for my mathematics major at Trinity University. 
The full write-up of the project is in paper.pdf. 
In the paper I prove that the following problem is NP-complete:
Given a multigraph $G$ on $n$ vertices and a positive integer $m$, is there a $G$-decomposition of $m K_n$ (the $m$-fold complete multigraph on $n$ vertices)? 

The source code in the folder src takes a graph $G$ on $n$ vertices and an integer $m$ and determines if there is a $G$ decomposition of $mK_n$. 
The main algorithm is in Decomposer.java.


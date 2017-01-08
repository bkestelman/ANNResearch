/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annresearch;

/**
 * This is the main class for an Artificial Neural Network Research library, 
 * which will contain functionality for creating various types of ANNs with 
 * customizable components (size, input types, activation functions, 
 * optimization methods, etc.). The goal of this project is to test how 
 * customizing different parts of an ANN affect its results, and also what kinds
 * of problems ANNs are suited to solve. 
 * 
 * Notes on terminology:
 * -Neurons and nodes are the same thing
 * -Inputs may refer to the input vector of an ANN or to the values after weight
 * processing being given to some node. The latter case represents the inputs of
 * a node. These two cases of inputs should be distinguishable from context. 
 * 
 * The first test will be a small ANN (no hidden layers, input layer 2 nodes, 
 * output layer 1 node), where the desired output is the AND logical operation 
 * on the two inputs (restricted to 0 or 1). The output should of course be 1
 * when the two inputs are 1, and should be 0 else. This result should be easily
 * obtainable with a perceptron (an ANN where nodes either fire a value of 1 if
 * their input exceeds a threshold, or do not fire at all, i.e. fire a value of
 * 0). If the two input nodes are connected to the output node with weights of 
 * value 1, and the threshold for the output firing is 2, the output will fire 
 * a 1 only if both inputs were 1, and will not fire (fire 0) else. Thus, the 
 * first element of this project will be a simple perceptron with weights and 
 * threshold set manually. Gradient descent, however, cannot be implemented for  
 * a perceptron, due to its discontinuous values. Then I will create a more
 * flexible ANN (with gradient descent), in which neurons always fire, but may 
 * fire any value, directly obtained from the weights, biases, and inputs to the 
 * node. I suspect this type of ANN will FAIL at reproducing the AND operation, 
 * because I don't think simple linear operations will suffice to produce the 
 * correct result. Thus, I will finally give it an activation function (the
 * sigmoid or logistic function to start), which will mimic the workings of the
 * perceptron in that nodes fire values close to 0 or 1, but will have the 
 * flexibility and continuity of the latter ANN. 
 *
 * @author Benito
 */
public class ANNResearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
    
}

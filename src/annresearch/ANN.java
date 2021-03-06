/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annresearch;

import static java.lang.System.setOut;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author Benito
 */
public class ANN {

    SimpleMatrix[] weights;
    SimpleMatrix[] layers; //each layer is a vector (1D matrix)
    double[] biases;

    enum Function {
        NONE, SIGMOID
    };
    Function function = Function.SIGMOID;
    double alpha = 0.1; //gradient descent multiplicative constant
    double delta = 0.01; //test change in weights/biases to approximate derivative of error function E(x)

    public ANN(int[] layerSizes, SimpleMatrix[] weights, double[] biases) {
        this.weights = weights.clone(); //make cloning optional
        this.biases = biases.clone();
        layers = new SimpleMatrix[layerSizes.length];
        for (int i = 0; i < layerSizes.length; i++) {
            layers[i] = new SimpleMatrix(1, layerSizes[i]);
        }
    }

    /**
     * Obtain the result of processing the input vector through the ANN, without
     * training the ANN.
     *
     * @param inputs
     * @return
     */
    public SimpleMatrix runOnInput(SimpleMatrix inputs) {
        layers[0] = inputs.plus(biases[0]); //Note that biases[0] does nothing 
        //more than add a constant to every input. This is what biases always 
        //do, but in this case, the input is not going through an activation
        //function. Note that after this addition, the inputs will be multiplied
        //by the first weights matrix and have the next layer's bias added to 
        //the result, before going through the activation function. In general, 
        //you probably want biases[0] to just be 0.
        if (Utilities.debugging) {
            setOut(System.err);
            System.err.println("layers[0]: ");
            layers[0].print();
            System.err.println("inputs: ");
            inputs.print();
        }
        /* This is not a perceptron
        for (int j = 0; j < inputs.numRows(); j++) {
            if (Utilities.debugging) {
                System.err.println("INPUT: " + inputs.get(j));
                System.err.println("BIAS: " + biases[0]);
            }
            if (inputs.get(j) + biases[0] > 0) {
                inputs.set(j, 0);
            } else {
                inputs.set(j, 1);
            }
        }
         */
        if (Utilities.debugging) {
            System.err.println("layers[0]: ");
            layers[0].print();
            System.err.println("inputs: ");
            inputs.print();
        }
        for (int i = 1; i < weights.length + 1; i++) {
            if (Utilities.debugging) {
                System.err.println("layers[" + i + "] pure: ");
                layers[i].print();
                System.err.println("Weights[" + (i - 1) + "]: ");
                weights[i - 1].print();
                System.err.println("layers[" + (i - 1) + "]: ");
                layers[i - 1].print();
                System.err.println("layers[" + i + "] after weight processing: ");
                layers[i].print();
            }
            //Utilities.setDebugging(true);
            layers[i] = weights[i - 1].mult(layers[i - 1]).plus(biases[i]); //same as the perceptron, plus the bias
            if (Utilities.debugging) {
                System.err.println("layers[" + i + "] before activationFunction: ");
                layers[i].print();
            }
            layers[i] = activationFunction(layers[i]);
            /* This is not a perceptron
            for (int j = 0; j < layers[i].numRows(); j++) {
                if (layers[i].get(j) + biases[i] > 0) {
                    layers[i].set(j, 0);
                } else {
                    layers[i].set(j, 1);
                }
            }
             */
            if (Utilities.debugging) {
                System.err.println("layers[" + i + "] after activationFunction: ");
                layers[i].print();
            }
            //Utilities.setDebugging(false);
        }
        if (Utilities.debugging) {
            setOut(System.out);
        }
        return layers[layers.length - 1]; //return output vector
    }

    /**
     * Train the ANN on a single input vector with desired output vector. Will
     * make a small adjustment to weights and biases following gradient descent.
     *
     * @param inputs
     * @return
     */
    public void trainOnInput(SimpleMatrix inputs,
            SimpleMatrix desiredOutputs, int descentIterations) {
        for (int i = 0; i < descentIterations; i++) {
            SimpleMatrix outputs = runOnInput(inputs);
            if (outputs.numCols() != desiredOutputs.numCols()) {
                System.err.println("Warning in trainOnInput: desiredOutputs "
                        + "are different length from actual outputs");
            }
            double error = 0;
            double epsilon; //change in error after changing a weight/bias 
            for (int j = 0; j < outputs.numCols(); j++) {
                error += Math.pow(outputs.get(j) - desiredOutputs.get(j), 2);
            }
            if (Utilities.debugging) {
                System.err.println("Output is: " + outputs.get(0));
                System.err.println("Desired output is: " + desiredOutputs.get(0));
                System.err.println("Error is: " + error);
            }
            SimpleMatrix[] derivs = new SimpleMatrix[weights.length];
            double[] biasDerivs = new double[biases.length];
            for (int b = 0; b < biases.length; b++) {
                biases[b] += delta;
                outputs = runOnInput(inputs);
                double newError = 0;
                for (int j = 0; j < outputs.numCols(); j++) {
                    newError += Math.pow(outputs.get(j) - desiredOutputs.get(j), 2); //calculate new error function, E(x)
                }
                epsilon = newError - error; //change in error
                biasDerivs[b] = epsilon / delta;
                biases[b] -= delta;
            }
            //calculate partial derivatives of error function with respect to each weight:
            for (int w = 0; w < weights.length; w++) {
                derivs[w] = new SimpleMatrix(weights[w].numRows(), weights[w].numCols());
                for (int r = 0; r < weights[w].numRows(); r++) {
                    for (int c = 0; c < weights[w].numCols(); c++) {
                        weights[w].set(r, c, weights[w].get(r, c) + delta); //change a weight by delta
                        outputs = runOnInput(inputs);
                        double newError = 0;
                        for (int j = 0; j < outputs.numCols(); j++) {
                            newError += Math.pow(outputs.get(j) - desiredOutputs.get(j), 2); //calculate new error function, E(x)
                        }
                        epsilon = newError - error; //change in error
                        derivs[w].set(r, c, epsilon / delta); //store the partial derivative of E with respect to this weight
                        weights[w].set(r, c, weights[w].get(r, c) - delta);
                    }
                }
            }
            //change biases accordingly
            for (int b = 0; b < biases.length; b++) {
                biases[b] -= alpha * biasDerivs[b];
            }
            //change weights accordingly
            for (int w = 0; w < weights.length; w++) {
                for (int r = 0; r < weights[w].numRows(); r++) {
                    for (int c = 0; c < weights[w].numCols(); c++) {
                        weights[w].set(r, c, weights[w].get(r, c) - alpha * derivs[w].get(r, c));
                    }
                }
            }
        }
        /*for i = 0->descentIterations
        calculate E(x)
        for all weights, biases
            weight += alpha
            recalculate E(x) (i.e. runOnInput again), change is delta (may be positive or negative) 
            store change in E (delta) in array of matrices of approxDerivatives corresponding to the array of weight matrices
            weight -= alpha
        for all weights, biases
            weight -= alpha*delta/|delta_av|
         */
        //return layers[layers.length - 1]; //return output matrix
    }

    /**
     * Trains the ANN using gradient descent on a batch of inputs. This weak
     * version of the method simply calls trainOnInput for each input, and
     * repeats this the desired number of iterations. A presumably more
     * effective strategy would be to have an error function which depends on
     * multiple inputs, and calculate the derivatives of this multi-input error
     * function, so the ANN's gradient descent isn't trying to optimize a single
     * input at a time.
     *
     * @param inputs
     * @param desiredOutputs
     * @param iterations
     */
    public void trainOnBatchWeak(SimpleMatrix[] inputs, SimpleMatrix[] desiredOutputs, int iterations) {
        for (int i = 0; i < iterations; i++) {
            for (int in = 0; in < inputs.length; in++) {
                trainOnInput(inputs[in], desiredOutputs[in], 1);
            }
        }
    }

    /**
     * Trains the ANN using gradient descent on a batch of inputs. The strategy
     * implemented in this method calculates the total error between the outputs
     * and desired outputs of multiple inputs, and applies gradient descent on
     * this error function, rather than applying gradient descent on each input
     * separately, as in trainOnBatchWeak.
     * Interestingly, trainOnBatchWeak seems to do a slightly better job of 
     * reducing the total error than trainOnBatch does, over the same number of
     * iterations. However, I need to look into whether the way I'm comparing 
     * the two is fair. For instance, each iteration of trainOnBatchWeak is 
     * actually making a small step down the gradient of each input, as 
     * opposed to a single step down the gradient of the total error function. 
     * Thus, trainOnBatchWeak calculates the gradient for each input's error 
     * function in one iteration, whereas trainOnBatch only needs to calculate a
     * single gradient. Since gradient calculation (or gradient approximation, 
     * as I've implemented it) depends heavily on the size of the ANN, it's 
     * probably wise to avoid doing it as much as possible. I'll bet 
     * trainOnBatch runs much faster than trainOnBatchWeak on larger nets. 
     * Future tests will focus on this issue. Still, despite the expected 
     * runtime advantage of trainOnBatch over trainOnBatchWeak, I'm unclear why
     * trainOnBatchWeak seems to reduce the error faster than trainOnBatch per 
     * iteration. 
     *
     * @param inputs
     * @param desiredOutputs
     * @param descentIterations
     */
    public void trainOnBatch(SimpleMatrix[] inputs, SimpleMatrix[] desiredOutputs, int descentIterations) {
        for (int i = 0; i < descentIterations; i++) {
            double error = 0;
            double epsilon; //change in error after changing a weight/bias 
            SimpleMatrix outputs;
            for (int in = 0; in < inputs.length; in++) {
                outputs = runOnInput(inputs[in]);
                if (outputs.numCols() != desiredOutputs[in].numCols()) {
                    System.err.println("Warning in trainOnInput: desiredOutputs "
                            + "are different length from actual outputs");
                }
                for (int j = 0; j < outputs.numCols(); j++) {
                    error += Math.pow(outputs.get(j) - desiredOutputs[in].get(j), 2);
                }
                if (Utilities.debugging) {
                    System.err.println("Cumulative error before training is (using sum of square of differences between output and desired output): " + error);
                    System.err.println("Output is: " + outputs.get(0));
                    System.err.println("Desired output is: " + desiredOutputs[in].get(0));
                    System.err.println("Error is: " + error);
                }
            }
            SimpleMatrix[] derivs = new SimpleMatrix[weights.length];
            double[] biasDerivs = new double[biases.length];
            for (int b = 0; b < biases.length; b++) {
                biases[b] += delta;
                if (Utilities.debugging) {
                    System.err.println("Changing bias " + b + " to " + biases[b] + " has the effect: ");
                }
                double newError = 0;
                for (int in = 0; in < inputs.length; in++) {
                    outputs = runOnInput(inputs[in]);
                    for (int j = 0; j < outputs.numCols(); j++) {
                        newError += Math.pow(outputs.get(j) - desiredOutputs[in].get(j), 2); //calculate new error function, E(x)
                        if (Utilities.debugging) {
                            System.err.println("newError for input " + in + " changed by " + Math.pow(outputs.get(j) - desiredOutputs[in].get(j), 2));
                        }
                    }
                }
                epsilon = newError - error; //change in error
                biasDerivs[b] = epsilon / delta;
                biases[b] -= delta;
            }
            //calculate partial derivatives of error function with respect to each weight:
            for (int w = 0; w < weights.length; w++) {
                derivs[w] = new SimpleMatrix(weights[w].numRows(), weights[w].numCols());
                for (int r = 0; r < weights[w].numRows(); r++) {
                    for (int c = 0; c < weights[w].numCols(); c++) {
                        weights[w].set(r, c, weights[w].get(r, c) + delta); //change a weight by delta
                        if (Utilities.debugging) {
                            System.err.println("Changing weight " + r + ", " + c + " to " + weights[w].get(r, c) + " has the effect: ");
                        }
                        double newError = 0;
                        for (int in = 0; in < inputs.length; in++) {
                            outputs = runOnInput(inputs[in]);
                            for (int j = 0; j < outputs.numCols(); j++) {
                                newError += Math.pow(outputs.get(j) - desiredOutputs[in].get(j), 2); //calculate new error function, E(x)
                                if (Utilities.debugging) {
                                    System.err.println("newError for input " + in + " changed by " + Math.pow(outputs.get(j) - desiredOutputs[in].get(j), 2));
                                }
                            }
                        }
                        epsilon = newError - error; //change in error
                        derivs[w].set(r, c, epsilon / delta); //store the partial derivative of E with respect to this weight
                        weights[w].set(r, c, weights[w].get(r, c) - delta);
                    }
                }
            }
            //change biases accordingly
            for (int b = 0; b < biases.length; b++) {
                biases[b] -= alpha * biasDerivs[b];
            }
            //change weights accordingly
            for (int w = 0; w < weights.length; w++) {
                for (int r = 0; r < weights[w].numRows(); r++) {
                    for (int c = 0; c < weights[w].numCols(); c++) {
                        weights[w].set(r, c, weights[w].get(r, c) - alpha * derivs[w].get(r, c));
                    }
                }
            }
        }
    }

    public SimpleMatrix activationFunction(SimpleMatrix layer) {
        if (function == Function.SIGMOID) {
            return layer.scale(-1).elementExp().plus(1).elementPower(-1); //perform sigmoid function on each element of layer (y = 1/(1+e^-x))
        } else {
            return layer;
        }
    }

    public static SimpleMatrix activationFunction(SimpleMatrix layer, Function function) {
        if (function == Function.SIGMOID) {
            return layer.scale(-1).elementExp().plus(1).elementPower(-1); //perform sigmoid function on each element of layer (y = 1/(1+e^-x))
        } else {
            return layer;
        }
    }

    public void printOutput() {
        layers[layers.length - 1].print();
    }
}

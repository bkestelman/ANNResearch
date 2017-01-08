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
public class Perceptron {
    SimpleMatrix[] weights;
    SimpleMatrix[] layers; //each layer is a vector (1D matrix)
    double[] thresholds;

    public Perceptron(int[] layerSizes, SimpleMatrix[] weights, double[] thresholds) {
        this.weights = weights;
        this.thresholds = thresholds;
        layers = new SimpleMatrix[layerSizes.length];
        for(int i = 0; i < layerSizes.length; i++) {
            layers[i] = new SimpleMatrix(1, layerSizes[i]);
        }
    }

    /**
     *
     * @param inputs
     * @return
     */
    public SimpleMatrix runOnInput(SimpleMatrix inputs) {
        layers[0] = inputs;
        Utilities.debugging = false;
        if (Utilities.debugging) {
            setOut(System.err);
            System.err.println("layers[0]: ");
            layers[0].print();
            System.err.println("inputs: ");
            inputs.print();
        }
        for (int j = 0; j < inputs.numRows(); j++) {
            if (Utilities.debugging) {
                System.err.println("INPUT: " + inputs.get(j));
                System.err.println("THRESHOLD: " + thresholds[0]);
            }
            if (inputs.get(j) < thresholds[0]) {
                inputs.set(j, 0);
            } else {
                inputs.set(j, 1);
            }
        }
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
            layers[i] = weights[i - 1].mult(layers[i - 1]);
            for (int j = 0; j < layers[i].numRows(); j++) {
                if (layers[i].get(j) < thresholds[i]) {
                    layers[i].set(j, 0);
                } else {
                    layers[i].set(j, 1);
                }
            }
            if (Utilities.debugging) {
                System.err.println("layers[" + i + "] after threshold processing: ");
                layers[i].print();
            }
        }
        if (Utilities.debugging) {
            setOut(System.out);
        }
        return layers[layers.length - 1]; //return output matrix
    }

    public void printOutput() {
        layers[layers.length-1].print();
    }
}

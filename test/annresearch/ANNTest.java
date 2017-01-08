/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annresearch;

import org.ejml.simple.SimpleMatrix;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benito
 */
public class ANNTest {
    
    public ANNTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Testing class ANN:");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Test of static activationFunction method, of class ANN
     */
    @Test
    public void testActivationFunction() {
        System.out.println("* testActivationFunction");
        Utilities.debugLocked = true;
        SimpleMatrix m = new SimpleMatrix(1, 5, true, 0, 1, -1, 10, -10);       
        m = ANN.activationFunction(m, ANN.Function.SIGMOID);
        assertEquals(0.5, m.get(0), 0.05);
        assertEquals(0.7311, m.get(1), 0.05);
        assertEquals(0.2689, m.get(2), 0.05);
        assertEquals(0.9999, m.get(3), 0.05);
        assertEquals(0.0000, m.get(4), 0.05);
    }
    
    /**
     * Test of runOnInput method, of class ANN
     */
    @Test
    public void testRunOnInput() {
        System.out.println("* testRunOnInput");
        Utilities.debugLocked = true;
        //manual test with nearly identical setup as the perceptron (with appropriate biases instead of thresholds)
        //appropriate biases means biases that give close to ideal results (not necessarily just the negative of the respective thresholds)
        SimpleMatrix inputs = new SimpleMatrix(2, 1, true, 1, 1);
        int[] layerSizes = {2,1};
        SimpleMatrix[] weights = new SimpleMatrix[1];
        weights[0] = new SimpleMatrix(1, 2, true, 1, 1);
        double[] biases = {0, -1};
        ANN instance = new ANN(layerSizes, weights, biases);
        instance.function = ANN.Function.NONE;
        assertEquals(1, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 0, 1);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 1, 0);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 0, 0);
        assertEquals(-1, instance.runOnInput(inputs).get(0), 0.05); //note: this result is different from the perceptron
        inputs = new SimpleMatrix(2, 1, true, 1, 1);
        assertEquals(1, instance.runOnInput(inputs).get(0), 0.05);
        //manual test with sigmoid activation function and appropriate biases
        inputs = new SimpleMatrix(2, 1, true, 1, 1);
        layerSizes = new int[] {2,1};
        weights = new SimpleMatrix[1];
        weights[0] = new SimpleMatrix(1, 2, true, 10, 10);
        biases = new double[] {0, -15};
        instance = new ANN(layerSizes, weights, biases);
        instance.function = ANN.Function.SIGMOID;
        inputs = new SimpleMatrix(2, 1, true, 1, 1);
        Utilities.debugLocked = false;
        assertEquals(1, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 0, 1);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 1, 0);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 0, 0);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05); //note: this result is different from the perceptron
        inputs = new SimpleMatrix(2, 1, true, 1, 1);
        assertEquals(1, instance.runOnInput(inputs).get(0), 0.05);
    }

    /**
     * Test of trainOnInput method, of class ANN
     */
    @Test
    public void testTrainOnInput() {
        System.out.println("* testTrainOnInput");
        Utilities.debugLocked = true;
        //manual test with nearly identical setup as the perceptron (with appropriate biases instead of thresholds)
        //appropriate biases means biases that give close to ideal results (not necessarily just the negative of the respective thresholds)
        SimpleMatrix inputs = new SimpleMatrix(2, 1, true, 1, 1);
        SimpleMatrix desiredOutputs = new SimpleMatrix(1, 1, true, 1);
        int[] layerSizes = {2,1};
        SimpleMatrix[] weights = new SimpleMatrix[1];
        weights[0] = new SimpleMatrix(1, 2, true, 0, 0);
        double[] biases = {0,0};
        ANN instance = new ANN(layerSizes, weights, biases);
        instance.function = ANN.Function.SIGMOID;
        SimpleMatrix originalOutput = instance.runOnInput(inputs);
        Utilities.debugLocked = false;
        int iterations = 100;
        instance.trainOnInput(inputs, desiredOutputs, iterations);
        Utilities.debugLocked = true;
        SimpleMatrix newOutput = instance.runOnInput(inputs);
        double error = Math.abs(originalOutput.get(0) - desiredOutputs.get(0));
        double newError = Math.abs(newOutput.get(0) - desiredOutputs.get(0));
        assertTrue(error >= newError);
        System.out.println("\tAfter " + iterations + " iterations of gradient descent, error on inputs{1,1} improved from " + error + " to " + newError);
        
        desiredOutputs.set(0, 0);
        inputs = new SimpleMatrix(2, 1, true, 1, 0);
        originalOutput = instance.runOnInput(inputs);
        error += Math.abs(originalOutput.get(0) - desiredOutputs.get(0));
        
        inputs = new SimpleMatrix(2, 1, true, 0, 1);
        originalOutput = instance.runOnInput(inputs);
        error += Math.abs(originalOutput.get(0) - desiredOutputs.get(0));
        
        inputs = new SimpleMatrix(2, 1, true, 0, 0);
        originalOutput = instance.runOnInput(inputs);
        error += Math.abs(originalOutput.get(0) - desiredOutputs.get(0));
        //now we've got a total error; the sum of the errors from all possible inputs
        
        desiredOutputs.set(0, 0);
        inputs = new SimpleMatrix(2, 1, true, 1, 0);
        instance.trainOnInput(inputs, desiredOutputs, iterations);        
        inputs = new SimpleMatrix(2, 1, true, 0, 1);
        instance.trainOnInput(inputs, desiredOutputs, iterations);        
        inputs = new SimpleMatrix(2, 1, true, 0, 0);
        instance.trainOnInput(inputs, desiredOutputs, iterations);
        
        inputs = new SimpleMatrix(2, 1, true, 1, 0);
        newOutput = instance.runOnInput(inputs);
        newError += Math.abs(newOutput.get(0) - desiredOutputs.get(0));
        inputs = new SimpleMatrix(2, 1, true, 0, 1);
        newOutput = instance.runOnInput(inputs);
        newError += Math.abs(newOutput.get(0) - desiredOutputs.get(0));
        inputs = new SimpleMatrix(2, 1, true, 0, 0);
        newOutput = instance.runOnInput(inputs);
        newError += Math.abs(newOutput.get(0) - desiredOutputs.get(0));
        
        System.out.println("\tAfter training " + iterations + " iterations on each possible input, total error improved from " + error + " to " + newError);
        assertTrue(newError < error);
        
        desiredOutputs.set(1); 
        inputs = new SimpleMatrix(2, 1, true, 1, 1);
        newOutput = instance.runOnInput(inputs);
        newError = Math.abs(newOutput.get(0) - desiredOutputs.get(0));
        System.out.println("\tThe error on inputs{1,1} is now " + newError + " after having trained for all other input cases, without retraining this one");
        System.out.println("lol");
        
    }
}

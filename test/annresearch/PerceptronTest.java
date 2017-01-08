/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annresearch;

import org.ejml.simple.SimpleMatrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benito
 */
public class PerceptronTest {
    
    public PerceptronTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Testing class Perceptron");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of constructor of class Perceptron
     */
    @Test
    public void testPerceptron() {
        System.out.println("* testPerceptron");
        //Perceptron with no hidden layer, weights initialized to 1, etc.
        int[] layerSizes = {2,1}; //2 nodes 
        SimpleMatrix[] weights = new SimpleMatrix[1];
        weights[0] = new SimpleMatrix(1, 2, true, 1, 1);
        double[] thresholds = {1, 2};
        Perceptron instance = new Perceptron(layerSizes, weights, thresholds);
    }

    /**
     * Test of runOnInput method, of class Perceptron.
     */
    @Test
    public void testRunOnInput() {
        System.out.println("* testRunOnInput");
        SimpleMatrix inputs = new SimpleMatrix(2, 1, true, 1, 1);
        int[] layerSizes = {2,1};
        SimpleMatrix[] weights = new SimpleMatrix[1];
        weights[0] = new SimpleMatrix(1, 2, true, 1, 1);
        double[] thresholds = {1, 2};
        Perceptron instance = new Perceptron(layerSizes, weights, thresholds);
        assertEquals(1, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 0, 1);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 1, 0);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 0, 0);
        assertEquals(0, instance.runOnInput(inputs).get(0), 0.05);
        inputs = new SimpleMatrix(2, 1, true, 1, 1);
        assertEquals(1, instance.runOnInput(inputs).get(0), 0.05);
    }

    /**
     * Test of printOutput method, of class Perceptron.
     */
    @Test
    public void testPrintOutput() {
        System.out.println("* testPrintOutput");
        //Perceptron instance = null;
        //instance.printOutput();
    }
    
}

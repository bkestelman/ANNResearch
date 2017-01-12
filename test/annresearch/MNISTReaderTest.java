/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annresearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MNISTReaderTest {
    
    public MNISTReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of readTrainingImages method, of class MNISTReader.
     */
    @Test
    public void testReadTrainingImage() {
        System.out.println("* readTrainingImage");
        MNISTReader.readTrainingImagesHeader();
        try {
            for(int i = 0; i < 5; i++) {
                MNISTReader.readTrainingImage();
            }
        } catch (IOException ex) {
            Logger.getLogger(MNISTReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of readTestImages method, of class MNISTReader.
     */
    @Test
    public void testReadTestImages() {
        System.out.println("* readTestImages");
        MNISTReader.readTestImages();
    }

    /**
     * Test of readTrainingLabels method, of class MNISTReader.
     */
    @Test
    public void testReadTrainingLabels() {
        System.out.println("* readTrainingLabels");
        MNISTReader.readTrainingLabels();
    }

    /**
     * Test of readTestLabels method, of class MNISTReader.
     */
    @Test
    public void testReadTestLabels() {
        System.out.println("* readTestLabels");
        MNISTReader.readTestLabels();
    }

    /**
     * Test of openTrainingImages method, of class MNISTReader.
     */
    @Test
    public void testOpenTrainingImage() {
        System.out.println("* openTrainingImages");
        //MNISTReader.openTrainingImages();
    }

    /**
     * Test of readTrainingImagesHeader method, of class MNISTReader.
     */
    @Test
    public void testReadTrainingImagesHeader() {
        System.out.println("* readTrainingImagesHeader");
        MNISTReader.readTrainingImagesHeader();
        assertEquals(MNISTReader.trainingImagesMagicNumber, 2051);
        assertEquals(MNISTReader.trainingImagesCount, 60000);
        assertEquals(MNISTReader.trainingImagesWidth, 28);
        assertEquals(MNISTReader.trainingImagesHeight, 28);
        try {
            MNISTReader.trainingImagesStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MNISTReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //assertEquals(MNISTReader.trainingImagesStream, null);
    }

    /**
     * Test of read4Bytes method, of class MNISTReader.
     */
    @Test
    public void testRead4Bytes() {
        System.out.println("* read4Bytes");
        //MNISTReader.openTrainingImages();
        //byte[] expResult = MNISTReader.readTrainingImagesHeader();
        MNISTReader.openTrainingImages();
        FileInputStream fstream = MNISTReader.trainingImagesStream;
        byte[] result = MNISTReader.read4Bytes(fstream);
        //assertArrayEquals(expResult, result);
        byte[] images_count = MNISTReader.read4Bytes(fstream);
        ByteBuffer bb = ByteBuffer.wrap(images_count);
        assertEquals(bb.getInt(), 60000);
        byte[] image_width = MNISTReader.read4Bytes(fstream);
        bb = ByteBuffer.wrap(image_width);
        assertEquals(bb.getInt(), 28);
        byte[] image_height = MNISTReader.read4Bytes(fstream);
        bb = ByteBuffer.wrap(image_height);
        assertEquals(bb.getInt(), 28);
    }
    
}

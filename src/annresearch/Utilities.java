/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annresearch;

import static java.lang.System.setOut;

/**
 *
 * @author Benito
 */
public class Utilities {
    public static double doubleDelta = 0; //error margin for double values
    public static boolean debugging = false; //when set to true, all debugging 
    //statements everywhere will display, so advice is set to true locally where
    //you need to debug
    public static boolean debugLocked = false;
    
    public static void setDebugging(boolean debug) {
        if(debugLocked) return;
        debugging = debug;
        if(debug) setOut(System.err);
        else {
            setOut(System.out);
            System.out.println("Uhhh... hello?");
        }
        
    }
}

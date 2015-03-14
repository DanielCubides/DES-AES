package Logic;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.io.*;
/**
 * Created by Daniel Cubides on 3/6/2015.
 * DES application
 */
public class DES {
    private String message;
    private String key;
    private LinkedList<String> keys = new LinkedList<String>();

    public DES(String message, String key) {
        this.message = message;
        this.key = key;
    }

    
    
    //-------------------------------------------------------- Manage the input ------------------------------------------------------------------------------------------
    /**
     * Function that convert a String message in a binary representation of the message converting letter by letter to ascii binary number
     * @param message
     * @return
     */
    public String convertMessageToBytes(){
        String bytes_message ="";
        //LinkedList<String> bytes_message = new LinkedList<String>();
        LinkedList<Integer> bytes = new LinkedList<Integer>();
                for (int i = 0; i < message.length(); i++)
            bytes.add( Character.hashCode( message.charAt(i)));

        for (int i = 0; i < bytes.size(); i++) {
            bytes_message += ("0" + Integer.toBinaryString( bytes.get(i) )  );
        }
        return bytes_message;
    }
    
    public String convertKeyToBytes(){
        String bytes_key = "";
        LinkedList<Integer> bytes = new LinkedList<Integer>();
                for (int i = 0; i < key.length(); i++)
            bytes.add( Character.hashCode( key.charAt(i)));

        for (int i = 0; i < bytes.size(); i++) {
            bytes_key += Integer.toBinaryString( bytes.get(i) ) + "1"  ;
        }
        return bytes_key;
    }


        //-------------------------------------------------------- Key Generator ------------------------------------------------------------------------------------------
    /**
     * Permuted choice 1
     * Function that permute the key.
     * @param key
     * @return
     */
    public LinkedList<String> pc1(){
        String k = convertKeyToBytes();
        String newk = "";
        LinkedList<Integer> cd = new LinkedList<Integer>(Arrays.asList(57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55,47,39, 31, 23, 15, 07, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4));
        //System.out.println(k);
        //System.out.println(cd);
        for (int i = 0; i < cd.size() ; i++) {
            newk += k.charAt( cd.get(i) - 1) ;
        }
        //System.out.println(newk);
        String c0 = newk.substring(0,28);
        String d0 = newk.substring(28);
        //System.out.println("c0: " + c0 +" " +c0.length() + "\n\n" + "d0: " + d0 +" " +d0.length());
        LinkedList<String> c0d0 = new LinkedList<String>();
        c0d0.add(c0);
        c0d0.add(d0);
        return c0d0;
    }

    /**
     *
     * @param c
     * @param d
     * @return
     */
    public String pc2(String c, String d){
        String cd = c + d;
        String k = "";
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 14, 52, 31, 37, 47, 55, 30 ,40, 51, 42, 33, 48, 44, 49, 39, 56, 34, 53, 46 ,42, 50, 36, 29, 32));
        for (int i = 0; i < pc.size() ; i++) {
            k += cd.charAt( pc.get(i) -1 ) ;
        }
        return k;
    }
    
    public String leftShift(String a){
    String z = "";
    for (int i = 0; i < a.length(); i++) {
            z += a.charAt( ( i%( a.length() - 1 ) ) + 1 );
        }
    return z;
    }

    public void  keyGenerator(){
        LinkedList<String> cd = pc1();
        String c,d;
        for (int i = 0; i < 16; i++) {
            c = leftShift(cd.get(0)); 
            d = leftShift(cd.get(1));
            keys.add(pc2(c,d));   
        }
    }
    
    
//-------------------------------------------------------- Core Methods ------------------------------------------------------------------------------------------
    //--------------- Phase 1 ---------------------    
    /**
     * 
     * @param message
     * @return 
     */
    public LinkedList<String> initialPermutation(String message){
        String m = convertMessageToBytes();
        //58, 50, 42, 34, 26, 18, 10, 02, 60, 52, 44, 36, 28, 20, 12, 04, 62, 54, 46, 38, 30, 22, 14, 06, 64, 56, 48, 40, 32, 24, 16, 08, 57, 49, 41, 33, 25, 17, 09, 01, 59, 51, 43, 35, 27, 19, 11, 03, 61, 53, 45, 37, 29, 21, 13, 05, 63, 55, 47, 39, 31, 23, 15, 07
        String a = "";
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 14, 52, 31, 37, 47, 55, 30 ,40, 51, 42, 33, 48, 44, 49, 39, 56, 34, 53, 46 ,42, 50, 36, 29, 32));
        for (int i = 0; i < pc.size() ; i++) {
            a += m.charAt( pc.get(i) -1 ) ;
        }
        LinkedList<String> lr = new LinkedList<String>();
        lr.add(a.substring(0,16));
        lr.add(a.substring(16));
        return lr;
    }
    //--------------- Phase 2 ---------------------    
    
    public String innerFunction(String ri, String keyi){
        
        String infu = "";
        return infu;
    }
    
    /**
     * 
     * @param message
     * @return 
     */
    public String expantion(String ri){
        //32, 01, 02, 03, 04, 05, 04, 05, 06, 07, 08, 09, 08, 09, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 01
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1));
        String a = "";
        for (int i = 0; i < pc.size() ; i++) {
            a += ri.charAt( pc.get(i) -1 ) ;
        }
        
        return a;
    }
    
    public String XOR(String ri, String k){
        String a = "";
        for(int i = 0; i < 48; i++) {
            if(ri.charAt(i) == k.charAt(i)) a += 1;
            else a +=0 ;
        }
        return a;
    }
    
    
    /**
     * 
     * @param l
     * @param r
     * @param i
     * @return 
     */
    public LinkedList<String> round(String l, String r,int i){
        LinkedList<String> lr = new LinkedList<String>();
        String li = "",ri="";
        li = r;
        ri = XOR(expantion(ri),keys.get(i));
        if (i == 16) {
            lr.add(ri);
            lr.add(li);
        }
        return lr;
    }

    
    public String des_Encryption(String key, String message){
        //convert message to bytes
        String m = convertMessageToBytes();
        String encriptedMessage = "";
        //Generate 16 keys
        keyGenerator();
        //check if message is multiple of 64 if not fill it with zeros
        if (m.length() % 64 != 0) {
            int a = 64 - (m.length()%64);
            for (int i = 0; i < a; i++) {
                m += "0";
            }
        }
        int numOfBlocks = m.length()/64;
        // for every block make the encryption
        for (int i = 0; i < numOfBlocks; i++) {
            String block = m.substring(64*i, 64*(i+1));
            initialPermutation(block);
        }
        
        
        
        
        
        return encriptedMessage; 
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        DES des = new DES("Hello World", "danielfe");
        String m = des.convertMessageToBytes();
        if (m.length() % 64 != 0) {
            int a = 64 - (m.length()%64);
            for (int i = 0; i < a; i++) {
                m += "0";
            }
        }
        int numOfBlocks = m.length()/64;
        // for every block make the encryption
        for (int i = 0; i < numOfBlocks; i++) {
            String block = m.substring(64*i, 64*(i+1));
            System.out.println("block: " + block.length() + " -> " + block);
        }
        
        
    }


}

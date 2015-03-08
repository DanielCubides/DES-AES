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

    /**
     * Function that convert a String message in a binary representation of the message converting letter by letter to ascii binary number
     * @param message
     * @return
     */
    public LinkedList<String> convertMessageToBytes(String message){
        LinkedList<String> bytes_message = new LinkedList<String>();
        LinkedList<Integer> bytes = new LinkedList<Integer>();
                for (int i = 0; i < message.length(); i++)
            bytes.add( Character.hashCode( message.charAt(i)));

        for (int i = 0; i < bytes.size(); i++) {
            bytes_message.add("0" + Integer.toBinaryString( bytes.get(i) )  );
        }
        return bytes_message;
    }
    
    public String convertKeyToBytes(String message){
        String bytes_message = "";
        LinkedList<Integer> bytes = new LinkedList<Integer>();
                for (int i = 0; i < message.length(); i++)
            bytes.add( Character.hashCode( message.charAt(i)));

        for (int i = 0; i < bytes.size(); i++) {
            bytes_message += Integer.toBinaryString( bytes.get(i) ) + "1"  ;
        }
        return bytes_message;
    }


    /**
     * Permuted choice 1
     * Function that permute the key.
     * @param key
     * @return
     */
    public LinkedList<String> pc1(String key){
        String k = convertKeyToBytes(key);
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
        LinkedList<String> cd = pc1(key);
        String c,d;
        for (int i = 0; i < 16; i++) {
            c = leftShift(cd.get(0)); 
            d = leftShift(cd.get(1));
            keys.add(pc2(c,d));   
        }
        
        
        
        

    }

    public String initialPermutation(String[] IP, String message){

        String a = "";
        return a;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        DES des = new DES("Hello World", "danielfe");
        
        des.keyGenerator();
        System.out.println( des.keys.size()+ " "+ des.keys);
        
    }


}

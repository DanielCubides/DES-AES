package Logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.io.*;
import javax.crypto.KeyGenerator;

/**
 * Created by Daniel Cubides on 3/6/2015. DES application
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
     * Function that convert a String message in a binary representation of the
     * message converting letter by letter to ascii binary number
     *
     * @param message
     * @return
     */
    public String convertMessageToBytes(String Message) {
        String bytes_message = "";
        //LinkedList<String> bytes_message = new LinkedList<String>();
        LinkedList<Integer> bytes = new LinkedList<Integer>();
        for (int i = 0; i < message.length(); i++) {
            bytes.add(Character.hashCode(message.charAt(i)));
        }

        for (int i = 0; i < bytes.size(); i++) {
            bytes_message += ("0" + Integer.toBinaryString(bytes.get(i)));
        }
        return bytes_message;
    }

    public String convertKeyToBytes() {
        String bytes_key = "";
        LinkedList<Integer> bytes = new LinkedList<Integer>();
        for (int i = 0; i < key.length(); i++) {
            bytes.add(Character.hashCode(key.charAt(i)));
        }

        for (int i = 0; i < bytes.size(); i++) {
            bytes_key += Integer.toBinaryString(bytes.get(i)) + "1";
        }
        return bytes_key;
    }

    //-------------------------------------------------------- Key Generator ------------------------------------------------------------------------------------------
    /**
     * Permuted choice 1 Function that permute the key.
     *
     * @param key
     * @return
     */
    public LinkedList<String> pc1() {
        String k = convertKeyToBytes();
        String newk = "";
        LinkedList<Integer> cd = new LinkedList<Integer>(Arrays.asList(57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 07, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4));
        //System.out.println(k);
        //System.out.println(cd);
        for (int i = 0; i < cd.size(); i++) {
            newk += k.charAt(cd.get(i) - 1);
        }
        //System.out.println(newk);
        String c0 = newk.substring(0, 28);
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
    public String pc2(String c, String d) {
        String cd = c + d;
        String k = "";
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 14, 52, 31, 37, 47, 55, 30, 40, 51, 42, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32));
        for (int i = 0; i < pc.size(); i++) {
            k += cd.charAt(pc.get(i) - 1);
        }
        return k;
    }

    public String leftShift(String a) {
        String z = "";
        for (int i = 0; i < a.length(); i++) {
            z += a.charAt((i % (a.length() - 1)) + 1);
        }
        return z;
    }

    public void keyGenerator() {
        LinkedList<String> cd = pc1();
        String c, d;
        for (int i = 0; i < 16; i++) {
            c = leftShift(cd.get(0));
            d = leftShift(cd.get(1));
            keys.add(pc2(c, d));
        }
    }

//-------------------------------------------------------- Core Methods ------------------------------------------------------------------------------------------
    //--------------- Phase 1 ---------------------    
    /**
     *
     * @param message
     * @return
     */
    public LinkedList<String> initialPermutation(String message) {
        //58, 50, 42, 34, 26, 18, 10, 02, 60, 52, 44, 36, 28, 20, 12, 04, 62, 54, 46, 38, 30, 22, 14, 06, 64, 56, 48, 40, 32, 24, 16, 08, 57, 49, 41, 33, 25, 17, 09, 01, 59, 51, 43, 35, 27, 19, 11, 03, 61, 53, 45, 37, 29, 21, 13, 05, 63, 55, 47, 39, 31, 23, 15, 07
        String a = "";
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7));
        for (int i = 0; i < pc.size(); i++) {
            a += message.charAt(pc.get(i) - 1);
        }
        LinkedList<String> lr = new LinkedList<String>();
        lr.add(a.substring(0, 32));
        lr.add(a.substring(32));
        return lr;
    }
    //--------------- Phase 2 ---------------------    

    public String innerFunction(String ri, String keyi) {
        String c = XOR(expantion(ri), keyi);
        LinkedList<String> B = new LinkedList<>();
        //System.out.println(c);
        for (int i = 0; i < 8; i++) {
            B.add(c.substring(i * 6, 6 * (i + 1)));
        }
        //System.out.println(B);
        String s = "";
        LinkedList<String> sbox = sbox(B);
        //System.out.println(sbox);
        for(int i = 0; i < 8; i++) {
                s += sbox.get(i);
            }
        return s;
    }

    /**
     *
     * @param message
     * @return
     */
    public String expantion(String ri) {
        //32, 01, 02, 03, 04, 05, 04, 05, 06, 07, 08, 09, 08, 09, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 01
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1));
        String a = "";
        //System.out.println(ri.length());
        for (int i = 0; i < pc.size(); i++) {
            a += ri.charAt(pc.get(i) - 1);
        }

        return a;
    }

    public String XOR(String ri, String k) {
        String a = "";
        for (int i = 0; i < ri.length(); i++) {
            if (ri.charAt(i) != k.charAt(i)) {
                a += 1;
            } else {
                a += 0;
            }
        }
        return a;
    }

    public LinkedList<String> sbox(LinkedList<String> bs) {
        LinkedList<int[][]> s_boxes = new LinkedList<int[][]>();
        int[][] sbox1 = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        };
        int[][] sbox2 = {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        };
        int[][] sbox3 = {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
        };
        int[][] sbox4 = {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        };
        int[][] sbox5 = {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
        };
        int[][] sbox6 = {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
        };
        int[][] sbox7 = {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
        };
        int[][] sbox8 = {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
        };
        s_boxes.add(sbox1);
        s_boxes.add(sbox2);
        s_boxes.add(sbox3);
        s_boxes.add(sbox4);
        s_boxes.add(sbox5);
        s_boxes.add(sbox6);
        s_boxes.add(sbox7);
        s_boxes.add(sbox8);

        //System.out.println(b1 + " " +b1.substring(0, 2) + " "+ b1.substring(2));
        //System.out.println(b1 + " " +Integer.parseInt(b1.substring(0, 2),2) + " "+ Integer.parseInt(b1.substring(2),2));
        LinkedList<String> soutput = new LinkedList<String>();
        for (int i = 0; i < 8; i++) {
            String bi = bs.get(i);
            int row = Integer.parseInt(bi.substring(0, 2), 2);
            int col = Integer.parseInt(bi.substring(2), 2);
            int[][] sbox = s_boxes.get(i);
            int a = sbox[row][col];
            String s = Integer.toBinaryString(a);
            if( s.length() < 4){
                String zeros = "";
                 for(int j = 0; j < 4-s.length(); j++) {
                         zeros +="0";
                     }
                 
            soutput.add(zeros + s);
            }
            else
                soutput.add(s);
        }
        return soutput;

    }

    /**
     *
     * @param l
     * @param r
     * @param i
     * @return
     */
    public LinkedList<String> round(String l, String r, int i) {
        LinkedList<String> lr = new LinkedList<String>();
        String li = "", ri = "";
        li = r;
        ri = XOR(innerFunction(r, keys.get(i)), l);
        //System.out.println(li + " -> "+ li.length());
        //System.out.println(ri + " -> "+ ri.length());
        if (i == 16) {
            lr.add(ri);
            lr.add(li);
        } else {
            lr.add(li);
            lr.add(ri);
        }
        return lr;
    }
//-------------------------------------------------------------------------Last pertumation--------------------------------------------------------------------------------------------------
    public String LastPermutation(String message) {
        //58, 50, 42, 34, 26, 18, 10, 02, 60, 52, 44, 36, 28, 20, 12, 04, 62, 54, 46, 38, 30, 22, 14, 06, 64, 56, 48, 40, 32, 24, 16, 08, 57, 49, 41, 33, 25, 17, 09, 01, 59, 51, 43, 35, 27, 19, 11, 03, 61, 53, 45, 37, 29, 21, 13, 05, 63, 55, 47, 39, 31, 23, 15, 07
        String a = "";
        LinkedList<Integer> pc = new LinkedList<Integer>(Arrays.asList(58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7));
        LinkedList<Integer> pcinverse = new LinkedList<>();
        for(int j = 0; j < pc.size(); j++) {
                pcinverse.add(pc.get(pc.size() - j - 1));
            }
        for (int i = 0; i < pc.size(); i++) {
            a += message.charAt(pc.get(i) - 1);
        }
        return a;
    }
    
    public String byteToString(String EncryptedMessageinbytes){
    LinkedList<String> c = new LinkedList<String>();
    String message = "";
        for (int i = 0; i < 8; i++) {
            int letter = Integer.parseInt(EncryptedMessageinbytes.substring(i*8, (i+1)*8 -1), 2);
            char a = (char)letter;
            message += a;
        }
        return message;
    }
//------------------------------------------------------------------------Des Encryption-----------------------------------------------------------------------------------------------------
    public String des_Encryption(String key, String message) {
        //convert message to bytes
        String m = convertMessageToBytes(message);
        System.out.println("El mensaje es: " + message);
        System.out.println("El mensaje en bytes es: " + m);
        String encriptedMessage = "";
        //Generate 16 keys
        keyGenerator();
        System.out.println("Las 16 llaves son: " + keys.toString());
        //check if message is multiple of 64 if not fill it with zeros
        if (m.length() % 64 != 0) {
            int a = 64 - (m.length() % 64);
            for (int i = 0; i < a; i++) {
                m += "0";
            }
        }
        int numOfBlocks = m.length() / 64;
        // for every block make the encryption
        for (int i = 0; i < numOfBlocks; i++) {
            String block = m.substring(64 * i, 64 * (i + 1));
            //System.out.println(block);
            LinkedList<String> IP = initialPermutation(block);
            //System.out.println(IP);
            String l = "", r = "";
            for (int j = 0; j < 16; j++) {
                if (j == 0){
                    l = IP.get(0);
                    r = IP.get(1);
                }
                //System.out.println(l + " " + r + " " + j );
                LinkedList<String> lr = round(l, r, j);
                l = lr.get(0);
                r = lr.get(1);
            }
            String c = l + r;
            c = LastPermutation(c);
            //System.out.println(c);
            
            encriptedMessage += byteToString(c);
            
        }

        return encriptedMessage;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        DES des = new DES("TE AMO MUJER DE MI VIDA", "danielfe");
        String c = des.des_Encryption(des.key, des.message);
        System.out.println("el mensaje escriptado es: " + c);
    }

}

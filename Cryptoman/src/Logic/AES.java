/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.Random;

/**
 *
 * @author Felipe
 */
public class AES {
    
    void initialRound(String message){
        
    }
    
    void addRoundKey(){
        
    }
    
    void invSubBytes(){
        
    }
    
    void invShiftRows(){
        
    }
    
    void keyGenerator(){
        byte[] bytes = new byte[16];
        byte[][] keyMatrix = new byte[4][4];
        Random rand = new Random(System.currentTimeMillis());
        rand.nextBytes(bytes);
        
        int k=0;
        for(int i=0; i<bytes.length; i++){
            keyMatrix[i%4][k]=bytes[i];
            if ((i%4)==3){
                k++;
            }
        }
        
        subKeyGenerator(keyMatrix);
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                System.out.print(String.format("%X", keyMatrix[i][j]) + " ");
            }
            System.out.println();
        }
        
        
    }
    
    
    void subKeyGenerator(byte[][] keyMatrix){
        byte [][]subKeyMatrix= new byte[4][44];
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                subKeyMatrix[i][j]=keyMatrix[i][j];
            }
        }
        for (int j=4; j<44; j++){
            for (int i=0; i<4; i++){
                subKeyMatrix[i][j]=(byte) (subKeyMatrix[i][j-1]^(byte)subKeyMatrix[i][j-4]);
            }
            System.out.println();
        }
        
        for (int i=0; i<4; i++){
            for (int j=0; j<44; j++){
                System.out.print(String.format("%X", subKeyMatrix[i][j]) + " ");
            }
            System.out.println();
        }

        
    }
    
    public static void main(String args[]){
        AES aes = new AES();
        aes.keyGenerator();
    }

}


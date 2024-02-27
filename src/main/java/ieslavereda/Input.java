package ieslavereda;

import java.util.Scanner;

public class Input {
    private static Scanner sc = new Scanner(System.in);
    public static Coordinate enterCoordinate(String message){

        int num=-1;
        char letter='Z';

        String chain;

        do {
            System.out.print(message);
            chain = sc.next().toUpperCase();

            if (chain.length()>1){
                letter = chain.charAt(0);
                num = chain.charAt(1)-48;
            }
        } while (!letterCorrect(letter) || !numberCorrect(num));

        return new Coordinate(letter,num);
    }
    public static boolean enterOption(String message){
        String chain;

        do {
            System.out.print(message);
            chain = sc.next().toUpperCase();
        } while(chain.length()!=1 || (chain.charAt(0)!='Y' && chain.charAt(0)!='N'));
        return chain.charAt(0)=='Y';
    }
    public static String enterString(String message){
        System.out.print(message);
        return sc.next();
    }
    private static boolean letterCorrect(char letter){
        return letter>='A' && letter<='H';
    }
    private static boolean numberCorrect(int number){
        return number>=1 && number<=8;
    }
    public static String enterNombre(){
        return sc.next();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ryandalal
 */
public class Words {

    /**
     * @param args the command line arguments
     */
    public static ArrayList <String> words;
    public static int SizeX;
    public static int SizeY;
    
    public static void main(String[] args) {
        // TODO code application logic here
        //File fl = new File("/Users/ryandalal/Documents/josh is very annoying.txt");
        //PrintStream ps = new PrintStream(new FileOutputStream(fl, true));
        words = new ArrayList<String>();
        System.out.println("Word Search Creator");
        getSize();
        getWords();
        PuzzleMaker pm = new PuzzleMaker();
        pm.pgen();
        System.out.println(pm);
    }
    public static <T> int randomIndex(ArrayList<T> list)
    {
        return 0 + (int)Math.floor(Math.random()* ((list.size() - 1) - 0 + 1));
    }
    public static char randomLetter()
    {
        final int MIN_LETTER = 'A';
        final int MAX_LETTER = 'Z';
        
        return (char)(MIN_LETTER + (int)Math.floor(Math.random() * (MAX_LETTER - MIN_LETTER + 1)));
    }
    public static int RandomDir()
    {
        return PuzzleMaker.MIN_DIR + (int)Math.floor(Math.random() * (PuzzleMaker.MAX_DIR - PuzzleMaker.MIN_DIR + 1));
    }
    public static void getWords()
    {
        Scanner sc = new Scanner(System.in);
        
        String cword = "";//current word

        while(true)  
        {
            do
            {
                System.out.print("Enter enod if you don't want anymore words; Word:");
                cword = sc.nextLine();
                cword = cword.toUpperCase();
                if(cword.length() > Math.max(SizeX, SizeY) || cword.length() < 3)
                {
                    System.out.println("Minimum length of 3 maximum length of " + Math.max(SizeX, SizeY));
                    cword = "";
                }
                else if(cword.matches("[A-Z]+") == false)
                {
                    System.out.println("Make sure you only use letters!");
                    cword = "";
                }
            }
            while(cword.equals(""));
           
            if(cword.equals("ENOD"))
            {
                break;//break out of all looops
            }
            else
            {
                words.add(cword); 
                cword = "";
            }
        }
        System.out.println(words);
    }
    
    public static void getSize()//get the list of words from the user
    {
        Scanner sc = new Scanner(System.in);
        
        //x size
        SizeX = 0;
        do
        {
            System.out.print("Length of Puzzle: ");
            try
            {
                SizeX = sc.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Pick a whole number");
                sc.nextLine();     
                SizeX = 0;
            }
            if(SizeX < 5 || SizeX > 20)
            {
                System.out.println("Minimum size of 5 and Maximum of 20");
                SizeX = 0;
            }
        }
        while(SizeX < 5 || SizeX > 20);
        
        // size of y
        SizeY = 0;
        do
        {
            System.out.print("Width of Puzzle: ");
            try
            {
                SizeY = sc.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Pick a whole number");
                sc.nextLine();     
                SizeY = 0;
            }
            if(SizeY < 5 || SizeY > 20)
            {
                System.out.println("Minimum size of 5 and Maximum of 20");
                SizeY = 0;
            }
        }
        while(SizeY < 5 || SizeY > 20);
    }
    
}

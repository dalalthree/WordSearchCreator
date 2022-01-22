/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

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
        File fl = new File("output.html");
        File fl_key = new File("outputkey.html");
        fl.delete();
        fl_key.delete();
        Scanner sc = new Scanner(System.in);
        words = new ArrayList<String>();
        System.out.println();
        System.out.println("Word Search Creator");
        System.out.print("Title of word search: ");
        String title = sc.nextLine();
        System.out.println();
        getSize(sc);
        System.out.println();
        getWords(sc);
        PuzzleMaker pm = new PuzzleMaker();
        pm.pgen();
        try{
          PrintStream ps = new PrintStream(new FileOutputStream(fl, true), false);
          PrintStream ps_key = new PrintStream(new FileOutputStream(fl_key, true), false);
          //outputs puzzle
          ps.println("<h1 style=\"font-size: 80px; text-align: center;\">" + title + "</h1>");
          ps.println("<p style=\"font-size: 40px; text-align: center;font-family: monospace;\" >" + pm.output() + "</p>");
          ps.println("<h4 style=\"font-weight: normal; font-size: 50px; text-align: center;\">");
          for(int i = 0; i < words.size() - 1; i ++){
            ps.println(words.get(i) + ", ");
          }
          ps.println(words.get(words.size() - 1));
          ps.println("</h4>");
          ps.close();
          //outputs key
          ps_key.println("<h1 style=\"font-size: 80px; text-align: center;\">" + title + " KEY" + "</h1>");
          ps_key.println("<p style=\"font-size: 40px; text-align: center;font-family: monospace;\" >" + pm.outputKey() + "</p>");
          ps_key.println("<h4 style=\"font-weight: normal; font-size: 50px; text-align: center;\">");
          for(int i = 0; i < words.size() - 1; i ++){
            ps_key.println(words.get(i) + ", ");
          }
          ps_key.println(words.get(words.size() - 1));
          ps_key.println("</h4>");
          ps_key.close();

          System.out.println();
          System.out.println("Successfully printed open output.html to view and print");
          System.out.println();
        }
        catch(Exception e){
          System.out.print(e);
        }
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
    public static void getWords(Scanner sc)
    {   
        String cword = "";//current word
        System.out.println("Enter \"enod\" when you finish entering words");
        sc.nextLine();
        while(true)  
        {
            do
            {
                System.out.print("Enter a word:");
                cword = sc.nextLine();
                cword = cword.toUpperCase();
                if(cword.length() > Math.max(SizeX, SizeY) || cword.length() < 3)
                {
                    System.out.println("Minimum length of 3, Maximum length of " + Math.max(SizeX, SizeY));
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
    }
    
    public static void getSize(Scanner sc)//get the list of words from the user
    {
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

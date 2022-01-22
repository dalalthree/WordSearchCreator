/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collections;
import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author ryandalal
 */
public class PuzzleMaker {
    private char[][] puzzle;
    private char[][] key;
    
    private final static char NO_LETTER = '?';
    
    private static final int DIR_RIGHT = 0;
    private static final int DIR_DOWN = 1;
    private static final int DIR_LEFT = 2;
    private static final int DIR_UP = 3;
    public static final int MIN_DIR = 0;
    public static final int MAX_DIR = 3;
    
    ArrayList<WPI> my_wpis;
    
    public PuzzleMaker()
    {
        puzzle = new char[Words.SizeY][Words.SizeX];
        key = new char[Words.SizeY][Words.SizeX];
        my_wpis = new ArrayList<>();
        resetPuzzle();
    }
    public boolean pgen()//puzzle generateor
    {
        //first sort words longest to shortest
        Collections.sort(Words.words, new WordComparator());
        
        //base of creating za puzzle
        int num_words_in_puzzle = 0;
        
        while (num_words_in_puzzle < Words.words.size())
        {
            if (placeOneWordInPuzzle(num_words_in_puzzle))
            {
                ++num_words_in_puzzle;
            }
            else 
            {
                --num_words_in_puzzle;
                if (num_words_in_puzzle < 0)
                    return false;
                else
                    rmLastWordFromPuzzle();
            }
        }
        
        copyAnswers();
        fillRestOfPuzzleWithRandomLetters();
        return true;
    }
    private void copyAnswers()
    {
      for(int i = 0; i < puzzle.length; i ++)
      {
        for(int j = 0; j < puzzle[i].length; j ++)
        {
          key[i][j] = puzzle[i][j];
        }
      }
    }
    private boolean placeOneWordInPuzzle(int ix_word)
    {
        ArrayList<WPI> poss_placements = genAllPossWPIsForWord(ix_word);
        
        while (!poss_placements.isEmpty())
        {
            int ix_wpi_to_try = Words.randomIndex(poss_placements);
            WPI wpi_to_try = poss_placements.remove(ix_wpi_to_try);
            
            if (canPlaceWordAt(ix_word, wpi_to_try))
            {
                addWordAt(ix_word, wpi_to_try);
                return true;
            }
        }
        return false;
    }
    public String output()
    {
        String output = "";
        
        for(int y = 0 ; y <= Words.SizeY - 1; y += 1)
        {
            for(int x = 0 ; x <= Words.SizeX - 1; x += 1)
            {
                output += puzzle[y][x];
                if(x == Words.SizeX - 1)
                {
                    output += "<br />";
                }
                else
                {
                    output += ' ';
                }
            }
        }
        
        return output;
    }
    public String outputKey()
    {
        String output = "";
        
        for(int y = 0 ; y <= Words.SizeY - 1; y += 1)
        {
            for(int x = 0 ; x <= Words.SizeX - 1; x += 1)
            {
                if(key[y][x] == puzzle[y][x])
                {
                  output += "<span style=\"color:red\">" + puzzle[y][x] + "</span>";
                }
                else
                  output += puzzle[y][x];
                if(x == Words.SizeX - 1)
                {
                    output += "<br />";
                }
                else
                {
                    output += ' ';
                }
            }
        }
        
        return output;
    }
    
    private ArrayList<WPI> genAllPossWPIsForWord(int ix_word)
    {
        ArrayList<WPI> all_wpis = new ArrayList<>();
        
        int ori_x_max = Words.SizeX - Words.words.get(ix_word).length();
        int ori_x_min = 0 + Words.words.get(ix_word).length();
        
        for (int y = 0; y <= Words.SizeY - 1;  ++y)
        {
            for (int x = 0; x <= ori_x_max; ++x)
            {
                all_wpis.add(new WPI(new Point(x, y), DIR_RIGHT));
            }
            for(int x = Words.SizeX - 1; x >= ori_x_min; -- x)
            {
              all_wpis.add(new WPI(new Point(x, y), DIR_LEFT));
            }
        }
        
        int ori_y_max = Words.SizeY - Words.words.get(ix_word).length();
        int ori_y_min = 0 + Words.words.get(ix_word).length();
        
        for (int x = 0; x <= Words.SizeX - 1;  ++x)
        {
            for (int y = 0; y <= ori_y_max; ++y)
            {
                all_wpis.add(new WPI(new Point(x, y), DIR_DOWN));
            }
            for (int y = Words.SizeY - 1; y >= ori_y_min; --y)
            {
                all_wpis.add(new WPI(new Point(x, y), DIR_UP));
            }
        }
        
        return all_wpis;
    }
    private boolean canPlaceWordAt(int ix_word, WPI wpi)
    {
        if (wpi.dir == DIR_RIGHT)
        {
            for (int i = 0; i < Words.words.get(ix_word).length();  ++i)
            {
                char whats_here = puzzle[wpi.origin.y][wpi.origin.x + i];
                char my_letter = Words.words.get(ix_word).charAt(i);
                
                if (whats_here != NO_LETTER && whats_here != my_letter)
                    return false;
            }
        }
        else if (wpi.dir == DIR_LEFT)
        {
            for (int i = 0; i < Words.words.get(ix_word).length();  ++i)
            {
                char whats_here = puzzle[wpi.origin.y][wpi.origin.x - i];
                char my_letter = Words.words.get(ix_word).charAt(i);
                
                if (whats_here != NO_LETTER && whats_here != my_letter)
                    return false;
            }
        }
        
        else if (wpi.dir == DIR_DOWN)
        {
            for (int i = 0; i < Words.words.get(ix_word).length();  ++i)
            {
                char whats_here = puzzle[wpi.origin.y + i][wpi.origin.x];
                char my_letter = Words.words.get(ix_word).charAt(i);
                
                if (whats_here != NO_LETTER && whats_here != my_letter)
                    return false;
            }
        }
        else if (wpi.dir == DIR_UP)
        {
          for (int i = 0; i < Words.words.get(ix_word).length();  ++i)
          {
              char whats_here = puzzle[wpi.origin.y - i][wpi.origin.x];
              char my_letter = Words.words.get(ix_word).charAt(i);
              
              if (whats_here != NO_LETTER && whats_here != my_letter)
                  return false;
          }
        }
        return true;
    }
    
    private void addWordAt(int ix_word, WPI wpi)
    {
        if (wpi.dir == DIR_RIGHT)
        {
            for (int i = 0; i < Words.words.get(ix_word).length(); ++i)
            {
                puzzle [wpi.origin.y][wpi.origin.x + i] = Words.words.get(ix_word).charAt(i);
            }
        }
        if (wpi.dir == DIR_LEFT)
        {
            for (int i = 0; i < Words.words.get(ix_word).length(); ++i)
            {
                puzzle [wpi.origin.y][wpi.origin.x - i] = Words.words.get(ix_word).charAt(i);
            }
        }
        else if (wpi.dir == DIR_DOWN)
        {
            for (int i = 0; i < Words.words.get(ix_word).length(); ++i)
            {
                puzzle [wpi.origin.y + i][wpi.origin.x] = Words.words.get(ix_word).charAt(i);
            }
        }
        else if (wpi.dir == DIR_UP)
        {
            for (int i = 0; i < Words.words.get(ix_word).length(); ++i)
            {
                puzzle [wpi.origin.y - i][wpi.origin.x] = Words.words.get(ix_word).charAt(i);
            }
        }
        
        my_wpis.add(wpi);
    }
    
    public void fillRestOfPuzzleWithRandomLetters()
    {
        for (int y = 0; y < Words.SizeY; ++y)
        {
            for (int x = 0; x < Words.SizeX; ++x)
            {
                if (puzzle[y][x] == NO_LETTER)
                    puzzle[y][x] = Words.randomLetter();
            }
        }   
    }
    public void resetPuzzle()
    {
        for (int y = 0; y < Words.SizeY; ++y)
        {
            for (int x = 0; x < Words.SizeX; ++x)
            {
                puzzle[y][x] = NO_LETTER;
            }
        }
        my_wpis.clear();
    }
    private void rmLastWordFromPuzzle()
    {
        int ix_last_word = my_wpis.size() - 1;
        
        WPI wpi_last_word = my_wpis.get(ix_last_word);
        
        if (wpi_last_word.dir == DIR_RIGHT)
        {
            for (int i = 0; i < Words.words.get(ix_last_word).length(); ++i)
            {
                if (!isLocInAnyWordBefore(new Point(wpi_last_word.origin.x + i, 
                    wpi_last_word.origin.y), ix_last_word))
                    puzzle[wpi_last_word.origin.y][wpi_last_word.origin.x + i] = NO_LETTER;
            }
        }
        else if (wpi_last_word.dir == DIR_LEFT)
        {
            for (int i = 0; i < Words.words.get(ix_last_word).length(); ++i)
            {
                if (!isLocInAnyWordBefore(new Point(wpi_last_word.origin.x - i, 
                    wpi_last_word.origin.y), ix_last_word))
                    puzzle[wpi_last_word.origin.y][wpi_last_word.origin.x - i] = NO_LETTER;
            }
        }
        else if (wpi_last_word.dir == DIR_DOWN)
        {
            for (int i = 0; i < Words.words.get(ix_last_word).length(); ++i)
            {
                if (!isLocInAnyWordBefore(new Point(wpi_last_word.origin.x, 
                    wpi_last_word.origin.y + i), ix_last_word))
                    puzzle[wpi_last_word.origin.y + i][wpi_last_word.origin.x] = NO_LETTER;
            }
        }
        else if (wpi_last_word.dir == DIR_UP)
        {
            for (int i = 0; i < Words.words.get(ix_last_word).length(); ++i)
            {
                if (!isLocInAnyWordBefore(new Point(wpi_last_word.origin.x, 
                    wpi_last_word.origin.y - i), ix_last_word))
                    puzzle[wpi_last_word.origin.y - i][wpi_last_word.origin.x] = NO_LETTER;
            }
        }
        
        my_wpis.remove(wpi_last_word);
    }
    private boolean isLocInAnyWordBefore(Point loc, int ix_word)
    {
        for (int w = 0; w < ix_word; ++w)
        {
            if (isLocInWord(loc, w))
                return true;
        }
        return false;
    }
    private boolean isLocInWord(Point loc, int w)
    {
        WPI wpi_w = my_wpis.get(w);
        int len_w = Words.words.get(w).length();
        
        if (wpi_w.dir == DIR_RIGHT)
        {
            if(wpi_w.origin.x <= loc.x && loc.x < wpi_w.origin.x + len_w && loc.y == wpi_w.origin.y)
                return true;
        }
        else if (wpi_w.dir == DIR_LEFT)
        {
            if(wpi_w.origin.x <= loc.x && loc.x < wpi_w.origin.x - len_w && loc.y == wpi_w.origin.y)
                return true;
        }
        else if (wpi_w.dir == DIR_DOWN)
        {
            if(loc.x == wpi_w.origin.x && wpi_w.origin.y <= loc.y && loc.y < wpi_w.origin.y + len_w)
                return true;
        }
        else if (wpi_w.dir == DIR_DOWN)
        {
            if(loc.x == wpi_w.origin.x && wpi_w.origin.y <= loc.y && loc.y < wpi_w.origin.y - len_w)
                return true;
        }
        
        return false;
    }
}
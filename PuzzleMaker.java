/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;

/**
 *
 * @author ryandalal
 */
public class PuzzleMaker {
    private char[][] puzzle;
    public PuzzleMaker()
    {
        puzzle = new char[Words.SizeY][Words.SizeX];
        for(int y = 0 ; y <= Words.SizeY - 1; y += 1)
        {
            for(int x = 0 ; x <= Words.SizeX - 1; x += 1)
            {
                puzzle[y][x] = 'I';
            }
        }
    }
    public void pgen()//puzzle generateor
    {
        //fill in later jdhfjkdhslfajkshdjk
    }
    public String toString()
    {
        String output = "";
        
        for(int y = 0 ; y <= Words.SizeY - 1; y += 1)
        {
            for(int x = 0 ; x <= Words.SizeX - 1; x += 1)
            {
                output += puzzle[y][x];
                if(x == Words.SizeX - 1)
                {
                    output += '\n';
                }
                else
                {
                    output += ' ';
                }
            }
        }
        
        return output;
    }
}
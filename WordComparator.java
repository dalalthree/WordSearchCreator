/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words;
import java.util.Comparator;
/**
 *
 * @author ryandalal
 */
public class WordComparator implements Comparator<String>{
    public int compare(String s1, String s2)
    {
        if(s1.length() > s2.length())
        {
            return -1;//if S1 is loger than s2 put it before s2 in the list of words
        }
        else if(s1.length() < s2.length())
        {
            return 1;//if S1 is shorter than s2 put it after s2 in the list of words
        }
        else
        {
            return 0;//if S1 is the same length as s2 dont change the order of th list of words
        }
    }
}

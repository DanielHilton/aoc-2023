package dev.dhilton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        File input = new File("input.txt");
        try (Scanner sc = new Scanner(input))
        {
            List<List<Character>> rawSchematic = new ArrayList<>();
            while(sc.hasNextLine())
            {
                char[] lineChars = sc.nextLine().toCharArray();
                Character[] objChars = new Character[lineChars.length];

                for(int i = 0; i < lineChars.length; i++)
                {
                    objChars[i] = Character.valueOf(lineChars[i]);
                }

                rawSchematic.add(Arrays.asList(objChars));
            }
            sc.close();

            Schematic s = new Schematic(rawSchematic);
            int partNoTotal = s.GetPartNumberTotal();
            System.out.println(partNoTotal);
        } 
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}

package RoughBook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import Alabama.AL_PropertyWare;
import Alabama.PDFAppConfig;

public class PDFExtraction 
{

	public static void main(String[] args) throws Exception 
	{

		//String ind = PDFExtraction.extractNumber("I am Gopi vasala, Are you Gopi too?");
			  // System.out.println(ind);
		ArrayList<ArrayList<String>> list = new ArrayList<>(3);
		for(int i =0;i<list.size();i++)
		{
			list.add(new ArrayList());
			
		}
	}

	public static String extractNumber(String str) 
	{
	    //String str = "26.23,for";
	    StringBuilder myNumbers = new StringBuilder();
	    for (int i = 0; i < str.length(); i++) 
	    {
	    	char c = str.charAt(i);
	    	
	        if (Character.isDigit(str.charAt(i))||(String.valueOf(c).equals(".")&&i!=str.length()-1)) 
	        {
	            myNumbers.append(str.charAt(i));
	            System.out.println(str.charAt(i) + " is a digit.");
	        } else {
	            System.out.println(str.charAt(i) + " not a digit.");
	        }
	    }
	    System.out.println("Your numbers: " + myNumbers.toString());
	    return myNumbers.toString();
	}
}

package RoughBook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import Alabama.AL_PropertyWare;
import Alabama.PDFAppConfig;

public class PDFExtraction 
{

	public static void main(String[] args) throws Exception 
	{

		String date = "02/01/2022";
		String newDay;
		int dayInDate =Integer.parseInt(date.split("/")[0]);
		if(dayInDate<=25)
		{
			newDay = "25";
		}
		else newDay = String.valueOf(dayInDate);
		System.out.println(date);
		System.out.println(date.replaceFirst(date.split("/")[0], newDay));
			   
	}

}

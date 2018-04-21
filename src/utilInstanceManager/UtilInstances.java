package utilInstanceManager;

import java.util.Random;
import java.util.Scanner;

public class UtilInstances 
{
	private static Scanner sc;
	private static Random rngJesus;
	
	private UtilInstances ()
	{
		
	}
	
	public static Scanner getScannerInstance()
	{
		if(sc == null)
		{
			sc = new Scanner(System.in);
		}
		return sc;
	}
	
	public static Random getRandomNumberGeneratorInstance()
	{
		if(rngJesus == null)
		{
			rngJesus = new Random();
		}
		return rngJesus;
	}
}

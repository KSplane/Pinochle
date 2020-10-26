package deck;

import java.util.Random;


import utilInstanceManager.UtilInstances;

public class Card 
{
	private char suit;
	private String value;
	private char color;
	
	public Card()
	{
		
	}
	
	public Card(char suit, String value, char color)
	{
		this.suit = suit;
		this.value = value;
		this.color = color;
	}
	
	public char getSuit()
	{
		return suit;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public char getColor()
	{
		return color;
	}
	
	public void setSuit(char suit)
	{
		this.suit = suit;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public void setColor(char color)
	{
		this.color = color;
	}

	public boolean compare(Card card) //returning true if the compared card is greater
	{
		boolean result = false;
		String comparedValue = card.getValue();
		
		if(!this.value.equals(comparedValue))
		{
			if(comparedValue.equals("A")) //IF COMPARING TO AN ACE SWAP
			{
				result = true;
			}
			
			if(comparedValue.equals("10")) //IF COMPARING TO A 10 UNLESS OBJECT IS AN ACE SWAP
			{
				if(!this.value.equals("A"))
				{
					result = true;
				}
			}
			
			else if(comparedValue.equals("K"))//IF COMPARING TO A K IF THE OBJECT IS NOT A 10 OR AN ACE SWAP
			{
				if(!this.value.equals("10") && !this.value.equals("A"))
				{
					result = true;
				}
			}
			
			else if(comparedValue.equals("Q"))// IF COMPARING TO A QUEEN IF THE OBJECT IS NOT A 9 OR A JACK SWAP
			{
				if(this.value.equals("9") || this.value.equals("J"))
				{
					result = true;
				}
			}
			
			else if(comparedValue.equals("J"))// IF COMPARING TO A JACK UNLESS OBJECT IS A 9 SWAP
			{
				if(this.value.equals("9"))
				{
					result = true;
				}
			}
		}
		
		return result;
	}
	
	public Card Generate()
	{
		Random rnJesus = UtilInstances.getRandomNumberGeneratorInstance();
		int result;
		Card temp = new Card();
		
		result = rnJesus.nextInt(3); //Generate suit 
		
		if(result == 0)
		{
			temp.setColor('B');
			temp.setSuit('S');
			
		}
		
		else if(result == 1)
		{
			temp.setColor('B');
			temp.setSuit('C');
		}
		
		else if(result == 2)
		{
			temp.setColor('R');
			temp.setSuit('D');
		}
		
		else
		{
			temp.setColor('R');
			temp.setSuit('H');
		}
		
		result = rnJesus.nextInt(12); //Generate value
		
		if(result < 11)
			temp.setValue(Integer.toString(result));
		
		return temp;
	}
}

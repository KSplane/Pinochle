package deck;

import java.util.ArrayList;
import java.util.Random;
import gameController.GameController;
import player.Player;
import table.Table;
import utilInstanceManager.UtilInstances;


public class Deck 
{
	private static Card [] deck;
	private static int topCard;
	
	private Deck()
	{
		
	}
	
	public static Card [] getDeckInstance(String type)
	{
		topCard = -1;
		
		if(deck == null)
		{
			if(type.equals("Pinochle"))
			{
				deck = new Card [48];
				generatePinochleDeck();
			}
			
			else if(type.equals("Standard"))
			{
				deck = new Card[52];
				generateStandardDeck();
			}
		}
		
		return deck;
	}
	
	private static void generateStandardDeck() 
	{
		char currColor   = 'B';
		char currSuit    = 'S';
		String faceValues = "J";
		int index = 0;
		
		for(int count = 0; count < 4; count++)
		{
			
			for(int innercount = 0; innercount < 13; innercount++)
			{
				if(innercount < 9)
				{
					deck[index] = new Card(currSuit,Integer.toString(innercount + 2),currColor);
					index++;
				}
				
				else if(innercount == 9)
				{
					deck[index] = new Card(currSuit,faceValues,currColor);
					faceValues = "Q";
					index++;
				}
				
				else if(innercount == 10)
				{
					deck[index] = new Card(currSuit,faceValues,currColor);
					faceValues = "K";
					index++;
				}
				
				else if(innercount == 11)
				{
					deck[index] = new Card(currSuit,faceValues,currColor);
					faceValues = "A";
					index++;
				}
				
				else
				{
					deck[index] = new Card(currSuit,faceValues,currColor);
					index++;
					faceValues = "J";
				}
			}
			
			
			if(count == 0)
			{
				currSuit = 'C';
			}
			
			else if(count == 1)
			{
				currSuit = 'D';
				currColor = 'R';
			}
			
			
			else
			{
				currSuit = 'H';
			}
		}
		
		Shuffle();
	}

	private static void generatePinochleDeck()
	{
		char currColor   = 'B';
		char currSuit    = 'S';
		String currValue = "9";
		int index = 0;
		
		//GENERATE SPADES
		for(int spadesCount = 0; spadesCount < 2; spadesCount++)
		{
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "J";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "Q";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "K";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "10";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "A";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "9";
			index++;
		}
		
		currSuit = 'C';
		
		//GENERATE CLUBS	
		for(int clubsCount = 0; clubsCount < 2; clubsCount++)
		{
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "J";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "Q";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "K";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "10";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "A";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "9";
			index++;
		}
		
		currSuit = 'D';
		currColor = 'R';
		
		//GENERATE DIAMONDS	
		for(int diamondsCount = 0; diamondsCount < 2; diamondsCount++)
		{
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "J";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "Q";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "K";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "10";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "A";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "9";
			index++;
		}
		
		currSuit = 'H';
		
		//GENERATE HEARTS
		for(int heartsCount = 0; heartsCount < 2; heartsCount++)
		{
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "J";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "Q";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "K";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "10";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "A";
			index++;
			deck[index] = new Card(currSuit,currValue,currColor);
			currValue = "9";
			index++;
		}
		
		Shuffle();
		
	}
	public static void Print() //mostly for debugging
	{
		for(int count = 0; count < deck.length; count++)
		{
			System.out.print(deck[count].getValue());
			System.out.print(deck[count].getSuit());
			System.out.println();
		}
	}
	
	
	public static void Deal()
	{
		int index = 0;
			
		Player currPlayer = Table.Next();
		
		if(Table.getLength() == 3)
		{
			for(int count = 0; count < (deck.length - 3); count++)
			{
				currPlayer.addToHand(deck[index]);
				index++;
				
				if(Math.floorDiv(count, 3) < Math.floorDiv(count + 1, 3))
				{
					currPlayer = Table.Next();
				}
			}
		
			Card [] tempKiddy = new Card[3];
		
			tempKiddy[0] = deck[index];
			index++;
			tempKiddy[1] = deck[index];
			index++;
			tempKiddy[2] = deck[index];
			index++;
		
			GameController.setKiddy(tempKiddy);
		}
		
		else
		{
			for(int count = 0; count < 15; count++)
			{
				currPlayer.addToHand(deck[index]);
				index++;
			}
			
			Card [] tempKiddy = new Card[3];
			
			tempKiddy[0] = deck[index];
			System.out.println(tempKiddy[0].getValue() + tempKiddy[0].getSuit());
			index++;
			tempKiddy[1] = deck[index];
			System.out.println(tempKiddy[1].getValue() + tempKiddy[1].getSuit());
			index++;
			tempKiddy[2] = deck[index];
			System.out.println(tempKiddy[2].getValue() + tempKiddy[2].getSuit());
			index++;
			
			GameController.setKiddy(tempKiddy);
		}
		
		Table.setCurr(Table.getDealerValue());
	}
	
	
	public static void Shuffle()
	{
		int index;
		Card[] newDeck =  new Card[deck.length];
		Random rngJesus = UtilInstances.getRandomNumberGeneratorInstance();
		ArrayList<Integer> usedNums = new ArrayList<Integer>();
		
		getDeckInstance("Pinochle"); //need to initialize deck because it is first time deck is used in code
		
		for(int count = 0; count < deck.length; count++)
		{
			index = rngJesus.nextInt(deck.length);
			
			if(usedNums.contains(index))
			{
				do
				{
				  index = rngJesus.nextInt(deck.length); 	
				} while(usedNums.contains(index));
			}
			newDeck[index] = deck[count]; // we are adding a new card into the new deck at a random spot but are using count to iterate though the old deck
			usedNums.add(index);
		}
		
		Deck.setDeck(newDeck);
		newDeck = null;
			
	}

	public static void setDeck(Card[] newDeck) 
	{
		deck = newDeck;
		
	}

	public static Card Draw()
	{
		topCard++;
		return deck[topCard];
	}
}

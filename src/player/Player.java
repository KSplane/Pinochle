package player;

import deck.Card;

public class Player 
{
	private Card[] hand;
	private Card[] spades;
	private Card[] clubs;
	private Card[] hearts;
	private Card[] diamonds;
	private int bid;
	private char trumpSuit;
	private String name; //FOR DEBUGGING
	private boolean isBidding;
	private boolean isPlayer; //USED TO TELL WHICH OBJECT IN THE TABLE IS THE PLAYER AND WHICH IS THE COMPTUER
	
	public Player(int numberOfPlayers)
	{
		spades   = new Card[1];
		clubs    = new Card[1];
		hearts   = new Card[1];
		diamonds = new Card[1];
		
		if(numberOfPlayers == 4)
		{
			hand = new Card[12];
		}
		
		else
		{
			hand = new Card[15];
		}
		
		isBidding = true;
		isPlayer = true;
	}
	
	public Card[] getHand()
	{
		return hand;
	}
	
	public void setHand(Card [] hand)
	{
		this.hand = hand;
	}
	
	public Card[] getSpades() {
		return spades;
	}

	public  void setSpades(Card[] spades) {
		this.spades = spades;
	}

	public  Card[] getClubs() {
		return clubs;
	}

	public void setClubs(Card[] clubs) {
		this.clubs = clubs;
	}

	public Card[] getHearts() {
		return hearts;
	}

	public void setHearts(Card[] hearts) {
		this.hearts = hearts;
	}

	public Card[] getDiamonds() {
		return diamonds;
	}

	public void setDiamonds(Card[] diamonds) {
		this.diamonds = diamonds;
	}
	
	public Card [] getSuit(String suit)
	{
		if(suit.equals("S"))
			return spades;
		
		else if(suit.equals("H"))
			return hearts;
		
		else if(suit.equals("C"))
			return clubs;
		else
			return diamonds;
	}
	
	public void setTrumpSuit(char trumpSuit)
	{
		this.trumpSuit = trumpSuit;
	}
	
	public char getTrumpSuit()
	{
		return trumpSuit;
	}
	
	public void setBid(int bid) 
	{
		this.bid = bid;
		
	}
	
	public int getBid()
	{
		return bid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setIsBidding(boolean isBidding)
	{
		this.isBidding = isBidding;
	}

	public boolean getIsBidding()
	{
		return isBidding;
	}
	
	public void setIsPlayer(boolean isPlayer)
	{
		this.isPlayer = isPlayer;
	}

	public boolean getIsPlayer()
	{
		return isPlayer;
	}
	
	public void addToHand(Card card)
	{
		char suit = card.getSuit();
		
		/*
		 * We are using dynamic arrays because we do not know how many cards of each suit we will get
		 * temp is used to hold the current array so we can repopulate it when we declare it as a bigger size
		 */
		
		Card [] temp;
		
		switch(suit)
		{
			//spades is commented for what code is doing
			case 'S':
				if(spades[0] != null) //we dont have to do it on the first time because the aray size is one
				{
					temp = spades; //need to hold onto what is currently in spades
					spades = new Card[spades.length + 1]; //reallocate spades to be one size bigger to hold new card
					spades = rePopulate(spades,temp); //read everything back into spades that was already in it
				}
				
				spades[spades.length-1] = card; //add new card to hand
				spades = sort(spades); //put cards from 9-A
				break;
			
			case 'C':
				if(clubs[0] != null)
				{
					temp = clubs;
					clubs = new Card[clubs.length + 1];
					clubs = rePopulate(clubs, temp);
				}
				
				clubs[clubs.length-1] = card;
				clubs = sort(clubs);
				break;
				
			case 'H':
				if(hearts[0] != null)
				{
						temp = hearts;
						hearts = new Card[hearts.length + 1];
						hearts = rePopulate(hearts, temp);
				}
				
				hearts[hearts.length - 1] = card;
				hearts = sort(hearts);
				break;
				
			case 'D':
				if(diamonds[0] != null)
				{
					temp = diamonds;
					diamonds = new Card[diamonds.length + 1];
					diamonds = rePopulate(diamonds, temp);
				}
				
				diamonds[diamonds.length-1] = card;
				diamonds = sort(diamonds);
				break;
		}
	
		if(spades.length + clubs.length + hearts.length + diamonds.length == hand.length) //we have all of our cards
		{
			merge();
		}
	}
	
	private void merge()
	{
		int index = 0;
		
		for(int spadesCount = 0; spadesCount < spades.length; spadesCount++)
		{
			hand[index] = spades[spadesCount];
			index++;
		}
		
		for(int heartsCount = 0; heartsCount < hearts.length; heartsCount++)
		{
			hand[index] = hearts[heartsCount];
			index++;
		}
		
		for(int clubsCount = 0; clubsCount < clubs.length; clubsCount++)
		{
			hand[index] = clubs[clubsCount];
			index++;
		}
		
		for(int diamondsCount = 0; diamondsCount < diamonds.length; diamondsCount++)
		{
			hand[index] = diamonds[diamondsCount];
			index++;
		}
	}
	
	private Card[] sort(Card[] currHand)
	{
		boolean swap = false;
		Card placeHolder;
		
		if(currHand.length > 1)
		{
			for(int count = currHand.length -1; count >  0; count--)
			{
				swap = currHand[count].compare(currHand[count-1]);
				
				if(swap)
				{
					placeHolder = currHand[count-1];
					currHand[count-1] = currHand[count];
					currHand[count] = placeHolder;
				}
			}
		}
			
		return currHand;
	}
	
	private Card[] rePopulate(Card[] finished, Card[] temp)
	{
		for(int count = 0; count < temp.length; count++)
		{
			finished[count] = temp[count];
		}
		
		return finished;
	}
	
	public void printHand()
	{
		System.out.println(getName()+ " Your hand is: ");
		
		for(int count = 0; count < hand.length; count++)
		{
			System.out.print(hand[count].getValue() + hand[count].getSuit() + " ");
		}
		
		System.out.println("");
	}

	public void addKideyToHand(Card[] kiddy) 
	{
		this.hand = new Card[this.hand.length + 3];
		
		for(int count = 0; count < 3; count++)
		{
			addToHand(kiddy[count]);
		}
		
	}

}

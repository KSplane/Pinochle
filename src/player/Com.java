package player;

import deck.Card;

public class Com extends Player
{

	private boolean bidOnKiddey;
	private String  runSuits;
	private String  runOffByOneSuit;
	private boolean hasJackAround;
	private boolean hasQueenAround;
	private boolean hasKingAround;
	private boolean hasTenAround;
	private boolean hasAceAround;
	private int nineCount;
	
	
	public Com(int numberOfPlayers) 
	{
		super(numberOfPlayers);
		setBidOnKiddey(false);
		setBid(0); //so we can tell that we have not calculated our bid yet
		setIsPlayer(false);
		runSuits = "";
		runOffByOneSuit = "";
		hasJackAround = hasQueenAround = hasKingAround = hasTenAround = hasAceAround = false;
	}
		
	public boolean isHasJackAround() {
		return hasJackAround;
	}

	public void setHasJackAround(boolean hasJackAround) {
		this.hasJackAround = hasJackAround;
	}

	public boolean isHasQueenAround() {
		return hasQueenAround;
	}

	public void setHasQueenAround(boolean hasQueenAround) {
		this.hasQueenAround = hasQueenAround;
	}

	public boolean isHasKingAround() {
		return hasKingAround;
	}

	public void setHasKingAround(boolean hasKingAround) {
		this.hasKingAround = hasKingAround;
	}

	public boolean isHasTenAround() {
		return hasTenAround;
	}

	public void setHasTenAround(boolean hasTenAround) {
		this.hasTenAround = hasTenAround;
	}

	public boolean isHasAceAround() {
		return hasAceAround;
	}

	public void setHasAceAround(boolean hasAceAround) {
		this.hasAceAround = hasAceAround;
	}

	public boolean getBidOnKiddey() {
		return bidOnKiddey;
	}

	public void setBidOnKiddey(boolean bidOnKiddey) {
		this.bidOnKiddey = bidOnKiddey;
	}

	public String getRunSuits() {
		return runSuits;
	}

	public void setRunSuits(String runSuits) {
		this.runSuits = runSuits;
	}

	public String getRunOffByOneSuit() {
		return runOffByOneSuit;
	}

	public void setRunOffByOneSuit(String runOffByOneSuit) {
		this.runOffByOneSuit = runOffByOneSuit;
	}

	public int getNineCount() {
		return nineCount;
	}

	public void setNineCount(int nineCount) {
		this.nineCount = nineCount;
	}

	public void calcualteBid()
	{
		Card [] tempHand = getHand();
		
		int currBid = 0;
		
		char runSuit = checkRun(tempHand);
		setTrumpSuit(runSuit);
		int marriages = checkMarriage(tempHand);
		int pinochles  = checkPinochles();
		
		if(checkRound("J", tempHand))
		{
			currBid+=4;
			hasJackAround = true;
		}
		
		if(checkRound("Q", tempHand))
		{
			currBid += 6;
			hasQueenAround = true;
		}
		
		if(checkRound("K", tempHand))
		{
			hasKingAround = true;
			currBid += 8;
		}
		
		if(checkRound("A", tempHand))
		{
			hasAceAround = true;
			currBid += 10;
		}
		
		for(int marriageCount = 0; marriageCount < marriages; marriageCount++)
		{
			currBid+= 2;
		}
		
		currBid += aceCount(); 
		
		//if we do not have run and are not biddign on kiddey the longest suit with an ace will be the trump suit. Also calculating if AI has any 9s in here because it is the easiest place to
		currBid += checkLongSuit();
		
		
		if(pinochles == 1)
			currBid+= 4;
	
		
		else if (pinochles > 1)
			currBid += 30;
		
		
		if(runSuit != 'n')
			currBid+= 15;
		
		if(calcualteBidOnKiddey())
		{
			currBid+= 15;
		}
		
		this.setBid(currBid);
	}
	
	private int checkLongSuit() 
	{
		int result = 0;
		Card [] tempSuit;
		
		for(int count = 0; count < 4; count++)
		{
			// tempSuit.length > 4 because the suit only matters if it is 5 or greater
			switch(count)
			{
				case 0:
					tempSuit = getHearts();
					
					if(tempSuit.length > result && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
							result = tempSuit.length;
					}
						
					break;
				
				case 1:
					tempSuit = getSpades();
					
					if(tempSuit.length > result && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
							result = tempSuit.length;
					}
						
					break;
					
				case 2:
					tempSuit = getDiamonds();
					
					if(tempSuit.length > result && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
							result = tempSuit.length;
					}
						
					break;
				
				case 3:
					tempSuit = getClubs();
					
					if(tempSuit.length > result && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
							result = tempSuit.length;
					}
						
					break;
			}
		}
		
		//we are not going to return the length of the suit but the point value that we can add on to our bid
		// for a 5 card suit with an ace we add 10 points
		//for a 6 card suit with an ace we add 11 points
		//for a 7 card suit with an ace we add 12 points
		
		if(result == 5)
			result = 10;
		
		else if(result == 6)
			result = 11;
		
		else if(result == 7)
			result = 12;
			
		return result;
	}

	private int aceCount() 
	{
		int result = 0;
		
		Card [] tempHand = getHand();
		
		for(int count = 0; count < tempHand.length; count++)
		{
			if(tempHand[count].getValue().equals("A"))
			{
				result++;
			}
		}
		
		return result;
		
	}
	private char checkRun(Card [] tempHand)
	{
		boolean hasJack, hasQueen, hasKing, hasTen, hasAce, first;
		hasJack = hasQueen =  hasKing = hasTen = hasAce = first = false;
		
		int runCount = 0;
		char result = 'n';
		
		for (int index = 0; index < tempHand.length; index++ )
		{
			if(index > 0)
				first = checkFirst(tempHand, index);
				
			if(first)
			{
				if(hasJack && hasQueen && hasKing && hasTen && hasAce)
				
					/*
					 * Basically gotta do this dumb shit because we could have a run in more than
					 * one suit. So make a string of all the suits then pick the best suit and 
					 * return that as a char
					 */
					runSuits = runSuits.concat(Character.toString(tempHand[index -1].getSuit()));  
				
				
				else // If we are only missing one card in the run then bid on kiddy to try to get bid
				{
					if(hasJack)
						runCount++;
					
					if(hasQueen)
						runCount++;
					
					if(hasKing)
						runCount++;
					
					if(hasTen)
						runCount++;
					
					if(hasAce)
						runCount++;
					
					if(runCount == 4)
					{
						runOffByOneSuit = runOffByOneSuit.concat(Character.toString(tempHand[index -1].getSuit()));
						bidOnKiddey = true;
					}
				}
				
				hasJack = hasQueen = hasKing = hasTen = hasAce = false;   	
			}
			
			if(tempHand[index].getValue().equals("J"))
					hasJack = true;
			
			else if(tempHand[index].getValue().equals("Q"))
			
					hasQueen = true;
			
			else if(tempHand[index].getValue().equals("K"))
					hasKing = true;
			
				
			else if(tempHand[index].getValue().equals("10"))
					hasTen = true;
			
			
			else if(tempHand[index].getValue().equals("A"))
					hasAce = true;
		}
		
		//need to check for a run in diamonds

		if(hasJack && hasQueen && hasKing && hasTen && hasAce)
			runSuits = runSuits.concat("D");
		
		else
		{
			runCount = 0;
			
			if(hasJack)
				runCount++;
			
			if(hasQueen)
				runCount++;
			
			if(hasKing)
				runCount++;
			
			if(hasTen)
				runCount++;
			
			if(hasAce)
				runCount++;
			
			if(runCount == 4)
			{
				runOffByOneSuit = runOffByOneSuit.concat("D");
				bidOnKiddey = true;
			}
		}
		
				
		if(runSuits.length() == 1)
			result = runSuits.charAt(0);
		
		else if(runSuits.length() > 1)
			result = getRunSuit(runSuits);
		
		return result;
	}

	private char getRunSuit(String runSuits) 
	{
		Card [][] potentialSuits = new Card[4][]; //Maximum there could be 4 suits
		
		for(int index = 0; index < runSuits.length(); index++)
		{
			if(Character.toString(runSuits.charAt(index)).equals("C"))
					potentialSuits [index] = getClubs();
			
			else if(Character.toString(runSuits.charAt(index)).equals("D"))
				potentialSuits [index] = getDiamonds();
			
			else if(Character.toString(runSuits.charAt(index)).equals("S"))
				potentialSuits [index] = getSpades();
			
			else
				potentialSuits [index] = getHearts();	
		}
		
		int longestSuit = 0;
		
		for(int count = 1; count < runSuits.length(); count++)
		{
			if(potentialSuits[longestSuit].length < potentialSuits[count].length)
			{
				longestSuit = count;
			}
		}
		
		return runSuits.charAt(longestSuit);
	}

	private int checkMarriage(Card[] tempHand)
	{
		int queenCount    = 0;
		int kingCount     = 0;
		int marriageCount = 0;
		
		boolean first = false;
		
		for(int index = 0; index < tempHand.length; index++)
		{
			if(index > 0)
			{
				first = checkFirst(tempHand, index);
			}
			
			if(first)
			{
				marriageCount+= updateMarriageCount(queenCount, kingCount);
				//king and queen has to be in the same suit so we have to reset the counters
				queenCount = 0;
				kingCount = 0;
			}
			
			if(tempHand[index].getValue().equals("Q") && tempHand[index].getSuit() != getTrumpSuit())
			{
				queenCount++;
			}
			
			else if(tempHand[index].getValue().equals("K"))
			{
				kingCount++;
			}
		}
		
		marriageCount += updateMarriageCount(queenCount, kingCount); //one last time to add to the diamond marriages
		
		return marriageCount;
	}
	
	private int checkPinochles()
	{
		int count = 0;
		
		Card [] diamonds = getDiamonds();
		Card [] spades   = getSpades();
		
		int jackCount = 0;
		int queenCount = 0;
		
		for(int diamondsCount = 0; diamondsCount < diamonds.length; diamondsCount++)
		{
			if(diamonds[diamondsCount].getValue().equals("J"))
			{
				jackCount++;
			}
		}
		
		for(int spadesCount = 0; spadesCount < spades.length; spadesCount++)
		{
			if(spades[spadesCount].getValue().equals("Q"))
			{
				queenCount++;
			}
		}
				
		while(jackCount > 0 && queenCount > 0)
		{
			count++;
			jackCount--;
			queenCount--;
		}
		
		return count;
	}
	
	private boolean checkFirst(Card [] tempHand, int index)
	{
		boolean result = false;
		
		if(tempHand[index].getSuit() != tempHand[index - 1].getSuit())
		{
			result = true;
		}
		
		return result;
	}
	
	private int updateMarriageCount(int queenCount, int kingCount) //loops until we either run out of queens or kings and returns the sums
	{
		int counter = 0;
		
		while(queenCount > 0 && kingCount > 0)
		{
			counter++;
			queenCount--;
			kingCount--;
		}
		
		return counter;
	}
	
	private boolean checkRound(String value, Card [] tempHand)
	{
		boolean result = true;
		boolean found  = false;
		boolean first = false;
		
		int index = 0;
		
		while(result && index < tempHand.length)
		{
			if(index > 0) //after the first card in hand we need to check to see if the current card is a different suit then last
			{
				first = checkFirst(tempHand, index);
			}
			
			if(first) //check if we stil have  round
			{
				if(found) //the last suit had the value we are looking for
				{
					found = false;
				}
				
				else //if we diddnt have one
				{
					result = false;
				}
			}
			
			if(tempHand[index].getValue().equals(value)) //if we have value in suit
			{
				found = true;
			}
			
			index++;
		}
		
		if(!found)
		{
			result = false;
		}
		
		return result;
	}

	private boolean calcualteBidOnKiddey() 
	{
		boolean result = false;
		
		if(!(runOffByOneSuit.equals("")))
			result = true;
		
	
		return result;
	}

	public void displayMeld() 
	{
		String meld = "";
		
		
	}
}

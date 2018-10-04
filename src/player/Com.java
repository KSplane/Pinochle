package player;

import deck.Card;
import gameController.GameController;

public class Com extends Player
{

	private boolean bidOnKiddey;
	private String  runSuits;
	private boolean hasJackAround;
	private boolean hasQueenAround;
	private boolean hasKingAround;
	private boolean hasTenAround;
	private boolean hasAceAround;
	private String  marriageSuits;
	private int     pinochles;
	private int     nineCount;

	public Com(int numberOfPlayers) 
	{
		super(numberOfPlayers);
		setBidOnKiddey(false);
		setBid(0); //so we can tell that we have not calculated our bid yet
		runSuits = "";
		marriageSuits = "";
		hasJackAround = hasQueenAround = hasKingAround = hasTenAround = hasAceAround = false;
	}
		
	public boolean isHasJackAround() {
		return hasJackAround;
	}

	public void setHasJackAround(boolean hasJackAround) {
		this.hasJackAround = hasJackAround;
	}

	public int getPinochles() {
		return pinochles;
	}

	public String getMarriageSuits() {
		return marriageSuits;
	}

	public void setMarriageSuits(String marriageSuits) {
		this.marriageSuits = marriageSuits;
	}

	public void setPinochles(int pinochles) {
		this.pinochles = pinochles;
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

	public int getNineCount()
	{
		if(GameController.getTrumpSuit() != ' ')
		{
			Card [] tempSuit = getSuit(GameController.getTrumpSuit());
			int index = 0;
			
			do{
				
				if(tempSuit[index].getValue() == "9")
				{
					nineCount++;
					index++;
				}
			
			}while(tempSuit[index].getValue() == "9");
		}
		
		return nineCount;
	}

	public void calcualteBid()
	{
		Card [] tempHand = getHand();
		Card [] tempSuit;
		
		int currBid = 0;
		char longSuit;
		
		//System.out.println("about to chec run");
		checkRun(tempHand);
			
		if(runSuits != "")
			currBid+= 15;
		
		//System.out.println("Bid is at: " + currBid);
		
		//System.out.println("about to check marriages");
		checkMarriage(tempHand);
		//System.out.println("Bid is at: " + currBid);
		
		//System.out.println("about to check pinochles");
		pinochles  = checkPinochles();
		
		if(pinochles == 1)
			currBid+= 4;
	
		else if (pinochles > 1)
			currBid += 30;
		
		//System.out.println("Bid is at: " + currBid);
	
		//System.out.println("about to check jacks around");
		if(checkRound("J", tempHand))
		{
			currBid+=4;
			hasJackAround = true;
		}
		
	//	System.out.println("Bid is at: " + currBid);
		
		//System.out.println("about to check queens around");
		if(checkRound("Q", tempHand))
		{
			currBid += 6;
			hasQueenAround = true;
		}
		
		//System.out.println("Bid is at: " + currBid);
			
		//System.out.println("about to check kings around");
		if(checkRound("K", tempHand))
		{
			hasKingAround = true;
			currBid += 8;
		}
		
		//System.out.println("Bid is at: " + currBid);
		
		//System.out.println("about to check aces around");
		if(checkRound("A", tempHand))
		{
			hasAceAround = true;
			currBid += 10;
		}
		
		//System.out.println("Bid is at: " + currBid);
		
		//System.out.println("counting aces");
		currBid += aceCount();
		//System.out.println("Bid is at: " + currBid);
		
		//if we do not have run and are not biddign on kiddey the longest suit with an ace will be the trump suit. Also calculating if AI has any 9s in here because it is the easiest place to
		//System.out.println("checking long suit");
		longSuit = checkLongSuit();
		tempSuit = getSuit(longSuit);
		
		//we are not going to return the length of the suit but the point value that we can add on to our bid
				// for a 5 card suit with an ace we add 10 points
				//for a 6 card suit with an ace we add 11 points
				//for a 7 card suit with an ace we add 12 points
				
		if(tempSuit.length == 5)
			currBid += 10;
				
		else if(tempSuit.length == 6)
			currBid += 11;
				
		else if(tempSuit.length == 7)
			currBid +=  12;
		
		//System.out.println("Bid is at: " + currBid);
					
		//System.out.println("about to check if were bidding on kiddey");
		if(bidOnKiddey)
		{
			currBid+= 15;
		}
		
		//System.out.println("Bid is at: " + currBid);
		
		currBid+= getBid();
		setBid(currBid);
		
	}
	
	private char checkLongSuit() 
	{
		int currLength = 0;
		char result = 0;
		Card [] tempSuit;
		
		for(int count = 0; count < 4; count++)
		{
			// tempSuit.length > 4 because the suit only matters if it is 5 or greater
			switch(count)
			{
				case 0:
					tempSuit = getHearts();
					
					if(tempSuit.length > currLength && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
						{
							currLength = tempSuit.length;
							result = tempSuit[0].getSuit();
						}
					}
						
					break;
				
				case 1:
					tempSuit = getSpades();
					
					if(tempSuit.length > currLength && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
						{
							currLength = tempSuit.length;
							result = tempSuit[0].getSuit();
						}
					}
						
					break;
					
				case 2:
					tempSuit = getDiamonds();
					
					if(tempSuit.length > currLength && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
						{
							currLength = tempSuit.length;
							result = tempSuit[0].getSuit();
						}
					}
						
					break;
				
				case 3:
					tempSuit = getClubs();
					
					if(tempSuit.length > currLength && tempSuit.length > 4)
					{
						if(tempSuit[tempSuit.length-1].getValue().equals("A"))
						{
							currLength = tempSuit.length;
							result = tempSuit[0].getSuit();
						}
					}
						
					break;
			}
		}
				
		return result;
	}

	private int aceCount() 
	{
		int result = 0;
		
		Card [] tempHand = getHand();
		
		for(int count = 0; count < tempHand.length; count++)
		{
			if(tempHand[count].getValue().equals("A"))
				result++;	
		}
		
		return result;
		
	}
	private void checkRun(Card [] tempHand)
	{
		Card[] spades = getSpades();
		Card[] clubs = getClubs();
		Card[] hearts = getHearts();
		Card[] diamonds = getDiamonds();

		String tempRunSuits = "";
		
		if(hasRun(spades))
			tempRunSuits = tempRunSuits.concat("S");	
		
		if(hasRun(hearts))
			tempRunSuits = tempRunSuits.concat("H");
		
		if(hasRun(clubs))
			tempRunSuits = tempRunSuits.concat("C");	
		
		if(hasRun(diamonds))
			tempRunSuits = tempRunSuits.concat("D");
				
		setRunSuits(tempRunSuits);	
	}

	private boolean hasRun(Card[] suit)
	{	
		int cardCount = 0;
		
		boolean result, hasJack, hasQueen, hasKing, hasTen, hasAce;
			result = hasJack = hasQueen =  hasKing = hasTen = hasAce = false;	

		for(int count = 0; count < suit.length; count++)
		{
			if(suit[count].getValue().equals("J"))
			{	
				if(!hasJack)
				{
					hasJack = true;
					cardCount++;
				}		
			}
			
			else if(suit[count].getValue().equals("Q"))
			{	
				if(!hasQueen)
				{
					hasQueen = true;
					cardCount++;
				}
			}
			
			else if(suit[count].getValue().equals("K"))
			{	
				if(!hasKing)
				{
					hasKing = true;
					cardCount++;
				}	
			}	
			
			else if(suit[count].getValue().equals("10"))
			{	
				if(!hasTen)
				{
					hasTen = true;
					cardCount++;
				}			
			}
			
			else if(suit[count].getValue().equals("A"))
			{
				if(!hasAce)
				{
					hasAce = true;
					cardCount++;
				}		
			}
		}
			
		if(hasJack && hasQueen && hasKing && hasTen && hasAce)
			
			result = true;
		
		else if(!bidOnKiddey && !getisBiddingWinner() && cardCount == 4)
		{
			bidOnKiddey = true;
		}
			
		return result;	
	}
	
	private void checkMarriage(Card[] tempHand)
	{
		int queenCount    = 0;
		int kingCount     = 0;
		
		boolean first = false;
		
		for(int index = 0; index < tempHand.length; index++)
		{
			if(index > 0)
			{
				first = checkFirst(tempHand, index);
			}
			
			if(first)
			{	
				if(queenCount > 0 && kingCount > 0)
						updateMarriageCount(queenCount, kingCount, tempHand[index-1].getSuit());
				
				
				//king and queen has to be in the same suit so we have to reset the counters
				queenCount = 0;
				kingCount = 0;
			}
			
			if(tempHand[index].getValue().equals("Q"))
					queenCount++;
			
			
			else if(tempHand[index].getValue().equals("K") )
				kingCount++;
			
			
		}
		
		updateMarriageCount(queenCount, kingCount, 'D'); //one last time to add to the diamond marriages	
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
	
	private void updateMarriageCount(int queenCount, int kingCount, char c) //loops until we either run out of queens or kings and returns the sums
	{
		int currBid = this.getBid();
		//System.out.println("bid is at: " + currBid );
		char trumpSuit = GameController.getTrumpSuit();
		//System.out.println("Trump suit is: " + trumpSuit);
		//System.out.println("runSuits is: " + runSuits);
		//System.out.println("suit is: " + c);
		
		while(queenCount > 0 && kingCount > 0)
		{
			//System.out.println("in whle loop");
			//System.out.println(runSuits.indexOf(c));
			
			if(runSuits.indexOf(c) < 0)
			{
				//System.out.println("hi");
				
				if(trumpSuit == ' ')
				{
					//System.out.println("my");
					marriageSuits = marriageSuits.concat(Character.toString(c));
					currBid+=2;
				}
				
				else if(trumpSuit == c)
				{
					//System.out.println("name");
					marriageSuits = marriageSuits.concat(Character.toString(c));
					currBid+=4;
				}
			
				else
				{
					//System.out.println("is");
					marriageSuits = marriageSuits.concat(Character.toString(c));
					currBid+=2;
				}
				
			}
			
			//System.out.println("what?");
			queenCount--;
			kingCount--;
		}
		
		setBid(currBid);
		//System.out.println(getBid());
		
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

	public void calcTrumpSuit()
	{	
		if(!runSuits.equals(""))
		{
			GameController.setTrumpSuit(runSuits.charAt(0));
		}
		
		else
		{
			GameController.setTrumpSuit(checkLongSuit());
		}
	}
	
	
}

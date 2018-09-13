package gameController;

import java.util.Scanner;
import deck.Card;
import deck.Deck;
import utilInstanceManager.UtilInstances;
import player.Com;
import player.Player;
import table.Table;

public class GameController 
{
	private static Card [] kiddy;
	private static int bid = 20;
	private static Player biddingWinner;
	private static char trumpSuit;
	
	public GameController()
	{
		trumpSuit = ' ';
	}
	
	public static char getTrumpSuit() {
		return trumpSuit;
	}

	public static void setTrumpSuit(char trumpSuit) {
		GameController.trumpSuit = trumpSuit;
	}

	public  static Player getBiddingWinner() {
		return biddingWinner;
	}

	public static void setBiddingWinner(Player biddingWinner) {
		GameController.biddingWinner = biddingWinner;
	}

	public static void setKiddy(Card [] kiddy)
	{
		GameController.kiddy = kiddy;
	}
	
	public static Card[] getKiddy()
	{
		if(kiddy == null)
		{
			kiddy = new Card[3];
		}
		
		return GameController.kiddy;
	}
	
	public static void setBid(int bid)
	{
		GameController.bid = bid;
	}
	
	public static int getBid()
	{
		return bid;
	}
	
	public static void Start()
	{
		System.out.println("Please enter the number of players: ");
		
		int numberOfPlayer = UtilInstances.getScannerInstance().nextInt();
		setUpGame(numberOfPlayer);
	}
	
	private static void setUpGame(int numberOfPlayers)
	{
		Deck.getDeckInstance("Pinochle");
		
		if(numberOfPlayers == 3)
		{
			Player playerOne   = new Player(3);
			playerOne.setName("kevin");
			Com playerTwo      = new Com(3);
			playerTwo.setName("tom");
			Com playerThree    = new Com(3);
			playerThree.setName("noah");
			
			Table.Sit(playerOne);
			Table.Sit(playerTwo);
			Table.Sit(playerThree);
						
			Play(numberOfPlayers); 
		}
		
		else if(numberOfPlayers == 4)
		{
			//WILL WRITE THIS SHIT AFTER FINISHING CUT THROAT
		}
	}
	
	private static void Play(int numberOfPlayers)
	{
		boolean gameOver = false;
				
		while(!gameOver)
		{
			Deck.Deal();
			
			Player currBidder = Table.Next();
			
			for(int count = 0; count < numberOfPlayers; count++)
			{
				if(((Player) currBidder).getIsPlayer())
				{
					((Player) currBidder).printHand();
				}
				
				else
				{
					((Com) currBidder).printHand();
					((Com) currBidder).calcualteBid();
				}
				
				currBidder = Table.Next();
					
			} 
			
			Table.setCurr(Table.getDealerValue());
			biddingPhase();			
			gameOver = true;
			
		}	
	}
	
	private static void biddingPhase() 
	{
		
		boolean biddingPhaseOver = false;
		Player currPlayer =  Table.Next();
		Scanner sc = UtilInstances.getScannerInstance();
		String choice = "";
		int currBid = 20;
		
		System.out.println("Starting Bidding Phase");

		
		while(!biddingPhaseOver)
		{
			System.out.println("Bid is at " + currBid);
			
			if(currPlayer.getIsBidding()) //if the current player is bidding
			{
				if(currPlayer.getIsPlayer())
				{
					System.out.println("Would you like to bid? (y or n)");
					
					choice = sc.next();
					
					if(choice.equals("Y")|| choice.equals("y") )
					{
						System.out.println("Please enter your bid: ");
						
						currBid = sc.nextInt();
						currPlayer.setBid(currBid);
					}
					
					else
					{
						System.out.println("Setting bid to false");
						currPlayer.setIsBidding(false);
					}
				}
				
				else
				{
					if(currPlayer.getBid() == 0)
					{			
						((Com) currPlayer).calcualteBid();
						System.out.println(currPlayer.getName() + " bid is " + currPlayer.getBid());
					}
					
					if(currPlayer.getBid() > currBid)
					{
						System.out.println(currPlayer.getName() + " is bidding");
						currBid++;	
					}
					
					else
					{
						System.out.println(currPlayer.getName() + " is done bidding");
						currPlayer.setIsBidding(false);
					}	
				}
			}
			currPlayer = Table.Next();
			biddingPhaseOver = checkIsBiddingPhaseOver();	
		}
		
		if(currBid == 20) //no one bid so the dealer is stuck with the bid
			biddingWinner = Table.getDealer();
		
		
		System.out.println(biddingWinner.getName());
		
		biddingWinner.addKideyToHand(kiddy);
		biddingWinner.printHand();
		((Com) biddingWinner).setMarriageSuits("");
		((Com) biddingWinner).calcualteBid();
		
		displayMeld();
	}
	
	
	
	public static void displayMeld() 
	{
		if(!biddingWinner.getIsPlayer())
			((Com) biddingWinner).calcTrumpSuit();
		
		Player tempPlayer = Table.Next();
		String marriages;
		String runs;
		int pinochles;
		boolean jacksAround;
		boolean queensAround;
		boolean kingsAround;
		boolean acesAround;
		
		for(int count = 0; count < Table.getLength(); count++)
		{
			if(!tempPlayer.getIsPlayer())
			{
				System.out.print(tempPlayer.getName() + ": ");
				
				marriages   = ((Com) tempPlayer).getMarriageSuits();
				pinochles   = ((Com) tempPlayer).getPinochles();
				jacksAround = ((Com) tempPlayer).isHasJackAround();
				queensAround = ((Com) tempPlayer).isHasQueenAround();
				kingsAround = ((Com) tempPlayer).isHasKingAround();
				acesAround = ((Com) tempPlayer).isHasAceAround();
				runs = ((Com) tempPlayer).getRunSuits();
				
				if(runs != "")
				{
					Card [] tempSuit = tempPlayer.getSuit(runs.charAt(0));
					int index = 0;
				
					do{
					
						if(tempSuit[index].getValue() == "9")
						{
							System.out.print(tempSuit[index].getValue() + tempSuit[index].getSuit() + ", ");
							index++;
						}
					
					}while(tempSuit[index].getValue() == "9");
					
					if(runs.length() == 1)
					{
						System.out.print("Has a run in " + runs + " , ");
					}
				}
				
				if(!marriages.equals(""))
				{
					System.out.print("marriages: ");
					
					for(int marriageCount = 0; marriageCount < marriages.length(); marriageCount++)
					{
						if(marriageCount == 0)
							System.out.print(marriages.charAt(marriageCount));
						
						else
							System.out.print(", " + marriages.charAt(marriageCount));	
					}
				}
					
				if(pinochles > 0)
				{
					System.out.print(", pinochles: " + pinochles);
				}
				
				if(jacksAround)
				{
					System.out.print(", Has Jack Around");
				}
				
				if(queensAround)
				{
					System.out.print(", Has Queen Around");
				}
				
				if(kingsAround)
				{
					System.out.print(", Has King Around");
				}
				
				if(acesAround)
				{
					System.out.print(", Has Ace Around");
				}
					
			}
			
			System.out.println();
			tempPlayer = Table.Next();
		}
				
	}
	

	private static boolean checkIsBiddingPhaseOver() 
	{
		Player[] tempTable = Table.getTableInstance();
		
		int biddingCount = 0;
		int index = 0;
		boolean result = true;
		
		do
		{
			if(tempTable[index].getIsBidding())
				biddingCount++;
			
			index++;
		
		} while(index < tempTable.length && result);
		
		if (biddingCount > 1)
			result = false;
		else
			setBiddingWinner(tempTable);
		
		return result;
	}
	
	private static void setBiddingWinner(Player [] tempTable)
	{
		int count = 0;
		
		do
		{
			if(tempTable[count].getIsBidding())
				biddingWinner = tempTable[count];
			
			count++;
			
		}while(count < tempTable.length);
	}
}

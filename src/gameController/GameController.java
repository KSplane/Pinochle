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
	
	public Player getBiddingWinner() {
		return biddingWinner;
	}

	public void setBiddingWinner(Player biddingWinner) {
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
			System.out.println("Top of while loop");
			System.out.println("Bid is at " + currBid);
			
			if(currPlayer.getIsBidding()) //if the current player is bidding
			{
				if(currPlayer.getIsPlayer())
				{
					System.out.println("The bidder is player");
					System.out.println("Would you like to bid? (y or n)");
					
					choice = sc.next();
					
					if(choice.equals("Y")|| choice.equals("y") )
					{
						System.out.println("the player is bidding");
						System.out.println("Please enter your bid: ");
						
						currBid = sc.nextInt();
						currPlayer.setBid(currBid);
					}
					
					else
					{
						System.out.println("Person done bidding, setting bid to false");
						currPlayer.setIsBidding(false);
					}
				}
				
				else
				{
					System.out.println("The bidder is ai");
					
					if(currPlayer.getBid() == 0)
					{	
						System.out.println("calculating ai bid");
						
						Card [] testSuit = currPlayer.getDiamonds();
						
						for(int testCounter = 0; testCounter < testSuit.length; testCounter++ )
						{
							System.out.println(testSuit[testCounter].getSuit());
						}
						
						((Com) currPlayer).calcualteBid();
						System.out.println(currPlayer.getName() + ": " + currPlayer.getBid());
					}
					
					if(currPlayer.getBid() > currBid)
					{
						System.out.println("AI is bidding");
						currBid++;
						currPlayer.setBid(currBid);		
					}
					
					else
					{
						System.out.println("AI is done bidding");
						currPlayer.setIsBidding(false);
					}
				}
			}
			
			System.out.println("switchign to next bidder");
			currPlayer = Table.Next();
			System.out.println("chekcing if bidding is over");
			biddingPhaseOver = checkIsBiddingPhaseOver();	
		}
		
		System.out.println("Out of while loop");
		
		if(currBid == 20) //no one bid so the dealer is stuck with the bid
			biddingWinner = Table.getDealer();
		
		
		else
		{	
			biddingWinner = currPlayer;
			currPlayer.addKideyToHand(getKiddy());
			
			if(!currPlayer.getIsPlayer())
			{
				currPlayer.printHand();
				((Com) currPlayer).calcualteBid();	
			}
				
		}
		
		System.out.println(biddingWinner.getName());
		//displayMeld();
	}
	
	
	
	private static void displayMeld() 
	{
		Player currPlayer = Table.Next(); ;
		
		for(int count = 0; count < Table.getLength(); count++)
		{
			if(!currPlayer.getIsPlayer())
			{
				((Com) currPlayer).displayMeld();
			}
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
			
			System.out.println("top of do while");
			
			if(tempTable[index].getIsBidding())
			{
				System.out.println(tempTable[index].getName() + " is still bidding");
				biddingCount++;
			}
			
			index++;
		
		}while(index < tempTable.length && result);
		
		System.out.println("There are " + biddingCount);
		
		if(biddingCount > 1)
		{
			System.out.println("Setting result to false");
			result = false;
		}
		return result;
	}
}

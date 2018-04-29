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
		
		while(!biddingPhaseOver)
		{
			if(currPlayer.getIsBidding()) //if the current player is bidding
			{
				if(currPlayer.getIsPlayer())
				{
					choice = sc.next();
					
					if(choice.equals("Y"))
					{
						currBid = sc.nextInt();
						currPlayer.setBid(currBid);
					}
					
					else
					{
						currPlayer.setIsBidding(false);
					}
				}
				
				else
				{
					if(currPlayer.getBid() == 0)
						((Com) currPlayer).calcualteBid();
				
					if(currPlayer.getBid() > currBid)
					{
						currBid++;
						currPlayer.setBid(currBid);		
					}
					
					else
						currPlayer.setIsBidding(false);
				}
			}
			
			currPlayer = Table.Next();
			biddingPhaseOver = checkIsBiddingPhaseOver();	
		}
		
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
		
		//displayMeld();
	}
	
	
	/*
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
	*/

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
		
		}while(index < tempTable.length && result);
		
		if(biddingCount > 1)
			result = false;
		
		return result;
	}
}

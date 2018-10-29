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
	private static int numberOfPlayer;
	
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
	
	public static int getNumberOfPlayer() {
		return numberOfPlayer;
	}

	public static void setNumberOfPlayer(int numberOfPlayer) {
		GameController.numberOfPlayer = numberOfPlayer;
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
		
		numberOfPlayer = UtilInstances.getScannerInstance().nextInt();
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
				if(currBidder instanceof Player)
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
				if(!(currPlayer instanceof Com))
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
		
		if(biddingWinner instanceof Com )
		{
			((Com) biddingWinner).setMarriageSuits("");
			((Com) biddingWinner).calcualteBid();
		}
		
		displayMeld();
	}
	
	private static void displayMeld() 
	{
		String marriages;
		String meld = "";
		String runs;
		int pinochles;
		int nines;
		boolean jacksAround;
		boolean queensAround;
		boolean kingsAround;
		boolean acesAround; 
		
		Scanner sc = UtilInstances.getScannerInstance();
		Player tempPlayer = Table.Next();
		 
		
		if(biddingWinner instanceof Com)
			((Com) biddingWinner).calcTrumpSuit();
		
		else
		{
			System.out.println("Please declare trump: ");
			trumpSuit = sc.next().charAt(0); // no way to read in a char 
		}

		
		for(int count = 0; count < Table.getLength(); count++)
		{
			if(tempPlayer instanceof Com) //if were playing a computer hand
			{
				meld = meld.concat(tempPlayer.getName() + ": ");
				
				marriages   = ((Com) tempPlayer).getMarriageSuits();
				pinochles   = ((Com) tempPlayer).getPinochles();
				jacksAround = ((Com) tempPlayer).isHasJackAround();
				queensAround = ((Com) tempPlayer).isHasQueenAround();
				kingsAround = ((Com) tempPlayer).isHasKingAround();
				acesAround = ((Com) tempPlayer).isHasAceAround();
				runs = ((Com) tempPlayer).getRunSuits();
				nines = ((Com) tempPlayer).getNineCount();
				
				if(nines > 0)
				{
					for(int nineCount = 0; nineCount < nines; nineCount++)
					{
						meld = meld.concat("9" + trumpSuit + ",");
					}
				}
				if(!marriages.equals(""))
				{
					meld = meld.concat("marriages: ");
					
					for(int marriageCount = 0; marriageCount < marriages.length(); marriageCount++)
					{
						//if(marriageCount == 0)
							meld = meld.concat(Character.toString(marriages.charAt(marriageCount)));
						
						//else
							//meld = meld.concat(", " + marriages.charAt(marriageCount));	
					}
					
					meld = meld.concat(", ");
				}
				
				if(pinochles > 0)
				{
					meld = meld.concat(", pinochles: " + pinochles);
				}
				
				if(jacksAround)
				{
					meld = meld.concat(", Has Jack Around");
				}
				
				if(queensAround)
				{
					meld = meld.concat(", Has Queen Around");
				}
				
				if(kingsAround)
				{
					meld = meld.concat(", Has King Around");
				}
				
				if(acesAround)
				{
					meld = meld.concat(", Has Ace Around");
				}
				
				if(runs != "") //ADD CHECK NINES HERE FUKBOI
				{
					if(runs.length() == 1)
					{
						meld = meld.concat("Has a run in " + runs + " , ");
					}
					else if(runs.length() == 2)
					{
						if(tempPlayer.getSuit(runs.charAt(0)).length > tempPlayer.getSuit(runs.charAt(1)).length)
							meld = meld.concat("Has a run in " + runs.charAt(0) + " , ");
						else
							meld = meld.concat("Has a run in " + runs.charAt(1) + " , ");
					}
					
					else
						meld = meld.concat("How the fuck did you get 15 cards in a row that wre meaningful fuck you");
				}
			}
			
			else //we are displaying the players meld
			{
				Card [] tempMeld = new Card [2];
				Card [] tempHand = tempPlayer.getHand();
				Card [] placeholderMeld;
				int arrayIndex = 0;
				String response;
				int meldIndex;
				boolean done = false;
				
				System.out.println("Do you have any meld to play?");
				response = sc.next();
				
				if(response.equals("Y") || response.equals("y")) // Proceed to loop if they have meld to play
				{
					while(!done)
					{
						System.out.print("Please enter index of the next card you wish to play for meld:  ");
						meldIndex = sc.nextInt();
						
						tempMeld[arrayIndex] = tempHand[meldIndex];
						
						System.out.print("Play Meld: ");
						response = sc.next(); 
						
						if(response.equals("Y") || response.equals("y")) // cat meld onto meld string and refresh tempMeld
						{
							tempPlayer.sort(tempMeld);
							meld = meld.concat(tempPlayer.checkMeld(tempMeld));
							
							arrayIndex = 0;
							tempMeld = new Card[2];
							
							System.out.println("Do you have any meld to play?");
							response = sc.next();
							
							if(response.equals("N") || response.equals("n"))
								done = true;
						}
						
						else //need to continue adding cards to meld
						{
							if(arrayIndex == 1)
							{
								placeholderMeld = tempMeld;
								tempMeld = new Card[4];
								tempPlayer.rePopulate(tempMeld, placeholderMeld);
							}
							
							else if(arrayIndex == 3)
							{
								placeholderMeld = tempMeld;
								tempMeld = new Card[5];
								tempPlayer.rePopulate(tempMeld, placeholderMeld);
							}
							
							arrayIndex++;
						}
					}
				}
			}
			
			memorize(meld, tempPlayer.getName());
			System.out.println(meld);
			meld = "";
			tempPlayer = Table.Next();
			}
		}
	
	private static void memorize(String meld, String name)
	{
		Player [] tempTable= Table.getTableInstance();
		
		for(int testCount = 0; testCount < Table.getLength(); testCount++)
		{
			if(!tempTable[testCount].getName().equals(name) && tempTable[testCount] instanceof Com)
			{
				((Com)tempTable[testCount]).Memorize(meld);
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

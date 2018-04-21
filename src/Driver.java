import deck.Card;
import deck.Deck;
import gameController.GameController;
import player.Com;
import player.Player;
import table.Table;

/*
FIX BUG WHERE KIDDY NOT ADDING TO HAND
FIX WHATEVER DUMBASS SHIT YOURE DOING WITH TEMP TABLE
TRY RANDOMIZING THE INDEX IN DEAL TO GENERATE BETTER HANDS
*/

public class Driver {
	
	public static void main(String[] args)
	{
		
		//GameController.Start();
		
		Player [] tempTable = Table.getTableInstance();
		Deck.getDeckInstance("Pinochle");
		Card   [] kiddy = GameController.getKiddy();
		
		Player playerOne   = new Player(3);
		playerOne.setName("FuckYouJava");
		Table.Sit(playerOne);
		Deck.Deal(tempTable);
		playerOne.printHand();

		Card [] tempKiddy = GameController.getKiddy();
		
		System.out.println(tempKiddy[0].getValue() + tempKiddy[0].getSuit());
		System.out.println(tempKiddy[1].getValue() + tempKiddy[1].getSuit());
		System.out.println(tempKiddy[2].getValue() + tempKiddy[2].getSuit());
		
		Card ifThisShitWorksImFuckingDone = tempKiddy[0];
		//System.out.println(ifThisShitWorksImFuckingDone.getSuit());
		playerOne.addToHand(ifThisShitWorksImFuckingDone);
		playerOne.printHand();
		/*
		playerOne.addToHand(kiddy[1]);
		playerOne.addToHand(kiddy[2]);
		playerOne.printHand();
		*/
	}
	
}

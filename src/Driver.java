import gameController.GameController;

import deck.Card;
import player.Com;
import table.Table;


/*
 NEXT PROJECT STEP:
LOOK OVER HANDS AND KIDDY GENERATION
Rewrite function for checking which suit is the longest when having runs in multiple suits
*/

public class Driver {
	
	public static void main(String[] args)
	{
		
		//GameController.Start();
		
		
		Com testComputer = new Com(3);
		
		testComputer.addToHand(new Card('S', "Q", 'B'));
		testComputer.addToHand(new Card('S', "K", 'B'));
		testComputer.addToHand(new Card('C', "J", 'B'));
		testComputer.addToHand(new Card('C', "10", 'B'));
		testComputer.addToHand(new Card('D', "9", 'R'));
		testComputer.addToHand(new Card('D', "J", 'B'));
		testComputer.addToHand(new Card('D', "Q", 'B'));
		testComputer.addToHand(new Card('D', "K", 'B'));
		testComputer.addToHand(new Card('D', "10", 'B'));
		testComputer.addToHand(new Card('D', "A", 'R'));
		testComputer.addToHand(new Card('H', "J", 'R'));
		testComputer.addToHand(new Card('H', "9", 'R'));
		testComputer.addToHand(new Card('H', "10", 'R'));
		testComputer.addToHand(new Card('H', "J", 'R'));
		testComputer.addToHand(new Card('H', "Q", 'R'));
		
		testComputer.printHand();
		testComputer.calcualteBid();
		
		System.out.println(testComputer.getBid());
		Table.Sit(testComputer);
		GameController.setBiddingWinner(testComputer);
		GameController.displayMeld();
		System.out.println(GameController.getTrumpSuit());
		
	}
	
}

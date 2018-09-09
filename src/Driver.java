import deck.Card;
import gameController.GameController;
import player.Com;
import table.Table;


/*
 NEXT PROJECT STEP:
CHANGE CODE TO ADD 4 TO MARRIAGES IN TRUMP
Rewrite function for checking which suit is the longest when having runs in multiple suits
ADD LOGIC FOR COUNTING 9S TO CHECK RUN AND CHECK LONG SUIT SO DISPLAYING MELD ISNT A HASSLE

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
		testComputer.addToHand(new Card('C', "A", 'B'));
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

import deck.Card;
import gameController.GameController;
import player.Com;


/*
NEXT PROJECT STEP:
Trump suit is not getting returned to calcbid fix that
Rewrite function for checking which suit is the longest when having runs in multiple suits
check to make sure new run function works the same as before
ADD LOGIC FOR COUNTING 9S TO CHECK RUN AND CHECK LONG SUIT SO DISPLAYING MELD ISNT A HASSLE
WORK ON DISPAYING MELD FOR EACH PLAYER

THINGS TO GO BACK AND LOOK AT:
TRY RANDOMIZING THE INDEX IN DEAL TO GENERATE BETTER HANDS
*/

public class Driver {
	
	public static void main(String[] args)
	{
		
		//GameController.Start();
		
		Com testComputer = new Com(3);
		//Card tempCard = ;
		
		testComputer.addToHand(new Card('S', "J", 'B'));
		testComputer.addToHand(new Card('S', "Q", 'B'));
		testComputer.addToHand(new Card('S', "K", 'B'));
		testComputer.addToHand(new Card('S', "10", 'B'));
		testComputer.addToHand(new Card('S', "A", 'B'));
		testComputer.addToHand(new Card('S', "J", 'B'));
		testComputer.addToHand(new Card('C', "Q", 'B'));
		testComputer.addToHand(new Card('C', "K", 'B'));
		testComputer.addToHand(new Card('C', "A", 'B'));
		testComputer.addToHand(new Card('D', "J", 'R'));
		testComputer.addToHand(new Card('H', "J", 'R'));
		testComputer.addToHand(new Card('H', "9", 'R'));
		testComputer.addToHand(new Card('H', "10", 'R'));
		testComputer.addToHand(new Card('H', "J", 'R'));
		testComputer.addToHand(new Card('H', "Q", 'R'));
		
		testComputer.printHand();
		testComputer.calcualteBid();
		System.out.println(testComputer.getBid());
			
	}
	
}

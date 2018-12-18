package table;

import player.Player;

public class Table 
{
	//We need to declare this as a object array so we can have players and com in the same array
	//Everything will cast when getting an object from this array
	private static Player [] table;
	
	private static int dealer;
	private static int curr;
	
	private static Player biddingWinner;
	
	private Table()
	{
		
	}
	
	public static Player[] getTable() {
		return table;
	}

	public static void setTable(Player[] table) {
		Table.table = table;
	}
	
	public static int getDealerValue() {
		return dealer;
	}

	public static void setDealerValue(int curr) {
		Table.curr = curr;
	}


	public static Player getBiddingWinner() {
		return biddingWinner;
	}

	public static void setBiddingWinner(Player biddingWinner) {
		Table.biddingWinner = biddingWinner;
	}
	
	public static int getCurr() {
		return curr;
	}

	public static void setCurr(int curr) {
		Table.curr = curr;
	}

	public static Player [] getTableInstance()
	{
		if(table == null)
		{
			dealer = 0;
			curr = dealer;
			table = new Player [1];
		}
		return table;
	}

	public static void Sit(Player newPlayer) 	//PUSH INTO THE QUEUE
	{
		Player [] tempTable; 
		Player [] currTable = getTableInstance();
		
		if(currTable[0] == null) //if table is empty
		
			currTable[0] = newPlayer;
		
		
		else
		{
			tempTable = currTable; //save everyone we have in the current table
			currTable = new Player[tempTable.length + 1]; //declare a new table of one size extra
			currTable = repopulate(currTable,tempTable); // put everyone from the old table into the new table
			currTable[currTable.length - 1] = newPlayer; // add the newest player to the table
		}
		
		setTable(currTable);
	}

	private static Player [] repopulate(Player [] currTable, Player [] tempTable) 
	{
		for(int count = 0; count < tempTable.length; count++)
		{
			currTable[count] = tempTable[count];
		}
		
		return currTable;
	}
	
	public static void updateDealer()
	{
		if(dealer <(table.length -1))
			dealer++;
		
		else
			dealer = 0;
		
	}
	
	public static Player getDealer()
	{
		return  (Player) table[dealer];
	}
	
	public void setDealer(int dealer)
	{
		Table.dealer = dealer;
	}
	
	public static Player Next()
	{
		if(curr < (table.length - 1) ) //-1 because arrays start counting at 0
			curr++;
		else
			curr = 0; // we have gone trough the entire queue and need to loop back to the start
		
		return  (Player) table[curr];
	}

	public static int getLength() 
	{
		return table.length;
	}
	
}

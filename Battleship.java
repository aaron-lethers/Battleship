import java.util.Random;
import java.util.Scanner;

public class Battleship {

	public static int row = 10;
	public static int col = 10;
	public static char[][] opponent = new char[row][col];
	public static char[][] player = new char[row][col];
	public static char[][] shots = new char[row][col];
	public static char[][] opponentShots = new char[row][col];
	public static char water = '~';
	public static int boatLen = 5;
	public static int numBoats = 4;
	public static boolean ckWin = false;
	public static boolean sunkpA = false;
	public static boolean sunkpB = false;
	public static boolean sunkpC = false;
	public static boolean sunkpD = false;
	public static boolean sunkoA = false;
	public static boolean sunkoB = false;
	public static boolean sunkoC = false;
	public static boolean sunkoD = false;

	
	
	public static void main(String[] args) {
		boolean coordError = false;
		
		System.out.println("Welcome to Battleship\n\n");
		
		resetMap();
		
		placeBoats();
		
		placeOpponent();
		
		displayMap();
			
		do {
			try {
				new Battleship();
				Battleship.battle();
				coordError = false;
			} catch (NumberFormatException e) {
				System.out.println("Invalid coordinate: Must be a capital letter followed by a number");
				coordError = true;
			}
		} while(coordError == true);

	}

	public static void resetMap() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				player[i][j] = water;
			}
		}
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				opponent[i][j] = water;
			}
		}	
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				shots[i][j] = water;
			}
		}	
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				opponentShots[i][j] = water;
			}
		}	
	}
	
	public static void displayMap() {
		System.out.println(" ");
		System.out.println("Opponent's Ships");
		
		//col numbers
		System.out.print("  ");		
		for (int i = 0; i < col; i++) {
			System.out.print(" " + (i+1));
		}
		
		//Separator
		System.out.println();
		System.out.print("  ");
		for (int i = 0; i < col; i++) {
			System.out.print("--");
		}
		
		System.out.println();
		
		//row letters and grid
		for (int i = 0; i < row; i++) {
			System.out.print((char)('A' + i) + "|");
			for (int j = 0; j < shots.length; j++) {
				System.out.print(" " + shots[i][j]);
			}
			System.out.println();
		}
		
//###########################################################
		
		System.out.println(" ");
		System.out.println("Your Ships");
		
		//col numbers
		System.out.print("  ");		
		for (int i = 0; i < col; i++) {
			System.out.print(" " + (i+1));
		}
		
		//Separator
		System.out.println();
		System.out.print("  ");
		for (int i = 0; i < col; i++) {
			System.out.print("--");
		}
		
		System.out.println();
		
		//row letters and grid
		for (int i = 0; i < row; i++) {
			System.out.print((char)('A' + i) + "|");
			for (int j = 0; j < player.length; j++) {
				System.out.print(" " + player[i][j]);
			}
			System.out.println();
		}
		//System.out.println("\n###################################");
		
	}
	
	public static void placeBoats() {
		Random rand = new Random();
		char whichBoat = 'A';
		int boats = numBoats;
		int length = boatLen;
		
		int i = 0;
		while (i < boats) {
			int x = rand.nextInt(row+1);
			int y = rand.nextInt(col+1);
			int dir = rand.nextInt(2);
			//System.out.println("x:" + x + " y: " + y + " dir: " + dir);
			boolean valid = false;
			
			if ((x + length < row) && (y + length < col)) {
				valid = isValid(length, x, y, dir, whichBoat);
			}
			
			if ((valid == true)) {
				if (dir == 0) {
					for (int j = 0; j < length; j++) {
						player[y+j][x] = whichBoat;
					}
				}
				else {
					for (int j = 0; j < length; j++) {
						player[y][x+j] = whichBoat;
					}
				}
				i++;
				whichBoat +=1;
				length--;
			}
		}
	}
	
	public static boolean isValid(int boatLen, int x, int y, int dir, char whichBoat) {
		boolean valid = true;
		
		if (dir == 0) {
			for (int i = 0; i <= boatLen; i++) {
				if (player[y+i][x] != water || opponent[y+i][x] != water) {
					valid = false;
				}
			}
		}
		else {
			for (int i = 0; i <= boatLen; i++) {
				if (player[y][x+i] != water || opponent[y][x+i] != water) {
					valid = false;
				}
			}
		}
		return valid;
	}
	
	public static void placeOpponent() {
		Random rand = new Random();
		char whichBoat = 'A';
		int boats = numBoats;
		int length = boatLen;
		
		int i = 0;
		while (i < boats) {
			int x = rand.nextInt(row+1);
			int y = rand.nextInt(col+1);
			int dir = rand.nextInt(2);
			//System.out.println("x:" + x + " y: " + y + " dir: " + dir);
			boolean valid = false;
			
			if ((x + length < row) && (y + length < col)) {
				valid = isValid(length, x, y, dir, whichBoat);
			}
			
			if ((valid == true)) {
				if (dir == 0) {
					for (int j = 0; j < length; j++) {
						opponent[y+j][x] = whichBoat;
					}
				}
				else {
					for (int j = 0; j < length; j++) {
						opponent[y][x+j] = whichBoat;
					}
				}
				i++;
				whichBoat +=1;
				length--;
			}
		}
	}
	
	public static void battle() throws NumberFormatException{
		do {
			System.out.println("###### YOUR TURN ######");
			playerTurn();
			checkplayerSunk();
			System.out.println("###### COMPUTER TURN #####");
			computerTurn();
			checkopponentSunk();
		} while(ckWin == false);		
		
		System.out.println("you win");
	}
	
	public static void playerTurn() {
		Scanner in = new Scanner(System.in);

		String str = "";
		boolean check = false;
		int colNum = 0;
		int rowNum = 0;
		
		do {
			System.out.print("Enter coordinate (capital letter and a number) to fire on: ");
			str = in.nextLine();
			
			String t = "";
			
			//parse input into row and stringCol
			String stringRow = str.substring(0, 1);
			String stringCol = str.substring(1);
			
			//turn stringCol into a number
			colNum = Integer.parseInt(stringCol);
			
			//turn stringRow into a number
			for (int i = 0; i < stringRow.length(); ++i) {
				char ch = stringRow.charAt(i);
				if (!t.isEmpty()) {
					t += " ";
				}
				int n = (int)ch - (int)'A' + 1;
				t += String.valueOf(n);
			}
			rowNum = Integer.parseInt(t);
			
			check = checkCoords(colNum, rowNum);

		} while(check == false);
		rowNum--;
		colNum--;
				
		//if valid check if coordinate is a hit or a miss
		if (opponent[rowNum][colNum] != water) {
			shots[rowNum][colNum] = 'X';
			opponent[rowNum][colNum] = 'X';
			displayMap();
			System.out.println("->HIT!");
		}
		else {
			shots[rowNum][colNum] = '0';
			displayMap();
			System.out.println("->Miss :( ");
			
		}
	}
	
	public static void computerTurn() {
		Random rand = new Random();
		boolean ckCoords = false;
		int x = 0;
		int y = 0;
		char boat = ' ';
		
		do {
			x = rand.nextInt(row);
			y = rand.nextInt(col);
			
			if (x <= 10 && x >= 1 || y <= 10 && y >= 1) {
				ckCoords = true;
			}
			if (player[x][y] != water) {
				ckCoords = false;
			}
			else {
				ckCoords = true;
			}
		} while(ckCoords == false);
		
		//if valid check if coordinate is a hit or a miss
		if (player[x][y] != water) {
			boat = player[x][y];
			player[x][y] = 'X';
			opponentShots[x][y] = 'X';
			System.out.println("->Your " + boat + " boat was hit!");
		}
		else {
			opponentShots[x][y] = '0';
			System.out.println("->They missed.");	
		}
	}
	
	public static boolean checkCoords(int colNum, int rowNum) {
		boolean valid = true;

		//check if col is valid
		if (colNum > 10 || colNum < 1) {
			valid = false;
			System.out.println("Invalid coordinate: Column must be between 1 and 10");
		}
		
		//check if row is valid
		if (rowNum > 10 || rowNum < 1) {
			valid = false;
			System.out.println("Invalid coordinate: Row must be a capial letter between A and J");
			return valid;
		}
		
		//check if coordinates have already been used
		colNum--;
		rowNum--;
		if (valid == true && shots[rowNum][colNum] != water) {
			valid = false;
			System.out.println("You already shot here, pick different coordinates.");
		}
		
		return valid;
	}
	
	public static void checkplayerSunk() {
		int ckA = 0;
		int ckB = 0;
		int ckC = 0;
		int ckD = 0;
				
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (opponent[i][j] == 'A') {
					ckA++;
				}
				else if (opponent[i][j] == 'B') {
					ckB++;
				}
				else if (opponent[i][j] == 'C') {
					ckC++;
				}
				else if (opponent[i][j] == 'D') {
					ckD++;
				}
			}
		}
				
		if (sunkpA == true && sunkpB == true && sunkpC == true && sunkpD == true) {
			ckWin = true;
		}
		if (ckA == 0 && sunkpA == false) {
			System.out.println("->You sunk their 'A' boat!!");
			sunkpA = true;
		}
		else if (ckB == 0 && sunkpB == false) {
			System.out.println("->You sunk their 'B' boat!!");
			sunkpB = true;
		}
		else if (ckC == 0 && sunkpC == false) {
			System.out.println("->You sunk their 'C' boat!!");
			sunkpC = true;
		}
		else if (ckD == 0 && sunkpD == false) {
			System.out.println("->You sunk their 'D' boat!!");
			sunkpD = true; 
		}
	}
	
	public static void checkopponentSunk() {
		int ckA = 0;
		int ckB = 0;
		int ckC = 0;
		int ckD = 0;
				
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (player[i][j] == 'A') {
					ckA++;
				}
				else if (player[i][j] == 'B') {
					ckB++;
				}
				else if (player[i][j] == 'C') {
					ckC++;
				}
				else if (player[i][j] == 'D') {
					ckD++;
				}
			}
		}
				
		if (sunkoA == true && sunkoB == true && sunkoC == true && sunkoD == true) {
			ckWin = true;
		}
		if (ckA == 0 && sunkoA == false) {
			System.out.println("->They sunk their 'A' boat!!");
			sunkoA = true;
		}
		else if (ckB == 0 && sunkoB == false) {
			System.out.println("->They sunk their 'B' boat!!");
			sunkoB = true;
		}
		else if (ckC == 0 && sunkoC == false) {
			System.out.println("->They sunk their 'C' boat!!");
			sunkoC = true;
		}
		else if (ckD == 0 && sunkoD == false) {
			System.out.println("->They sunk their 'D' boat!!");
			sunkoD = true; 
		}
	}


}

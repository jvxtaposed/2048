//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/* 
 * Name: Jennifer Mei Yan Fung
 * Date: January 28th 2016
 * File:  Board.java
 * 
 *this program manages the general functions of creating a board, saving a board,
 *and general game play.
 */
import java.util.*;
import java.io.*;


public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting
    public final Direction j = Direction.DOWN;
 	public final Direction h = Direction.LEFT;
 	public final Direction l = Direction.RIGHT;
 	public final Direction k = Direction.UP;
    
    /* Name:  GameManager
     * Purpose:  generate new game
     * Parameters:  takes an integer, string (filename), and random
     * Return: nothing; it is a constructor
     */
    // GameManager Constructor
    // Generate new game
    public GameManager(int boardSize, String outputBoard, Random random) {
    	//prints out a line to inform the player
        System.out.println("Generating a New Board");
        //creates a new board
        this.board = new Board(boardSize, random);
        //forces the intake value for boardSize to be even
        boardSize = boardSize%2;
        //prints the board onto console
        System.out.println(board.toString());
        
    }

    /* Name:  GameManager
     * Purpose:  loads a board
     * Parameters:  takes in a string value, , and random
     * Return: nothing; it is a constructor
     */
    // GameManager Constructor
    // Load a saved game
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
    	//prints out the loaded board
        System.out.println("Loading Board from " + inputBoard);
        //creates the loaded board
        this.board = new Board(inputBoard, random);
        //prints the board onto console
        System.out.println(board.toString());
    }

    /* Name:  play
     * Purpose: control game play and initializes control and allow user input.
     * 			Also exits game when game is over.
     * Parameters:  n/a
     * Return: void, no return type
     */

    public void play() throws IOException {
    	//prints the control for play
    	this.printControls(); 
    	//prints the board onto console
    	System.out.println(board.toString());
    	//creates a scanner to take a new user input
    	Scanner userInput = new Scanner(System.in);
    	//will keep going until no more scanner input
    	while(userInput.hasNext()) { 	
    		//checks if the game is over
    		if (board.isGameOver()) {
    			board.saveBoard(outputFileName);
    			System.out.println("Sorry! Game Over :(");
    			return;
    		}
    		//performs downward move if can move
    		else if (userInput.next().equalsIgnoreCase("j")) {
    			board.move(j);
    			System.out.println(board.toString());
    		}
    		//performs left move if can move
    		else if (userInput.next().equalsIgnoreCase("h")) {
    			board.move(h);
    			System.out.println(board.toString());
    		}
    		//performs right move if can move
    		else if (userInput.next().equalsIgnoreCase("l")) {
    			board.move(l);
    			System.out.println(board.toString());
    		}
    		//performs upward move if can move
    		else if (userInput.next().equalsIgnoreCase("k")) {
    			board.move(k);
    			System.out.println(board.toString());
    		}
    		//quits game
    		else if (userInput.next().equalsIgnoreCase("q")) {
    			board.saveBoard(outputFileName);
    			System.out.println("Bye!");
    			return;   		
    		}
    		//invalid scanner input display
    		else {
    			System.out.println("invalid move, please try again!");
    			this.printControls(); 
    			board.toString();

    			//Scanner newInput = new Scanner(System.in);
    		}
    		//prevent data leak
    		userInput.close();
    		
    	}
    }
    		

    	
 

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println(); 
    }
}


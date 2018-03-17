/* 
 * Name: Jennifer Mei Yan Fung
 * Date: January 28th 2016
 * File:  Board.java
 * 
 *this program designs the board to play the game 2048, has a function of saving
 *and uploading a game from a file. At the beginning of a game,
 *it will add 2 random tiles to the board using method addRandomTile().
 *The method, 'rotate', rotates the board by 90 degrees clockwise
 * or 90 degrees counter-clockwise.
 *The method canMove() tests whether or not this is a viable move on the board
 *The method move() moves the tiles accordingly
 *The method isGameOver() tests whether or not the game is over
 *
 */
/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;
    public final Direction j = Direction.DOWN;
 	public final Direction h = Direction.LEFT;
 	public final Direction l = Direction.RIGHT;
 	public final Direction k = Direction.UP;

    private final Random random;
    private int[][] grid;
    private int score;
    
    /* Name:  Board
     * Purpose:  creates a new 2048 board using a 2D array with a customizable
     * 			 gridSize
     * Parameters:  takes in an integer 
     * Return: it's a constructor, doesn't return anything
     */
    public Board(int boardSize, Random random) {
    	//initializes random
    	 this.random = random;
    	 //sets instance variable GRID_SIZE to input value (boardSize)
         GRID_SIZE = boardSize;
         //sets instance variable score to 0
         score = 0;
         //sets 2D array (grid) that was initialized in the class to size GRID_SIZE
         this.grid = new int[GRID_SIZE][GRID_SIZE];
         //int i to iterate through as long as there are less 2 tiles in the grid
         int i = 0;
            	 while(i < NUM_START_TILES) {
            		 //accesses method addRandomTile
            		 this.addRandomTile();
            		 //increases count on i
            		 i++;
            	 }
    }

    /* Name:  Board
     * Purpose:  creates a new 2048 board boased off of an input file
     * Parameters:  takes in a string and random
     * Return: doesn't return anything, it's a constructor
     */
    public Board(String inputBoard, Random random) throws IOException {
    	//creates a new scanner to read input from file
    	 Scanner inputNextInt = new Scanner(new File(inputBoard));
    	 //initializes random
         this.random = random; 
         //grabs the first integer from file and sets it as grid_size
         GRID_SIZE = inputNextInt.nextInt(); 
         //grabs the next integer from file and sets it as score
         score = inputNextInt.nextInt();
         //sets 2D array 'grid' to size GRID_SIZE, GRID_SIZE
         grid = new int[GRID_SIZE][GRID_SIZE]; 
         //iterates through the rows of grid
         for(int row = 0; row<GRID_SIZE; row++) {
        	 //iterates through the columns of each row
             for(int column = 0; column<GRID_SIZE; column++) {
            	 //grabs the next integer from file and sets it in each specific index
            	 grid[row][column] = inputNextInt.nextInt();
                 }
             System.out.println();
         }
         //closes scanner so there is no data leak
         inputNextInt.close();
    }

    /* Name:  saveBoard
     * Purpose:  writes the current board into an output file
     * Parameters:  a string
     * Return: return type void; doesn't return anything
     */
    public void saveBoard(String outputBoard) throws IOException {
    	//creates a new printWriter to write the array into a file
    	PrintWriter writeBoard = new PrintWriter( new File (outputBoard));
    	//writes the value of GRID_SIZE into the file and starts new line
    	writeBoard.println(GRID_SIZE);
    	//writes the value of score into the file and starts new line
    	writeBoard.println(score);
    	//iterates through the rows of the array
    	for(int row = 0; row<GRID_SIZE; row++) {
    		//iterates through the columns of each row
    		for(int column = 0; column<GRID_SIZE; column++) { 
    			//writes each specific index into the file and adds a space at the end
    			writeBoard.print(this.grid[row][column] + " ");   
            }
    		//writes a new line at the end of each column and at the start of each row
    		writeBoard.println();
    	}
    	//closes the printWriter
    	writeBoard.close();
    }

    /* Name:  addRandomTile()
     * Purpose:  adds a random tile of (2 or 4) to a random empty space on board
     * Parameters:  n/a
     * Return: void; no return type
     */
    public void addRandomTile() {
    	//counts the number of empty spaces in board
    	int count=0;
    	//does the same time, but required for tester method
    	int location = 0;
    	List<Integer> list = new ArrayList <> ();
    	//iterates through rows of board
    	for(int row = 0; row<GRID_SIZE; row++) {
    		//iterates through columns of board
    		for(int column = 0; column<GRID_SIZE; column++){  
    			//if this specific index is 0, will increment count
    			if(this.grid[row][column] == 0) {
    				count++;
    				list.add(row);
    				list.add(column);
    				
    			}
    			//if there are no empty spaces, exits method
    			if(count == 0) {
    				return;
    			}
    		}
    	}
    	//a primitive to randomly place the tile in the index of one of the empty space
    	location = random.nextInt(count);
    	//spits out an integer ranging from 0-99
    	int value = random.nextInt(100);
    	
    	int index = 2*location;
    	
    	if (value<TWO_PROBABILITY) {
    		grid[list.get(index)][list.get(index+1)] = 2;
    	}
    	else{
    		grid[list.get(index)][list.get(index+1)] = 4;
    	}
    	
    }
    	
    				
    /* Name:  rotate
     * Purpose:  Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
     * Parameters:  takes in a boolean (if true rotate clockwise; if false counter)
     * Return: no return type; void
     */		
    public void rotate(boolean rotateClockwise) {
    	//boolean if its clockwise, enter in this code
    	if(rotateClockwise ==true) {
    		//creates a temporary array the same size as board
    		int[][] rotate = new int[GRID_SIZE][GRID_SIZE];
    		//iterates through the rows of board
    		for(int row = 0; row<GRID_SIZE; row++) {
    			//iterates through the columns of board
        		for(int column = 0; column<GRID_SIZE; column++){  
        			//sets the temporary array as board with the rotations completed
        			rotate[row][column] = this.grid[this.grid.length-1-column][row];			
        		}
    		}
    		//sets board as the rotated array
    		this.grid = rotate; 
    	}
    	else {
    		//creates a temporary array the same size as board
    		int[][] backwards = new int[GRID_SIZE][GRID_SIZE];
    		//iterates through the rows of board
    		for(int row = 0; row<GRID_SIZE; row++) {
    			//iterates through the columns of board
        		for(int column = 0; column<GRID_SIZE; column++){  
        			//sets the temporary array as board with the rotations completed
        			backwards[row][column] = this.grid[column][this.grid.length-1-row];
        					
        		}
    		}
    		//sets board as the rotated array
    		this.grid = backwards; 
    	}
    }

    //Returns true if the file to be read is in the correct format, else return
    //false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
            //write your code to check for all conditions and return true if it satisfies
            //all conditions else return false
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* Name:  move()
     * Purpose:  method to execute moves in game
     * Parameters:  takes in direction
     * Return: returns a boolean
     */
    public boolean move(Direction direction)
    {
      if (direction == Direction.LEFT) {
        moveLeftBlank();
        moveLeftAdd();
      }

     else if (direction == Direction.UP) {
        moveUpBlank();
        moveUpAdd();
      } 

      else if (direction == Direction.DOWN) {
        moveDownBlank();
        moveDownAdd();
      } 

      else if (direction == Direction.RIGHT) {
        moveRightBlank();
        moveRightAdd();
      }
      return true;
    }
    
    /* Name:  moveLeft()
     * Purpose:  a helper method to execute in move() to move tiles left
     * Parameters:  no parameters accepted
     * Return: boolean
     */
    private void moveLeftBlank() {
    	if (canMoveLeft()) {
        //loop through rows i and columns j
    		for (int i = 0; i < GRID_SIZE; i++) {
    			for (int j = 0; j < GRID_SIZE; j++) {
    			  //Check if index is 0
            if (grid[i][j] == 0) {
    					int x = j + 1;
              //While x is between 0 and grid size, and grid at i/x is 0
              //increment x to look for the next number.
    					while (x < GRID_SIZE && grid [i][x] == 0) {
    						x++;
    					}
    					//if grid at i/x is not 0, and is still within bounds,
              //swap it with the leftmost 0. Set grid at i/x back to 0.
    					if (x < GRID_SIZE) {
    						grid[i][j] = grid[i][x];
    						grid[i][x] = 0;
    					}
    				}			
    			}
    		}
    	}
    }
    
    private void moveLeftAdd() {
        //initialize variable sum to hold sum
      	int sum = 0;
        //loop through rows i and columns  j
        //j must end at size -1 to preserve bounds
      	for (int i = 0; i < GRID_SIZE; i++) {
      		for (int j = 0; j < GRID_SIZE-1; j++) {
      			int x = j + 1;
              //if number after is not equal 0 or equal to current number, add
      				if (grid[i][x] != 0 && grid[i][j] == grid[i][x]) {
      					sum = grid [i][j] + grid[i][x];
      					grid[i][j] = sum;
               // while next (current + 2) remains within bounds, slide over 
               // and increment x. This ensures staying in bounds, and the case 
               // of 4 0 2 2 (makes sure slides all the way over)
      			    	while (x+1 < GRID_SIZE) {
      					  	grid[i][x] = grid[i][x+1];
      				    	x++;
      				  	} 
                //Increment score
      					score += sum;
      					grid[i][x] = 0;
      				}
      		}
      			
      	}
      }
    
    private void moveRightBlank() {
    	if (canMoveRight()) {
    		for (int i = 0; i < GRID_SIZE; i++) {
    			//Index must start from the end becausse we check inwards out
          for (int j = GRID_SIZE-1; j >= 0; j--) {
    				if (grid[i][j] == 0) {
    					int x = j - 1;
    					//while x stays in bounds, and grid at i/x is 0, skip
    					while (x >= 0 && grid [i][x] == 0) {
    						x--;
    					}
    					//when it is nonzero, swap with original index
    					if (x >= 0) {
    						grid[i][j] = grid[i][x];
    						grid[i][x] = 0;
    					}
    				}			
    			}
    		}
    	}
   }

   
    /* Name:  moveRight()
     * Purpose:  a helper method to execute in move() to move tiles right
     * Parameters:  no parameters accepted
     * Return: boolean
     */
    private void moveRightAdd() {
    	int sum = 0;
    	for (int i = 0; i < GRID_SIZE; i++) {
        //Must decrement from size-1 because we start from the right corner
    		for (int j = GRID_SIZE-1; j > 0; j--) {
    			int x = j - 1;
            //if index before is not 0 and equal to previous, add and set to
            //original (the rightmost)
    				if (grid[i][x] != 0 && grid[i][j] == grid[i][x]) {
    					sum = grid [i][j] + grid[i][x];
    					grid[i][j] = sum;
              //while x stays within bounds, slide tile before to the right
    					while (x-1 >= 0) {
    						grid[i][x] = grid[i][x-1];
    						x--;
   						}
              //increment sum
    					score += sum;
    					grid[i][x] = 0;
    				}
    		}
    			
    	}
    }

   
    /* Name:  moveUp()
     * Purpose:  a helper method to execute in move() to move tiles up
     * Parameters:  no parameters accepted
     * Return: boolean
     */
    private void moveUpBlank() {
    	if (canMoveUp()) {
        //loop through grid finding values of zero
    		for (int i = 0; i < GRID_SIZE; i++) {
    			for (int j = 0; j < GRID_SIZE; j++) {
    				if (grid[i][j] == 0) {
              //check if next value below is 0, if so skip  
    					int x = i + 1;
    					while (x < GRID_SIZE && grid [x][j] == 0) {
    						x++;
    					}
    					if (x < GRID_SIZE) {
    						grid[i][j] = grid[x][j];
    						grid[x][j] = 0;
    					}
    				}			
    			}
    		}
    	}
   
    }
    // Private helper method moveUpAdd in up direction
    // checks if tiles are equal and adds them
    // If number below is equal to the current number, add them at the topmost
    private void moveUpAdd() {
    	int sum = 0;
      //must loop through size - 1 to ensure bounds
    	for (int i = 0; i < GRID_SIZE-1; i++) {
    		for (int j = 0; j < GRID_SIZE; j++) {
    			int x = i + 1;
            //If tile below is not equal to zero and equal to current tile,
            // add together and replace current tile
    				if (grid[x][j] != 0 && grid[i][j] == grid[x][j]) {
    					sum = grid [i][j] + grid[x][j];
    					grid[i][j] = sum;
              //while x stays within bounds slide tile upwards
    					while (x+1 < GRID_SIZE) {
    						grid[x][j] = grid[x+1][j];   						
    						x++;
    					} 
              //increment sum
    					score += sum;
    					grid[x][j] = 0;
    					
    				}
    		}
    			
    	}
    }
    
    /* Name:  moveDown()
     * Purpose:  a helper method to execute in move() to move tiles down
     * Parameters:  no parameters accepted
     * Return: boolean
     */
    private void moveDownBlank() {
    	if (canMoveDown()) {
          //Start at end of column and decrement
    			for (int j = GRID_SIZE-1; j >= 0; j--) {
    				for (int i = 0; i < GRID_SIZE; i++) {
    					//if current value is 0 
              if (grid[j][i] == 0) {
    						int x = j - 1;
    					  //while next value is zero, skip
    						while (x >= 0 && grid [x][i] == 0) {
    							x--;
    						}	
    					  //if it is nonzero swap with current indexed value
    						if (x >= 0) {
    							grid[j][i] = grid[x][i];
    							grid[x][i] = 0;
    						}
    					}			
    				}
    			}
    	}
    }
    
    // Private helper method moveDownAdd(): adds like tiles together downwards
    // If tile above is non zero and equal to current, collapse them 
    private void moveDownAdd() {
    	int sum = 0;
    	//Start at end of column and decrement
      for (int i = GRID_SIZE-1; i > 0; i--) {
    		for (int j = 0; j < GRID_SIZE; j++) {
    			int x = i - 1;
            //If next value is nonzero and equal to current value
    				if (grid[x][j] != 0 && grid[i][j] == grid[x][j]) {
    					sum = grid [i][j] + grid[x][j];
    					grid[i][j] = sum;
              //while x stays in bounds slide tile
    					while (x- 1 >= 0) {
    						grid[x][j] = grid[x-1][j];   						
    						x--;
    					} 
              //increment sum
    					score += sum;
    					grid[x][j] = 0;
    				}
    		}
    			
    	}
      } 

    
    /* Name:  isGameOver
     * Purpose:  Check to see if we have a game over
     * Parameters:  doesn't take in a parameter
     * Return: a boolean
     */
    public boolean isGameOver() {
    			//if canMove() every direction returns false, return true
    			if(canMove(k)== false && canMove(l)== false 
    			 && canMove(h)== false && canMove(j)== false ){
    				System.out.println("game over");
    					return true;
    			}
    	//return false otherwise
    	return false;
    }

    /* Name:  canMove
     * Purpose:  Determine if we can move in a given direction
     * Parameters:  takes in a direction
     * Return: returns a boolean
     */
    public boolean canMove(Direction direction) {
    	//creates a boolean to be returned at the end
    	boolean canMove = false;
    	//iterates through rows of board
    	for(int row = 0; row<GRID_SIZE; row++) {
			//iterates through the columns of board
    		for(int column = 0; column<GRID_SIZE; column++) {
    			//if current index is not 0, continue
    			if( this.grid[row][column] != 0) {
    				//if you can move up, return true
    				if(direction.equals(k)) {
    					if(canMoveUp()) {
    						canMove = true;
    					}
    				}
    				//if you can move right, return true
	    			else if(direction.equals(l)) {
	        			if(canMoveRight()) {
	        				canMove = true;
	        			}
	        		}
    				//if you can move left, return true
	        		else if(direction.equals(h)) {
	            		if(canMoveLeft()) {
	            			canMove = true;
	            		}
	        		}
    				//if you can move down, return true
	            	else if(direction.equals(j)) {
	                	if(canMoveDown()) {
	                		canMove = true;
	                	}
	            	}	
    				//otherwise, return false
	                else {               
	                    	canMove = false;
	                }
    		
    					
    			}
    		}
    	}
    	//returns boolean at the end
    	return canMove;
    	}
    	
    /* Name:  canMoveLeft
     * Purpose:  helper method to execute in canMove to determine whether or not 
     *this is a valid move or not
     * Parameters:  doesn't take in anything
     * Return: a boolean
     */
    private boolean canMoveLeft() {
    	//creates a boolean to be returned at the end
    	boolean Left = false; 
    	//iterates through rows of board
    	for(int row = 0; row<GRID_SIZE; row++) {
			//iterates through the columns of board
    		for(int column = 1; column<GRID_SIZE; column++) {
    			//if index is not 0
    			if(this.grid[row][column]!=0) {
    				//if left of index is same as index return true
    				if(this.grid[row][column]==this.grid[row][column-1]){
    					Left = true;
    				}
    				//if left of index is 0 return true
    				if(this.grid[row][column-1]==0) {
    					Left = true;
    				}
    			}
    		}
    	}
    	return Left;
    }
		
    /* Name:  canMoveRight
     * Purpose:  helper method to execute in canMove to determine whether or not 
     * this is a valid move or not
     * Parameters:  doesn't take in anything
     * Return: a boolean
     */
    
    //read inline comments for canMoveLeft()
    private boolean canMoveRight() {
    	boolean Right = false; 
    	for(int row = 0; row<GRID_SIZE; row++) {
			//iterates through the columns of board
    		for(int column = 0; column<GRID_SIZE-1; column++) {
    			if(this.grid[row][column]!=0) {
    				if(this.grid[row][column]==this.grid[row][column+1]){
    					Right = true;
    				}
    				if(this.grid[row][column+1]==0) {
    					Right = true;
    				}
    			}
    		}
    	}
    	return Right;
    }	
	
    /* Name:  canMoveUp
     * Purpose:  helper method to execute in canMove to determine whether or not 
     * this is a valid move or not
     * Parameters:  doesn't take in anything
     * Return: a boolean
     */
    
    //read inline comments for canMoveLeft()
    private boolean canMoveUp() {
    	boolean Up = false; 
    	for(int row = 1; row<GRID_SIZE; row++) {
			//iterates through the columns of board
    		for(int column = 0; column<GRID_SIZE; column++) {
    			if(this.grid[row][column]!=0) {
    				if(this.grid[row][column]==this.grid[row-1][column]){
    					Up = true;
    				}
    				if(this.grid[row-1][column]==0) {
    					Up = true;
    				}
    			}
    		}
    	}
    	return Up;
    }

    /* Name:  canMoveDown
     * Purpose:  helper method to execute in canMove to determine whether or not 
     * this is a valid move or not
     * Parameters:  doesn't take in anything
     * Return: a boolean
     */
    //read inline comments for canMoveLeft()
    private boolean canMoveDown() {
    	boolean Down = false; 
    	for(int row = 0; row<GRID_SIZE-1; row++) {
			//iterates through the columns of board
    		for(int column = 0; column<GRID_SIZE; column++) {
    			if(this.grid[row][column]!=0) {
    				if(this.grid[row][column]==this.grid[row+1][column]){
					Down = true;
				}
    				if(this.grid[row+1][column]==0) {
    					Down = true;
    				}
    			}
    		}
    	}
    	return Down;
    }
    
	// Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }
    
    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}        

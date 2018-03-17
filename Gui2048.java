/* 
 * Name: Jennifer Mei Yan Fung
 * Date: March 4th 2016
 * File:  Gui2048.java
 * 
 *this program designs the board to play the game 2048 and is handled by an
 *	event manager called principal // handler()
 *The method gameOver() is called when the game is over and makes the
 *		interface not playable
 *The method updateScore()updates the score
 *The method updateBoard() updates the board
 *The method fillColor() fills in the color of the tiles
 */
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
                                                 //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
                                                  //(1024, 2048, Higher)

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
                        // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles < 8

    //pane is initialised
    private GridPane pane = new GridPane();
   

    /** Add your own Instance Variables here */
    //initialised borderpane
    private BorderPane boardPane = new BorderPane();
    //labels
    private Label score;
    private Label title;
    //score
    private int scoreNum = 0;
   // private int[][] grid = board.getGrid();
    //private final int gridSize = board.GRID_SIZE;

    
    /* Name:  start
     * Purpose:  sets the scene and stage of the gui, so theres something to build upon
     * Parameters:   takes in a stage
     * Return: void, no return type
     */
    @Override
    public void start(Stage primaryStage)
    {
    	
    	
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);
        
    //creates the top pane while includes the title and score with customisations
        StackPane topPane = new StackPane();
        StackPane lolol = new StackPane();
        this.title = new Label("2048");
        this.score = new Label("score:" + this.scoreNum);
        Label lols = new Label("deez nuts");
        this.title.setFont(Font.font("Arial", FontWeight.MEDIUM, 69));
        this.score.setFont(Font.font("Arial", FontWeight.NORMAL, 30));
        lols.setFont(Font.font("Comic Sans", FontWeight.LIGHT, 8));
       
        //more customisations
        topPane.setAlignment(score, Pos.TOP_RIGHT);
        lolol.setAlignment(lols, Pos.BOTTOM_CENTER);
        topPane.setMargin(score, new Insets(3,10,10,0));
        
        //adds the labels onto the stackpane
        topPane.getChildren().addAll(title, score);
        lolol.getChildren().addAll(lols);

        //adds the stackpanes onto the borderpane
        boardPane.setTop(topPane);
        boardPane.setCenter(pane);
        boardPane.setBottom(lolol);
        boardPane.setStyle("-fx-background-color: rgb(187, 173, 160)");
  
        //creates the scene
        Scene gameScene = new Scene(boardPane);
        //calls the event manager
        gameScene.setOnKeyPressed(new Principal());
        //title of the stage
        primaryStage.setTitle("~*2048*~");
        //puts the scene on the stage
        primaryStage.setScene(gameScene);
        //update board constantly
        updateBoard();
        //show the stage
        primaryStage.show();
        
        
        
    }
    /* Name:  update score
     * Purpose:  updates the score in top pane
     * Parameters:  n/a
     * Return:n/a
     */

    private void updateScore(){
    	this.scoreNum = board.getScore();
    	this.score.setText("score:" + this.scoreNum);
    	
    }
    /* Name:  gameover
     * Purpose:  when the game is no longer playable, 
     * 				players are no longer able to operate
     * 				the display
     * Parameters:  n/a
     * Return: void
     */
    private void gameOver() {
    	//creates a stackpane to hold rectangle
    	StackPane gameOverTextPane = new StackPane();
    	//creates a rectangle to hold game over text
    	Rectangle gameOverRect = new Rectangle();
    	//creates a stackpane to overlay the entire game so its not playable
    	StackPane gameOverPane = new StackPane();
    	//width & size of rectangle
    	gameOverRect.setWidth(500);
    	gameOverRect.setHeight(500);
    	//creates text to put in rectangle
    	Text displayGameOver = new Text();
    	//text will display game over
    	displayGameOver.setText("Game Over!");
    	//customisations
    	displayGameOver.setFont(Font.font("Arial", FontWeight.BOLD, 30));
    	//fills the rectangle w/ the transparent overlay
    	gameOverRect.setFill(COLOR_GAME_OVER);
    	//adds rectangle & text to text pane
    	gameOverTextPane.getChildren().addAll(gameOverRect, displayGameOver);
    	
    	//adds the game grid pane and game over pane
    	gameOverPane.getChildren().addAll(pane, gameOverTextPane);
    	//resets borderpane as the gameover pane so its not playable
    	boardPane.setCenter(gameOverPane);
    }
    
    /* Name:  updateBoard
     * Purpose:  change pane to the updated state of tiles
     * Parameters:  n/a
     * Return: void
     */
    private void updateBoard(){
        int gridSize = board.GRID_SIZE;
        int [][] grid = board.getGrid();
    	//font for 1024+
    	Font high = new Font("Arial",  TEXT_SIZE_HIGH);
    	//font for 9-1023
    	Font mid = new Font("Arial",  TEXT_SIZE_MID);
    	//font for < 8
    	Font low = new Font("Arial",  TEXT_SIZE_LOW);
    	
    	//short circuiting, if it's over don't updateBoard
    	if(board.isGameOver()){
    		//if boolean is true, call gameOver
    		gameOver();
    	}
    	else{
            //initialises and instantialises tileText
            Text tileText = new Text(" ");
            //will be updated as new tiles are created/combined
            tileText.setText(" ");
            
          //iterates through rows of board
        	for(int row = 0; row<gridSize; row++) {
        		//iterates through columns of board
        		for(int column = 0; column<gridSize; column++){ 
        			//creates a stackpane for each tile
        			StackPane tilePane = new StackPane();
        			//creates a rectangle for each pane
        			Rectangle tileBox = new Rectangle();
        			//set width & height @ 100
        			tileBox.setWidth(100);
        			tileBox.setHeight(100);
        			tileBox.setArcHeight(10);
        			tileBox.setArcWidth(10);

        			//if tile number is 0, no text change or color change
        			if(grid[row][column]==0){
        				tileText = new Text(" ");
        				tileBox.setFill(COLOR_EMPTY);
        			}
        			//if tile isn't 0
        			else {
        				//change text to match 2D array index
        				tileText = new Text(" "+grid[row][column]);
        				//change color using fillColor
        				fillColor(grid[row][column], tileBox);
        		
        				//if less than 8, font size low & darker shade
            			if(grid[row][column]< 8){
            				tileText.setFont(low);
            				tileText.setFill(COLOR_VALUE_DARK);	
            			}
            			//if greater than 8
            			else if(grid[row][column]>=8){
            				//color is lighter
            				tileText.setFill(COLOR_VALUE_LIGHT);
            				//if index is greater than 1023, font size is changed
            				if(grid[row][column] > 1023){
                				tileText.setFont(high);
                			}
            				//otherwise, midsize font
                			else{
                				tileText.setFont(mid);
                			}
            			}
        			}

        			//customisation
        			tilePane.setMargin(tileText, new Insets(10,10,10,0) );
        			//adds rectangle & text to the tile pane
        			tilePane.getChildren().addAll(tileBox, tileText);
        			//adds tile pane, row per column to the main grid pane
        			pane.add(tilePane, column, row);
        				
        		}
        	}
    	}
            
            
    }


    /* Name:  fillcolor
     * Purpose:  fills in the color of the tile accordingly
     * Parameters:  takes in an int and rectangle
     * Return: void
     */
        private void fillColor (int i, Rectangle rectangle){
        	//if grid[row][column] (i) is == to this int
        	//fill it with said color
        	//exactly the same throughout method
        	if( i== 0){
        		rectangle.setFill(COLOR_EMPTY);
        	}
        	else if (i == 2) {
        		rectangle.setFill(COLOR_2);
        	}
        	else if (i == 4) {
        	    rectangle.setFill(COLOR_4);
        	} 
        	else if (i == 8) {
        		rectangle.setFill(COLOR_8);
        	} 
        	else if (i == 16) {
        	    rectangle.setFill(COLOR_16);
        	} 
        	else if (i == 32) {
        	    rectangle.setFill(COLOR_32);
        	} 
        	else if (i == 64) {
        	    rectangle.setFill(COLOR_64);
        	} 
        	else if (i == 128) {
        	    rectangle.setFill(COLOR_128);
        	} 
        	else if (i == 256) {
        	    rectangle.setFill(COLOR_256); 
        	} 
        	else if (i == 512) {
        	    rectangle.setFill(COLOR_512);
        	} 
        	else if (i == 1024) {
        	    rectangle.setFill(COLOR_1024);
        	} 
        	else if (i == 2048) {
        	    rectangle.setFill(COLOR_2048);
        	} 
        	else {
        	    rectangle.setFill(COLOR_OTHER);
            }
        }

        

        /* Name:  principal
         * Purpose:  the principal handler for key events
         * Parameters:  takes in a key event
         * Return: void
         */
        public class Principal implements EventHandler <KeyEvent> {
        	public void handle (KeyEvent e) {
        		//action gets the code to the key entered
        		KeyCode action = e.getCode();
        		//if key is pressed, will move in said direction
        		//add the random tiles, update the score & board accordingly
        		//and print to console the move performed
        		//exactly the same throughout
        		if(action == KeyCode.J || action == KeyCode.DOWN) {
        			board.move(Direction.DOWN);
        			board.addRandomTile();
        			updateScore();
        			updateBoard();
        			System.out.println("Moved down");
        		}
        		if(action == KeyCode.H || action == KeyCode.LEFT) {
        			board.move(Direction.LEFT);
        			board.addRandomTile();
        			updateScore();
        			updateBoard();
        			System.out.println("Moved left");
        		}
        		if(action == KeyCode.L || action == KeyCode.RIGHT) {
        			board.move(Direction.RIGHT);
        			board.addRandomTile();
        			updateScore();
        			updateBoard();
        			System.out.println("Moved right");
        		}
        		if(action == KeyCode.K || action == KeyCode.UP) {
        			board.move(Direction.UP);
        			board.addRandomTile();
        			updateScore();
        			updateBoard();
        			System.out.println("Moved up");
        		}
        		
        		if(action == KeyCode.S || action == KeyCode.Q) {
        			System.out.println(outputBoard);
        			System.out.println("saved board" + outputBoard);
        			try {
        				board.saveBoard(outputBoard);
        				} catch (IOException e1) { 
        				System.out.println("saveBoard threw an Exception");
        				}
        		}

        		
        	}
        }
        






    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}

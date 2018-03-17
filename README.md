# Welcome to 2048 on java! 
To play, please have a java compiler and X11 (or Xquartz for MacOS) installed and simply run
```
java Game2048
```

## Board.java: 

this program designs the board to play the game 2048, has a function of saving
and uploading a game from a file. At the beginning of a game,
it will add 2 random tiles to the board using method addRandomTile().
The method, 'rotate', rotates the board by 90 degrees clockwise
 or 90 degrees counter-clockwise.
The method canMove() tests whether or not this is a viable move on the board
The method move() moves the tiles accordingly
The method isGameOver() tests whether or not the game is over

| Sample Board|
|---|---|---|---|---|
|0  |  1|  2|  3|   | 
|0  |  -|  -|  -|  -|
| 1 | - |-  | - |  -|
| 2 |  -|  -| - |  -|
|3  |  -| - | - |  -|
 The sample board shows the index values for the columns and rows
 Remember that you access a 2D array by first specifying the row and then the column: grid[row][column]

## GameManager.java:
 this program manages the general functions of creating a board, saving a board and general game play.

 
## Gui2048.java:
this program designs the board to play the game 2048 and is handled by an event manager called principal // handler()
The method gameOver() is called when the game is over and makes the		interface not playable
The method updateScore()updates the score
The method updateBoard() updates the board
The method fillColor() fills in the color of the tiles

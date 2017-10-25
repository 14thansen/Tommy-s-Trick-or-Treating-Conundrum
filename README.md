# Tommy-s-Trick-or-Treating-Conundrum

## Synopsis
"Tommy's Trick-or-Treating Conundrum" is a Minesweeper style game. In this game the user helps Tommy solve a big problem; Tommy lives in a trailer park with his grandma, we’ll call her granny for short. Everyone in this trailer park is old and some of them, including Tommy’s grandma, are really mean. In granny’s trailer park a few of her neighbors have not bought candy and granny just told Tommy that if he knocks on even one door that doesn’t have any candy they have to go home! The problem is, no one will tell Tommy which trailers don’t have candy, they can only tell him how many trailers around theirs don’t have any. The users goal is to help Tommy get to all of the trailers with candy before going to any house without.


## Code Example

//Recursively check boxes
	void check(int x, int y) {
		setStuff(x, y);
		if (checked.contains("(" + x + "," + y + ")"))
			return;
		
		if (candy[x][y] == 0)
			return;
		
		checked += ("(" + x + "," + y + ")");
	
		if (hint[x][y] != 0) 
			return;
		
		if (x + 1 < W) 
			check(x + 1, y);
		if (x + 1 < W && y + 1 < L) 
			check(x + 1, y + 1);
		if (x + 1 < W && y - 1 >= 0) 
			check(x + 1, y - 1);
		if (y + 1 < L)
			check(x, y + 1);
		if (y - 1 >= 0)
			check(x, y - 1);
		if (x - 1 >= 0)
			check(x - 1, y);
		if (x - 1 >= 0 && y + 1 < L)
			check(x - 1, y + 1);
		if (x - 1 >= 0 && y - 1 >= 0)
			check(x - 1, y - 1);
	}
	
	//change buttons
	public void setStuff(int x, int y) {
		bHomes[x][y].setStyle("-fx-background-color: white");
		bHomes[x][y].setDisable(true);
		knocked[x][y] = 1;
		if (hint[x][y] == 1){
			bHomes[x][y].setText(Integer.toString(hint[x][y]));
			bHomes[x][y].setTextFill(Color.BLUE);
		}
		if (hint[x][y] == 2){
			bHomes[x][y].setText(Integer.toString(hint[x][y]));
			bHomes[x][y].setTextFill(Color.GREEN);
		}
		if (hint[x][y] == 3){
			bHomes[x][y].setText(Integer.toString(hint[x][y]));
			bHomes[x][y].setTextFill(Color.ORANGE);
		}
		if (hint[x][y] > 3){
			bHomes[x][y].setText(Integer.toString(hint[x][y]));
			bHomes[x][y].setTextFill(Color.RED);
		}
		if (candy[x][y] == 0){
			bHomes[x][y].setText(":(");
			bHomes[x][y].setTextFill(Color.ORANGE);
			bHomes[x][y].setStyle("-fx-background-color: black");
		}
    
This snippet of code shows how the program recursively searches for open boxes and allows the program to have an explosion effect, clearing out a chunk of the boxes that don't have anything to show, that we are all used to when playing Minesweeper. It also assigns a color to the numbers that are shown.

## Motivation

I wrote this code for my java final project, I needed to do a project that involved GUI's and that was object oriented.

## Installation

The main file that needs to be downloaded is the "game.java", also make sure that all of the other files including the images and the .mp3 files are in the SRC folder as well.

## Tests

When playing the game you will first be able to choose the width and the length of the playing board and then you will be able to choose the difficulty of the game. After you create your personalized game you play by clicking on the buttons, click on a button that is associated with "a house without candy" you lose, otherwise you will be given hints as to where those buttons are. It's minesweeper at its finest.

## Contributors

This code is anything if not perfect, if you want to add to the game please feel free to do so. I was planning on adding an instructions pane as well as the ability to flag which buttons the user beleives don't have any candy but did not have time.

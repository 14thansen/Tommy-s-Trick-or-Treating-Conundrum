import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class Game extends Application {
	//info for comboBox
	private String[] difficulties = {"Difficulty, percent", "Easy, 5%", "Normal, 10%", "Hard, 20%"};
	private double[] difficultyValues = {0, .05, .10, .20};
	ComboBox<String> cbDifficulty = new ComboBox<>();
	String checked = "";
	
	public int W = 15;
	public int L = 15;
	public int X = 0;
	
	Stage menu = new Stage();
	Stage game = new Stage();
	Stage loseStage = new Stage();
	Stage winStage = new Stage();
	
	//menu pane
	protected BorderPane getMenu() {
		//Declare panes
		BorderPane pane = new BorderPane();
		StackPane messagePane = new StackPane();
		messagePane.setStyle("-fx-border-color: black");
		messagePane.setPadding(new Insets(15, 15, 15, 15));
		VBox optionPane = new VBox(15);
		HBox sizeLine = new HBox(5);
		sizeLine.setAlignment(Pos.TOP_LEFT);
		HBox difficultyLine = new HBox(5);
		optionPane.setPadding(new Insets(15, 15, 15, 15));
		optionPane.setAlignment(Pos.CENTER_LEFT);
		HBox buttonLine = new HBox();
		buttonLine.setAlignment(Pos.CENTER_RIGHT);
		pane.setTop(messagePane);
		pane.setCenter(optionPane);
		
		//declare variables
		
		
		//Labels and objects
		Label lbWelcome = new Label("Welcome to \"Tommy's Trick-or-Treating Conundrum\"!"
						 	    + "\nTommy has a big problem and you need to help him! "
							    + "\nTommy lives in a trailer park with his grandma, we’ll call her "
							    + "\ngranny for short; everyone in this trailer park is old and "
							    + "\nsome of them, including Tommy’s grandma, are really mean. "
							    + "\nIn granny’s trailer park a few of her neighbors have not bought "
							    + "\ncandy and granny just told Tommy that if he knocks on even one "
							    + "\ndoor that doesn’t have any candy they have to go home! "
							    + "\nThe problem is, no one will tell Tommy which trailers don’t have "
							    + "\ncandy, they can only tell him how many trailers around theirs "
							    + "\ndon’t have any. The users goal is to help Tommy get to all of the "
							    + "\ntrailers with candy before going to any house without.");
		Label lbSizeMessage = new Label("How big is granny's trailer park?");
		Label lbW = new Label("trailers wide (10 min, 106 max)");
		lbW.setWrapText(true);
		TextField tfW = new TextField();
		tfW.setPrefColumnCount(3);
		tfW.setText(Integer.toString(W));
		tfW.setStyle("-fx-text-inner-color: gray");
		tfW.setAlignment(Pos.CENTER_RIGHT);
		Label lbL = new Label("trailers long (10 min, 34 max)");
		lbL.setWrapText(true);
		TextField tfL = new TextField();
		tfL.setPrefColumnCount(2);
		tfL.setText(Integer.toString(L));
		tfL.setStyle("-fx-text-inner-color: gray");
		tfL.setAlignment(Pos.CENTER_RIGHT);
		Label lbDifficulty = new Label("Decide how rude granny's neighbors are to set the difficulty:");
		Label lbPercent = new Label("of granny's neighbors don't have candy.");
		ObservableList<String> items = FXCollections.observableArrayList(difficulties);
		cbDifficulty.getItems().addAll(items);
		cbDifficulty.setValue(difficulties[0]);
		cbDifficulty.setMaxWidth(160);
		Button bCreate = new Button("Create your trailer park");
		bCreate.setAlignment(Pos.CENTER_RIGHT);
		
		//text field controls
		tfW.setOnMouseClicked(e -> {
			tfW.setText("");
			tfW.setStyle("-fx-text-inner-color: black");
		});
		tfL.setOnMouseClicked(e -> {
			tfL.setText("");
			tfL.setStyle("-fx-text-inner-color: black");
		});
		
		//create button controls
		bCreate.setOnAction(e ->{
			if (Integer.parseInt(tfW.getText()) <= 100 && Integer.parseInt(tfW.getText()) > 10)
				W = Integer.parseInt(tfW.getText());
			else if (Integer.parseInt(tfW.getText()) > 100)
				W = 100;
			else if (Integer.parseInt(tfW.getText()) < 10)
				W = 10;
			//end if
			if (Integer.parseInt(tfL.getText()) <= 30 && Integer.parseInt(tfL.getText()) > 10)
				L = Integer.parseInt(tfL.getText());
			else if (Integer.parseInt(tfL.getText()) > 30)
				L = 30;
			else if (Integer.parseInt(tfL.getText()) < 10)
				L = 10;
			//end if
			
			if (items.indexOf(cbDifficulty.getValue()) != 0)
				X = items.indexOf(cbDifficulty.getValue());
			else
				X = 2;
			
			menu.close();
			
			Scene scene = null;
			try {
				scene = new Scene(getPane(W, L, X), W * 30 + 10, L * 27 + 50);
			} catch (Exception e1) {
				System.out.println("error opening image");
			}
			game.setTitle("Tommy's Trick-or-Treating Conundrum");
			game.setScene(scene);
			game.show();
		});
		
		
	
		//add children
		messagePane.getChildren().add(lbWelcome);
		sizeLine.getChildren().addAll(tfW, lbW, tfL, lbL);
		difficultyLine.getChildren().addAll(cbDifficulty, lbPercent);
		buttonLine.getChildren().add(bCreate);
		optionPane.getChildren().addAll(lbSizeMessage, sizeLine, lbDifficulty, difficultyLine, buttonLine);
		
		return pane;
	}
	
	public Button[][] bHomes = new Button[W][L];
	public int[][] hint = new int[W][L];
	public int[][] candy = new int[W][L];
	public int[][] knocked = new int[W][L];
	
	//The game
	protected BorderPane getPane(int w, int l, int x) throws MalformedURLException {
		//set up pane
		BorderPane pane = new BorderPane();
		GridPane trailers = new GridPane();
		trailers.setPadding(new Insets(5, 5, 5, 5));
		pane.setCenter(trailers);
		
		Image detective = new Image(new File("/Users/student/Documents/workspace/Game/src/detective.gif")
				.toURI().toURL().toExternalForm());
		ImageView ivDetective = new ImageView(detective);
		ivDetective.setFitHeight(40);
		ivDetective.setFitWidth(40);
		HBox topPane = new HBox(20);
		topPane.getChildren().add(ivDetective);
		topPane.setAlignment(Pos.CENTER);
		pane.setTop(topPane);
		
		//info to be checked
		
		
		//set difficulty percentage
		double percent = difficultyValues[x];
		
		int noCandy = 0;
		
		//set original values
		for(int i = 0; i < w; i++){
			for(int j = 0; j < l; j++){
				//how many neighbors don't have candy
				hint[i][j] = 0;
				//which houses don't have candy
				candy[i][j] = 1;
				//which houses have been knocked
				knocked[i][j] = 0;
			}
		}
		
		for (int i = 0; i < (int)((w * l) * percent); i++){
			//pick random houses to not have candy
			Random rand = new Random();
			int rw = rand.nextInt(w - 2) + 1;
			int rl = rand.nextInt(l - 2) + 1;
			if (candy[rw][rl] != 0) {
					candy[rw][rl] = 0;
					System.out.println(rw + ", " + rl);
					//add a hint to the surrounding blocks
					hint[rw + 1][rl + 1]++;
					hint[rw + 1][rl - 1]++;
					hint[rw + 1][rl]++;
					hint[rw - 1][rl + 1]++;
					hint[rw - 1][rl - 1]++;
					hint[rw - 1][rl]++;
					hint[rw][rl + 1]++;
					hint[rw][rl - 1]++;
					noCandy++;
			}
		}
		
		//number of houses to visit to win
		int toWin = (w * l) - noCandy;
		System.out.println(toWin + "\n" + noCandy);
		
		//knocking noise
		String knock = "/Users/student/Documents/workspace/Game/src/Knocking.mp3";
		String cheer = "/Users/student/Documents/workspace/Game/src/Cheer.mp3";
		String lose = "/Users/student/Documents/workspace/Game/src/lose.mp3";
		Media knockNoise = new Media(new File(knock).toURI().toString());
		Media cheerNoise = new Media(new File(cheer).toURI().toString());
		Media loseNoise = new Media(new File(lose).toURI().toString());
		MediaPlayer mpKnock = new MediaPlayer(knockNoise);
		MediaPlayer mpCheer = new MediaPlayer(cheerNoise);
		MediaPlayer mpLose = new MediaPlayer(loseNoise);
		
		//set on click
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < l; j++){
				bHomes[i][j] = getTrailer();
				trailers.add(bHomes[i][j], i + 1, j);
				final int ii = i;
				final int jj = j;
				bHomes[i][j].setOnAction(e -> {
					check(ii, jj);
					if (mpKnock.getStatus().equals(Status.PLAYING))
						mpKnock.stop();
					mpKnock.play();
					
					int winCount = isWon();
					System.out.println(winCount);
					
					if (winCount == toWin){
						mpCheer.play();
						Scene winScene = null;
						try {
							winScene = new Scene(winPane(), 300, 200);
						} catch (Exception e1) {
							System.out.println("Error opening image");
						}
						winStage.setTitle("You Win!");
						winStage.setScene(winScene);
						winStage.show();
						setWin();
					}
					
					if (candy[ii][jj] == 0) {
						Scene loseScene = null;
						mpLose.play();
						try {
							loseScene = new Scene(losePane(toWin, winCount), 550, 250);
						} catch (Exception e1) {
							System.out.println("Error opening image");
						}
						loseStage.setTitle("You Lose");
						loseStage.setScene(loseScene);
						loseStage.show();
						setLose();
					}
				});
			}
		}
		
		return pane;
	}
	
	public BorderPane winPane() throws MalformedURLException {
		BorderPane winPane = new BorderPane();
		Image winImage = new Image(new File("/Users/student/Documents/workspace/Game/src/winImage.jpg")
				.toURI().toURL().toExternalForm());
		ImageView ivWin = new ImageView(winImage);
		ivWin.setFitHeight(100);
		ivWin.setFitWidth(200);
		HBox winBox = new HBox(20);
		winBox.setAlignment(Pos.CENTER);
		winBox.getChildren().add(ivWin);
		winPane.setTop(winBox);
		
		Label lbWin = new Label("You found all the candy! Congratulations!");
		lbWin.setStyle("-fx-font-size: 2em");
		lbWin.setWrapText(true);
		winPane.setCenter(lbWin);
		
		Button playAgain = new Button("Play Again");
		Button close = new Button("Close");
		HBox buttonPane = new HBox(20);
		buttonPane.setPadding(new Insets(5, 5, 5, 5));
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(playAgain, close);
		winPane.setBottom(buttonPane);
		
		playAgain.setOnAction(e -> {
			game.close();
			winStage.close();
			menu.show();
		});
		close.setOnAction(e -> {
			game.close();
			winStage.close();
		});
		
		return winPane;
	}
	
	public BorderPane losePane(int toWin, int winCount) throws MalformedURLException {
		BorderPane losePane = new BorderPane();
		losePane.setPadding(new Insets(10, 10, 10, 10));
		Image loseImage = new Image(new File("/Users/student/Documents/workspace/Game/src/loseImage.png")
				.toURI().toURL().toExternalForm());
		ImageView ivLose = new ImageView(loseImage);
		ivLose.setFitHeight(200);
		ivLose.setFitWidth(125);
		
		losePane.setRight(ivLose);
		
		Label lbLose = new Label("You only visited " + winCount + "/" + toWin + " houses. \nYou Lose!");
		lbLose.setStyle("-fx-font-size: 2em");
		losePane.setCenter(lbLose);

		Button retry = new Button("Retry");
		Button close = new Button("Close");
		HBox bottomPane = new HBox(20);
		bottomPane.setPadding(new Insets(5, 5, 5, 5));
		bottomPane.setAlignment(Pos.CENTER);
		bottomPane.getChildren().addAll(retry, close);
		losePane.setBottom(bottomPane);
		
		retry.setOnAction(e -> {
			game.close();
			loseStage.close();
			menu.show();
		});
		close.setOnAction(e -> {
			game.close();
			loseStage.close();
		});
		
		return losePane;
	}
	
	public int isWon() {
		int winCount = 0;
		for(int i = 0; i < W; i++){
			for(int j = 0; j < L; j++){
				if (knocked[i][j] == 1)
					winCount++;
			}
		}
		return winCount;
	}
	
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
	}
	public void setLose() {
		for(int i = 0; i < W; i++){
			for(int j = 0; j < L; j++) {
				if (candy[i][j] == 0){
					bHomes[i][j].setText(":(");
					bHomes[i][j].setTextFill(Color.ORANGE);
					bHomes[i][j].setStyle("-fx-background-color: black");
				}
			}
		}
	}
	public void setWin() {
		for(int i = 0; i < W; i++){
			for(int j = 0; j < L; j++) {
				if (candy[i][j] == 0){
					bHomes[i][j].setText(":)");
					bHomes[i][j].setTextFill(Color.BLACK);
					bHomes[i][j].setStyle("-fx-background-color: orange");
				}
			}
		}
	}
	
	//make the button
	public Button getTrailer() {
		Button trailer = new Button("   ");
		return trailer;
	}
	
	@Override
	public void start(Stage primaryStage) {
		menu.setTitle("Tommy's Trick-or-Treating Conundrum");
		Scene menuScene = new Scene(getMenu(), 460, 450);
		menu.setScene(menuScene);
		menu.setResizable(false);
		menu.show();
		
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}

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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Game extends Application {
	//info for comboBox
	private String[] difficulties = {"Difficulty, percent", "Easy, 5%", "Normal, 10%", "Hard, 20%"};
	private double[] difficultyValues = {0, .05, .10, .20};
	ComboBox<String> cbDifficulty = new ComboBox<>();
	String checked = "";
	
	public int W = 100;
	public int L = 100;
	public int X = 0;
	
	Stage menu = new Stage();
	Stage game = new Stage();
	Stage instructions = new Stage();
	Stage loseStage = new Stage();
	Stage winStage = new Stage();
	Stage resetStage = new Stage();
	
	//menu pane
	protected BorderPane getMenu() {
		//Declare panes
		W = 15;
		L = 15;
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
		HBox buttonLine = new HBox(175);
		buttonLine.setAlignment(Pos.CENTER);
		pane.setTop(messagePane);
		pane.setCenter(optionPane);
		
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
		Label lbW = new Label("trailers wide (5 min, 63 max)");
		lbW.setWrapText(true);
		TextField tfW = new TextField();
		tfW.setPrefColumnCount(3);
		tfW.setText(Integer.toString(W));
		tfW.setStyle("-fx-text-inner-color: gray");
		tfW.setAlignment(Pos.CENTER_RIGHT);
		Label lbL = new Label("trailers long (5 min, 33 max)");
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
		Button bInstructions = new Button("How to Play");
		bInstructions.setAlignment(Pos.CENTER_LEFT);
		
		//text field controls
		tfW.setOnMouseClicked(e -> {
			tfW.setStyle("-fx-text-inner-color: black");
			tfW.setText("");
		});
		tfL.setOnMouseClicked(e -> {
			tfL.setStyle("-fx-text-inner-color: black");
			tfL.setText("");
		});
		
		//create button controls
		bCreate.setOnAction(e ->{
			if (tfW.getLength() == 0)
				tfW.setText("15");
			if (tfL.getLength() == 0)
				tfL.setText("15");
				
			if (Integer.parseInt(tfW.getText()) <= 63 && Integer.parseInt(tfW.getText()) >= 5)
				W = Integer.parseInt(tfW.getText());
			else if (Integer.parseInt(tfW.getText()) > 63)
				W = 63;
			else if (Integer.parseInt(tfW.getText()) < 5)
				W = 5;
			//end if
			if (Integer.parseInt(tfL.getText()) <= 33 && Integer.parseInt(tfL.getText()) >= 5)
				L = Integer.parseInt(tfL.getText());
			else if (Integer.parseInt(tfL.getText()) > 33)
				L = 33;
			else if (Integer.parseInt(tfL.getText()) < 5)
				L = 5;
			//end if
			
			if (items.indexOf(cbDifficulty.getValue()) != 0)
				X = items.indexOf(cbDifficulty.getValue());
			else
				X = 2;
			
			menu.close();
			
			Scene scene = null;
			try {
				scene = new Scene(getPane(W, L, X), W * 30 + 10, L * 27 + 60);
			} catch (Exception e1) {
				System.out.println("error opening image 1");
			}
			game.setTitle("Tommy's Trick-or-Treating Conundrum");
			game.setScene(scene);
			game.show();
		});
		
		bInstructions.setOnAction(e -> {
			Scene instructionsScene = new Scene(instructionPane(), 450, 250);
			instructions.setTitle("Instructions");
			instructions.setScene(instructionsScene);
			instructions.show();
		});
		
		
	
		//add children
		messagePane.getChildren().add(lbWelcome);
		sizeLine.getChildren().addAll(tfW, lbW, tfL, lbL);
		difficultyLine.getChildren().addAll(cbDifficulty, lbPercent);
		buttonLine.getChildren().addAll(bInstructions, bCreate);
		optionPane.getChildren().addAll(lbSizeMessage, sizeLine, lbDifficulty, difficultyLine, buttonLine);
		
		return pane;
	}
	
	public BorderPane instructionPane() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		Label lbInstructions = new Label("How to Play:"
									  +"\n1. Choose the dimensions and difficulty of your game board."
									  +"\n2a. Left click on trailers to reveal reveal whether they have candy or not or how many of their neighbors do not have candy."
									  +"\n2b. If you have uncovered all of the trailers with candy without clicking on any with candy you win."
									  +"\n3a. Right click on any trailer to flag it with an astrisk (*) to keep track of which houses do not have candy, mark all the houses without candy with the (*) and you will win."
									  +"\n3b. If you're not sure witch trailer doesn't have candy, right click on any trailer a second time and you will mark the trailer with a question mark (?)");
		lbInstructions.setWrapText(true);
		Button btClose = new Button("Close");
		btClose.setOnAction(e -> instructions.close());
		
		pane.setTop(lbInstructions);
		pane.setRight(btClose);
		return pane;
	}
	
	public Button[][] bHomes = new Button[W][L];
	public int[][] hint = new int[W][L];
	public int[][] candy = new int[W][L];
	public int[][] knocked = new int[W][L];
	public int[][] flag = new int[W][L];
	public int[][] winFlags = new int[W][L];
	public int score = 0;
	
	//The game
	protected BorderPane getPane(int w, int l, int x) throws MalformedURLException {
		//set up pane
		BorderPane pane = new BorderPane();
		GridPane trailers = new GridPane();
		trailers.setPadding(new Insets(5, 5, 5, 5));
		pane.setCenter(trailers);
		
		Image iHappy = new Image(new File("monsterHappy.png")
				.toURI().toURL().toExternalForm());
		Image iExcited = new Image(new File("MonsterExcited.png")
				.toURI().toURL().toExternalForm());
		Image iSad = new Image(new File("monsterSad.png").toURI().toURL().toExternalForm());
		ImageView ivFace = new ImageView(iHappy);
		ivFace.setFitHeight(40);
		ivFace.setFitWidth(40);
		HBox topPane = new HBox((W * 30) / 3.5);
		topPane.setPadding(new Insets(5, 5, 5, 5));
		topPane.setAlignment(Pos.CENTER);
		pane.setTop(topPane);
		
		TextField tfScore = new TextField();
		tfScore.setAlignment(Pos.CENTER);
		tfScore.setEditable(false);
		tfScore.setStyle("-fx-background-color: orange");
		tfScore.setPrefColumnCount(5);
		
		topPane.getChildren().addAll(timerPane(), ivFace, tfScore);
		
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
				//set flag status to 0
				flag[i][j] = 0;
				//set winning flags
				winFlags[i][j] = 0;
			}
		}
		
		for (int i = 0; i < (int)((w * l) * percent); i++){
			//pick random houses to not have candy
			Random rand = new Random();
			int rw = rand.nextInt(w - 2) + 1;
			int rl = rand.nextInt(l - 2) + 1;
			if (candy[rw][rl] != 0) {
					candy[rw][rl] = 0;
					winFlags[rw][rl] = 1;
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
		int toWin2 = noCandy;
		
		//knocking noise
		String knock = "Knocking.mp3";
		String cheer = "Cheer.mp3";
		String lose = "lose.mp3";
		Media knockNoise = new Media(new File(knock).toURI().toString());
		Media cheerNoise = new Media(new File(cheer).toURI().toString());
		Media loseNoise = new Media(new File(lose).toURI().toString());
		MediaPlayer mpKnock = new MediaPlayer(knockNoise);
		MediaPlayer mpCheer = new MediaPlayer(cheerNoise);
		MediaPlayer mpLose = new MediaPlayer(loseNoise);
		
		ivFace.setOnMouseClicked(e -> {
			Scene resetScene = new Scene(resetPane(), 250, 75);
			resetStage.setTitle("Reset");
			resetStage.setScene(resetScene);
			resetStage.show();
		});
		
		//set on click
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < l; j++){
				bHomes[i][j] = getTrailer();
				trailers.add(bHomes[i][j], i + 1, j);
				final int ii = i;
				final int jj = j;
				bHomes[i][j].setOnMousePressed(e -> ivFace.setImage(iExcited));
				bHomes[i][j].setOnMouseReleased(e -> ivFace.setImage(iHappy));
				bHomes[i][j].setOnMouseClicked(e -> {
					if (e.getButton() == MouseButton.PRIMARY) {
						check(ii, jj);
						if (mpKnock.getStatus().equals(Status.PLAYING))
							mpKnock.stop();
						mpKnock.play();
						
						int winCount = isWon();
						score = winCount;
						tfScore.setText(Integer.toString(score) + "/" + toWin);
											
						if (winCount == toWin){
							mpCheer.play();
							Scene winScene = null;
							try {
								winScene = new Scene(winPane(), 300, 200);
							} catch (Exception e1) {
								System.out.println("Error opening image 2");
							}
							winStage.setTitle("You Win!");
							winStage.setScene(winScene);
							winStage.show();
							ivFace.setImage(iExcited);
							setWin();
						}
						
						if (candy[ii][jj] == 0) {
							Scene loseScene = null;
							mpLose.play();
							try {
								loseScene = new Scene(losePane(toWin, winCount), 575, 250);
							} catch (Exception e1) {
								System.out.println("Error opening image 3");
							}
							loseStage.setTitle("You Lose");
							loseStage.setScene(loseScene);
							loseStage.show();
							ivFace.setImage(iSad);
							setLose();
						}
					}else if(e.getButton() == MouseButton.SECONDARY) {
						if (flag[ii][jj] >= 2)
							flag[ii][jj] = 0;
						else 
							flag[ii][jj]++;
						setFlags(flag[ii][jj], ii, jj);
						
						int winCount2 = isWon2();
						if (winCount2 == toWin2) {
							mpCheer.play();
							Scene winScene = null;
							try {
								winScene = new Scene(winPane(), 300, 200);
							} catch (Exception e1) {
								System.out.println("Error opening image 4");
							}
							winStage.setTitle("You Win!");
							winStage.setScene(winScene);
							winStage.show();
							ivFace.setImage(iExcited);
							setWin();
						}
					}
				});
			}
		}
		
		return pane;
	}
	
	public BorderPane resetPane() {
		BorderPane pane = new BorderPane();
		Label lbReset = new Label("Would you like to reset the game?");
		
		HBox buttonPane = new HBox(25);
		Button bReset = new Button("Reset");
		Button bCancel = new Button("Cancel");
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setPadding(new Insets(5, 5, 5, 5));
		buttonPane.getChildren().addAll(bReset, bCancel);
		
		pane.setCenter(lbReset);
		pane.setBottom(buttonPane);
		
		bReset.setOnAction(e -> {
			game.close();
			menu.show();
			resetStage.close();
			checked = "";
			timeSeconds = 0;
			minutes = 0;
		});
		
		bCancel.setOnAction(e -> {
			resetStage.close();
		});
		
		return pane;
	}
	
	private Integer startTime = 0;
	private Timeline timeline;
	private TextField tfTimerS = new TextField();
	private TextField tfTimerM = new TextField();
	private Integer timeSeconds = startTime;
	private Integer minutes = 0;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HBox timerPane() {
		HBox pane = new HBox();
		pane.setAlignment(Pos.CENTER);
		
		tfTimerS.setAlignment(Pos.CENTER);
		tfTimerS.setEditable(false);
		tfTimerS.setText("0");
		tfTimerS.setStyle("-fx-background-color: orange");
		tfTimerS.setPrefColumnCount(2);
		
		tfTimerM.setAlignment(Pos.CENTER);
		tfTimerM.setEditable(false);
		tfTimerM.setText("0");
		tfTimerM.setStyle("-fx-background-color: orange");
		tfTimerM.setPrefColumnCount(2);
		
		Label colon = new Label(":");
		pane.getChildren().addAll(tfTimerM, colon, tfTimerS);
		
		
		if(timeline != null)
			timeline.stop();
			
		timeSeconds = Integer.parseInt(tfTimerS.getText());
		minutes = Integer.parseInt(tfTimerM.getText());
		//tfTimer.setText(Integer.toString(minutes) + ":" + Integer.toString(timeSeconds));
		tfTimerS.setText(timeSeconds.toString());
		tfTimerM.setText(minutes.toString());
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
				new EventHandler() {
					@Override
					public void handle(Event event) {
						timeSeconds++;
						if(timeSeconds >= 60){
							timeSeconds -= 60;
							minutes++;
						}
						tfTimerS.setText(timeSeconds.toString());
						tfTimerM.setText(minutes.toString());
					}
				}));
		timeline.playFromStart();
		
		return pane;
	}
	
	public BorderPane winPane() throws MalformedURLException {
		BorderPane winPane = new BorderPane();
		Image winImage = new Image(new File("winImage.jpg")
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
			checked = "";
			timeSeconds = 0;
			minutes = 0;
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
		Image loseImage = new Image(new File("loseImage.png")
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
			checked = "";
			timeSeconds = 0;
			minutes = 0;
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
				if (knocked[i][j] == 1 && candy[i][j] != 0)
					winCount++;
			}
		}
		return winCount;
	}
	
	public int isWon2() {
		int winCount = 0;
		for(int i = 0; i < W; i++){
			for(int j = 0; j < L; j++){
				if (flag[i][j] == 1 && candy[i][j] == 0)
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
	public void setFlags(int status, int x, int y) {
		if (status == 0)
			bHomes[x][y].setText("   ");
		else if (status == 1)
			bHomes[x][y].setText("* ");
		else if (status == 2)
			bHomes[x][y].setText("? ");
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

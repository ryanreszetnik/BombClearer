
package application;
//Bomb Clearer
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Font;

import javafx.stage.Stage;

public class Main extends Application {
	StackPane roottitle;
	Stage window;
	static Scene scene, titlescene, leaderboard, instructions;
	int first, second;

	static int xsize = 9;
	static int ysize = 9;
	static int numofbombs = 10;
	static double buttonsize = 50;
	// board specifications

	static boolean shown[][];
	static int board[][];
	static int bombs[][];
	static boolean firstturnarray[][];
	static Button[][] button;
	static boolean flagged[][];
	static boolean loseshown[][];
	// board arrays

	static int checkleft, checkright, checkup, checkdown;
	static boolean playing = false;

	static boolean firstturn = true;

	static String difficulty = "EasyScore";
	static String easy, medium, hard;
	// dificulties

	static boolean lose = false;
	static boolean win = false;

	static Image title, podium, bombclicked, flagimage, unplayed, played, missed, mistake, P1, P2, P3, P4, P5, P6, P7,
			P8;
	// all the images used
	static long start;
	static String score = "0.00";
	static int bombcount = numofbombs;

	static Label timelabel = new Label();
	static Label bombcountlabel = new Label();
	static Label endgamelabel = new Label();
	static Label topscoreseasy = new Label();
	static Label topscoresmedium = new Label();
	static Label topscoreshard = new Label();
	static Label easyTitle = new Label("Easy");
	static Label mediumTitle = new Label("Medium");
	static Label hardTitle = new Label("Hard");
	static Button resetbutton = new Button();
	static Button menu = new Button();
	// labels + buttons

	static AnimationTimer timer;
	static boolean reset = false;
	static ArrayList<Double> scoresList = new ArrayList<Double>();// for storing
																	// scores

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;// allows primary stage to be accessed by other
								// methods
		window.setResizable(false);
		primaryStage.setTitle("Bomb Clearer");// sets the title of the window
		try {
			// imports images
			title = new Image(getClass().getResource("Title1.png").toExternalForm(), 360, 50, false, false);
			// used https://www.dafont.com/peinture-fraiche.font to make it
			podium = new Image(getClass().getResource("Podium.png").toExternalForm(), 75, 50, false, false);
			// image from https://pngtree.com/so/champion-podium

			Label width = new Label("Width: " + xsize);
			width.setFont(new Font("Ariel", 15));
			width.setTranslateX(100);
			width.setTranslateY(70);
			// shows what the width of the board will be

			Label height = new Label("Height: " + ysize);
			height.setTranslateX(100);
			height.setTranslateY(90);
			height.setFont(new Font("Ariel", 15));
			// shows what the height of the board will be
			Label totalmines = new Label("# of Mines: " + numofbombs);
			totalmines.setTranslateX(100);
			totalmines.setTranslateY(110);
			totalmines.setFont(new Font("Ariel", 15));
			// shows how many mines there will be

			Button easy = new Button("EASY");
			Button medium = new Button("MEDIUM");
			Button hard = new Button("HARD");
			easy.setTranslateX(-100);
			hard.setTranslateX(100);

			easy.setStyle(" -fx-background-color: yellow; -fx-border-color: black; -fx-border-width: 5px;");
			medium.setStyle(
					" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
			hard.setStyle("-fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
			// initializes the styles of the buttons to have easy selected

			easy.setOnAction(event -> {
				xsize = 9;
				ysize = 9;
				numofbombs = 10;

				width.setText("Width: " + xsize);
				height.setText("Height: " + ysize);
				totalmines.setText("# of Mines: " + numofbombs);
				// updates the board specifications and the labels that show
				// them

				easy.setStyle(" -fx-background-color: yellow; -fx-border-color: black; -fx-border-width: 5px;");
				medium.setStyle(
						" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
				hard.setStyle(
						"-fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
				// updates styles to make it look like easy is selected
				buttonsize = 50;// makes buttons original size if they were
								// changed
				difficulty = "EasyScore";// used for saying where to save top
											// scores

			});

			medium.setStyle(
					" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");

			medium.setOnAction(event -> {
				xsize = 16;
				ysize = 16;
				numofbombs = 40;
				width.setText("Width: " + xsize);
				height.setText("Height: " + ysize);
				totalmines.setText("# of Mines: " + numofbombs);
				// updates the board specifications and the labels that show
				// them
				easy.setStyle(
						" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
				medium.setStyle("-fx-background-color: orange; -fx-border-color: black; -fx-border-width: 5px;");
				hard.setStyle(
						" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
				// updates styles to make it look like medium is selected
				buttonsize = 40;// makes buttons a bit smaller for the bigger
								// size
				difficulty = "MediumScore";// used for saying where to save top
											// scores

			});

			hard.setStyle(" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");

			// hard.setTranslateY(-50);
			hard.setOnAction(event -> {
				xsize = 30;
				ysize = 16;
				numofbombs = 99;
				width.setText("Width: " + xsize);
				height.setText("Height: " + ysize);
				totalmines.setText("# of Mines: " + numofbombs);
				// updates the board specifications and the labels that show
				// them
				easy.setStyle(
						" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
				medium.setStyle(
						" -fx-background-color:rgb(241, 241, 241); -fx-border-color: black; -fx-border-width: 5px;");
				hard.setStyle(" -fx-background-color: red; -fx-border-color: black; -fx-border-width: 5px;");
				// updates styles to make it look like hard is selected
				buttonsize = 40;// makes buttons a bit smaller for the bigger
								// size
				difficulty = "HardScore";// used for saying where to save top
											// scores
			});

			Button leaderboards = new Button();
			leaderboards.setTranslateX(-100);
			leaderboards.setTranslateY(100);
			leaderboards.setGraphic(new ImageView(podium));
			leaderboards.setOnAction(event -> {
				leaderboards();
			});
			// button for seeing your top score

			Button instructionsbutton = new Button();
			instructionsbutton.setTranslateX(-155);
			instructionsbutton.setTranslateY(-137);
			instructionsbutton.setText("Instructions");
			instructionsbutton.setOnAction(event -> {
				instructions();
			});
			// button for seeing instructions

			Button startbutton = new Button("START");
			startbutton.setTranslateX(0);
			startbutton.setTranslateY(100);
			// button for starting the game

			ImageView titleimage = new ImageView(title);
			titleimage.setTranslateY(-80);
			// puts the title on the screen

			roottitle = new StackPane();
			roottitle.getChildren().addAll(easy, medium, hard, width, height, totalmines, startbutton, leaderboards,
					instructionsbutton, titleimage);
			// adds all of the elements to a stackpane

			titlescene = new Scene(roottitle, 400, 300);
			// declares the scene
			primaryStage.setScene(titlescene);
			primaryStage.show();
			// makes it so you can see the scene

			startbutton.setOnAction(event -> {
				// when you press start
				gamesetup();
				gamescene();
			});
			primaryStage.show();
			// displays it on the screen
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		launch(args);// starts the program
	}

	public static void print() {
		int numofflags = 0;

		for (int i = 0; i < xsize; i++) {
			for (int p = 0; p < ysize; p++) {// loops through whole board

				// sets the image of the buttons to corresponding values
				if (board[i][p] == 1 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P1));
				} else if (board[i][p] == 2 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P2));
				} else if (board[i][p] == 3 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P3));
				} else if (board[i][p] == 4 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P4));
				} else if (board[i][p] == 5 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P5));
				} else if (board[i][p] == 6 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P6));
				} else if (board[i][p] == 7 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P7));
				} else if (board[i][p] == 8 && shown[i][p] == true) {
					button[i][p].setGraphic(new ImageView(P8));
				}

				if (flagged[i][p] == true) {// if it is flagged
					button[i][p].setGraphic(new ImageView(flagimage));
					numofflags++;// calculate how many flags there are

				} else if (board[i][p] == 0 && shown[i][p] == true) {
					// checks if there are 0 bombs around and it is played
					button[i][p].setGraphic(new ImageView(played));
				}

			}
		}
		bombcount = numofbombs - numofflags;
		// for how many bombs are left(number in top right)
	}

	public static void play(int x, int y) {
		checkAround(x, y);// sees where it can look without going out of the
							// array
		calculate(x, y);// calculates that square's value
		boolean zerofound = true;
		while (zerofound) {
			zerofound = false;
			for (int i = 0; i < xsize; i++) {
				for (int p = 0; p < ysize; p++) {
					if (board[i][p] == 0 && shown[i][p] == false && flagged[i][p] == false) {
						// if there is a 0 found, it will make the while loop
						// run again (checking for any other zeros that appear)
						zerofound = true;
						shown[i][p] = true;
						// will prevent it from calculating that spot again (and
						// user can't play there)
						checkAround(i, p);
						// will check where it can check around that 0

						int up = checkup;
						int down = checkdown;
						int left = checkleft;
						int right = checkright;
						// stores where it can check

						for (int e = up + p; e <= down + p; e++) {
							for (int r = left + i; r <= right + i; r++) {
								checkAround(r, e);
								calculate(r, e);
								// will calculate all of the squares around that
								// zero
							}
						}
						print();// updates board
					}
				}
			}
		}
	}

	public static void checkAround(int x, int y) {
		// used to find out how many spots in each direction it can check
		// without going out of any array

		// default is 1 in each direction (3 by 3)
		checkleft = -1;
		checkright = 1;
		checkup = -1;
		checkdown = 1;

		if (firstturn == true) {
			// is going to clear a 5 by 5 around the first
			// played square of any bombs which makes it starting the game
			// easier
			checkleft = -2;
			checkright = 2;
			checkup = -2;
			checkdown = 2;
		}
		if (firstturn == true && x == 1) {
			// checks if it is the first turn and it is in the second column
			// (can only clear one to the left)
			checkleft = -1;
		}
		if (firstturn == true && x == xsize - 2) {
			// checks if it is the first turn and it is in the second last
			// column (can only clear one to the right)
			checkright = 1;
		}
		if (firstturn == true && y == 1) {
			// checks if it is the first turn and it is in the second row (can
			// only clear one above)
			checkup = -1;
		}
		if (firstturn == true && y == ysize - 2) {
			// checks if it is the first turn and it is in the second last row
			// (can only clear one below)
			checkdown = 1;
		}
		if (x == 0) {
			// checks if it is in the first column (can't clear any to the left)
			checkleft = 0;
		}
		if (x == xsize - 1) {
			// checks if it is in the last column (can't clear any to the right)
			checkright = 0;
		}
		if (y == 0) {
			// checks if it is in the top row (can't clear any above)
			checkup = 0;
		}
		if (y == ysize - 1) {
			// checks if it is in the bottom row (can't clear any below)
			checkdown = 0;
		}
	}

	public static void calculate(int x, int y) {
		checkAround(x, y);// first sees where it can check
		int squarevalue = 0;// counter for the value of the square
		for (int i = x + checkleft; i <= x + checkright; i++) {
			for (int p = y + checkup; p <= y + checkdown; p++) {
				// checks within the checkAround parameters
				if (bombs[i][p] == 1) {
					// if there is a bomb, the value increases
					squarevalue++;
				}
			}
		}
		board[x][y] = squarevalue;// sets that spot on the board to it's value
		if (squarevalue != 0) {
			// if it is a 0, it needs to display all around it (will be done
			// later)
			// but otherwise, it says it is already played in (can't be changed
			// by user)
			shown[x][y] = true;

		}
	}

	public static void setbomb() {
		// code for when there needs to be a bomb placed on the board
		// (could be when loading the game or on your first turn if you
		// click near a bomb)
		boolean notallowed = false;

		do {
			notallowed = false;
			int xbomb = (int) (Math.random() * xsize);
			int ybomb = (int) (Math.random() * ysize);
			// comes up with a random x and y coordinate to place the bomb in
			if (bombs[xbomb][ybomb] == 1 || firstturnarray[xbomb][ybomb] == true) {
				// checks if it is near where you clicked on your first turn or
				// it is already a bomb
				notallowed = true;
			} else {
				bombs[xbomb][ybomb] = 1;
				// sets that spot to a bomb
			}
		} while (notallowed);// by default, it runs once unless it can't place a
								// bomb where it tried to and it will try until
								// it works
	}

	public static void reset() {
		// done whenever you press the reset button
		score = "0.00";
		timelabel.setText("0.00");
		bombcountlabel.setText(Integer.toString(bombcount));
		endgamelabel.setText("");
		firstturn = true;
		lose = false;
		win = false;
		bombcount = numofbombs;
		// resets all of the labels and game variables
		for (int i = 0; i < xsize; i++) {
			for (int p = 0; p < ysize; p++) {
				// resets the board, bombs etc to default
				shown[i][p] = false;
				board[i][p] = -1;
				bombs[i][p] = 0;
				flagged[i][p] = false;
				firstturnarray[i][p] = false;
				button[i][p].setGraphic(new ImageView(unplayed));
				loseshown[i][p] = false;
			}
		}
		for (int i = 0; i < numofbombs; i++) {
			setbomb();
			// will place new bombs
		}
	}

	public static void gamesetup() {
		shown = new boolean[xsize][ysize];
		board = new int[xsize][ysize];
		bombs = new int[xsize][ysize];
		firstturnarray = new boolean[xsize][ysize];
		button = new Button[xsize][ysize];
		flagged = new boolean[xsize][ysize];
		loseshown = new boolean[xsize][ysize];
		// declares all of the arrays based on the size that you chose

		lose = false;// resets it if you left the game when it was true

		for (int a = 0; a < xsize; a++) {
			for (int b = 0; b < ysize; b++) {// loops through all spots on board

				board[a][b] = -1;// sets the board to value of -1 (if it is not
									// played)
			}
		}
		for (int i = 0; i < numofbombs; i++) {
			setbomb();
			// places new bombs
		}
	}

	public static void lose(int x, int y) {
		// for if you played where a bomb is
		endgamelabel.setText("YOU LOSE");
		lose = true;
		reset = true;
		// stops the game from running

		for (int o = 0; o < xsize; o++) {
			for (int l = 0; l < ysize; l++) {// loops
												// through
												// the
												// board
				if (flagged[o][l] == false && bombs[o][l] == 1 && loseshown[o][l] == false) {
					// if you did not flag a bomb and it is not one that was
					// clicked on
					button[o][l].setGraphic(new ImageView(missed));
					// changes how it looks
				}
				if (flagged[o][l] == true && bombs[o][l] == 0) {
					// if you flagged a spot
					// that is not a bomb
					button[o][l].setGraphic(new ImageView(mistake));
					// updates the graphic

				}

				shown[o][l] = true;
				// makes it so that you can't
				// play anywhere

			}
		}
		button[x][y].setGraphic(new ImageView(bombclicked));
		// changes the graphic for the bomb you
		// clicked on

	}

	public void gamescene() {
		// code for the game
		bombcount = numofbombs;// resets the bomb counter
		unplayed = new Image(getClass().getResource("unplayed.png").toExternalForm(), buttonsize - 2, buttonsize - 2,
				false, false);
		played = new Image(getClass().getResource("Played.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false,
				false);
		missed = new Image(getClass().getResource("bombshown.png").toExternalForm(), buttonsize - 2, buttonsize - 2,
				false, false);
		mistake = new Image(getClass().getResource("wrongbombs.png").toExternalForm(), buttonsize - 2, buttonsize - 2,
				false, false);
		bombclicked = new Image(getClass().getResource("clickedbomb.png").toExternalForm(), buttonsize - 2,
				buttonsize - 2, false, false);
		flagimage = new Image(getClass().getResource("Flag.png").toExternalForm(), buttonsize - 2, buttonsize - 2,
				false, false);
		P1 = new Image(getClass().getResource("1.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P2 = new Image(getClass().getResource("2.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P3 = new Image(getClass().getResource("3.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P4 = new Image(getClass().getResource("4.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P5 = new Image(getClass().getResource("5.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P6 = new Image(getClass().getResource("6.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P7 = new Image(getClass().getResource("7.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		P8 = new Image(getClass().getResource("8.png").toExternalForm(), buttonsize - 2, buttonsize - 2, false, false);
		// imports all of the images
		// images from https://commons.wikimedia.org/wiki/Category:Minesweeper
		// and
		// https://itunes.apple.com/us/app/minesweeper-puzzle-bomb/id306937222?mt=8

		AnimationTimer timer = new AnimationTimer() {// what constantly runs
			@Override
			public void handle(long now) {
				double time = 0.0;
				long elapsedTime = now - start;// how much time has gone by
												// after the game has started
				double seconds = elapsedTime / 1000000000.0;
				// how many seconds have gone by

				if (win == false && lose == false && reset == false) {// when
																		// the
																		// game
																		// is
																		// being
							
					// played
					time = (Math.round(seconds * 100.0)) / 100.0;// rounds it to
																	// 2 decimal
																	// places
					score = Double.toString(Math.min(time, 999.99));// makes it
																	// so that
																	// the max
																	// time
																	// would be
																	// 999.99
																	// (would
																	// stop
																	// updating
																	// after)
				}

				if (time >= 100) {
					timelabel.setTranslateX(xsize * buttonsize - 90);// if it
																		// goes
																		// to 5
																		// digits,
																		// it
																		// will
																		// shift
																		// it
																		// over
																		// a bit
																		// so it
																		// says
																		// on
																		// the
																		// screen
				}

				timelabel.setText(score);// updated the time label

			}
		};

		StackPane rootPane = new StackPane();
		GridPane grid = new GridPane();
		// uses a gridpane as the boxes are in a grid format

		Pane pane = new Pane();
		pane.getChildren().addAll(timelabel, bombcountlabel, endgamelabel, resetbutton, menu);
		// adds all the things that are at the top of the screen
		resetbutton.setFont(new Font("Impact", 15));
		menu.setFont(new Font("Impact", 15));
		timelabel.setFont(new Font("Impact", 30));
		bombcountlabel.setFont(new Font("Impact", 30));
		endgamelabel.setFont(new Font("Impact", 30));
		endgamelabel.setTranslateX(xsize * (buttonsize) / 2 - 55);
		menu.setTranslateX(xsize * (buttonsize) - 150);
		menu.setText("Menu");
		menu.setTranslateY(5);
		resetbutton.setTranslateX(90);
		resetbutton.setTranslateY(5);
		resetbutton.setText("Reset");
		timelabel.setTranslateX(xsize * buttonsize - 70);
		bombcountlabel.setTranslateX(20);
		timelabel.setText("0.00");
		bombcountlabel.setText(Integer.toString(bombcount));
		endgamelabel.setText("");
		// formats all of the buttons and labels at the top of the screen

		rootPane.getChildren().addAll(pane, grid);
		// adds both the top of the screen and the game grid to another pane
		grid.setTranslateY(40);
		// shifts the grid down so you can see the top part of the screen

		for (int i = 0; i < xsize; i++) {
			for (int p = 0; p < ysize; p++) {// loops through the whole board
				button[i][p] = new Button();// create a button for each spot
				// makes them all look unplayed
				button[i][p].setGraphic(new ImageView(unplayed));
				// sets the dimensions of the button to what was set
				button[i][p].setMinWidth(buttonsize);
				button[i][p].setPrefWidth(buttonsize);
				button[i][p].setMaxWidth(buttonsize);
				button[i][p].setMinHeight(buttonsize);
				button[i][p].setPrefHeight(buttonsize);
				button[i][p].setMaxHeight(buttonsize);
				grid.add(button[i][p], i, p);// adds the buttons to the grid
												// pane
			}
		}

		scene = new Scene(rootPane, xsize * (buttonsize), (ysize * buttonsize) + 40);
		// makes the scene with the size based on the button size and how many
		// there are
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		// goes to application.css and updates the format for the buttons
		window.setScene(scene);// makes it visible

		for (first = 0; first < xsize; first++) {
			for (second = 0; second < ysize; second++) {// loops through all of
														// the buttons

				int one = first;
				int two = second;// used for accessing which button it is on
									// inside of the event handler
				button[first][second].setOnMouseClicked(event -> {// checks if
																	// the
																	// button is
																	// pressed

					if (flagged[one][two] == true && reset == false) {
						// if the button is flagged and the game is being
						// played, it makes it so you can click on it
						shown[one][two] = false;
					}
					if (shown[one][two] == false) {// if you are allowed to
													// click on it
						if (event.getButton() == MouseButton.PRIMARY) {// if you
																		// left
																		// click

							if (firstturn == false) {// code for after your
														// first turn
								if (flagged[one][two] == false) {// if it is not
																	// flagged
									if (bombs[one][two] == 0) {// if there are
																// no bombs
																// where you
																// clicked
										// plays there
										play(one, two);
										// updates the screen
										print();
									} else {
										lose(one, two);
									}
								}
							} else {// code for on your first turn
								if (flagged[one][two] == false) {// if it is not
																	// flagged
									checkAround(one, two);// sees where it can
															// check
									int up = checkup;
									int down = checkdown;
									int left = checkleft;
									int right = checkright;
									// stores those values
									int bombsreplaced = 0;

									for (int e = up + two; e <= down + two; e++) {
										for (int r = left + one; r <= right + one; r++) {
											// loops through where the
											// checkAround said to for the first
											// turn
											firstturnarray[r][e] = true;
											// makes it so that bombs can't be
											// re placed in those squares
											if (bombs[r][e] == 1) {// if it is a
																	// bomb
												bombsreplaced++;// needs to add
																// another bomb
																// somewhere
																// else
												bombs[r][e] = 0;
												// sets it to an empty square
											}
										}
									}
									for (int i = 0; i < bombsreplaced; i++) {
										setbomb();
										// sets new spots for the bombs that
										// were taken out
									}
									firstturn = false;// no longer is it your
														// first turn
									// plays in the spot that you clicked
									play(one, two);
									// updates the board
									print();
									start = System.nanoTime();// checks the
																// current time
																// (used for
																// calculating
																// how long has
																// passed)
									reset = false;// no longer resetting the
													// board
									timer.start();// starts the timer
								}
							}
						} else if (event.getButton() == MouseButton.SECONDARY) {// if
																				// you
																				// right
																				// click

							if (flagged[one][two] == true) {// if it is already
															// flagged
								flagged[one][two] = false;// sets it to no
															// longer flagged
								button[one][two].setGraphic(new ImageView(unplayed));
								// sets the graphic to empty

							} else {// if it is not flagged
								flagged[one][two] = true;// flags the spot
								button[one][two].setGraphic(new ImageView(flagimage));
								// sets the graphic to a flag
							}

							print();
							// updates the board
							bombcountlabel.setText(Integer.toString(bombcount));
							// updates the bomb counter

						}
					} else if (board[one][two] > 0 && event.getButton() == MouseButton.PRIMARY) {
						// for if you click on a square that is shown
						int numofflags = 0;
						int lostx = 0;
						int losty = 0;
						boolean havelost = false;
						checkAround(one, two);// sees where it can check
						for (int i = one + checkleft; i <= one + checkright; i++) {
							for (int p = two + checkup; p <= two + checkdown; p++) {
								if (flagged[i][p] == true) {
									numofflags++;// calculates how many flags
													// there are around that one
													// square
								}
							}
						}
						if (numofflags == board[one][two]) {// if it is the same
															// as the square's
															// value

							int u = checkup;
							int d = checkdown;
							int l = checkleft;
							int r = checkright;
							// keeps track of where it will play
							for (int i = one + l; i <= one + r; i++) {
								for (int p = two + u; p <= two + d; p++) {// loops
																			// through
																			// those
																			// values
									if (bombs[i][p] == 0) {// if it is not a
															// bomb, it plays
															// there
										play(i, p);
									} else if (flagged[i][p] == false) {// otherwise
																		// you
																		// lose
										havelost = true;
										button[i][p].setGraphic(new ImageView(bombclicked));
										// sets the bombs that were "clicked" on
										// to their texture
										lostx = i;
										losty = p;
										loseshown[i][p] = true;// makes it so
																// that those
																// spots won't
																// be changed
									}

								}
							}
							if (havelost) {// if you have lost
								lose(lostx, losty);
							} else {// otherwise it updates the board
								print();
							}

						}
					}
					// will check if you win
					win = true;
					for (int b = 0; b < xsize; b++) {
						for (int c = 0; c < ysize; c++) {// loops through whole
															// board
							if (!(bombs[b][c] == 1 || board[b][c] > -1)) {// if
																			// it
																			// isn't
																			// a
																			// bomb
																			// or
																			// played
																			// in
								win = false;// you haven't won yet
							}
						}
					}
					if (win && lose == false && reset == false) {// if you have
																	// won
						endgamelabel.setText("YOU WIN");// updates label to say
														// you win
						bombcountlabel.setText("0");
						BufferedReader br = null;
						String scores = "";
						// initializes a buffered reader and scores string

						scoresList.clear();// clears the array list
						scoresList.add(Double.parseDouble(score));// adds the
																	// score you
																	// just got
																	// to the
																	// array
																	// list
						String output = difficulty;// sets the file to the
													// variable declared when
													// you selected the
													// difficulty
						File f = new File(output);
						try {
							if (f.exists() == false) {// if the file doesn't
														// exist
								PrintWriter writer = new PrintWriter(output);// makes
																				// a
																				// new
																				// file
								writer.close();// closes the file
							}

							br = new BufferedReader(new FileReader(difficulty));
							// makes a buffered reader for the file name
							String CurrentLine;// string for the score on that
												// line

							while ((CurrentLine = br.readLine()) != null) {// loops
																			// through
																			// the
																			// file
																			// line
																			// by
																			// line
								scoresList.add(Double.parseDouble(CurrentLine));// adds
																				// each
																				// line
																				// to
																				// the
																				// array
																				// list
																				// of
																				// scores
							}
							Collections.sort(scoresList);// orders the scores

							for (int k = 0; k < scoresList.size() && k < 10; k++) {
								// loops through the score list (max 10 scores
								// saved)

								scores += scoresList.get(k) + "\n";// adds the
																	// score to
																	// one
																	// string
							}
							BufferedWriter out = new BufferedWriter(new FileWriter(difficulty));
							// makes a buffered writer for the new score list
							out.write(scores);// changes the text to the new
												// list of scores
							out.close();// closes the file
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						reset = true;
						// currently reseting (game doesn't continue)
						bombcount = 0;
						// resets the bomb count
						for (int d = 0; d < xsize; d++) {
							for (int o = 0; o < ysize; o++) {// loops through
																// the whole
																// board
								shown[d][o] = true;// prevents you from changing
													// the board
								if (bombs[d][o] == 1) {// if it is a bomb
									flagged[d][o] = true;// adds a flag to it
									button[d][o].setGraphic(new ImageView(flagimage));

								}
							}
						}
					}
				});
			}
		}
		resetbutton.setOnMouseClicked(event -> {// if you click the reset button
			reset = true;
			reset();// runs reset code
			bombcountlabel.setText(Integer.toString(bombcount));
			// updates the bomb count
		});
		menu.setOnMouseClicked(event -> {// if you click menu
			firstturn = true;// will be your first turn when you come back
			window.setScene(titlescene);// changes it to the title screen
			timer.stop();// stops the animation timer
		});
	}

	public void leaderboards() {
		// when you go to the fastest times page
		Pane board = new Pane();

		for (int i = 0; i < 3; i++) {// once for each difficulty

			BufferedReader br = null;

			// defines the buffered reader
			String scores = "";
			// string for all of the scores
			String input;
			// what file it should look for

			scoresList.clear();
			// empties the arraylist of any saved scores
			switch (i) {// sets it to the corresponding difficulty's file name
			case 1:
				input = "EasyScore";
				break;
			case 2:
				input = "MediumScore";
				break;
			default:
				input = "HardScore";
				break;
			}

			File f = new File(input);
			try {
				if (f.exists() == false) {// if the file doesn't exist
					PrintWriter writer = new PrintWriter(input);// makes
																// a
																// new
																// file
					writer.close();// closes the file
				}

				br = new BufferedReader(new FileReader(input));
				// makes a buffered reader for that file

				String CurrentLine;
				// for storing what is on each line

				while ((CurrentLine = br.readLine()) != null) {// runs through
																// the file and
																// reads each
																// line
					scoresList.add(Double.parseDouble(CurrentLine));// adds each
																	// score to
																	// the
																	// arraylist
				}
				Collections.sort(scoresList);// orders the arraylist from best
												// times to worst.

				for (int k = 0; k < scoresList.size() && k < 10; k++) {
					// runs through the array list (max 10 makes it so it can
					// only show your top 10 scores)

					scores += scoresList.get(k) + "\n";
					// adds the score to the scores string
				}

				switch (i) {// sets the corresponding variable to the scores
				case 1:
					easy = scores;
					;
					break;
				case 2:
					medium = scores;
					break;
				default:
					hard = scores;
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Label title = new Label("Fastest Times");
		title.setFont(new Font("Impact", 30));
		title.setTranslateX(85);
		// title at top of screen

		easyTitle.setStyle(" -fx-background-color: yellow; -fx-border-color: black; -fx-border-width: 2px;");
		mediumTitle.setStyle("-fx-background-color: orange; -fx-border-color: black; -fx-border-width: 2px;");
		hardTitle.setStyle(" -fx-background-color: red; -fx-border-color: black; -fx-border-width: 2px;");
		easyTitle.setTranslateX(50);
		mediumTitle.setTranslateX(143);
		hardTitle.setTranslateX(250);
		easyTitle.setTranslateY(50);
		mediumTitle.setTranslateY(50);
		hardTitle.setTranslateY(50);
		// easy, medium and hard labels positioned and formatted

		topscoreseasy.setText(easy);
		topscoreseasy.setTranslateX(50);
		topscoreseasy.setTranslateY(80);
		topscoresmedium.setText(medium);
		topscoresmedium.setTranslateX(150);
		topscoresmedium.setTranslateY(80);
		topscoreshard.setText(hard);
		topscoreshard.setTranslateX(250);
		topscoreshard.setTranslateY(80);
		// sets the labels' texts to the corresponding strings

		Button home = new Button("Back");
		// to return to the home page
		home.setTranslateY(272);
		board.getChildren().addAll(topscoreseasy, topscoresmedium, topscoreshard, home, easyTitle, mediumTitle, title,
				hardTitle);

		leaderboard = new Scene(board, 340, 300);
		// makes it a new scene
		window.setScene(leaderboard);
		// sets it to viable
		home.setOnMouseClicked(event -> {// when you click home, it returns to
											// the title screen
			window.setScene(titlescene);
		});
	}

	public void instructions()
	// when you go to the instructions scene
	{
		Pane instructionspane = new Pane();

		// instructions for the game
		String instructionstext = "1. You are shown a board of squares. Behind some of the squares are mines (bombs) and \n"
				+ "behind others, there are not. If you click on a square containing a bomb, you lose, but if you manage to click \n"
				+ "all the squares without clicking any bombs, you win. \n \n"
				+ "2. If you click on a square that doesn’t have a bomb in it, it will show you a number representing how \n"
				+ "many bombs there are in the squares directly adjacent to the number (including diagonals). Use this along \n"
				+ "with some guesswork to avoid the bombs.\n \n"
				+ "3. To open a square, left click on it. To mark a square that you think is a bomb, right click on it. To remove a \n"
				+ "mark that you have placed on a square, right click the square again.\n \n"
				+ "4. If there is an open square that has the correct number of marked neighboring bombs, you can click on the\n"
				+ "open square to open all of the remaining unopened squares all at once. It will have no effect if the surrounding\n"
				+ "square are all opened or all flagged. If an incorrect neighbor is marked, it will cause you to lose instantly. \n\n"
				+ "5. The first square that you open will never be a bomb. \n\n"
				+ "6. Note: If you click a square with 0 bombs in the surrounding squares, all of the surrounding squares will \n"
				+ "automatically open which can lead to a large area opening.\n \n";

		// instructions for the home page
		String homeinstructionstext = "1. Press Easy, Medium or Hard to select your difficulty (default is Easy) and then press Play to start \n\n"
				+ "2. Press the podium icon to see your fastest times";

		// button to return to the home page
		Button back = new Button("Back");
		back.setTranslateY(443);

		Label instructionslabel = new Label(instructionstext);
		Label instructionstitle = new Label("Game Instructions");
		Label homeinstructionslabel = new Label(homeinstructionstext);
		Label homeinstructionstitle = new Label("Home Screen Instructions");
		instructionstitle.setFont(new Font("Impact", 15));
		homeinstructionstitle.setFont(new Font("Impact", 15));
		instructionstitle.setTranslateX(295);
		instructionstitle.setTranslateY(100);
		instructionslabel.setTranslateY(120);
		homeinstructionstitle.setTranslateX(275);
		homeinstructionslabel.setTranslateY(20);
		// adds the text to the labels and formats them on the screen

		instructionspane.getChildren().addAll(instructionslabel, instructionstitle, homeinstructionslabel,
				homeinstructionstitle, back);
		// adds all the elements to the pane
		instructions = new Scene(instructionspane, 700, 470);
		// makes the scene
		window.setScene(instructions);
		// makes it visible
		back.setOnMouseClicked(event -> {
			window.setScene(titlescene);
		});// returns to the home screen
	}
}
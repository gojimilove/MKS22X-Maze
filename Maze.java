import java.util.*;
import java.io.*;

public class Maze{
  private char[][]maze;
  private boolean animate;//false by default

  /*Constructor loads a maze text file, and sets animate to false by default.

    1. The file contains a rectangular ascii maze, made with the following 4 characters:
    '#' - Walls - locations that cannot be moved onto
    ' ' - Empty Space - locations that can be moved onto
    'E' - the location of the goal (exactly 1 per file)
    'S' - the location of the start(exactly 1 per file)

    2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

    3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
       throw a FileNotFoundException or IllegalStateException
  */
  public Maze(String filename) throws FileNotFoundException{
    File f = new File(filename);
    Scanner n = new Scanner(f);
    String test = n.nextLine();
    int cols = test.length();
    int rows = 1;
    while (n.hasNextLine()) {
      String line = n.nextLine();
      rows++;
    }
    maze = new char[rows][cols];

    Scanner s = new Scanner(f);
    int r = 0;
    while (s.hasNextLine()) {
      String line = s.nextLine();
      for (int i = 0; i < line.length(); i++) {
        maze[r][i] = line.charAt(i);
      }
      r++;
    }
    animate = false;
  }

  public String toString() {
    String s = "";
    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[0].length; j++) {
        s += maze[i][j];
      }
      s += "\n";
    }
    return s;
  }

  private void wait(int millis){
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
    }
  }

  public void setAnimate(boolean b){
    animate = b;
  }

  public void clearTerminal(){
    //erase terminal, go to top left of screen.
    System.out.println("\033[2J\033[1;1H");
  }

  public boolean putCursor(int r, int c) {
  	if (maze[r][c] != ' ') return false;
  	else maze[r][c] = '@';
  	return true;
  }

  public boolean blockSpace(int r, int c) {
  	if (maze[r][c] != '@') return false;
  	else maze[r][c] = '.';
  	return true;
  }

  /*Wrapper Solve Function returns the helper function
    Note the helper function has the same name, but different parameters.
    Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
  */
  public int solve(){
    //find the location of the S.
    int sRow = 0;
    int sCol = 0;
  	for (int i = 0; i < maze.length; i++) {
  		for (int j = 0; j < maze[i].length; j++) {
  			if (maze[i][j] == 'S') {
  				sRow = i;
  				sCol = j;
  			}
  		}
  	}
  	System.out.println("S: ["+sRow+", "+sCol+"]");
    //erase the S
    maze[sRow][sCol] = '@';
    //and start solving at the location of the s.
    return solve(sRow,sCol);
    //return -1;
  }

  /*
  Recursive Solve function:

  A solved maze has a path marked with '@' from S to E.

  Returns the number of @ symbols from S to E when the maze is solved,
  Returns -1 when the maze has no solution.

  Postcondition:
    The S is replaced with '@' but the 'E' is not.
    All visited spots that were not part of the solution are changed to '.'
    All visited spots that are part of the solution are changed to '@'
  */
  private int solve(int row, int col){ //you can add more parameters since this is private
  	int[] rowMoves = new int[]{-1, 0, 1, 0};
  	int[] colMoves = new int[]{0, 1, 0, -1};

    if (maze[row][col] == 'E') {
      System.out.println("STOP");
      return 1;
    }

    //automatic animation! You are welcome.
    if(animate){
      //clearTerminal();
      System.out.println(this);
      wait(20);
    }
    for (int i = 0; i < 4; i++) {
    	if (putCursor(row+rowMoves[i], col+colMoves[i])) {
        //System.out.println("yay");
        solve(row+rowMoves[i],col+colMoves[i]);
      }
      // else {
      //   System.out.println("nay");
        
      // }
    }
    blockSpace(row,col);


    //COMPLETE SOLVE
    return -1; //so it compiles
  }

}

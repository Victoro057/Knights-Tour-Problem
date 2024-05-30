import java.util.ArrayList;

public class KnightsTourBruteForce {
    int moveCount = 0;//keeps track of the current move
    int rowPosition; //keeps track of the row value of the knight position
    int colPosition; //keeps track of the col value of the knight position
    /**
     * the board is an array of ints where elements are 0 if the
     * knight has not visited or it will hold an int value equal
     * the number of moves that it too to reach that element. E.g.
     * the square that the knight started should have 1 as it was move
     * 1.  The thrid square visited would have 3 as it took three moves
     * to get there.
     */
    int[][] board;//the chess board

    //used to initialize the board and can be used for move validation
    final int NUM_ROWS = 8;
    final int NUM_COLS = 8;
    /**
     * the SHIFT arrays are used in parallel.  That is to say that
     * elements with the same index are meant to be used together.
     * Move 0 is two down and one to the right where move 3 is
     * two up and 1 left.
     */
    final int[] H_SHIFT = {2, 1, -1, -2, -2, -1, 1, 2}; // These are the available HORIZONTAL points where the knight is able to move to
    final int[] V_SHIFT = {1, 2, 2, 1, -1, -2, -2, -1}; // These are the available VERTICAL points where the knight is able to move to

    public KnightsTourBruteForce(int rowStart, int colStart){
        //initialize the board
        //place the knight at (rowStart, colStart)
        //start tour
        //evaluate the result
        board = new int[NUM_ROWS][NUM_COLS];
        rowPosition = rowStart;
        colPosition = colStart;
        moveCount++;
        board[rowStart][colStart] = 1;  // This sets the point where the user chose to place the knight to 1
    }
    public boolean runTour(){   // This method runs the tour
        //continue to make moves while there are available moves
        //return true if all cells have been visited.
        resetBoard();   //resets the board every time a new tour is ran
        boolean fullTour = true;
        while(fullTour && moveCount < 64){
            fullTour = makeMove();
        }
        //printBoard();   //print board after the tour ran
        //System.out.println("Number of moves made: " + moveCount);
        return fullTour;
    }
    //return a list of all available moves from the knight's
    //current position
    public ArrayList<Integer> getAvailableMoves(){  // This method get the available moves there are
        //make the list
        ArrayList<Integer> moves = new ArrayList<>();
        for(int i = 0; i < H_SHIFT.length; i++){    // This simulates if the knight will move toward the moves that it can go
            int row = rowPosition + V_SHIFT[i];
            int col = colPosition + H_SHIFT[i];
            if(validMove(row, col)){    // This makes sure that the knight doen't move off the board, only to the value that will still keep the knight on the board
                moves.add(i);
            }
        }
        //traverse all moves by adding shifts to rows and cols
        //check validity
        //if valid, add the move number to the list

        return moves;
    }

    //pick a random move from the moves list
    //return -1 if there are no moves
    public int pickMove(ArrayList<Integer> moves){  // This method pick a random move from the moves available from the getAvailableMoves array list "moves"
        int rand = (int)(Math.random() * moves.size());
        if(moves.size() > 0){
            return moves.get(rand);
        } else {
            return -1;
        }
    }
    //move number is the value used to access the H_SHIFT and V_SHIFT
    //the move number will shift the knight position vertically
    //and horizontally to make a knight move
    public boolean makeMove(){
        //get available moves
        ArrayList<Integer> moves = getAvailableMoves();
        //pick move, if any moves available do the following
        int move = pickMove(moves);
        //update move count
        boolean moveMade = move >= 0;
        if(move >= 0){
            moveCount++;
            //update the rowPosition and colPosition variables
            rowPosition = V_SHIFT[move] + rowPosition;
            colPosition = H_SHIFT[move] + colPosition;
            //place piece, if the method returns true return true
            moveMade = placePiece(rowPosition, colPosition);
            return moveMade;

        }
        return moveMade;
    }

    public boolean placePiece(int row, int col){    // This method sets the position of the knight on the board
        if(validMove(row, col)){
            board[row][col] = moveCount;    // sets the position by changing that position from 0 to the number move that is made to the position
            return true;
        }
        return false;
    }

    public boolean validMove(int row, int col){
        //return true if the (row, col) element is on the board
        //and not visited,
        boolean topCheck = row >= 0;
        boolean bottomCheck = row < board.length;
        boolean leftCheck = col >= 0;
        boolean rightCheck = false;
        if(bottomCheck && topCheck){
            rightCheck = col < board[row].length;
        }

        return topCheck && bottomCheck && leftCheck && rightCheck && board[row][col] == 0;
        // What is short circuiting?




    }

    //set all values in the board array to 0
    public void resetBoard(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = 0;
            }
        }
        moveCount = 0;
    }

    //print the board array as a grid.
    public void printBoard(){
        for(int[] row: board){
            for(int col: row){
                if(moveCount < 10)
                    System.out.print(col + "  ");
                else{
                    System.out.print(col + " ");
                }
            }
            System.out.println();
        }

        // for(int[] row: board){
        //     System.out.println(Arrays.toString(row));
        // }
    }
    public int getMoveCount(){
        return moveCount;
    }
}

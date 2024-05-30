import java.util.ArrayList;

public class HeuristicTour {
    // Heuristic is basically problem solving plan



        int moveCount = 0;//keeps track of the current move
        int rowPosition; //keeps track of the row value of the knight position
        int colPosition; //keeps track of the col value of the knight position
        /**
         * the board is an array of ints where elements are 0 if the
         * knight has not visited or it will hold an int value equal
         * the number of moves that it too to reach that element. E.g.
         * the square that the knight started should have 1 as it was move
         * 1.  The third square visited would have 3 as it took three moves
         * to get there.
         */

        int[][] access = {
                {2, 3, 4, 4, 4, 4, 3, 2},
                {3, 4, 6, 6, 6, 6, 4, 3},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {3, 4, 6, 6, 6, 6, 4, 3},
                {2, 3, 4, 4, 4, 4, 3, 2}
        };
        int[][] accessReset = {
                {2, 3, 4, 4, 4, 4, 3, 2},
                {3, 4, 6, 6, 6, 6, 4, 3},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {4, 6, 8, 8, 8, 8, 6, 4},
                {3, 4, 6, 6, 6, 6, 4, 3},
                {2, 3, 4, 4, 4, 4, 3, 2}
        };


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


        final int[] H_SHIFT = {2, 1, -1, -2, -2, -1, 1, 2};
        final int[] V_SHIFT = {1, 2, 2, 1, -1, -2, -2, -1};

        public HeuristicTour(int rowStart, int colStart){
            //initialize the board
            //place the knight at (rowStart, colStart)

            //start tour
            //evaluate the result
            board = new int[NUM_ROWS][NUM_COLS];    // Initialize the board
            rowPosition = rowStart;
            colPosition = colStart;

            //moveCount++;
            runTour(rowStart, colStart);    // This runs the runTour method and passes down the coordinates where the user chose to place the knight
            //placePiece(rowPosition, colPosition);   // Placing the piece at the position that the user entered

        }

        public boolean runTour(int rowStart, int colStart){
            //continue to make moves while there are available moves
            //return true if all cells have been visited.


            resetBoard();   //Resets the board
            resetAccess();     // Resets the access board

            board[rowStart][colStart] = 1;  // This should set the place where the user puts the knight to 1
            moveCount++;    // Add 1 because for the program we need to consider placing a knight on board as a move so that it doesn't go to that place again
            //moveCount = 0;  // sets the move counter to 0 so the first move will actually be 1


            boolean fullTour = true;
            while(fullTour && moveCount < 64){  //repeat until fullTour is true and moveCount is 63 (counting from 0-63 will equal to 64)
                updateAccess();
                fullTour = makeMove();
                printBoard();
                System.out.println(); //Prints a new line for the result to be more readable and distinct from other moves
            }
            return fullTour;
        }
        //return a list of all available moves from the knight's
        //current position
        public ArrayList<Integer> getAvailableMoves(){
            //make the list
            ArrayList<Integer> moves = new ArrayList<>();
            for(int i = 0; i < H_SHIFT.length; i++){
                int row = rowPosition + V_SHIFT[i];
                int col = colPosition + H_SHIFT[i];
                if(validMove(row, col)){
                    moves.add(i);

                }
            }
            //traverse all moves by adding shifts to rows and cols
            //check validity
            //if valid, add the move number to the list

            return moves;
        }
        //return -1 if there are no moves
        public int pickMove(ArrayList<Integer> moves){
            if(moves.size() > 0){
                return bestMove(moves);  //if there is at least one move, we run the bestMove
            } else {
                System.out.println("No more moves left");
                return -1;     //If -1 returns that means that there is no moves more left. So the tour should end

            }
        }

        public int bestMove(ArrayList<Integer> moves){  //This method will decide what will be the best next move to make

            //My pseudocode
            //Get the available moves from the point where the piece currently stands
            //Compare those points by their accessibility number
                // Pick and return the place with the lowest access number.

            //Professors pseudocode
            // Initialize a value for the lowest access value
            // Initialize a variable for the move number with the lowest access value
            int lowAccess = Integer.MAX_VALUE;
            int lowMove = -1;
            //traverse moves list
            for (int i = 0; i < moves.size(); i++){
                int row = rowPosition + V_SHIFT[moves.get(i)];  //What were doing here is making a hypothetical move
                int col = colPosition + H_SHIFT[moves.get(i)];
                int accessVal = access[row][col];
                if(accessVal < lowAccess){  //If we found a lower access value than the current one, we assign it as the current lowest value
                    lowAccess = accessVal;
                    lowMove = moves.get(i);
                }
            }
            //for each move number
                //get the V-Shift and add it to the rowPosition
                //get the H-Shift and add it to the colPosition
                //Store the element on the access array at the position calculat
                //if the access element ran for the current move is lower than the
                    //store the access value in lowest access
                    //store  the move number in lowest access
            return lowMove;
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

                System.out.println("The moveMade method ran");
                System.out.println("The number of moves made is:" + moveCount);
                return moveMade;
            }
            return moveMade;
        }

        public boolean placePiece(int row, int col){ //method takes in the row and column of the knight
            if(validMove(row, col)){
                board[row][col] = moveCount;
                return true;
            }
            return false;
        }

        public boolean validMove(int row, int col){
            //return true if the (row, col) element is on the board
            //and not visited,
            boolean topCheck = row >= 0;
            boolean bottomCheck = row < board.length;
            boolean leftCheck= col >= 0;
            boolean rightCheck = false;
            if(bottomCheck && topCheck){
                rightCheck = col < board[row].length;
            }

            return topCheck && bottomCheck && leftCheck && rightCheck && board[row][col] == 0;
            // What is short circuiting?

        }

        public void updateAccess(){    // This method should be used only after the first move is made and after every move to update the board.
            ArrayList<Integer> moves = getAvailableMoves();
            //traversal over the moves list
            for(int m: moves){
                int row = H_SHIFT[m] + colPosition;
                int col = V_SHIFT[m] + rowPosition;
                access[row][col]--; //reduces the access number in the access array
            }

        }
        public void resetAccess(){      //This method is used to reset the access board at the beginning of each tour
            for(int i = 0; i < access.length; i++){
                for (int j = 0; j < access[i].length; j++){
                    access[i][j] = accessReset[i][j];
                }
            }
        }

        //set all values in the board array to 0
        public void resetBoard(){       // This method
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

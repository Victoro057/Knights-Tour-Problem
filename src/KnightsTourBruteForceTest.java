import java.util.Scanner;

public class KnightsTourBruteForceTest {
    public static void main(String[] args) {
        int toursNum = 0;
        int numOfToursSet = 100000000;
        int[] tourResults = new int[numOfToursSet];  //creating an array that will store the result of the max number of moves made in the 1000 knight tours
        int bestResult = 0;
        boolean fullTourMade;

        KnightsTourBruteForce tour = new KnightsTourBruteForce(0,0);

        for (int i = 0; i < numOfToursSet; i++){ //loop runs 1000 times
            toursNum++;
            //System.out.println("Tour number " + tourNum);
            tour.runTour();
            tourResults[i] = tour.getMoveCount();
            if(tourResults[i] > bestResult){    // This statement determines if the result of the tour that just ran is higher that the previous best score, and if it is, it is now the bestResult
                bestResult = tourResults[i];
            }
            if (tour.getMoveCount() == 64){ //If a full tour has been completed, get out of the for loop
                break;
            }
        }



        Scanner input = new Scanner(System.in);
        int in;

        System.out.println("Move number " + toursNum + " got " + bestResult);
        System.out.println("Do you wish to continue? 1 or 0");
        in = input.nextInt();

        if(in == 1) {
            System.out.println("Ran the knight's tour for " + numOfToursSet + " times.");
            System.out.println("Results in a tabular format below:\n");
            System.out.printf("%s     %s%n", "Tour #", "Moves made");
            for (int counter = 0; counter < toursNum; counter++) {
                System.out.printf("%6d%15d%n", (counter + 1), tourResults[counter]);
            }
            System.out.println("The best result out of all " + toursNum + " tours, is " + bestResult);

            if (tour.getMoveCount() == 64) {
                System.out.println("We've got a full tour!!!");
                System.out.println("The board looks like this: ");
                tour.printBoard();
                System.out.println();

            }
        }

    }

}

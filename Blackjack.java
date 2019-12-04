import java.util.Scanner;

public class Blackjack {
    public static void main(String args[]) {
        Scanner myScanner = new Scanner(System.in);
        P1Random range = new P1Random();
        boolean program = true;
        int gameCount = 1;
        int playerWs = 0;
        int dealerWs = 0;
        int ties = 0;

        while (program){ //Loops the game to begin a new game
            int handCount = 0;
            int dealer;
            boolean continueGame = true;
            System.out.println("START GAME #" + gameCount + "\n");
            int card = range.nextInt(13) + 1;

            if(card == 1) { //assigns playing card value to numerical value randomly dealt by the dealer
                System.out.println("Your card is a ACE!");
                handCount += 1;
            } else if(card == 11) {
                System.out.println("Your card is a JACK!");
                handCount += 10;
            } else if(card == 12) {
                System.out.println("Your card is a QUEEN!");
                handCount += 10;
            } else if(card == 13) {
                System.out.println("Your card is a KING!");
                handCount += 10;
            } else {
                System.out.println("Your card is a " + card + "!");
                handCount += card;
            }
            System.out.println("Your hand is: " + handCount + "\n");

            while(continueGame){ //secondary while loop necessary for the menu to loop after the input of the play
                System.out.println("1. Get another card \n" +
                        "2. Hold hand \n" +
                        "3. Print statistics\n" +
                        "4. Exit \n");
                System.out.println("Choose an option:");
                int menuSelection = myScanner.nextInt();

                switch(menuSelection){ // performs action selected by the player: get another card, hold hand, print
                                       // stats or end game
                    case 1:
                        card = range.nextInt(13) + 1;
                        if(card == 1) {
                            System.out.println("Your card is a ACE!");
                            handCount += 1;
                        } else if(card == 11) {
                            System.out.println("Your card is a JACK!");
                            handCount += 10;
                        } else if(card == 12) {
                            System.out.println("Your card is a QUEEN!");
                            handCount += 10;
                        } else if(card == 13) {
                            System.out.println("Your card is a KING!");
                            handCount += 10;
                        } else {
                            System.out.println("Your card is a " + card + "!");
                            handCount += card;
                        }

                        System.out.println("Your hand is: " + handCount);
                        System.out.println();

                        if(handCount == 21) {
                            System.out.println("BLACKJACK! You win!");
                            System.out.println();
                            playerWs++;
                            continueGame = false;
                        } else if(handCount > 21) {
                            System.out.println("You exceeded 21! You lose.");
                            System.out.println();
                            dealerWs++;
                            continueGame = false;
                        }
                        break;
                    case 2:
                        dealer = range.nextInt(11) + 16;

                        System.out.println("Dealer's hand: " + dealer);
                        System.out.println("Your hand is: " + handCount + "\n");

                        if(dealer > 21) {
                            System.out.println("You win!" + "\n");
                            playerWs++;
                        } else if(dealer == handCount) {
                            System.out.println("It's a tie! No one wins!" + "\n");
                            ties++;
                        } else if(dealer > handCount) {
                            System.out.println("Dealer wins!" + "\n");
                            dealerWs++;
                        } else {
                            System.out.println("You win!" + "\n");
                            playerWs++;
                        }
                        continueGame = false;
                        break;
                    case 3:
                        double playerWinPercent = ( (double)playerWs / (gameCount - 1) ) * 100;

                        System.out.println("Number of Player wins: " + playerWs);
                        System.out.println("Number of Dealer wins: " + dealerWs);
                        System.out.println("Number of tie games: " + ties);
                        System.out.println("Total # of games played is: " + (gameCount - 1));
                        System.out.println("Percentage of Player wins: " + playerWinPercent + "%");
                        System.out.println();

                        break;
                    case 4:
                        continueGame = false;
                        program = false;
                        break;
                    default:
                        System.out.println("Invalid input!");
                        System.out.println("Please enter an integer value between 1 and 4. \n");
                }
            }
            gameCount++; // increases the game count after a loop is completed
        }
    }
}

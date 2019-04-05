package com.becksm64.pitch;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
	
	//This should all be bundled into a Game class instead of being done in main with static variables and methods
	public static String trump;
	public static String currentSuit;
	public static int playedBestCard;
	public static Card bestCardPlayed;
	public static boolean isRoundOver;
	public static int currentPlayerIndex;
	public static int highestBid;
	public static int highestBidder;
	public static int currentDealer;
	public static boolean isGameOver;
	public static boolean isStartOfRound;
	public static int currentBidder;
	public static Scanner sc;
	public static Deck deck;
	public static CardCollection cardPile;
	public static List<Player> playerList;
	
	public static void main(String[] args) {
		
		trump = null;//Trump starts as null
		currentSuit = null;//Current suit also starts as null
		isRoundOver = false;//First round set to not over by default
		currentPlayerIndex = 0;
		highestBid = 0;
		highestBidder = -1;//Start at -1 to show that nobody has bid yet
		currentDealer = 0;
		currentBidder = 0;
		isStartOfRound = true;
		
		sc = new Scanner(System.in);//Scanner for input
		deck = new Deck();//The deck of cards
		cardPile = new CardCollection();//Pile of cards where players will play their cards
		playerList = new ArrayList<>();//List of players, all humans for now
		
		//Add the players to the list (Three players for testing)
		for(int k = 0; k < 3; k++) {
			playerList.add(new Player());
		}
		
		//Loop until game is over
		while(!isGameOver) {
		
			//Loop until round is over. Should start new round when round is over until game is over
			while(!isRoundOver) {
				
				if(isStartOfRound) {
					
					deck = new Deck();//Reset deck
					deck.shuffle();//shuffle deck
					dealPlayerHands();//Deal hands
					setPlayerBids();//Get the player bids at the start of the round
				}
				
				//Loop through players
				for(int i = 0; i < playerList.size(); i++) {
					
					//Show what trump is
					System.out.println("Trump: " + trump);
					System.out.println("Current Suit: " + currentSuit);
					
					//Show the player's hand
					System.out.println("Player " + currentPlayerIndex + "'s hand: ");
					playerList.get(currentPlayerIndex).showPlayerHand();
					
					//Show the cards the player is able to play
					System.out.println("\nCards Player " + currentPlayerIndex + " can play:");
					playerList.get(currentPlayerIndex).showPlayableCards(trump, currentSuit, cardPile.size());
					
					//Take user input to determine which card player will play
					System.out.print("\nPick a card to play: ");
					int cardIndex = Integer.parseInt(sc.nextLine());//convert input into integer. Scanner sucks, it's just easier this way
					
					Card cardPlayed = playerList.get(currentPlayerIndex).playCard(cardIndex);//Play a specified card, in this case the first card in the player's hand
					System.out.println("\nPlayer " + currentPlayerIndex + " played a card");
					
					//Set suit as trump if trump is null
					if(trump == null) {
						trump = cardPlayed.getSuit();
					}
						
					//Set suit as current suit if currentSuit is null
					if(currentSuit == null) {
						currentSuit = cardPlayed.getSuit();
						System.out.println("Current suit was set to: " + currentSuit);
					}
					
					//Check if the card that was just played is the best card
					if(cardPile.isBestCard(cardPlayed, trump, currentSuit)) {
						bestCardPlayed = cardPlayed;
						playedBestCard = currentPlayerIndex;
					}
					
					cardPile.addToPile(cardPlayed);//Add that card to the pile, that is, play the card
					System.out.print('\n');
					
					//Show the best card played
					if(bestCardPlayed != null) {
						System.out.println(playedBestCard + " played the best card, the " + bestCardPlayed.getName());
					}
					
					//Check if at the end of player list (test. Think this works but requires more testing)
					if((currentPlayerIndex + 1) == playerList.size()) {
						currentPlayerIndex = 0;
					} else {
						currentPlayerIndex++;
					}
				}
				
				currentPlayerIndex = playedBestCard;//If won that play, they go first on next
				
				//Reset current suit
				System.out.println("Hand over. " + playedBestCard + " won this hand");//Show who won round
				
				//Put pile in player's collection of cards they won. Can use size of player list because that will equal number of cards played in a round
				for(int i = 0; i < playerList.size(); i++) {
					playerList.get(playedBestCard).addToCardsWon(cardPile.removeCard());
				}
				
				//Reset the best card played and the current suit
				bestCardPlayed = null;
				currentSuit = null;
				
				//Check if all player's hands are empty and end the round if they are
				for(int i = 0; i < playerList.size(); i++) {
					if(playerList.get(i).isHandEmpty()) {
						isRoundOver = true;
					} else {
						isRoundOver = false;
					}
				}
			}
			
			//End of the round. Tally scores and stuff
			System.out.println("This round is over! Calculating score:");
			
			//Figure out score for each player and display scores
			calculatePlayerScores();
			displayPlayerScores();
			
			//Show the cards that each player won
			for(int i = 0; i < playerList.size(); i++) {
				System.out.println("\nCards Player " + i + " won: ");
				playerList.get(i).getWonCards().showCardsInPile();
				System.out.println("\n");
			}
			
			//Check if any player has reached 21
			
			//Change the dealer
			if((currentDealer + 1) == playerList.size()) {
				currentDealer = 0;
			} else {
				currentDealer++;
			}
			
			//Change the current bidder
			currentBidder = currentDealer;
			highestBid = 0;
			highestBidder = -1;
			
			//Reset player bids
			resetPlayerBids();
			
			//Empty players' won cards, reset deck, and shuffle. Considering making method to simply reset state. Methods are meant for encapsulating logic, duh
			resetPlayerCardsWon();
			
			//Start the next round
			isRoundOver = false;
			isStartOfRound = true;
		}
		sc.close();//Close scanner, no more input
	}
	
	/* Reset all player bids */
	public static void resetPlayerBids() {
		
		for(int i = 0; i < playerList.size(); i++) {
			playerList.get(i).setBid(0);
		}
	}
	
	/* Reset all player cards won in round */
	public static void resetPlayerCardsWon() {
		
		for(int i = 0; i < playerList.size(); i++) {
			playerList.get(i).resetWonCards();
		}
	}
	
	/* Finds the player with the lowest trump card so they can be awarded a point */
	public static int findLowestTrumpPlayer() {
		
		int hadLowestTrump = -1;
		Card lowestTrump = null;
		for(int i = 0; i < playerList.size(); i++) {
			
			Player currentPlayer = playerList.get(i);
			Card playersLowestTrump = currentPlayer.getWonCards().getLowestTrump(trump);//This could be null if had no trump, check for it
			
			if(playersLowestTrump != null) {
				
				if(lowestTrump == null || playersLowestTrump.getValue() < lowestTrump.getValue()) {
					lowestTrump = playersLowestTrump;
					hadLowestTrump = i;
				}
			}
		} return hadLowestTrump;
	}
	
	/* Finds the player with the highest trump card so they can be awarded a point */
	public static int findHighestTrumpPlayer() {
		
		int hadHighestTrump = -1;
		Card highestTrump = null;
		for(int i = 0; i < playerList.size(); i++) {
			
			Player currentPlayer = playerList.get(i);
			Card playersHighestTrump = currentPlayer.getWonCards().getHighestTrump(trump);//This could be null if had no trump, check for it
			
			if(playersHighestTrump != null) {
				
				if(highestTrump == null || playersHighestTrump.getValue() > highestTrump.getValue()) {
					highestTrump = playersHighestTrump;
					hadHighestTrump = i;
				}
			}
		} return hadHighestTrump;
	}
	
	/* Finds the player with the highest number of points towards game point */
	public static int findHighestGamePointPlayer() {
		
		int hadHighestGameScore = -1;
		int highestGameScore = 0;
		for(int i = 0; i < playerList.size(); i++) {
			
			Player currentPlayer = playerList.get(i);
			int playersGameScore = currentPlayer.getWonCards().getGamePointScore();//Player's game point score
			
			if(playersGameScore > 0) {
				
				if(highestGameScore == 0 || playersGameScore > highestGameScore) {
					highestGameScore = playersGameScore;
					hadHighestGameScore = i;
				}
			}
		} return hadHighestGameScore;
	}
	
	/* Calculates the scores of all the players and assigns their scores. Only works for find jack point and lowest trump right now */
	public static void calculatePlayerScores() {
		
		//Get player index who had lowest trump
		int hadLowestTrump = findLowestTrumpPlayer();
		int hadHighestTrump = findHighestTrumpPlayer();
		int hadGamePoint = findHighestGamePointPlayer();
		
		//Loop through player list to calculate score for each individual player
		for(int i = 0; i < playerList.size(); i++) {
			
			Player currentPlayer = playerList.get(i);
			int pointsToAward = 0;
			int playerBid = currentPlayer.getBid();
			
			//See if player was lowest trump holder
			if(hadLowestTrump == i) {
				pointsToAward += 1;
			}
			
			//See if player was highest trump holder
			if(hadHighestTrump == i) {
				pointsToAward += 1;
			}
			
			//See if this player had the jack
			if(currentPlayer.getWonCards().hasTrumpJack(trump)) {
				pointsToAward += 1;
			}
			
			//See if this player got game point
			if(hadGamePoint == i) {
				pointsToAward += 1;
			}
			
			//Check to make sure player met their bid before awarding points
			if(pointsToAward >= playerBid) {
				currentPlayer.addToScore(pointsToAward);
			} else {
				currentPlayer.addToScore(playerBid * -1);//Subtract bid from player score
			}
		}
	}
	
	public static void setPlayerBids() {
		
		//Have players place bids at beginning of the round
		for(int j = 0; j < playerList.size(); j++) {
			
			//Get the current player
			Player currentPlayer = playerList.get(currentBidder);
			
			//Stop bidding if there is a bid of 4
			if(highestBid == 4) {
				break;
			}
			
			//Player currentPlayer = playerList.get(j);
			System.out.println("Player " + currentBidder + ", make your bid: ");
			int currentPlayerBid = Integer.parseInt(sc.nextLine());//Get the player's bid as an int
			
			//Make sure bid is higher than current bid, or 0
			while(currentPlayerBid <= highestBid && currentPlayerBid != 0) {
				System.out.println("Bid must be higher than previous bid");
				currentPlayerBid = Integer.parseInt(sc.nextLine());
			}
			
			if(currentPlayerBid > highestBid) {
				highestBid = currentPlayerBid;
				highestBidder = currentBidder;
				currentPlayerIndex = highestBidder;//Make the highest bidder go first
				currentPlayer.setBid(highestBid);//Set the player's bid
			}
			
			//Check if at the end of player list
			if((currentBidder + 1) == playerList.size()) {
				currentBidder = 0;
			} else {
				currentBidder++;
			}
		}
		
		//Check if there were no bids
		boolean noBids = true;
		for(int i = 0; i < playerList.size(); i++) {
			if(playerList.get(i).getBid() > 0) {
				noBids = false;
			}
		}
		
		//If nobody bid, make dealer bid 2
		if(noBids) {
			System.out.println("Everyone passed. Dealer must bid 2");
			highestBid = 2;
			highestBidder = currentDealer;
			currentPlayerIndex = highestBidder;
			playerList.get(currentDealer).setBid(highestBid);
		}
		
		//Make sure only the highest bidder has a bid set
		for(int i = 0; i < playerList.size(); i++) {
			if(i != highestBidder) {
				playerList.get(i).setBid(0);
			}
		}
		
		//System.out.println("The highest bid is " + highestBid + " by Player " + highestBidder);//Display highest bidder and bid
		for(int i = 0; i < playerList.size(); i++) {
			System.out.println("Player " + i + " bid: " + playerList.get(i).getBid());
		}
		isStartOfRound = false;//Round has started after bids have been placed
	}
	
	/* Displays each player's score */
	public static void displayPlayerScores() {
		
		for(int i = 0; i < playerList.size(); i++) {
			System.out.println("Player " + i + "'s score: " + playerList.get(i).getScore());
		}
	}
	
	/* Deal all the players a hand consisting of 6 cards each */
	public static void dealPlayerHands() {
		
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < playerList.size(); j++) {
				playerList.get(j).addToHand(deck.getTopCard());
			}
		}
	}
}

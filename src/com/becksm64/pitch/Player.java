package com.becksm64.pitch;

public class Player {
	
	private Hand hand;//Hand consisting of 6 cards to start that the player holds and uses to play
	private CardCollection cardsWonInRound;//A collection of cards that the player wins in a round
	private int score;
	private int bid;

	public Player() {
		
		hand = new Hand();
		cardsWonInRound = new CardCollection();
		score = 0;
		bid = 0;
	}
	
	/* Get the player's bid */
	public int getBid() {
		return this.bid;
	}
	
	/* Set the player's bid */
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	/* Get the player's score */
	public int getScore() {
		return this.score;
	}
	
	/* Add to score */
	public void addToScore(int pointsToAdd) {
		this.score += pointsToAdd;
	}
	
	/* Get the player's current hand */
	public Hand getPlayerHand() {
		return this.hand;
	}
	
	/* Empty the player's hand */
	public void discardHand() {
		hand = new Hand();
	}
	
	/* Show the player's current hand. Calls show hand method for hand object */
	public void showPlayerHand() {
		this.hand.showHand();
	}
	
	/* Deal a card to player hand */
	public void addToHand(Card card) {
		this.hand.addToHand(card);
	}
	
	/* Takes a card from the player's hand a returns it. Also removes it from hand */
	public Card playCard(int index) {
		return this.hand.playCard(index);
	}
	
	/* Add a card to the player's collection */
	public void addToWonCards(Card card) {
		this.cardsWonInRound.addToPile(card);
	}
	
	/* Check if the player's hand is empty */
	public boolean isHandEmpty() {
		
		if(this.hand.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/* Add cards to player's collection of won cards */
	public void addToCardsWon(Card card) {
		this.cardsWonInRound.addToPile(card);
	}
	
	/* Get the cards won in round */
	public CardCollection getWonCards() {
		return this.cardsWonInRound;
	}
	
	/* Get rid of cards won in round */
	public void resetWonCards() {
		this.cardsWonInRound = new CardCollection();
	}
	
	/* Show playable cards. Compares current played card suit and trump suit to other cards
	 * THIS NEEDS SOME SERIOUS WORK. NOT DONE YET. HAVE TO CHECK IF PLAYER EVEN HAS TRUMP CARDS OR CURRENT SUIT
	 */
	public void showPlayableCards(String trump, String currentSuit, int pileSize) {
		
		if(trump == null && currentSuit == null && pileSize < 1 || pileSize < 1) {//Needs to be modified for when different player wins hand
			
			//All cards are playable if nothing has been played yet
			this.showPlayerHand();
			
		} else if(trump != null && currentSuit != null && trump != currentSuit && pileSize > 0) {
			
			//Check if has current suit and trump
			if(this.hand.hasSuit(trump) && this.hand.hasSuit(currentSuit)) {
				
				//Display cards whose suit is either trump or current suit (Can change to showCardsOfSuit() but have to call twice so order will be off)
				for(int i = 0; i < this.hand.size(); i++) {
					
					Card currentCard = this.hand.getCard(i);
					if(currentCard.getSuit() == trump || currentCard.getSuit() == currentSuit) {
						System.out.println(i + ": " + currentCard.getName());
					}
				}
			} else if(this.hand.hasSuit(trump) && !this.hand.hasSuit(currentSuit)) {
				
				this.showPlayerHand();
			} else if(!this.hand.hasSuit(trump) && this.hand.hasSuit(currentSuit)) {
				
				this.hand.showCardsOfSuit(currentSuit);
			} else {
				
				this.showPlayerHand();
			}
		} else if(trump != null && currentSuit != null && trump == currentSuit && pileSize > 0) {
			
			//Check if player has trump
			if(this.hand.hasSuit(trump)) {
				this.hand.showCardsOfSuit(trump);
			} else {
				this.showPlayerHand();
			}
		} else if(trump != null && currentSuit == null && pileSize > 0) {
			
			//Can play anything
			this.showPlayerHand();
		}
	}
}

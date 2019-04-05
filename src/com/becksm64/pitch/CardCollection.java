package com.becksm64.pitch;

import java.util.Stack;

public class CardCollection {

	Stack<Card> cardPile;//Stack because cards will be thrown on top of each other, like a stack ;p
	
	public CardCollection() {
		cardPile = new Stack<>();
	}
	
	/* Adds a card to the top of the pile of cards */
	public void addToPile(Card card) {
		this.cardPile.add(card);
	}
	
	/* Shows what cards are currently in the pile */
	public void showCardsInPile() {
		
		System.out.println("Cards in pile: ");
		for(int i = 0; i < this.size(); i++) {
			System.out.println(this.cardPile.get(i).getName());
		}
	}
	
	/* Remove the top card in the pile */
	public Card removeCard() {
		return this.cardPile.pop();
	}
	
	/* Gets card at specified index */
	public Card getCard(int index) {
		return this.cardPile.get(index);
	}
	
	/* Gets the number of cards in the stack */
	public int size() {
		return this.cardPile.size();
	}
	
	/* Check if the collection contains at least one trump card */
	public boolean hasTrump(String trump) {
		
		for(int i = 0; i < cardPile.size(); i++) {
			
			Card currentCard = cardPile.get(i);
			if(currentCard.getSuit() == trump) {
				return true;
			}
		}
		return false;//If trump card wasn't found, return false
	}
	
	/* Compare card about to be added to pile to see if it beats cards in collection. Needs to consider current suit also */
	public boolean isBestCard(Card cardToCompare, String trump, String currentSuit) {
		
		//Check if collection has trump in it
		if(this.hasTrump(trump) && cardToCompare.getSuit() != trump) {
			
			return false;
			
		} else if(this.hasTrump(trump) && cardToCompare.getSuit() == trump) {
			
			//Check if any card in the pile beats this card
			for(int i = 0; i < this.size(); i++) {
				
				Card currentCard = this.cardPile.get(i);
				if(currentCard.getValue() > cardToCompare.getValue()) {
					return false;
				}
			} return true;
			
		} else if(!this.hasTrump(trump) && cardToCompare.getSuit() != trump){

			//Check if any card in the pile beats this card
			for(int i = 0; i < this.size(); i++) {
				
				Card currentCard = this.cardPile.get(i);
				if(cardToCompare.getSuit() != currentSuit) {
					return false;
				} else if(cardToCompare.getSuit() == currentSuit 
						&& currentCard.getSuit() == currentSuit 
						&& currentCard.getValue() > cardToCompare.getValue()) {
					return false;
				}
			} return true;
		} else {
			return true;
		}
	}
	
	/* Checks to see if the Jack of the trump suit exists within the collection */
	public boolean hasTrumpJack(String trump) {
		
		for(int i = 0; i < this.size(); i++) {
			
			Card currentCard = this.getCard(i);
			//Look for card that has trump suit and value is 11. 11 represents jack because it's one after 10
			if(currentCard.getSuit() == trump && currentCard.getValue() == 11) {
				return true;
			}
		} return false;//If the jack wasn't found then return false
	}
	
	/* Find and return lowest trump card in collection */
	public Card getLowestTrump(String trump) {
		
		Card lowestTrump = null;
		for(int i = 0; i < this.size(); i++) {
			
			Card currentCard = this.getCard(i);
			if((this.getCard(i).getSuit() == trump && lowestTrump == null) 
					|| (currentCard.getSuit() == trump && currentCard.getValue() < lowestTrump.getValue())) {
				lowestTrump = currentCard;
			}
		} return lowestTrump;
	}
	
	/* Find and return highest trump card in collection */
	public Card getHighestTrump(String trump) {
		
		Card highestTrump = null;
		for(int i = 0; i < this.size(); i++) {
			
			Card currentCard = this.getCard(i);
			if((this.getCard(i).getSuit() == trump && highestTrump == null) 
					|| (currentCard.getSuit() == trump && currentCard.getValue() > highestTrump.getValue())) {
				highestTrump = currentCard;
			}
		} return highestTrump;
	}
	
	/* Calculate the player's score towards game point */
	public int getGamePointScore() {
		
		int gamePointScore = 0;
		for(int i = 0; i < this.size(); i++) {
			
			Card currentCard = this.getCard(i);
			//Suits are irrelevant when counting for game point
			if(currentCard.getValue() == 14) {
				gamePointScore += 4;//Ace is worth four points
			} else if(currentCard.getValue() == 13) {
				gamePointScore += 3;//King is worth three points
			} else if(currentCard.getValue() == 12) {
				gamePointScore += 2;//Queen is worth two points
			} else if(currentCard.getValue() == 11) {
				gamePointScore += 1;//Jack is worth one point
			} else if(currentCard.getValue() == 10) {
				gamePointScore += 10;//10s are worth 10 points
			}
		} return gamePointScore;
	}
}

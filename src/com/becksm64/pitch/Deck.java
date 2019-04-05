package com.becksm64.pitch;

import java.util.Collections;
import java.util.Stack;

public class Deck {

	private Stack<Card> deckOfCards;//Represent deck. Stack for now because cards can be dealt by performing pop
	
	/* Create a standard deck of cards containing 52 cards, no jokers */
	public Deck() {
		
		deckOfCards = new Stack<>();
		
		String suit;//Suit will change every 13 cards
		
		//Hearts
		suit = "Hearts";
		for(int i = 2; i < 15; i++) {
			deckOfCards.push(new Card(suit, i));//Add the new card to the deck
		}
		
		//Diamonds
		suit = "Diamonds";
		for(int i = 2; i < 15; i++) {
			deckOfCards.push(new Card(suit, i));//Add the new card to the deck
		}
		
		//Clubs
		suit = "Clubs";
		for(int i = 2; i < 15; i++) {
			deckOfCards.push(new Card(suit, i));//Add the new card to the deck
		}
		
		//Spades
		suit = "Spades";
		for(int i = 2; i < 15; i++) {
			deckOfCards.push(new Card(suit, i));//Add the new card to the deck
		}
	}
	
	/* Return the card at the top of the deck */
	public Card getTopCard() {
		return this.deckOfCards.pop();
	}
	
	/* Look at the top card and then put it back */
	public Card peekTopCard() {
		return this.deckOfCards.peek();
	}
	
	/* Get specific card from deck based on index */
	public Card viewCard(int index) {
		return this.deckOfCards.get(index);
	}
	
	/* Returns the number of cards in the deck */
	public int deckSize() {
		return deckOfCards.size();
	}
	
	/* Shuffles the deck */
	public void shuffle() {
		Collections.shuffle(this.deckOfCards);
	}
}

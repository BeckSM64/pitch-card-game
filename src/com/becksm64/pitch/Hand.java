package com.becksm64.pitch;

import java.util.List;
import java.util.ArrayList;

public class Hand {

	private List<Card> hand;//Hand which will hold six cards
	
	/* Create a hand that contains six cards from the deck */
	public Hand(Card card1, Card card2, Card card3, Card card4, Card card5, Card card6) {
		
		hand = new ArrayList<>();
		
		//Add cards to hand
		hand.add(card1);
		hand.add(card2);
		hand.add(card3);
		hand.add(card4);
		hand.add(card5);
		hand.add(card6);
	}
	
	/* Constructor with no arguments to make an empty hand and then add cards one by one after (not sure which would be better) */
	public Hand() {
		hand = new ArrayList<>();
	}
	
	/* Print all the cards that are in the hand */
	public void showHand() {
		
		if(this.hand.size() > 0) {
			for(int i = 0; i < this.hand.size(); i++) {
				System.out.println(i + ": " + this.hand.get(i).getName());
			}
		} else {
			System.out.println("This hand is empty");
		}
	}
	
	/* Show cards of a specified suit */
	public void showCardsOfSuit(String suit) {
		
		//Check if has trump but not current suit
		for(int i = 0; i < this.size(); i++) {
			
			Card currentCard = this.getCard(i);
			if(currentCard.getSuit() == suit) {
				System.out.println(i + ": " + currentCard.getName());
			}
		}
	}
	
	/* Add a card to the hand */
	public void addToHand(Card card) {
		this.hand.add(card);
	}
	
	/* Get the number of cards currently in hand */
	public int size() {
		return this.hand.size();
	}
	
	/* Plays the specified card, according to index, from hand. Also removes that card from hand */
	public Card playCard(int index) {
		
		Card cardToPlay = this.hand.get(index);
		this.hand.remove(index);
		return cardToPlay;
	}
	
	/* Get the specified card given provided index. Does not remove card from hand */
	public Card getCard(int index) {
		return this.hand.get(index);
	}
	
	/* Check if hand contains a trump card */
	public boolean hasSuit(String suitToFind) {
		
		for(int i = 0; i < this.hand.size(); i++) {
			
			Card currentCard = this.hand.get(i);
			if(currentCard.getSuit() == suitToFind) {
				return true;
			}
		}
		return false;//If trump card wasn't found, return false
	}
}

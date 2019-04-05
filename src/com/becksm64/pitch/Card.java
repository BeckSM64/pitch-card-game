package com.becksm64.pitch;

public class Card {
	
	private String suit;
	private int value;
	private String name;
	
	/* Constructor that takes the suit and value of the card to be created */
	public Card(String suit, int value) {
		
		this.suit = suit;
		this.value = value;
		
		if(value > 10) {
			if(value == 11) {
				this.name = "Jack of " + suit;
			} else if(value == 12) {
				this.name = "Queen of " + suit;
			} else if(value == 13) {
				this.name = "King of " + suit;
			} else {
				this.name = "Ace of " + suit;
			}
		} else {
			this.name = value + " of " + suit;
		}
	}
	
	public String getName()  {
		return this.name;
	}
	
	public String getSuit() {
		return this.suit;
	}
	
	public int getValue() {
		return this.value;
	}
}

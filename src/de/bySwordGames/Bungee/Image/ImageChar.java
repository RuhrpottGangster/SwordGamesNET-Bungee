/**
 * Die Klasse heißt: ImageChar.java
 * Die Klasse wurde am: 11.05.2017 | 20:55:27 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Image;

public enum ImageChar {
	
    BLOCK('\u2588'),
    DARK_SHADE('\u2593'),
    MEDIUM_SHADE('\u2592'),
    LIGHT_SHADE('\u2591');
	
    private char c;
    
    ImageChar(char c) {
        this.c = c;
    }
    
    public char getChar() {
        return c;
    }

}

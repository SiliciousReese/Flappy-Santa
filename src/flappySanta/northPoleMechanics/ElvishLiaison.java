package flappySanta.northPoleMechanics;

import java.awt.Graphics;

public class ElvishLiaison {
	private static final ElvishLiaison INSTANCE = new ElvishLiaison();

	/** @return Liaison between the section of the code that does math and the
	 *         section that does graphics. */
	public static ElvishLiaison getInstance() {
		return INSTANCE;
	}

	private NorthPole northPole;

	private ElvishLiaison() {
		northPole = new NorthPole();
	}

	/** Draw the game on the given graphics object. */
	public void drawNorthPole(Graphics g) {
		/* TODO Draw should be done entirely by UI. This code needs to be
		 * restructured to put all calculations in this section but all drawing
		 * go in user interface. Double buffer mayber? */
		northPole.draw(g);
	}

	/** Tell the northPole to jump santa. */
	public void jump() {
		northPole.santaJump();
	}

	/** Restart the northPole. */
	public void noahsFlood() {
		northPole = new NorthPole();
	}

}

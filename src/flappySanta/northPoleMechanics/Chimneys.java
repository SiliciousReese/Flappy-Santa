package flappySanta.northPoleMechanics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Chimneys {
	/** Default object to prevent using null assignment. */
	private static final Chimneys NULL_SET = getNullSet();

	/* Size constants */
	private static final int DISTANCE_BETWEEN_CHIMNEYS = 200;
	private static final int WIDTH = 20;
	private static final int STARTING_LENGTH = NorthPole.getNorthPoleWidth();

	/* Movement constants */
	private static final int MOVE_DISTANCE = 1;
	private static final int MOVE_LEFT_DELAY = 5;

	private static Chimneys getNullSet() {
		Chimneys set = new Chimneys();

		/* TODO Force off screen or somewhere they won't be a problem, or create
		 * a visible variable. */
		set.top = set.bottom = set.xLoc = 0;

		return set;
	}

	/* Location variables */
	/** Height of top chimney. */
	private int top;
	/** Height of bottom chimney. */
	private int bottom;
	private int xLoc;

	/** Counter for delaying moveLeft. */
	private int moveLeftDelay;

	public Chimneys() {
		/* TODO Use a rectangle array to store the position. The collision
		 * detection and drawing already converts the position into a rectangle
		 * anyway. */
		/* TODO Make random */
		top = NorthPole.getNorthPoleHeight() / 4;
		bottom = top + DISTANCE_BETWEEN_CHIMNEYS;
		xLoc = STARTING_LENGTH;
		moveLeftDelay = 0;
	}

	/** Detect if a collision occurred with the given rectangle.
	 * 
	 * @param rect
	 *            The rectangle to check collision with
	 * @return True if the two the rectangle is inside the bounds of one the
	 *         chimneys. */
	public boolean collision(Rectangle rect) {
		/* TODO Re-organize chimneys collision detection. */
		boolean collision = false;
		Rectangle topRect = new Rectangle(xLoc, 0, WIDTH, top);
		Rectangle botRect = new Rectangle(xLoc, bottom, WIDTH,
				NorthPole.getNorthPoleHeight());

		collision = rect.intersects(topRect) || rect.intersects(botRect);

		return collision;
	}

	/** Draw the chimney on the given graphics object. Does nothing if called on
	 * a null chimney. */
	public void draw(Graphics2D g) {
		if (this != NULL_SET) {
			Rectangle topRect = new Rectangle(xLoc, 0, WIDTH, top);
			Rectangle botRect = new Rectangle(xLoc, bottom, WIDTH,
					NorthPole.getNorthPoleHeight());
			g.setColor(Color.GREEN);
			g.fill(topRect);
			g.fill(botRect);
		}
	}

	/** @return true if the chimneys are too far too the left to be shown. */
	public boolean isOffScreen() {
		return (xLoc + WIDTH <= 0);
	}

	/** Move the set the given distance to the left. The result is never
	 * negative. */
	public void moveLeft() {
		/* Only moves the chimneys at a rate controlled by the delay. This felt
		 * better than using floating point arithmetic. */
		if (++moveLeftDelay > MOVE_LEFT_DELAY) {
			xLoc -= MOVE_DISTANCE;
			moveLeftDelay = 0; // Reset the counter
		}
	}

	/** Reset the x location. */
	public void reset() {
		xLoc = STARTING_LENGTH;
	}
}

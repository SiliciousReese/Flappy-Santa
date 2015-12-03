package flappySanta.northPolePhysics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PipeSet {
	/* TODO Remove public field. */
	public static final PipeSet NULL_SET = getNullSet();
	private static final int BETWEEN_PIPES = 200;
	private static final int WIDTH = 20;

	private static final int MOVE_DISTANCE = 1;

	private static final int STARTING_LENGTH = World.WORLD_WIDTH - 100;

	/** Height of top pipe. */
	private int top;

	/** Height of top pipe. */
	private int bottom;

	private int xLoc;
	/* Counter for delaying moveLeft. */
	private int moveLeftDelay;
	private static final int MOVE_LEFT_DELAY_MAX = 5;

	public PipeSet() {
		/* TODO Use a rectangle array to store the position. The collision
		 * detection and drawing already converts the position into a rectangle
		 * anyway. */
		/* TODO Make random */
		top = World.WORLD_HEIGHT / 4;
		bottom = top + BETWEEN_PIPES;
		xLoc = STARTING_LENGTH;
		moveLeftDelay = 0;
	}

	/** Detect if a collision occurred with the given rectangle.
	 * 
	 * @param rect
	 *            The rectangle to check collision with
	 * @return True if the two the rectangle is inside the bounds of one of teh
	 *         pipes. */
	public boolean collision(Rectangle rect) {
		/* TODO Re-organize pipeSet collision detection. */
		boolean collision = false;
		Rectangle topRect = new Rectangle(xLoc, 0, WIDTH, top);
		Rectangle botRect = new Rectangle(xLoc, bottom, WIDTH,
				World.WORLD_HEIGHT);

		collision = rect.intersects(topRect) || rect.intersects(botRect);

		return collision;
	}

	/** Draw the pipe on the given graphics object. Does nothing if called on a
	 * null pipe. */
	public void draw(Graphics2D g) {
		if (this != NULL_SET) {
			Rectangle topRect = new Rectangle(xLoc, 0, WIDTH, top);
			Rectangle botRect = new Rectangle(xLoc, bottom, WIDTH,
					World.WORLD_HEIGHT);
			g.setColor(Color.GREEN);
			g.fill(topRect);
			g.fill(botRect);
		}
	}

	/** Move the set the given distance to the left. The result is never
	 * negative. */
	public void moveLeft() {
		/* Only moves the pipes at a rate controlled by the delay. This felt
		 * better than using floating point. */
		if (++moveLeftDelay > MOVE_LEFT_DELAY_MAX) {
			xLoc -= MOVE_DISTANCE;
			moveLeftDelay = 0;
		}
	}

	/** @return true if the pipes are too far too the left to be shown. */
	public boolean isOffScreen() {
		return (xLoc + WIDTH <= 0);
	}

	/** Reset the x location. */
	public void reset() {
		xLoc = STARTING_LENGTH;
	}

	private static PipeSet getNullSet() {
		PipeSet set = new PipeSet();

		set.top = set.bottom = set.xLoc = 0;

		return set;
	}
}

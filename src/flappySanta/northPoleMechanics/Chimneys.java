package flappySanta.northPoleMechanics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Chimneys {
	/** Default object to prevent using null assignment. */
	/* Temporary until more pipes are added. */
	@SuppressWarnings("unused")
	private static final Chimneys NULL_SET = getNullSet();

	/* Size constants */
	private static final int DISTANCE_BETWEEN_CHIMNEYS = 150;
	private static final int WIDTH = 60;
	private static final int STARTING_LENGTH = NorthPole.getNorthPoleWidth();

	/* Movement constants */
	private static final int MOVE_DISTANCE = 1;

	private static Chimneys getNullSet() {
		Chimneys set = new Chimneys();

		set.visible = false;

		return set;
	}

	/* Location variables */
	/** Height of top chimney. */
	private int top;
	/** Height of bottom chimney. */
	private int bottom;
	private int xLoc;

	private Rectangle[] chimneys = new Rectangle[2];

	/** Used for determining whether a chimney should be rendered or not, if
	 * false it will also not collide with santa. Invisible chimneys locations
	 * are also not updated. */
	private boolean visible;

	public Chimneys() {
		/* TODO Use a rectangle array to store the position. The collision
		 * detection and drawing already converts the position into a rectangle
		 * anyway. */
		/* TODO Make random */
		top = NorthPole.getNorthPoleHeight() / 4;
		bottom = top + DISTANCE_BETWEEN_CHIMNEYS;
		xLoc = STARTING_LENGTH;

		chimneys[0] = new Rectangle(xLoc, 0, WIDTH, top);
		chimneys[1] = new Rectangle(xLoc, bottom, WIDTH,
				NorthPole.getNorthPoleHeight());

		visible = true;
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

		if (visible) {
			chimneys[0].x = chimneys[1].x = xLoc;

			/* Collision is true if the input rectangle collides with either of
			 * the chimney rectangles. */
			collision = rect.intersects(chimneys[0])
					|| rect.intersects(chimneys[1]);
		}

		return collision;
	}

	/** Draw the chimney on the given graphics object. Does nothing if called on
	 * an invisible chimney. */
	public void draw(Graphics2D g) {
		if (visible) {
			g.setColor(Color.RED);
			g.fill(chimneys[0]);
			g.fill(chimneys[1]);
		}
	}

	/** updates and returns the visibility.
	 * 
	 * @return True if the chimney is visible. */
	public boolean isVisible() {
		visible = xLoc + WIDTH > 0;
		return visible;
	}

	/** Move the set the given distance to the left. The result is never
	 * negative. */
	public void moveLeft() {
		/* Only moves the chimneys at a rate controlled by the delay. This felt
		 * better than using floating point arithmetic. */
		if (visible) {
			xLoc -= MOVE_DISTANCE;
		}
	}

	/** Reset the x location and sets visible true. */
	public void reset() {
		/* TODO Reset with random height. */
		xLoc = STARTING_LENGTH;
		visible = true;
	}
}

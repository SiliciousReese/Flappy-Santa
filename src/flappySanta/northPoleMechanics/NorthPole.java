package flappySanta.northPoleMechanics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import flappySanta.streams.StreamManager;

public class NorthPole {
	/* TODO Class is slightly bloated, refactor. */

	/* TODO Make bottom relative to bottom of swing container. */
	/** Height in pixels of the north pole. */
	private static final int NORTH_POLE_HEIGHT = 600;
	private static final int NORTH_POLE_WIDTH = 800;
	/** Height in pixels of santa. */
	private static final int SANTA_HEIGHT = 70;

	private static final double GRAVTY = 0.002;
	/** Change in motion after each jump. */
	private static final double MAXIMAL_VELOCITY = -0.7;

	private static final int X_LOCATION = 30;

	/** The maximum number of chimneys to create at one time. */
	/* TODO Only works with one chimneys currently. */
	private static final int MAX_CHIMNEYS = 1;

	/* Constant rate at which calculations are updated. */
	private static final long PHYSICS_TIMER_PERIOD = 1;

	// private static final StreamManager STREAMS = StreamManager.getInstance();

	public static int getNorthPoleHeight() {
		return NORTH_POLE_HEIGHT;
	}

	public static int getNorthPoleWidth() {
		return NORTH_POLE_WIDTH;
	}

	/** Keeps track of updating the state of all the physical objects. */
	private Timer physicsUpdates;
	private TimerTask doPhysics;

	private double yPosition;

	private double downVelocity;

	private Chimneys[] chimneys;

	private BufferedImage santaBirdImage;

	/** Determines whether santa or the chimneys should move. */
	private boolean gameOver;

	/** Slightly bloated object that controls almost everything related to the
	 * game engine. */
	public NorthPole() {
		gameOver = false;

		yPosition = 0;
		downVelocity = 0;

		chimneys = new Chimneys[MAX_CHIMNEYS];
		chimneys[0] = new Chimneys();

		/* TODO Make santa flap like a rain-deer by using a changing sprite. */
		santaBirdImage = StreamManager.getUpSanta();

		setTimer();
	}

	/** Draw the north pole. */
	public void draw(Graphics g) {
		if (!gameOver) {
			/* Draw santa. */
			/* TODO I am not sure why this works (Graphics2D extends Graphics,
			 * so I don't know how it can convert between them), so this should
			 * be changed to make more sense. */
			Graphics2D gTmp = (Graphics2D) g;
			g.drawImage(santaBirdImage, X_LOCATION, (int) yPosition, null);
			for (Chimneys p : chimneys)
				p.draw(gTmp);
		} else {
			/* Draw fail to the screen. */
			g.setColor(Color.black);
			g.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 50));
			g.drawString("FAIL", 100, 100);
		}
	}

	/** Temporarily increase the velocity. */
	public void santaJump() {
		downVelocity = MAXIMAL_VELOCITY;
	}

	private boolean checkCollision() {
		boolean collision = false;

		for (Chimneys p : chimneys) {
			int x = X_LOCATION;
			int y = (int) yPosition;
			/* Sprite image is square. TODO Sprite hitbox should not be square. */
			/* TODO Is this one-liner to obscure? */
			int height, width = height = SANTA_HEIGHT;
			Rectangle collisionRect = new Rectangle(x, y, width, height);

			/* Collision is true if and only if ANY of the chimneys collide with
			 * the image. */
			collision |= p.collision(collisionRect);
		}

		return collision;
	}

	/** Sets physics operations to be performed at a scheduled frequency. */
	private void setTimer() {
		/* The timer creates a thread and runs the physics calculations using a
		 * fixed rate. */
		physicsUpdates = new Timer("Physcs");
		/* The TimerAction controls the code that is run by the timer. */
		/* TODO Move this to a separate method. */
		doPhysics = new TimerTask() {
			@Override
			public void run() {
				/* Assumes time = 1, so position is increased by velocity plus
				 * acceleration and then velocity is increased by acceleration.
				 * 
				 * This is the same as the position and velocity equations from
				 * introductory physics, position = initial position plus
				 * initial velocity multiplied by time plus half acceleration
				 * multiplied by the square of time and velocity equals initial
				 * velocity plus acceleration multiplied by time:
				 * 
				 * (x = x0 + v0*t + (1/2)*a*t^2, v = a*t). */
				yPosition += downVelocity + (GRAVTY / 2.0);
				downVelocity += GRAVTY;

				/* The game is over if the player goes out of the bounds of the
				 * window. */
				if (yPosition + SANTA_HEIGHT > NORTH_POLE_HEIGHT
						|| yPosition < 0) {
					gameOver = true;
					yPosition = 0;
				}

				/* Move all the chimneys. */
				for (Chimneys p : chimneys)
					/* TODO Does this need thread safety? */
					synchronized (p) {
						p.moveLeft();
						if (p.isOffScreen())
							p.reset();
					}

				/* If the player and the chimneys share a space the game is
				 * over. */
				/* TODO Rectangular collision detection is not good enough. */
				if (checkCollision())
					gameOver = true;
			}
		};

		/* If the rate is not fixed, the physics calculations may not work as
		 * expected. A result of this is that the physics calculations MUST
		 * finish within the time given or weird things may happen. */
		physicsUpdates.scheduleAtFixedRate(doPhysics, 0, PHYSICS_TIMER_PERIOD);
	}
}

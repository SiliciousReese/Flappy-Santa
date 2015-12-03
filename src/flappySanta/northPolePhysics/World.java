package flappySanta.northPolePhysics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import flappySanta.streams.StreamManager;

public class World {
	/* TODO Make bottom relative to bottom of swing container. */
	/** Height in pixels of the world. */
	public static final int WORLD_HEIGHT = 600;
	public static final int WORLD_WIDTH = 800;
	/** Height in pixels of santa. */
	public static final int SANTA_HEIGHT = 70;

	private static final double GRAVTY = 0.002;
	/** Change in motion after each jump. */
	private static final double MAXIMAL_VELOCITY = -0.7;

	private static final int X_LOCATION = 30;

	/** The maximum number of pipes to create at one time. */
	/* TODO Only works with one pipes currently. */
	private static final int MAX_PIPES = 1;

	private static final long PHYSICS_TIMER_PERIOD = 1;

	private static StreamManager streams = StreamManager.getInstance();

	/** Keeps track of updating the state of all the physical objects. */
	private Timer physicsUpdates;
	private TimerTask doPhysics;

	private double yPosition;
	private double downVelocity;

	private PipeSet[] pipes;

	private BufferedImage santaBirdImage;

	/** Determines whether santa or the pipes should move. */
	private boolean gameOver;

	public World() {
		gameOver = false;
		yPosition = 0;
		downVelocity = 0;

		pipes = new PipeSet[MAX_PIPES];
		pipes[0] = new PipeSet();

		/* TODO Make santa flap like a rain-deer. */
		santaBirdImage = streams.getUpSanta();

		setTimer();
	}

	/** Temporarily increase the velocity. */
	public void santaJump() {
		downVelocity = MAXIMAL_VELOCITY;
	}

	/** Draw the world. */
	public void draw(Graphics g) {
		if (!gameOver) {
			/* Draw santa. */
			/* TODO I am not sure why this works (Graphics2D extends Graphics,
			 * so I don't know how it can convert between them). Change it to
			 * make more sense. */
			Graphics2D gTmp = (Graphics2D) g;
			g.drawImage(santaBirdImage, X_LOCATION, (int) yPosition, null);
			for (PipeSet p : pipes)
				p.draw(gTmp);
		} else {
			g.setColor(Color.black);
			g.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 50));
			g.drawString("FAIL", 100, 100);
		}
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

				if (yPosition + SANTA_HEIGHT > WORLD_HEIGHT || yPosition < 0) {
					gameOver = true;
					yPosition = 0;
				}

				/* Move all the pipes. */
				for (PipeSet p : pipes)
					/* TODO Does this need thread safety? */
					synchronized (p) {
						p.moveLeft();
						if (p.isOffScreen())
							p.reset();
					}

				if (checkCollision())
					gameOver = true;
			}
		};
		/* If the rate is not fixed, the physics calculations may not work
		 * correctly. */
		physicsUpdates.scheduleAtFixedRate(doPhysics, 0, PHYSICS_TIMER_PERIOD);
	}

	private boolean checkCollision() {
		boolean collision = false;

		for (PipeSet p : pipes) {
			int x = X_LOCATION;
			int y = (int) yPosition;
			/* Sprite image should be square. TODO Sprite hitbox should not be
			 * square. */
			int height, width = height = SANTA_HEIGHT;
			if (p.collision(new Rectangle(x, y, width, height)))
				gameOver = true;
		}

		return collision;
	}
}

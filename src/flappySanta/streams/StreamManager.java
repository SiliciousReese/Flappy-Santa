package flappySanta.streams;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class StreamManager {
	private static final StreamManager INSTANCE = new StreamManager();

	private static final String SANTA_SPRITE = "santa.png";
	private static final String RESOURCE_PREFIX = "res" + File.separator;

	private static final int[] SANTA_UP_LOCATIONS = { 0, 0, 70, 70 };

	private HashSet<Closeable> streams;

	private StreamManager() {
		streams = new HashSet<Closeable>();
		setShutdownHooks();
	}

	/** @return Singleton for managing streams. */
	public static StreamManager getInstance() {
		return INSTANCE;
	}

	/** @return Santa sprite facing up. */
	public BufferedImage getUpSanta() {
		int x = SANTA_UP_LOCATIONS[0];
		int y = SANTA_UP_LOCATIONS[1];
		int width = SANTA_UP_LOCATIONS[2];
		int height = SANTA_UP_LOCATIONS[3];

		return getImage(SANTA_SPRITE).getSubimage(x, y, width, height);
	}

	/** @param loc
	 *            The location of the image. See the constants in this class.
	 * @return An image from the given location or null if the image could not
	 *         be read. */
	/* TODO This belongs in a separate class. */
	private BufferedImage getImage(String loc) {
		/* Initialize image from file. */
		File imageFile = new File(RESOURCE_PREFIX + loc);
		BufferedImage img;
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
			/* TODO Handle IO exception. */
			System.err.println("Image path attempted: "
					+ imageFile.getAbsolutePath() + ".\n"
					+ "Check to make sure that path makes"
					+ " sense (does not end in /res/res).");
			img = null;
			e.printStackTrace();
		}

		return img;
	}

	/** Close all streams when program exits. */
	private void setShutdownHooks() {
		/* Action to perform in thread. */
		Runnable closeStreams = new Runnable() {
			@Override
			public void run() {
				/* Long message deserves its own line. */
				String errorMessage = "Error occured while closing streams. "
						+ "Some streams may not have been closed.";
				/* Don't let other things play with streams while they are
				 * closing. */
				synchronized (streams) {
					try {
						/* Close and remove each stream. */
						for (Closeable c : streams) {
							c.close();
							streams.remove(c);
						}
					} catch (IOException e) {
						/* TODO Figure out why this would throw an IOException. */
						System.err.println(errorMessage);
						e.printStackTrace();
					} finally {
						streams.clear();
					}
				}
			}
		};

		/* Create Thread for shutdown hook. */
		Thread hook = new Thread(closeStreams);
		Runtime.getRuntime().addShutdownHook(hook);
	}
}

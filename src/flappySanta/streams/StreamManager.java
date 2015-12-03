package flappySanta.streams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StreamManager {
	private static final String SANTA_SPRITE = "santa.png";
	private static final String RESOURCE_PREFIX = "res" + File.separator;

	private static final int[] SANTA_UP_LOCATIONS = { 0, 0, 70, 70 };

	/** @return Santa sprite facing up. */
	public static BufferedImage getUpSanta() {
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
	private static BufferedImage getImage(String loc) {
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
}

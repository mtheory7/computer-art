import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtImage {

  private BufferedImage bufferedImage;
  private float width;
  private float height;
  private int red;
  private int green;
  private int blue;
  private int[][] image;
  private int pixelMultiplier = 1;
  private List<Integer> uniqueColors;
  private List<Integer> usedColors;

  ArtImage(int width, int height, int red, int green, int blue) {
    this.width = width;
    this.height = height;
    this.red = red;
    this.green = green;
    this.blue = blue;
    image = new int[width][height];
    bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    uniqueColors = new ArrayList<Integer>();
    usedColors = new ArrayList<Integer>();
  }

  public void generateImage() {

    uniqueColors = new ArrayList<Integer>();
    for (int r = 0; r < red; r++) {
      for (int g = 0; g < green; g++) {
        for (int b = 0; b < blue; b++) {
          int p = (r << 16) | (g << 8) | b;
          uniqueColors.add(p);
        }
      }
    }
    Collections.shuffle(uniqueColors);
    int INDEX = 0;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (usedColors.contains(INDEX)) {
          System.out.println("Collision! Continuing...");
          INDEX++;
          continue;
        }
        image[x][y] = uniqueColors.get(INDEX);
        usedColors.add(INDEX);
        INDEX++;
      }
    }
  }

  BufferedImage getBufferedImage() {
    int xIndex = 0;
    int yIndex = 0;
    for (int y = 0; y < height * pixelMultiplier; y++) {
      for (int x = 0; x < width * pixelMultiplier; x++) {
        if (y % pixelMultiplier == 0 && y != 0 && x == 0) {
          yIndex++;
        }
        if (x % pixelMultiplier == 0 && x != 0) {
          xIndex++;
        }
        bufferedImage.setRGB(x, y, image[xIndex][yIndex]);
      }
      xIndex = 0;
    }
    return bufferedImage;
  }

  void setPixelMultiplier(int pixelMultiplier) {
    this.pixelMultiplier = pixelMultiplier;
    bufferedImage =
        new BufferedImage(
            (int) width * pixelMultiplier,
            (int) height * pixelMultiplier,
            BufferedImage.TYPE_INT_RGB);
  }
}

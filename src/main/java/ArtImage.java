import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtImage {

  private BufferedImage bufferedImage;
  private float width;
  private float height;
  private int[][] image;
  private int pixelMultiplier = 1;
  private List<Integer> uniqueColors;

  ArtImage(int width, int height) {
    this.width = width;
    this.height = height;
    image = new int[width][height];
    bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    uniqueColors = new ArrayList<Integer>();
  }

  public void generateImage() {
    uniqueColors = new ArrayList<Integer>();
    for (int r = 0; r < 256; r++) {
      for (int g = 0; g < 256; g++) {
        for (int b = 0; b < 256; b++) {
          int p = (r << 16) | (g << 8) | b;
          uniqueColors.add(p);
        }
      }
    }
    Collections.shuffle(uniqueColors);
    int INDEX = 0;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image[x][y] = uniqueColors.get(INDEX++);
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

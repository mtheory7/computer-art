import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class ArtImage {

  private BufferedImage bufferedImage;
  private float width;
  private float height;
  private int[][] image;
  private int pixelMultiplier = 1;
  private Set<Integer> uniqueColors;

  ArtImage(int width, int height) {
    this.width = width;
    this.height = height;
    image = new int[width][height];
    bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    uniqueColors = new HashSet<Integer>();
  }

  public void generateInitialImage() {
    uniqueColors = new HashSet<Integer>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = ((int) (Math.random() * 255));
        int g = ((int) (Math.random() * 255));
        int b = ((int) (Math.random() * 255));
        int p = (r << 16) | (g << 8) | b;
        image[x][y] = p;
        uniqueColors.add(p);
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
                (int)width * pixelMultiplier, (int)height * pixelMultiplier, BufferedImage.TYPE_INT_RGB);
  }

  float getUniqueness() {
    return uniqueColors.size() / (width * height);
  }
}

import java.awt.image.BufferedImage;

public class ArtImage {

  private int modR;
  private int modG;
  private int modB;
  private BufferedImage bufferedImage;
  private int width;
  private int height;
  private int[][] image;

  ArtImage(int width, int height) {
    modR = ((int) (Math.random() * 255)) + 1;
    modG = ((int) (Math.random() * 255)) + 1;
    modB = ((int) (Math.random() * 255)) + 1;
    this.width = width;
    this.height = height;
    image = new int[width][height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = (int) (Math.random() * 256) % modR;
        int g = (int) (Math.random() * 256) % modG;
        int b = (int) (Math.random() * 256) % modB;
        int p = (r << 16) | (g << 8) | b;
        image[x][y] = p;
      }
    }
    bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  }

  void addBox() {
    modR = ((int) (Math.random() * 255)) + 1;
    modG = ((int) (Math.random() * 255)) + 1;
    modB = ((int) (Math.random() * 255)) + 1;
    int xStart = (int) (Math.random() * width);
    int xEnd = (int) (Math.random() * width);
    if (xStart > xEnd) {
      int tmp = xEnd;
      xEnd = xStart;
      xStart = tmp;
    }
    int yStart = (int) (Math.random() * width);
    int yEnd = (int) (Math.random() * width);
    if (yStart > yEnd) {
      int tmp = yEnd;
      yEnd = yStart;
      yStart = tmp;
    }
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x >= xStart && x <= xEnd && y >= yStart && y <= yEnd) {
          // System.out.println("Parameters: modR=" + modR + " modG=" + modG + " modB=" + modB);
          int r = (int) (Math.random() * 256) % modR;
          int g = (int) (Math.random() * 256) % modG;
          int b = (int) (Math.random() * 256) % modB;
          int p = (r << 16) | (g << 8) | b;
          if (p <= 4388608) {
            //image[x][y] = (image[x][y] | p) % 16777216;
            image[x][y] = p;
          } else {
            image[x][y] = (image[x][y] & p) % 16777216;
          }
        }
      }
    }
  }

  BufferedImage getBufferedImage() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        bufferedImage.setRGB(x, y, image[x][y]);
      }
    }
    return bufferedImage;
  }

  public void setBufferedImage(BufferedImage bufferedImage) {
    this.bufferedImage = bufferedImage;
  }

  public int[][] getImage() {
    return image;
  }

  public void setImage(int[][] image) {
    this.image = image;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 1; i++) {
      int width = 10;
      int height = 10;
      int maxR = (int) (Math.random() * 256);
      int maxG = (int) (Math.random() * 256);
      int maxB = (int) (Math.random() * 256);
      System.out.println("R-" + maxR + " G-" + maxG + " B-" + maxB);
      System.out.println("required unique colors: " + (width * height));
      System.out.println("Number of possible colors: " + (maxR * maxG * maxB));
      if ((width * height) > (maxR * maxG * maxB)) {
        throw new Exception("Too many required unique colors for RGB values.");
      }
      ArtImage artImage = new ArtImage(width, height, maxR, maxG, maxB);
      artImage.setPixelMultiplier(192);
      artImage.generateImage();
      try {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        ImageIO.write(artImage.getBufferedImage(), "png", new File("artImages/ArtImage_" + timeStamp + "r-" + maxR + "_g-" + maxG + "_b-" + maxB + ".png"));
      } catch (IOException e) {
        System.out.println("Error: " + e);
      }
    }
  }
}

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    ArtImage artImage = new ArtImage(4096, 4096);
    artImage.setPixelMultiplier(1);
    artImage.generateImage();
    try {
      String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
      ImageIO.write(artImage.getBufferedImage(), "png", new File("ArtImage_" + timeStamp + ".png"));
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }
}

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    //GR: 1.61803398875
    ArtImage artImage = new ArtImage(2, 2);
    artImage.generateInitialImage();
    artImage.setPixelMultiplier(1);

    try {
      String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
      ImageIO.write(artImage.getBufferedImage(), "png", new File("images/test" + timeStamp + ".png"));
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }
}

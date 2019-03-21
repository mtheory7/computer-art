import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String args[]) {
    ArtImage artImage = new ArtImage(3236, 2000);
    for (int i = 0; i < 100; i++) {
      artImage.addBox();
    }
    try {
      String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
      ImageIO.write(artImage.getBufferedImage(), "jpg", new File("images/test" + timeStamp + ".jpg"));
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }
}

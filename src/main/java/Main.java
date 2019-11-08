import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    // GR: 1.61803398875
    ArtImage artImage = new ArtImage(100, 100);
    artImage.setPixelMultiplier(16);
    int genCount = 0;

    for(;;) {
        genCount++;
        artImage.generateInitialImage();
        if (genCount % 100 == 0) System.out.println(genCount + " - uniqueness - " + artImage.getUniqueness());
        if (!(artImage.getUniqueness() < 1)) {
            System.out.println(genCount + " - uniqueness - " + artImage.getUniqueness());
            break;
        }
    }

    try {
      String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
      ImageIO.write(
          artImage.getBufferedImage(),
          "png",
          new File("ArtImage_" + timeStamp + ".png"));
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }
}

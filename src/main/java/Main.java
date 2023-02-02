import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static BufferedImage joinBufferedImage(BufferedImage img1,
                                                  BufferedImage img2) {
        int offset = 0;
        int width = img1.getWidth() + img2.getWidth() + offset;
        int height = Math.max(img1.getHeight(), img2.getHeight());
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth() + offset, 0);
        g2.dispose();
        return newImage;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Program starting! Time: " + LocalDateTime.now());
        int width = 10;
        int maxR = (int) (Math.random() * 255);
        int maxG = (int) (Math.random() * 255);
        int maxB = (int) (Math.random() * 255);
        List<BufferedImage> imageList = new ArrayList<>();
        List<Integer> knownPrimes = new ArrayList<>();
        List<Integer> knownComposites = new ArrayList<>();
        for (int w = width; w <= 50; w += 10) {
            System.out.println("R-" + maxR + " G-" + maxG + " B-" + maxB);
            System.out.println("required unique colors: " + (w * w * 5));
            System.out.println("Number of possible colors: " + (maxR * maxG * maxB));
            if ((w * w * 5) > (maxR * maxG * maxB)) {
                throw new Exception("Too many required unique colors for RGB values.");
            }
            ArtImage artImage = new ArtImage(w, w * 5, maxR, maxG, maxB, knownPrimes, knownComposites);
            artImage.setPixelMultiplier(1800 / w);
            artImage.generateImage();
            try {
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                BufferedImage image = artImage.getPixelMultiplied();
                imageList.add(image);
                String fileName = "artImages/ArtImage_" + w + "_x_" + w * 5 + "_" + timeStamp + "r-" + maxR + "_g-" + maxG + "_b-" + maxB + ".png";
                ImageIO.write(image, "png",
                        new File(fileName));
                System.out.println("Time: " + LocalDateTime.now() + " Filename: " + fileName);
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        BufferedImage joined = joinBufferedImage(imageList.get(0), imageList.get(1));
        for (int i = 2; i < imageList.size(); i++) {
            joined = joinBufferedImage(joined, imageList.get(i));
        }
        ImageIO.write(joined, "png",
                new File("artImages/ArtImage_JOINED_" + timeStamp + "r-" + maxR + "_g-" + maxG + "_b-" + maxB + ".png"));
        System.out.println("Program finished! Time: " + LocalDateTime.now());
    }
}

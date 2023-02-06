import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

public class Main {
    // This number seems to work well because image is
    // (10x50)+(20x100)+(30x150)+(40x200)+(50x250)
    // Resulting final images are both 12000x12000
    private static final int ART_IMAGE_X_Y_VALUE = 12000;
    private static final int ART_IMAGE_SQUARE_WIDTH = 10;
    private static final int CODE_IMAGE_FONT_SIZE = 82;
    private static final String MainClass = "src/main/java/Main.java";
    private static final String PrimeArtImageClass =
            "src/main/java/PrimeArtImage.java";
    private static final String AKSClass = "src/main/java/AKS.java";

    public static void main(String[] args) throws Exception {
        System.out.println("Program starting! Time: " + LocalDateTime.now());
        int maxR = (int) (Math.random() * 255);
        int maxG = (int) (Math.random() * 255);
        int maxB = (int) (Math.random() * 255);
        System.out.println("R-" + maxR + " G-" + maxG + " B-" + maxB);
        generatePrimeArt(maxR, maxG, maxB);
        generateCodeImage();
        System.out.println("Program finished! Time: " + LocalDateTime.now());
    }

    private static void generatePrimeArt(int maxR, int maxG, int maxB)
            throws Exception {
        List<BufferedImage> imageList = new ArrayList<>();
        for (int w = ART_IMAGE_SQUARE_WIDTH; w <= 50; w += 10) {
            if ((w * w * 5) > (maxR * maxG * maxB)) {
                throw new Exception("Too many required unique colors for RGB values.");
            }
            imageList.add(new PrimeArtImage(w, w * 5,
                    maxR, maxG, maxB, ART_IMAGE_X_Y_VALUE / 5 / w).generateImage());
        }
        BufferedImage joined = joinBufferedImage(imageList.get(0), imageList.get(1));
        for (int i = 2; i < imageList.size(); i++) {
            joined = joinBufferedImage(joined, imageList.get(i));
        }
        ImageIO.write(joined, "png", new File(
                "generatedArt/ArtImage_" + getCurrentTimeStampFormatted() +
                        "r-" + maxR + "_g-" + maxG + "_b-" + maxB + ".png"));
    }

    private static void generateCodeImage() throws IOException {
        final BufferedImage image = new BufferedImage(
                ART_IMAGE_X_Y_VALUE, ART_IMAGE_X_Y_VALUE, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < ART_IMAGE_X_Y_VALUE; x++) {
            for (int y = 0; y < ART_IMAGE_X_Y_VALUE; y++) {
                image.setRGB(x, y, 16777215);
            }
        }
        addBackgroundToCodeArt(image,
                Arrays.asList(MainClass, PrimeArtImageClass, AKSClass));
        addToCodeArt(image, MainClass, 150);
        addToCodeArt(image, PrimeArtImageClass, 4150);
        addToCodeArt(image, AKSClass, 8150);
        ImageIO.write(image, "png", new File("generatedArt/CodeImage_"
                + getCurrentTimeStampFormatted() + ".png"));
    }

    private static BufferedImage joinBufferedImage(BufferedImage img1,
                                                   BufferedImage img2) {
        int width = img1.getWidth() + img2.getWidth();
        int height = Math.max(img1.getHeight(), img2.getHeight());
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth(), 0);
        g2.dispose();
        return newImage;
    }

    private static String getCurrentTimeStampFormatted() {
        return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date());
    }

    private static void addBackgroundToCodeArt(BufferedImage image,
               List<String> fileNames) throws FileNotFoundException {
        Graphics g = image.getGraphics();
        g.setFont(new Font("Courier New", Font.PLAIN, CODE_IMAGE_FONT_SIZE));
        for (String fileName : fileNames) {
            String[] outputs = getStringFromFile(fileName).split("\n");
            g.setColor(new Color(220, 220, 220));
            for (int i = 0; i < 1000; i++) {
                g.drawString(outputs[((int) (Math.random() * outputs.length))],
                        (int) (Math.random() * ART_IMAGE_X_Y_VALUE) - (500),
                        (int) (Math.random() * ART_IMAGE_X_Y_VALUE));
            }
        }
        g.dispose();
    }

    private static void addToCodeArt(BufferedImage image, String fileName, int startingX)
            throws FileNotFoundException {
        Graphics g = image.getGraphics();
        g.setFont(new Font("Courier New", Font.BOLD, CODE_IMAGE_FONT_SIZE));
        g.setColor(Color.BLACK);
        String[] outputs = getStringFromFile(fileName).split("\n");
        for (int i = 0; i < outputs.length; i++) {
            g.drawString(outputs[i], startingX,
                    (CODE_IMAGE_FONT_SIZE + (CODE_IMAGE_FONT_SIZE * i) + 100));
        }
        g.dispose();
    }

    private static String getStringFromFile(String fileName) throws FileNotFoundException {
        String text;
        try (Scanner scanner = new Scanner(new File(fileName), StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }
        return text;
    }
}

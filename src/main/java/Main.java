import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Main {
    private static final int ART_IMAGE_X_Y_VALUE = 12000;
    private static final int ART_IMAGE_SQUARE_WIDTH = 10;
    private static final int FONT_SIZE = 82;
    public static final int WHITE_RGB = 16777215;
    private static final String MainClass = "src/main/java/Main.java";
    private static final String PrimeArtImageClass =
            "src/main/java/PrimeArtImage.java";
    private static final String AKSClass = "src/main/java/AKS.java";
    private static String RGB_TIMESTAMP;

    public static void main(String[] args) throws Exception {
        int maxR = (int) (Math.random() * 255);
        int maxG = (int) (Math.random() * 255);
        int maxB = (int) (Math.random() * 255);
        RGB_TIMESTAMP = "R-" + maxR + " G-" + maxG + " B-" + maxB + " "
                + new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        System.out.println(RGB_TIMESTAMP);
        generatePrimeArt(maxR, maxG, maxB);
        generateCodeImage(maxR, maxG, maxB);
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
                "generatedArt/ArtImage (" + RGB_TIMESTAMP + ").png"));
    }

    private static void generateCodeImage(int maxR, int maxG, int maxB)
            throws IOException {
        final BufferedImage image = new BufferedImage(
                ART_IMAGE_X_Y_VALUE, ART_IMAGE_X_Y_VALUE, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < ART_IMAGE_X_Y_VALUE; x++) {
            for (int y = 0; y < ART_IMAGE_X_Y_VALUE; y++) {
                image.setRGB(x, y, WHITE_RGB);
            }
        }
        addBackgroundToCodeArt(image,
                Arrays.asList(MainClass, PrimeArtImageClass, AKSClass));
        addToCodeArt(image, MainClass, 150, maxR, maxG, maxB);
        addToCodeArt(image, PrimeArtImageClass, 4150, maxR, maxG, maxB);
        addToCodeArt(image, AKSClass, 8150, maxR, maxG, maxB);
        ImageIO.write(image, "png", new File(
                "generatedArt/CodeImage (" + RGB_TIMESTAMP + ").png"));
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

    private static void addBackgroundToCodeArt(BufferedImage image,
               List<String> fileNames) throws FileNotFoundException {
        Graphics g = image.getGraphics();
        g.setFont(new Font("Courier New", Font.PLAIN, FONT_SIZE));
        g.setColor(new Color(220, 220, 220));
        for (String fileName : fileNames) {
            String[] outputs = getStringFromFile(fileName).split("\n");
            for (int i = 0; i < 1000; i++) {
                g.drawString(outputs[((int) (Math.random() * outputs.length))],
                        (int) (Math.random() * ART_IMAGE_X_Y_VALUE) - (500),
                        (int) (Math.random() * ART_IMAGE_X_Y_VALUE));
            }
        }
        g.dispose();
    }

    private static void addToCodeArt(BufferedImage image, String sourceFile, int startingX,
                    int maxR, int maxG, int maxB)
            throws FileNotFoundException {
        Graphics g = image.getGraphics();
        g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
        g.setColor(Color.BLACK);
        String[] outputs = getStringFromFile(sourceFile).split("\n");
        for (int i = 0; i < outputs.length; i++) {
            g.drawString(outputs[i], startingX, (FONT_SIZE + (FONT_SIZE * i) + 100));
        }
        if (startingX == 8150) {
            g.setColor(new Color(maxR, maxG, maxB));
            g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE * 5));
            g.drawString(RGB_TIMESTAMP, 150, 11800);
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

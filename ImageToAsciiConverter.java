import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageToAsciiConverter {
    boolean negative;

    public ImageToAsciiConverter() {
        this(false);
    }

    public ImageToAsciiConverter(final boolean negative) {
        this.negative = negative;
    }

    public String convert(final BufferedImage image) {
        // BufferedImage resizedImage = resize(image, 150, 150);

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        StringBuilder sb = new StringBuilder((imageWidth + 1) * imageHeight);

        for (int y = 0; y < imageHeight; y++) {
            if (sb.length() != 0) sb.append("\n");
            
            for (int x = 0; x < imageWidth; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                double grayscaleValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                final char asciiPixel = negative ? returnStrNeg(grayscaleValue) : returnStrPos(grayscaleValue);
                sb.append(asciiPixel);
            }
        }
        
        return sb.toString();
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private char returnStrPos(double grayscaleValue) {
        final char str;

        if (grayscaleValue >= 230.0) {
            str = ' ';
        } else if (grayscaleValue >= 200.0) {
            str = '.';
        } else if (grayscaleValue >= 180.0) {
            str = '*';
        } else if (grayscaleValue >= 160.0) {
            str = ':';
        } else if (grayscaleValue >= 130.0) {
            str = 'o';
        } else if (grayscaleValue >= 100.0) {
            str = '&';
        } else if (grayscaleValue >= 70.0) {
            str = '8';
        } else if (grayscaleValue >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str;

    }

    private char returnStrNeg(double grayscaleValue) {
        final char str;

        if (grayscaleValue >= 230.0) {
            str = '@';
        } else if (grayscaleValue >= 200.0) {
            str = '#';
        } else if (grayscaleValue >= 180.0) {
            str = '8';
        } else if (grayscaleValue >= 160.0) {
            str = '&';
        } else if (grayscaleValue >= 130.0) {
            str = 'o';
        } else if (grayscaleValue >= 100.0) {
            str = ':';
        } else if (grayscaleValue >= 70.0) {
            str = '*';
        } else if (grayscaleValue >= 50.0) {
            str = '.';
        } else {
            str = ' ';
        }
        return str;

    }

    public static void main(String[] args) {
        File input = new File(args[0]);
        String asciiString = "Empty";
        ImageToAsciiConverter ac = new ImageToAsciiConverter();

        try {
            BufferedImage image = ImageIO.read(input);
            asciiString = ac.convert(image);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        try {
            PrintWriter writer = new PrintWriter("ascii_art.txt", "UTF-8");
            writer.print(asciiString);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
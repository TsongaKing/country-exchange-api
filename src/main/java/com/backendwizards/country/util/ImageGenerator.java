package com.backendwizards.country.util;

import com.backendwizards.country.entity.Country;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Instant;
import java.util.List;

@Component
public class ImageGenerator {

    public File generateSummary(List<Country> allCountries, Instant timestamp) {
        try {
            int width = 900, height = 600;
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();

            // background
            g.setPaint(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // title
            g.setPaint(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Countries Summary", 20, 40);

            // meta
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Total countries: " + allCountries.size(), 20, 70);
            g.drawString("Last refresh: " + timestamp.toString(), 20, 95);

            // top 5 by estimated GDP
            List<Country> top5 = allCountries.stream()
                    .filter(c -> c.getEstimatedGdp() != null)
                    .sorted((a, b) -> Double.compare(b.getEstimatedGdp(), a.getEstimatedGdp()))
                    .limit(5)
                    .toList();

            int y = 140;
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Top 5 by estimated GDP:", 20, y);
            y += 28;

            g.setFont(new Font("Arial", Font.PLAIN, 14));
            for (Country c : top5) {
                String line = String.format("%s — %.2f (%s)", c.getName(), c.getEstimatedGdp(), c.getCurrencyCode());
                g.drawString(line, 28, y);
                y += 22;
            }

            g.dispose();

            File cacheDir = new File("cache");
            if (!cacheDir.exists()) cacheDir.mkdirs();

            File out = new File(cacheDir, "summary.png");
            ImageIO.write(img, "png", out);
            return out;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

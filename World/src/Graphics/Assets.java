package Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Assets {

    private static final int width = 48, height = 72;
    public static BufferedImage sheep, grass, wolf, eagle, mouse;
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        sheep = colorImage(sheet.crop(3*width,5*height, width, height), new int[] {255,255,255});
        grass = colorImage(sheet.crop(11*width, 3*height, width, height), new int[] {0,255,0});
        wolf = colorImage(sheet.crop(7*width, 5*height,width,height), new int[] {0,0,255});
        eagle = colorImage(sheet.crop(4*width, 4*height, width, height), new int[] {255,0,0});
        mouse = colorImage(sheet.crop(4*width, 5*height, width, height), new int[] {255,0,255});
    }
    private static BufferedImage colorImage(BufferedImage image, int[] colors) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = colors[0];
                pixels[1] = colors[1];
                pixels[2] = colors[2];
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
}

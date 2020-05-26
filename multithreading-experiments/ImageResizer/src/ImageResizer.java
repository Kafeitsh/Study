import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * класс, выполняющий алгоритм уменьшения изображений
 */
public class ImageResizer implements Runnable {

    /**
     * список файлов
     */
    private File[] files;

    /**
     * новая ширина
     */
    private int newWidth;

    /**
     * путь, в который должны быть помещены изображения после обработки
     */
    private String dstFolder;

    /**
     * переменная для замера длительности обработки изображений
     */
    private long start;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        try{
            // перебираются файлы, переданные на вход
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                BufferedImage newImage = null;

                // если файл не является изображением - пропускаем
                if (image == null) {
                    continue;
                }

                // алгоритм уменьшения изображения
                int step = (image.getWidth() / newWidth) - (image.getWidth() / newWidth % 8);
                for (int i = step; i > 0; i /= 2) {
                    int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) (newWidth)) * i);
                    newImage = new BufferedImage(newWidth * i, newHeight, BufferedImage.TYPE_INT_RGB);
                    int widthStep = image.getWidth() / (newWidth * i);
                    int heightStep = image.getHeight() / (newHeight * i);
                    for (int x = 0; x < newWidth * i; x++) {
                        for (int y = 0; y < newHeight; y++) {
                            int rgb = image.getRGB(x * widthStep, y * heightStep);
                            newImage.setRGB(x, y, rgb);
                        }
                    }
                }

                // запись уменьшенного изображения в файл
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // сообщение с длительностью обработки
        System.out.println("duration: " + (System.currentTimeMillis() - start) + " ms");
    }
}

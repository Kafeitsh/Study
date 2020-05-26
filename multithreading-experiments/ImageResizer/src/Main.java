import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * программа по уменьшению размера изображений в заданной директории
 *
 * по условиям количество потоков должно быть равным количеству ядер процессора
 * также должен быть осуществлен алгоритм плавного уменьшения изображения
 */
public class Main {

    /**
     * количество ядер процессора
     */
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    /**
     * путь, по которому находятся изображения, подлежащие уменьшению
     */
    private static final String SRC_FOLDER = "E:/ImagesResizer/src";

    /**
     * путь, по которому должны быть размещены уменьшеные изображения
     */
    private static final String DST_FOLDER = "E:/ImagesResizer/dest";

    /**
     * константа новой ширины изображения в пикселях
     *
     * задается только ширина, т.к. изображение должно быть масштабируемым
     */
    private static final int NEW_WIDTH = 300;


    public static void main(String[] args) {

        // создание объект класса File с директорией, в которой необходимо уменьшить изображения
        File srcDir = new File(SRC_FOLDER);

        // текущее время в милисекундах для замера продолжительности
        long start = System.currentTimeMillis();

        // список файлов в заданной директории
        List<File> filesList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(srcDir.listFiles())));

        // получение размера подсписков
        int size = filesList.size();
        int neededSize = size / CORES + 1;

        // запуск новых потоков в количестве, равном количеству ядер процессора
        for (int i = 0; i < size; i += neededSize) {
            List<File> choppedList = filesList.subList(i, Math.min(size, i  + neededSize));
            File[] files = choppedList.toArray(File[]::new);
            ImageResizer resizer = new ImageResizer(files, NEW_WIDTH, DST_FOLDER, start);
            new Thread(resizer).start();
        }
    }
}

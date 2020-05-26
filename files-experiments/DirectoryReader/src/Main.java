import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Scanner;

/**
 * программа измеряет размер всего содержимого папки, которая подается на вход
 * и выводит результат в читаемом виде
 *
 * размер измеряется двумя способами: рекурсия и stream api
 */
public class Main {

    // переменная с шагом для конвертации размера
    private static final int SIZE_STEP = 1024;

    // удобный для чтения формат
    private static DecimalFormat decimalFormat = new DecimalFormat("###.##");

    public static void main(String[] args) {

        // принимаем на вход путь к директории, переводим в объект класса File
        System.out.println("Укажить путь к директории, размер которой необходимо получить (формат ввода - C:\\*\\*): ");
        Scanner scanner = new Scanner(System.in);
        File folder = new File(scanner.nextLine());

        // реализация через метод с рекурсией
        long folderSize = getFolderSize(folder);
        changeSizeStep(folderSize, folder);

        //реализация через метод с использованием Stream
        long folderSizeByStream = getFolderSizeByStream(folder);
        changeSizeStep(folderSizeByStream, folder);
    }

    /**
     * метод получения размера заданной директории с помощью рекурсии
     *
     * @param folder - директория, размер которой хотим получить
     * @return возвращает размер в байтах
     *
     * если заданная директория не существует - возвращает 0
     */
    private static long getFolderSize(File folder) {

        if (folder.exists()) {

            // переменная для записи размера
            long size = 0;

            try {
                // перебираем содержимое,
                // если файл - плюсуем размер к переменной для записи размера,
                // если папка - проваливаемся в рекурсию
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += getFolderSize(file);
                    }
                }
            } catch (Exception ex) {
                return 0;
            }
            return size;
        } else {
            return 0;
        }
    }


    /**
     * метод получения размера заданной директории с помощью stream api
     *
     * @param folder - директория, размер которой хотим получить
     * @return возвращает размер в байтах
     * 
     * если заданная директория не существует - возвращает 0
     */
    private static long getFolderSizeByStream(File folder) {

        if (folder.exists()) {

            // переменная для записи размера
            long size = 0;

            // создаем объект класса Path
            Path path = folder.toPath();
            try {
                size = Files.walk(path)
                        .map(Path::toFile)
                        .filter(File::isFile)
                        .mapToLong(File::length)
                        .sum();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return size;
        } else {
            return 0;
        }
    }

    /**
     * методы конвертации
     *
     * @param size размер в байтах
     * @return возвращает в зависимости от метода размер в:
     * килобайтах
     * мегабайтах
     * гигабайтах
     * терабайтах
     */

    private static double getFolderSizeKilobytes(long size) {
        return (double) (size / SIZE_STEP);
    }

    private static double getFolderSizeMegabytes(long size) {
        return getFolderSizeKilobytes(size) / SIZE_STEP;
    }

    private static double getFolderSizeGigabyte(long size) {
        return getFolderSizeMegabytes(size) / SIZE_STEP;
    }

    private static double getFolderSizeTerabyte(long size) {
        return getFolderSizeGigabyte(size) / SIZE_STEP;
    }


    /**
     * метод определения правильного метода для конвертации размера папки с содержимым в удобочитаемый формат
     *
     * @param size   - подается на вход размер папки, размер которой необходимо получить
     * @param folder - директория, размер которой необходимо получить, используется только для информационного сообщения
     */
    private static void changeSizeStep(long size, File folder) {

        // если заданная папка не существует - выводится сообщение об отсутствии
        if (size == 0) {

            System.out.println("Отсутствует!");

        } else {

            System.out.print("Размер директории " + folder.getAbsolutePath() + " - ");

            // в зависимости от размера заданной директории выводится сообщение с размером в подходящем формате
            if (size <= SIZE_STEP) {
                System.out.println(size + "байт");
            } else if (size > SIZE_STEP && size <= Math.pow(SIZE_STEP, 2)) {
                System.out.println(decimalFormat.format(getFolderSizeKilobytes(size)) + " КБ");
            } else if (size > Math.pow(SIZE_STEP, 2) && size <= Math.pow(SIZE_STEP, 3)) {
                System.out.println(decimalFormat.format(getFolderSizeMegabytes(size)) + " МБ");
            } else if (size > Math.pow(SIZE_STEP, 3) && size <= Math.pow(SIZE_STEP, 4)) {
                System.out.println(decimalFormat.format(getFolderSizeGigabyte(size)) + " ГБ");
            } else {
                System.out.println(decimalFormat.format(getFolderSizeTerabyte(size)) + " ТБ");
            }
        }
    }
}

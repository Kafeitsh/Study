import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Scanner;

/**
 * программа копирует папку со всеми вложенными файлами и папками
 * из задаваемой директории в задаваемую директорию
 * с использованием двух разных способов
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Укажить путь к директории, которую необходимо скопировать (формат ввода - C:\\*\\*): ");
        String copyFrom = new Scanner(System.in).nextLine();

        System.out.println("Укажить путь к директории, в которую необходимо скопировать (формат ввода - C:\\*\\*): ");
        String copyTo = new Scanner(System.in).nextLine();

        // копирование через рекурсию
//        copyFolder(copyFrom, copyTo);

        //копирование через stream
        copyFolderByStream(copyFrom, copyTo);
    }

    /**
     * метод копирования папки со вложенными файлами с использованинем рекурсии
     *
     * @param from - путь к директории, из которой необходимо скопировать
     * @param to   - путь к директории, в которую необходимо скопировать
     */
    private static void copyFolder(String from, String to) {

        // создаются объекты класса Path из строковых путей к директории
        Path pathFrom = Paths.get(from);
        Path pathTo = Paths.get(to);

        if (pathFrom.toFile().exists()) {
            try {

                // если директория, в которую необходимо скопировать, отсутствует - создаем
                if (!pathTo.toFile().exists()) {
                    Files.createDirectory(pathTo);
                }

                // перебираем содержимое папки, из которой необходимо скопировать
                // если файл - копируем, если папка - используем рекурсию и вызываем этот метод заново
                for (File files : Objects.requireNonNull(pathFrom.toFile().listFiles())) {

                    if (files.isFile()) {
                        Files.copy(files.toPath(), pathTo.resolve(files.getName()), StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        copyFolder(files.toPath().toString(), pathTo.resolve(files.getName()).toString());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Указанный путь для копирования не существует!");
        }
    }


    /**
     * метод копирования папки со вложенными файлами с использованинем stream api
     *
     * @param from - путь к директории, из которой необходимо скопировать
     * @param to   - путь к директории, в которую необходимо скопировать
     */
    private static void copyFolderByStream(String from, String to) {

        // создается объект класса Path из строки с путем к директории, из которой необходимо скопировать
        Path pathFrom = Paths.get(from);
        try {
            // создается папка, в которую будет копироваться содержимое
            Files.createDirectory(Paths.get(to));
            System.out.println("Список скопированных файлов: \n");

            // перебирается содержимое папки, из которой необходимо скопировать,
            Files.walk(pathFrom).map(Path::toFile)
                    .map(f -> {
                        try {
                            // копирование файла в папку назначения
                            String allCopiedPath = f.toString().substring(f.toString().indexOf("\\") + 1);
                            File copiedFile = new File(to + "\\" + allCopiedPath);
                            return Files.copy(f.toPath(), copiedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).forEach(System.out::println);

            // сообщение с выводом количества скопированных элементов
            System.out.println("\nКопирование завершено! Скопировано "
                    + (int) (Files.walk(Paths.get(to))
                    .map(Path::toFile)
                    .map(File::listFiles).count() - 1)
                    + " элементов");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

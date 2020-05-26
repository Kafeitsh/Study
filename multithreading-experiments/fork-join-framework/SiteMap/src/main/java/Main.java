import nodes.Parser;
import nodes.Walker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private final static String URL1 = "https://skillbox.ru/";
    private final static String URL2 = "https://lenta.ru/";
    private final static String MAP_DEST = "data/SiteMap.txt";

    public static void main(String[] args) {

        // создается пустой список
        Set<String> links = new TreeSet<>();

        // создается парсер, в качестве конструктора передается ссылка и пустой список
        Parser parser = new Parser(URL1, links);

        System.out.println("...processing...");

        // список заполняется
        links = new ForkJoinPool().invoke(new Walker(parser));

        links.forEach(System.out::println);

        // список записывается в файл
        createFile(setTabs(links), MAP_DEST);

        System.out.println("done");
    }

    /**
     * метод расстовки табуляций
     * @param set передает список ссылок
     * @return - возвращает перебранный список ссылок с добавленными табуляциями
     */
    private static List<String> setTabs(Set<String> set) {

        List<String> links = new ArrayList<>();

        set.forEach(link -> {
            // создается массив элементов, ссылка из списка делится на части по "/"
            String[] parts = link.substring((link.indexOf("//") + 2)).split("/");
            // количество элементов
            int size = parts.length;

            // добавляется в начало строки количество табулиций, равное количеству элементов.
            // если элемент один - не добавляется
            // затем добавляется сама ссылка и получившаяся строка добавляется в список
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    builder.append("\t");
                }
            }
            builder.append(link);
            links.add(builder.toString());
        });

        return links;
    }

    /**
     * метод записи списка в файл
     * @param links передается список ссылок
     * @param path расположение файла
     */
    private static void createFile(List<String> links, String path) {
        try {
            PrintWriter writer = new PrintWriter(path);
            links.forEach(link -> writer.write(link + "\n"));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

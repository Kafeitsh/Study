package nodes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Parser {

    private static Logger logger = LogManager.getLogger();

    private String link;
    private Set<String> links;

    public Parser(String link, Set<String> links) {
        this.link = link;
        this.links = links;
    }

    /**
     * @return возвращает список "детей" - список новых объектов,
     * полученных путем перебора дочерних ссылок, из которых создается новый объект
     */
    public List<Parser> getChildren() {
        List<Parser> children = new ArrayList<>();
        Set<String> childrenUrls = new HashSet<>(); // список для отсечения лишних ссылок
        Set<String> links = getBaseLinks();

        try {
            // проверка на отсечение лишних ссылок, убираем переход на *.pdf
            if (!link.matches("[\\w\\W]*[.pdf]$")) {

                Document doc = Jsoup.connect(link).maxBodySize(0).get();
                Elements elements = doc.select("a");

                Thread.sleep(500);

                elements.forEach(element -> {

                    String url = element.absUrl("href");

                    // вторая проверка на отсечение лишних ссылок
                    if (url.matches(link + "[\\w\\W]+") && !childrenUrls.contains(url)) {

                        childrenUrls.add(url);
                        children.add(new Parser(url, links));
                    }
                });
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return children;
    }

    /**
     * @return возвращает список со дочерними ссылками, полученными по ссылке, переданной в конструктор
     */
    public Set<String> getLinks() {

        Set<String> links = getBaseLinks();

        try {

            // проверка на отсечение лишних ссылок, убираем переход на *.pdf
            if (!link.matches("[\\w\\W]*[.pdf]$")) {
                Document doc = Jsoup.connect(link).maxBodySize(0).get();
                Elements elements = doc.select("a");

                Thread.sleep(500);

                elements.forEach(element -> {

                    String url = element.absUrl("href");

                    // отсечение лишних ссылок
                    if (!links.contains(url) && url.matches(link + "[\\w\\W]+")) {
                        logger.info("\tlink added: \t" + url);
                        links.add(url);
                    }
                });
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return links;
    }

    /**
     * @return возвращает список имеющихся ссылок
     * если список пустой - добавляет в него ссылку, переданную в конструктор
     */
    public Set<String> getBaseLinks() {

        if (links.isEmpty()) {
            links.add(link);
            logger.info("\tlink added: \t" + link);
        }

        return links;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "link='" + link + '\'' +
                '}';
    }
}
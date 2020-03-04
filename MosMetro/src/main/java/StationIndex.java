import java.util.*;
import java.util.stream.Collectors;

public class StationIndex {

    /**
     * Данные, необходимые для работы объектов класса
     */
    private HashMap<String, Line> lines;
    private TreeSet<Station> stations;

    /**
     * Конструктор объекта
     */
    public StationIndex() {
        lines = new HashMap<>();
        stations = new TreeSet<>();
    }

    /**
     * Метод добавления станции
     * @param station - добавляемая станция
     */
    public void addStation(Station station)
    {
        stations.add(station);
    }

    /**
     * Метод добавления линии метро
     * @param line - добавляемая линия
     */
    public void addLine(Line line)
    {
        lines.put(line.getNumber(), line);
    }

    /**
     * Геттер для линии по номеру линии
     * @param number - номер линии
     */
    public Line getLine(String number) {
        return lines.get(number);
    }

    /**
     * Получение сортированного списка линии
     */
    public HashMap<String, Line> getLines() {
        return lines.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(String::hashCode)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}

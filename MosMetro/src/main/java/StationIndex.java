import java.util.*;
import java.util.stream.Collectors;

public class StationIndex {
    private HashMap<String, Line> lines;
    private TreeSet<Station> stations;

    public StationIndex() {
        lines = new HashMap<>();
        stations = new TreeSet<>();
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public void addLine(Line line)
    {
        lines.put(line.getNumber(), line);
    }

    public Line getLine(String number) {
        return lines.get(number);
    }

    public HashMap<String, Line> getLines() {
        return lines.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(String::hashCode)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ToJSON {
    /**
     * Данные для объектов класса
     */
    private List<Line> lines;
    private Map<String, List<String>> stations;
    private Set<Set<Station>> connections;

    /**
     * Констурктор мультиобъекта для создания JSON-файла
     * @param stations - список станций
     * @param connections - список переходов (пересадочных узлов)
     * @param lines - список линий
     */
    public ToJSON(Map<String, List<String>> stations, Set<Set<Station>> connections, List<Line> lines) {
        this.stations = stations;
        this.connections = connections;
        this.lines = lines;
    }

    /**
     * Геттеры и Сеттеры для переменных
     */
    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Map<String, List<String>> getStations() {
        return stations;
    }

    public void setStations(Map<String, List<String>> stations) {
        this.stations = stations;
    }

    public Set<Set<Station>> getConnections() {
        return connections;
    }

    public void setConnections(Set<Set<Station>> connections) {
        this.connections = connections;
    }

    /**
     * Переопределение метода toString()
     * @return возвращает строку
     */
    @Override
    public String toString() {
        return "ToJSON {" +
                "lines: " + lines +
                ", stations: " + stations +
                ", connections: " + connections +
                '}';
    }
}

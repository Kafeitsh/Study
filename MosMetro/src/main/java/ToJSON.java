import java.util.List;
import java.util.Map;
import java.util.Set;

public class ToJSON {
    private List<Line> lines;
    private Map<String, List<String>> stations;
    private Set<Set<Station>> connections;

    public ToJSON(Map<String, List<String>> stations, Set<Set<Station>> connections, List<Line> lines) {
        this.stations = stations;
        this.connections = connections;
        this.lines = lines;
    }

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

    @Override
    public String toString() {
        return "ToJSON {" +
                "lines: " + lines +
                ", stations: " + stations +
                ", connections: " + connections +
                '}';
    }
}

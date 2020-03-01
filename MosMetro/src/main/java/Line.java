import java.util.ArrayList;
import java.util.List;

public class Line {

    private String name;
    private String number;
    private List<Station> stations;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "Line {" +
                "number: " + number +
                ", name: " + name +
                '}';
    }
}

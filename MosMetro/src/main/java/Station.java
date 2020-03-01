import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Station implements Comparable<Station> {
    @SerializedName("number")
    private String lineNumber;
    @SerializedName("name")
    private String stationName;

    public Station(String lineNumber, String stationName) {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "Station {" +
                "line " + lineNumber + ": "
                + stationName +
                "}";
    }

    @Override
    public int hashCode() {
        int result = lineNumber.hashCode();
        result = result + stationName.hashCode();
        return result;
    }

    @Override
    public int compareTo(Station o) {
        return stationName.compareTo(o.getStationName());
    }
}

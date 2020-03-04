import com.google.gson.annotations.SerializedName;

public class Station implements Comparable<Station> {

    /**
     * Переменные объектов класса Station
     * Аннотации для корректной записи в JSON-файл
     */

    @SerializedName("number")
    private String lineNumber;
    @SerializedName("name")
    private String stationName;

    /**
     * Конструктор объекта Станции
     * @param lineNumber - номер линии
     * @param stationName - название станции
     */
    public Station(String lineNumber, String stationName) {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
    }

    /**
     * Геттеры и Сеттеры для переменных
     */
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

    /**
     * Переопределение метода toString()
     * @return возвращает строку
     */
    @Override
    public String toString() {
        return "Station {" +
                "line " + lineNumber + ": "
                + stationName +
                "}";
    }

    /**
     * Переопределение метода hashCode() для сортировки объектов
     */
    @Override
    public int hashCode() {
        int result = lineNumber.hashCode();
        result = result + stationName.hashCode();
        return result;
    }

    /**
     * Переопределение метода сравнения объектов
     */
    @Override
    public int compareTo(Station o) {
        return stationName.compareTo(o.getStationName());
    }
}

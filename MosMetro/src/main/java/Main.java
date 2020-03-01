import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String LINK = "https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String JSON_PATH = "src/main/resources/map.json";
    private static StationIndex stationIndex;

    public static void main(String[] args) {
        parseJson();
        printCountStationByLines();
    }

    private static List<Line> parseLinesFromHTML(String string) {
        List<Line> lines = new ArrayList<>();
        Map<String, String> map = new TreeMap<>();
        try {
            Document doc = Jsoup.connect(string).maxBodySize(0).get();
            Elements elements = doc.select("table.standard.sortable tbody tr");

            elements.forEach(e -> {
                Element element = e.select("td").first();
                if (!e.child(0).is("th")) {

                    String name = element.select("span").attr("title");
                    String number = element.selectFirst("span").text().replaceAll("^0", "");
                    map.put(number, name);
                }
            });
            map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(String::length)))
                    .collect(Collectors.toCollection(LinkedHashSet::new))
                    .forEach(e -> lines.add(new Line(e.getKey(), e.getValue())));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lines;
    }

    private static List<Station> parseStationsFromHTML(String string) {
        List<Station> stations = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(string).maxBodySize(0).get();
            Elements elements = doc.select("table.standard.sortable tbody tr");

            elements.forEach(e -> {
                if (!e.child(0).is("th")) {

                    String number = e.selectFirst("td").selectFirst("span").text().replaceAll("^0", "");
                    String name = e.select("td").get(1).selectFirst("a").text();

                    stations.add(new Station(number, name));
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stations;
    }

    private static Set<Set<Station>> parseConnectionsFromHTML(String string) {
        Set<Set<Station>> connections =  new TreeSet<>(Comparator.comparing(Set::hashCode));
        try {
            Document doc = Jsoup.connect(string).maxBodySize(0).get();
            Elements elements = doc.select("table.standard.sortable tbody tr");

            elements.forEach(e -> {
                if (!e.child(0).is("th")) {

                    if (!e.select("td").get(3).text().equals("")) {

                        Set<Station> stations = new HashSet<>();

                        String lineFrom = e.selectFirst("td").selectFirst("span").text()
                                .replaceAll("^0", "");
                        String stationFrom = e.select("td").get(1).selectFirst("a").text();

                        stations.add(new Station(lineFrom, stationFrom));

                        String lineTo = e.select("td").get(3).select("span").text()
                                .replaceAll("^0", "").trim();
                        String stationTo = null;

                        String[] temp = e.select("td").get(3).select("span")
                                .attr("title").split("станцию\\s");

                        if (lineTo.length() > 4) {
                            String[] lineToTemp = lineTo.split("\\s+");

                            for (int i = 0; i < lineToTemp.length; i++) {
                                lineTo = lineToTemp[i].replaceAll("^0", "").trim();
                                temp = e.select("td").get(3).select("span a").get(i)
                                        .attr("title").split("станцию\\s");
                                String[] temp2 = temp[1].split("\\s+");
                                for (Station station : parseStationsFromHTML(string)) {
                                    if (temp2[0].equalsIgnoreCase(station.getStationName())
                                            || (temp2[0] + " " + temp2[1]).equalsIgnoreCase(station.getStationName())
                                            || (temp2[0] + " " + temp2[1] + " " + temp2[2]).equalsIgnoreCase(station.getStationName())) {
                                        stationTo = station.getStationName();
                                        break;
                                    }
                                }
                                stations.add(new Station(lineTo, stationTo));
                            }
                        } else {
                            String[] temp2 = temp[1].split("\\s+");

                            for (Station station : parseStationsFromHTML(string)) {
                                if (temp2[0].equalsIgnoreCase(station.getStationName())
                                        || (temp2[0] + " " + temp2[1]).equalsIgnoreCase(station.getStationName())
                                        || (temp2[0] + " " + temp2[1] + " " + temp2[2]).equalsIgnoreCase(station.getStationName())) {
                                    stationTo = station.getStationName();
                                    break;
                                }
                            }
                            stations.add(new Station(lineTo, stationTo));
                        }
                        connections.add(stations);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connections;
    }

    private static Map<String, List<String>> stationsByLinesFromHTML(String string) {
        Map<String, List<String>> map = new TreeMap<>();
        Map<String, List<String>> sortedMap = new HashMap<>();
        try {
            parseLinesFromHTML(string).forEach(line -> {
                List<String> lines = new ArrayList<>();
                parseStationsFromHTML(string).forEach(station -> {
                    if(station.getLineNumber().equals(line.getNumber())) {
                        lines.add(station.getStationName());
                        line.addStation(station);
                    }
                });
                map.put(line.getNumber(), lines);
            });
            sortedMap = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(String::length)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sortedMap;
    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(JSON_PATH));
            lines.forEach(builder::append);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    private static void createJsonFile() {
        try {
            ToJSON generalObject = new ToJSON(stationsByLinesFromHTML(LINK), parseConnectionsFromHTML(LINK), parseLinesFromHTML(LINK));
            String json = GSON.toJson(generalObject);
            PrintWriter writer = new PrintWriter(JSON_PATH);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static void parseJson () {
        stationIndex = new StationIndex();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLinesFromJson(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStationsFromJson(stationsObject);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseLinesFromJson(JSONArray linesArray) {
        linesArray.forEach(lineObj -> {
            JSONObject lineJsonObject = (JSONObject) lineObj;
            Line line = new Line(lineJsonObject.get("number").toString(), lineJsonObject.get("name").toString());
            stationIndex.addLine(line);
        });
    }

    private static void parseStationsFromJson(JSONObject stationsObject) {
        stationsObject.keySet().forEach(lineNumber -> {
            String number = lineNumber.toString();
            Line line = stationIndex.getLine(number);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumber);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station(line.getNumber(), (String) stationObject);
                stationIndex.addStation(station);
                line.addStation(station);
            });
        });
    }

    private static void printCountStationByLines() {
        stationIndex.getLines().values().forEach(line -> {
            System.out.format("%40s%5s%15s%5s%n", line.getName() + " №", line.getNumber()
                    , ", количество станций: ", line.getStations().size());
        });
    }
}

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * программа обрабатывает банковскую выписку и выводит сводную информацию в консоль
 *
 * присутствует хардкодинг
 */
public class Main {

    // путь к выписке
    private static String movementList = "data/movementList.csv";
    // удобный для чтения формат
    private static DecimalFormat df = new DecimalFormat("###.00");

    public static void main(String[] args) {

        // выводит суммарный доход и суммарный расход
        getSummary();

        // выводит разбивку по категориям
        getStatement();

    }

    /**
     * метод выводит в консоль сообщение и список расходов по категориям
     */
    private static void getStatement() {
        try {
            System.out.println("\nРазбивка расходов по категориям:\n");
            categoriesSum().forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * метод группировки расходов по категориям
     *
     * @return возвращает список отформатированных строк со всей необходимой информацией и
     * итоговой суммой по каждой категории
     *
     * перебирается список объектов класса Movement (каждое движение ДС из выписки), затем
     * добавляется со слиянием в hashmap, где ключ - категория, сумма - значение
     *
     * затем в итоговый список строк добавляется отформатированная строка, содержащая категорию и сумму расходов
     */
    private static List<String> categoriesSum() {

        List<String> sum = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();

        try {

            // заполнение hashmap
            movementsList().forEach(movement -> {
                String[] parts = movement.getInfoAboutOperation()
                        .replaceAll("\\\\", "/")
                        .split("[0-9]*/\\s*", 2);
                String info = parts[1];
                double money = movement.getExpense();
                if(money != 0) {
                    map.merge(info, money, Double::sum);
                }
            });

            // добавление в итоговый список отформатированных строк
            map.keySet().forEach(key -> sum.add(String.format("%-35s%5s%15s", key, " - ",
                    df.format(map.get(key)) + " " + getMovementsCurrency())));

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return sum;
    }

    /**
     * метод преобразования выписки в список объектов класса Movement
     *
     * @return возвращает список объектов
     */
    private static List<Movement> movementsList() {

        List<Movement> movementsList = new ArrayList<>();

        try {
            // преобразование выписки в список строк
            List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(movementList)));

            // переборка списка строк, разбивка на элементы,
            // создание из каждой строки объекта Movement, добавление в конечный список
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] elements = line.split(",", 8);
                if(elements.length != 8) {
                    System.out.println("Wrong line: " + line);
                }
                String[] info = elements[5].split("\\s{4}", 3);
                movementsList.add(new Movement(
                        elements[1], elements[2], info[1],
                        Double.parseDouble(elements[6].replaceAll("[\"]", "")
                                .replaceAll(",", ".")),
                        Double.parseDouble(elements[7].replaceAll("[\"]", "")
                                .replaceAll(",", "."))));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return movementsList;
    }

    /**
     * @return возвращает суммарный доход по выписке в читаемом формате
     */
    private static String getMovementsIncome() {
        return df.format(movementsList().stream().mapToDouble(Movement::getIncome).sum());
    }

    /**
     * @return возвращает суммарный расход по выписке в читаемом формате
     */
    private static String getMovementsExpense() {
        return df.format(movementsList().stream().mapToDouble(Movement::getExpense).sum());
    }

    /**
     * @return возвращает валюту
     */
    private static String getMovementsCurrency() {
        return movementsList().get(0).getCurrency();
    }

    /**
     * @return возвращает номер счета
     */
    private static String getAccountNumber() {
        return movementsList().get(0).getAccountNumber();
    }

    /**
     * вывводит сообщения о суммарных дохода и расходах
     */
    private static void getSummary() {

        try {
            System.out.println("\nСуммарный доход по счету № " + getAccountNumber() + ": "
                    + getMovementsIncome() + " " + getMovementsCurrency());
            System.out.println("Суммарный расход по счету № " + getAccountNumber() + ": "
                    + getMovementsExpense() + " " + getMovementsCurrency());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

import java.sql.*;

/**
 * программа работает с учебной базой данных с использованием JDBC
 */
public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/skillbox?serverTimezone=Europe/Moscow";
        String user = "root";
        String pass = "testtest";
        String query = "SELECT course_name AS name, (SUM(temp.cnt)/COUNT(temp.date)) AS average FROM " +
                "(SELECT course_name, COUNT(subscription_date) AS cnt, " +
                "DATE_FORMAT(subscription_date, \"%m-%Y\") AS date FROM purchaselist GROUP BY 1,3) AS temp " +
                "GROUP BY 1;";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                String courseInfo = String.format("%-5s%-35s%-30s%10s%n", "Курс "
                        , resultSet.getString("name")
                        , " - среднее кол-во покупок в месяц: "
                        , resultSet.getString("average"));

                System.out.println(courseInfo);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

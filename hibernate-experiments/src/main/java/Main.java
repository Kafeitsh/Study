import SessionUtils.SessionCreator;
import SessionUtils.SessionFactoryCreator;
import SkillboxDatabase.*;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * программа работы с учебной базой данных с использованием hibernate
 */
public class Main {

    private static Session session;

    public static void main(String[] args) {

        session = SessionCreator.getSession();

        //=====================================================================

        // проверка работы класса Courses
        Course course = session.get(Course.class, 1);
        System.out.println(course.getName()  + ", направление: " + course.getType() +
                ". \nСтоимость курса: " + course.getPrice() + " рублей, длительность: " +
                course.getDuration() + " часов." +
                "\nСписок студентов: ");
        // проверка работы связей класса Courses
        course.getStudents().forEach(System.out::println);

        //=====================================================================

        // проверка работы класса Students
        Student student = session.get(Student.class, 4);
        System.out.println(student.getName() + " - " +
                student.getAge() + " лет, " +
                "Дата регистрации: " + new SimpleDateFormat("dd.MM.YYYY").format(student.getRegistrationDate()) +
                "\nCписок купленных курсов: ");
        // проверка работы связей класса Students
        student.getCourses().forEach(System.out::println);
        System.out.println("Потрачено денег на обучение: " +
                student.getCourses().stream().map(c -> c.getCourse().getPrice()).reduce(0, Integer::sum) +
                " рублей");

        //=====================================================================

        // проверка работы класса Subscription
        String hql1 = "FROM " + Subscription.class.getSimpleName() + " AS Sub WHERE Sub.student = '" +
                (session.get(Student.class, 6)).getId() + "'";
        List<Subscription> subscriptions = session.createQuery(hql1).setMaxResults(5).getResultList();
        subscriptions.forEach(System.out::println);
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription.getSubscriptionPK());
        }

        //=====================================================================

        // проверка работы класса PurchaseList

        String hqlQuery = "FROM " + Purchase.class.getSimpleName();
        List<Purchase> purchaseList = session.createQuery(hqlQuery).getResultList();
        purchaseList.forEach(System.out::println);

        //=====================================================================

        // проверка содерджимого таблицы LinkedPurchaseList

        String hqlCheck = "FROM " + LinkedPurchase.class.getSimpleName();
        List<LinkedPurchase> linkedPurchaseList = session.createQuery(hqlCheck).getResultList();
        linkedPurchaseList.forEach(System.out::println);
        System.out.println(linkedPurchaseList.size());

        //=====================================================================

        SessionFactoryCreator.sessionFactoryClose();

    }
}

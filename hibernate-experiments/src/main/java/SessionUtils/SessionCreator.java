package SessionUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionCreator {

    private static SessionFactory sessionFactory;
    private static Session session;

    public static Session getSession() {
        sessionFactory = SessionFactoryCreator.getSessionFactory();
        session = sessionFactory.openSession();
        return session;
    }

}

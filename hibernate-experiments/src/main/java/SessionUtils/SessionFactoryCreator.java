package SessionUtils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryCreator {
    private static final String HIBERNATE_CFG_PATH = "hibernate.cfg.xml";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(HIBERNATE_CFG_PATH).build();
            Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void sessionFactoryClose() {
        sessionFactory.close();
    }

}

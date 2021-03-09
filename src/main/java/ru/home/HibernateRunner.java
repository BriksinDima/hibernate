package ru.home;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.home.entity.User;
import ru.home.util.HibernateUtil;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {
        User usr = User.builder()
                .username("ivan@gmal.com")
                .lastname("Ivanov")
                .firstname("Ivan")
                .build();
        log.info("Uses entity is in transient state, object: {}", usr.getUsername());

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                log.trace("Transaction is created, {}", usr);

                session1.saveOrUpdate(usr);
                log.trace("Uses is in persistent state: {}, session {}", usr, session1);

                session1.getTransaction().commit();
            }

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                usr.setFirstname("Sveta");
                // session2.refresh(usr); update usr <- refreshedUser
                // session2.merge(usr); update urs -> refreshUser
                session2.getTransaction().commit();
            }
        }
    }
}

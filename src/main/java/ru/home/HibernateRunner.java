package ru.home;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.home.entity.User;
import ru.home.util.HibernateUtil;

public class HibernateRunner {

    public static void main(String[] args) {
        User usr = User.builder()
                .username("ivan@gmal.com")
                .lastname("Ivanov")
                .firstname("Ivan")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(usr);
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

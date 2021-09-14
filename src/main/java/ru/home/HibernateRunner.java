package ru.home;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.home.entity.PersonalInfo;
import ru.home.entity.User;
import ru.home.util.HibernateUtil;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        User usr = User.builder()
                .username("ivan@gmal.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Ivanov")
                        .firstname("Ivan")
                        .build())
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

        }
    }
}

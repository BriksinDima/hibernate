package ru.home;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.home.converter.BirthdayConverter;
import ru.home.entity.Birthday;
import ru.home.entity.Role;
import ru.home.entity.User;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.registerTypeOverride(new JsonBinaryType());
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // session.evict(user); Удалить из 1 уровня кэша конкретную сущность
//            session.clear(); очистить весь кэш 1 уровня
            // session.flush();  Если хотим синхронизировать изменения сделанные в сущности с базой данных.
//            User user = User.builder()
//                    .username("sffsadf.gma.ru")
//                    .firstname("Ivan")
//                    .lastname("Ivanov")
//                    .info("{\"name\": \"Ivan\"}")
//                    .birthDate(new Birthday(LocalDate.of(2001, 1, 19)))
//                    .role(Role.ADMIN)
//                    .build();
//            session.save(user);
            User user = session.get(User.class, "ivan@gmail.com");

            session.getTransaction().commit();
        }
    }
}

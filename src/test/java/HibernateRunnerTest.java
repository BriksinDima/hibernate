import org.junit.jupiter.api.Test;
import ru.home.entity.User;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws IllegalAccessException, SQLException {
        User user = User.builder()
                .username("sffsadf.gma.ru")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2001, 1, 19))
                .age(22)
                .build();

        String sql = "INSERT INTO %s (%s) values (%s)";

        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation-> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());
        Field[] declaredFields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect((Collectors.joining(", ")));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        System.out.println(String.format(sql, tableName, columnNames, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(String.format(sql, tableName, columnNames, columnValues));
        for(Field field : declaredFields){
          field.setAccessible(true);
            preparedStatement.setObject(1, field.get(user));
        }
    }

}
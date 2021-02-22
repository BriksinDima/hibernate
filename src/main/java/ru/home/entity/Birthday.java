package ru.home.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Birthday {

    LocalDate birthday;

    public Birthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public long getAge(){
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}

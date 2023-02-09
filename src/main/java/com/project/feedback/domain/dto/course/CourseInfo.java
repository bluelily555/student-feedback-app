package com.project.feedback.domain.dto.course;

import com.project.feedback.domain.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInfo {
    private Long id;
    private String name;
    private int week;
    private int dayOfWeek;

    public static CourseInfo fromEntity(CourseEntity entity) {
        return new CourseInfo(entity.getId(), entity.getName(), getWeek(entity.getStartDate()), getDayOfWeek());
    }

    public static int getDayOfWeek(){
        //월:1 ~ 일:7
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.format(formatter);
        int week = date.getDayOfWeek().getValue();
        return week;
    }

    public static int getWeek(LocalDate startDate){
        //현재 날짜 setting
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.format(formatter);

        int dayOfWeek = getDayOfWeek();
        if(dayOfWeek != 1){
            switch (dayOfWeek){
                case 2:
                    startDate.minusDays(1);
                case 3:
                    startDate.minusDays(2);
                case 4:
                    startDate.minusDays(3);
                case 5:
                    startDate.minusDays(4);
                case 6:
                    startDate.minusDays(5);
                case 7:
                    startDate.minusDays(6);
            }
        }

        Period period = Period.between(startDate, date);
        period.getDays(); //일수
        int day =  period.getDays();
        return (day / 7) + 1;
    }

}

package com.project.feedback.domain.dto.course;

import com.project.feedback.domain.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInfo {
    private Long id;
    private String name;
    private long week;
    private int dayOfWeek;

    public static CourseInfo fromEntity(CourseEntity entity) {
        return new CourseInfo(entity.getId(), entity.getName(), getWeek(entity.getStartDate()), getDayOfWeek());
    }

    public static int getDayOfWeek(){
        //월:1 ~ 일:7
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date.format(formatter);
        int week = date.getDayOfWeek().getValue();
        return week;
    }

    public static long getWeek(LocalDate startDate){
        //현재 날짜 setting
        LocalDate date = LocalDate.now();
        int cur_dayOfWeek = startDate.getDayOfWeek().getValue();
        int dayOfWeek = date.getDayOfWeek().getValue();

        int minus = 0;
        int cur_minus = 0;
        if(dayOfWeek != 1){
            minus = dayOfWeek - 1;
            date = date.minusDays(minus);
        }
        if(cur_dayOfWeek != 1){
            cur_minus = cur_dayOfWeek - 1;
            startDate = startDate.minusDays(cur_minus);
        }
        long day = Duration.between(startDate.atStartOfDay(),date.atStartOfDay()).toDays();
        return (day/ 7) + 1;
    }

}

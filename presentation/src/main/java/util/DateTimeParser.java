package util;

import java.time.LocalDateTime;

public class DateTimeParser {
    public static LocalDateTime parse(String date){
        String[] array = date.split(" ");
        String dayMonthYear = array[0];
        String hourMin = array[1];
        String amOrPm = array[2];

        int month = Integer.parseInt(dayMonthYear.split("/")[0]);
        int day = Integer.parseInt(dayMonthYear.split("/")[1]);
        int year = Integer.parseInt(dayMonthYear.split("/")[2]);

        int hour = amOrPm.equalsIgnoreCase("PM") ?
                (Integer.parseInt(hourMin.split(":")[0]) != 12 ?
                        Integer.parseInt(hourMin.split(":")[0]) +12
                            : Integer.parseInt(hourMin.split(":")[0]))
                                : (Integer.parseInt(hourMin.split(":")[0]) != 12 ?
                                        Integer.parseInt(hourMin.split(":")[0]) : 0);
        int min = Integer.parseInt(hourMin.split(":")[1]);

        return LocalDateTime.of(year, month, day, hour, min);
    }
}

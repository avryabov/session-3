package ru.sbt.jschool.session3.problem2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class ParkingService {

    private Map<Long, Long> cars = new HashMap<>();
    private long capacity;
    private double price;
    private long count = 0L;

    public ParkingService(long capacity, double price) {
        this.capacity = capacity;
        this.price = price;
    }

    public boolean entry(Long id, Long time) {
        if (count >= capacity)
            return false;
        if (cars.containsKey(id))
            return false;
        cars.put(id, time);
        count++;
        return true;
    }

    public double exit(Long id, Long time) {
        double cost = (price * calcDayHours(cars.get(id), time) + price * 2 * calcNightHours(cars.get(id), time));
        cars.remove(id);
        return cost;
    }

    private long calcAbsoluteHours(Long time1, Long time2) {
        long result = (long) Math.ceil((time2 - time1) / 1000.0 / 60.0 / 60.0);
        if (result < 0)
            result = 0;
        return result;
    }

    private long calcHours(Long time1, Long time2) {
        long result;
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);
        long startHour = date1.getHours();
        long endHour = date2.getHours();
        boolean oneDay = date1.getDay() == date2.getDay();
        date1.setHours(24);
        date2.setHours(0);

        long hoursFullDay = (calcAbsoluteHours(date1.getTime(), date2.getTime()));

        if (hoursFullDay == 0) {
            result = endHour - startHour + 1;
            if (!oneDay)
                result += 24;
        } else
            result = hoursFullDay + (24 - startHour) + (endHour + 1);

        return result;
    }

    private long calcNightHours(Long time1, Long time2) {
        long result = 0;
        Date date1 = new Date(time1);
        Date date2 = new Date(time2);
        long startHour = date1.getHours();
        long endHour = date2.getHours();

        boolean oneDay = date1.getDay() == date2.getDay();
        date1.setHours(24);
        date2.setHours(0);

        long nightHourFullDay = (calcAbsoluteHours(date1.getTime(), date2.getTime())) / 24 * 7;

        if (startHour < 6) {
            result += 6 - startHour;
        }

        if (!oneDay) {
            result += 1;
            if (endHour < 6)
                result += endHour + 1;
            if (endHour >= 6 && endHour < 23)
                result += 6;
            if (endHour >= 23)
                result += 7;
        }
        result += nightHourFullDay;
        return result;
    }


    private long calcDayHours(Long time1, Long time2) {
        return calcHours(time1, time2) - calcNightHours(time1, time2);
    }
}

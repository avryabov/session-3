package ru.sbt.jschool.session3;

import org.junit.Test;
import ru.sbt.jschool.session3.problem2.ParkingService;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class ParkingServiceTest {

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testEntryAndExit() throws Exception {
        Boolean result;
        Double cost;

        ParkingService parkingService = new ParkingService(5, 77.1);

        result = parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 9:00:01").getTime());

        assertEquals(true, result);

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-28 9:59:15").getTime());

        assertEquals(77.1, cost, .01d);
    }

    @Test
    public void testEntryExitEntry() throws Exception {
        Boolean result;
        Double cost;

        ParkingService parkingService = new ParkingService(5, 77.1);

        result = parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 9:00:01").getTime());

        assertEquals(true, result);

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-28 9:59:15").getTime());

        assertEquals(77.1, cost, .01d);

        result = parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 9:00:01").getTime());

        assertEquals(true, result);
    }

    @Test
    public void testCapacity(){

        ParkingService parkingService = new ParkingService(100, 1);

        for (int i = 0; i < 100; i++) {
            assertEquals(true, parkingService.entry((long) i, i * 100L));
        }

        assertEquals(false, parkingService.entry(100L, 100L));
    }

    @Test
    public void testDoubleEntry(){

        ParkingService parkingService = new ParkingService(100, 1);

        assertEquals(true, parkingService.entry(1L, 100L));

        assertEquals(false, parkingService.entry(1L, 100L));
    }

    @Test
    public void testNightParkingNeighborDay() throws Exception {
        Double cost;

        ParkingService parkingService = new ParkingService(100, 1);

        parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 22:50:01").getTime());

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-29 23:01:15").getTime());

        assertEquals(34.0, cost, .01d);

        parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 03:50:01").getTime());

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-29 23:01:15").getTime());

        assertEquals(56.0, cost, .01d);
    }

    @Test
    public void testNightParkingDifferentDay() throws Exception {
        Double cost;

        ParkingService parkingService = new ParkingService(100, 1);

        parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 22:50:01").getTime());

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-30 23:01:15").getTime());

        assertEquals(65.0, cost, .01d);

        parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 03:50:01").getTime());

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-30 23:01:15").getTime());

        assertEquals(87.0, cost, .01d);
    }

    @Test
    public void testNightParkingOneDay() throws Exception {
        Double cost;

        ParkingService parkingService = new ParkingService(100, 1);

        parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 2:50:01").getTime());

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-28 22:01:15").getTime());

        assertEquals(25.0, cost, .01d);

        parkingService.entry(1L, simpleDateFormat.parse("2018-03-28 10:00:01").getTime());

        cost = parkingService.exit(1L, simpleDateFormat.parse("2018-03-28 19:59:15").getTime());

        assertEquals(10.0, cost, .01d);
    }

}

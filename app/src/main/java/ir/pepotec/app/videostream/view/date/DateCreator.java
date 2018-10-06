package ir.pepotec.app.videostream.view.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by M-gh on 03-Sep-17.
 */

public class DateCreator {

    public static String todayDate() {
        SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mountFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        String currentYear = yearsFormat.format(new Date(System.currentTimeMillis()));
        String currentMount = mountFormat.format(new Date(System.currentTimeMillis()));
        String currentDay = dayFormat.format(new Date(System.currentTimeMillis()));
        int cY = Integer.parseInt(currentYear);
        int cM = Integer.parseInt(currentMount);
        int cD = Integer.parseInt(currentDay);
        Roozh currentroozh = new Roozh();
        currentroozh.GregorianToPersian(cY, cM, cD);

        return currentroozh.toString();
    }

    public static String futureDate_day(int nextTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, nextTime);

        SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mountFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String nextYear = yearsFormat.format(new Date(calendar.getTimeInMillis()));
        String nextMount = mountFormat.format(new Date(calendar.getTimeInMillis()));
        String nextDay = dayFormat.format(new Date(calendar.getTimeInMillis()));
        int nY = Integer.parseInt(nextYear);
        int nM = Integer.parseInt(nextMount);
        int nD = Integer.parseInt(nextDay);
        Roozh nextroozh = new Roozh();
        nextroozh.GregorianToPersian(nY, nM, nD);

        return nextroozh.toString();
    }

    public static String futureDate_month(int nextTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, nextTime);

        SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mountFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String nextYear = yearsFormat.format(new Date(calendar.getTimeInMillis()));
        String nextMount = mountFormat.format(new Date(calendar.getTimeInMillis()));
        String nextDay = dayFormat.format(new Date(calendar.getTimeInMillis()));
        int nY = Integer.parseInt(nextYear);
        int nM = Integer.parseInt(nextMount);
        int nD = Integer.parseInt(nextDay);
        Roozh nextroozh = new Roozh();
        nextroozh.GregorianToPersian(nY, nM, nD);

        return nextroozh.toString();
    }

    public static String futureDate_year(int nextTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, nextTime);

        SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mountFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String nextYear = yearsFormat.format(new Date(calendar.getTimeInMillis()));
        String nextMount = mountFormat.format(new Date(calendar.getTimeInMillis()));
        String nextDay = dayFormat.format(new Date(calendar.getTimeInMillis()));
        int nY = Integer.parseInt(nextYear);
        int nM = Integer.parseInt(nextMount);
        int nD = Integer.parseInt(nextDay);
        Roozh nextroozh = new Roozh();
        nextroozh.GregorianToPersian(nY, nM, nD);

        return nextroozh.toString();
    }

    public static String future_month_long(long nextTime, int next_month) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mountFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        calendar.setTimeInMillis(nextTime);
        calendar.add(Calendar.MONTH, next_month);
        String nextYear = yearsFormat.format(new Date(calendar.getTimeInMillis()));
        String nextMount = mountFormat.format(new Date(calendar.getTimeInMillis()));
        String nextDay = dayFormat.format(new Date(calendar.getTimeInMillis()));
        int nY = Integer.parseInt(nextYear);
        int nM = Integer.parseInt(nextMount);
        int nD = Integer.parseInt(nextDay);
        Roozh nextroozh = new Roozh();
        nextroozh.GregorianToPersian(nY, nM, nD);
        return nextroozh.toString();
    }

    public static String future_year_long(long nextTime, int next_year) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mountFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        calendar.setTimeInMillis(nextTime);
        calendar.add(Calendar.YEAR, next_year);
        String nextYear = yearsFormat.format(new Date(calendar.getTimeInMillis()));
        String nextMount = mountFormat.format(new Date(calendar.getTimeInMillis()));
        String nextDay = dayFormat.format(new Date(calendar.getTimeInMillis()));
        int nY = Integer.parseInt(nextYear);
        int nM = Integer.parseInt(nextMount);
        int nD = Integer.parseInt(nextDay);
        Roozh nextroozh = new Roozh();
        nextroozh.GregorianToPersian(nY, nM, nD);
        return nextroozh.toString();
    }
}

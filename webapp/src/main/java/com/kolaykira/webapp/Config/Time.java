package com.kolaykira.webapp.Config;



import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Time {


    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static int generateRemainingDays(Timestamp startDay, Timestamp finishDay)
    {
        return 0;
    }


    public static LocalDate getCurrentMonthDate(int dayOfMonth) {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Convert to LocalDateTime
        LocalDateTime currentDateTime = Instant.ofEpochMilli(currentTimeMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Get the current date
        LocalDate currentDate = currentDateTime.toLocalDate();

        // Create a new date with the specified day in the current month
        return LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayOfMonth);
    }

    public static LocalDate getNextMonthDate(int dayOfMonth) {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Convert to LocalDateTime
        LocalDateTime currentDateTime = Instant.ofEpochMilli(currentTimeMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Get the current date
        LocalDate currentDate = currentDateTime.toLocalDate();

        // Create a new date with the specified day in the next month
        return LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayOfMonth)
                .plusMonths(1);
    }

    public static int getDayOfMonthFromTimestamp(Timestamp timestamp) {
        // Check for null timestamp
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        // Convert Timestamp to LocalDateTime
        java.time.LocalDateTime localDateTime = timestamp.toLocalDateTime();

        // Get the day of the month
        return localDateTime.getDayOfMonth();
    }


    public static int getNextMonthLength(Timestamp timestamp) {
        // Convert Timestamp to LocalDate
        LocalDate currentDate = timestamp.toLocalDateTime().toLocalDate();

        // Get the length of the next month
        return currentDate.plusMonths(1).lengthOfMonth();
    }

    public static int getMonthLengthAfter(Timestamp timestamp, int numberOfMonths) {
        // Convert Timestamp to LocalDate
        LocalDate currentDate = timestamp.toLocalDateTime().toLocalDate();

        // Get the length of the month after the specified number of months
        return currentDate.plusMonths(numberOfMonths).lengthOfMonth();
    }

    public static LocalDate getNextMonthDate(Timestamp timestamp, int monthsLater, int dayOfMonth) {
        // Convert Timestamp to LocalDateTime
        LocalDateTime currentDateTime = timestamp.toLocalDateTime();

        // Get the current date
        LocalDate currentDate = currentDateTime.toLocalDate();

        // Create a new date with the specified day in the next month
        return LocalDate.of(currentDate.getYear(), currentDate.getMonth(), dayOfMonth)
                .plusMonths(monthsLater);
    }
    public static Timestamp createContractEndDate(Timestamp startDate, int monthLength)
    {
       return Time.createDayOfMonth(startDate, Time.getDayOfMonthFromTimestamp(startDate), monthLength);
    }


    public static Timestamp createDayOfMonth(Timestamp startDate, int dayOfTheMonth, int monthLength)
    {
        LocalDate localDate;
        int startDayOfMonth = dayOfTheMonth;
        int lengthOfMonthEndDate = Time.getMonthLengthAfter(startDate, monthLength);
        if(startDayOfMonth > lengthOfMonthEndDate)
        {
            localDate = getNextMonthDate(startDate,monthLength,lengthOfMonthEndDate);
        }
        else
        {
            localDate = getNextMonthDate(startDate,monthLength,startDayOfMonth);
        }

        System.out.println( localDate);
        return Time.convertToLocalDateToTimestamp(localDate);
    }

    public static Timestamp convertToLocalDateToTimestamp(LocalDate localDate) {
        // Assuming you want to set the time to midnight (00:00:00)
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(23, 59, 59, 999_000_000));
        return Timestamp.valueOf(localDateTime);
    }


    public static int getCeilingMonthDifference(LocalDateTime smallerDate, LocalDateTime biggerDate) {
        // Calculate the period between the two datetimes
        Period period = Period.between(smallerDate.toLocalDate(), biggerDate.toLocalDate());

        // If there are remaining days in the month of biggerDate, consider it as an additional month
        if (smallerDate.getDayOfMonth() <= biggerDate.getDayOfMonth()) {
            return period.getMonths();
        } else {
            return period.getMonths() + 1;
        }
    }
}


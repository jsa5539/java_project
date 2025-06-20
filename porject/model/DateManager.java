package model;

import java.util.Calendar;

public class DateManager {
    private Calendar calendar;
    private String day = "";

    public DateManager() {
        calendar = Calendar.getInstance();
    }

    public void changeMonth(int offset) {
        calendar.add(Calendar.MONTH, offset);
    }

    public void changeDate(int offset) {
        calendar.add(Calendar.DATE, offset);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /*public void setDay(String index) {
        this.day = index;
    }*/
    public void setDay(String dayStr) {
        int day = Integer.parseInt(dayStr);  // String → int 변환
        calendar.set(Calendar.DATE, day);   // calendar 내부 날짜 설정
    }

    public int getDay() {
    return calendar.get(Calendar.DATE); // 현재 calendar 객체의 "일" 가져오기
    }
    /* 
    public String getDay() {
        return this.day;
    }*/

    public int getFirstDayOfWeek() {
        calendar.set(Calendar.DATE, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getLastDateOfMonth() {
        return calendar.getActualMaximum(Calendar.DATE);
    }
}

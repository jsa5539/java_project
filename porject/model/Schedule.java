package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Schedule extends DateManager {
    int year;
    int month;
    int day;
    String date;
    public String sleepTime = "0.0";
    public String exerciseTime = "0.0";
    public String carb = "0.0";
    public String protein = "0.0";
    public String fat = "0.0";
    public String water = "0.0";
    boolean isMale = true;

    public List<Double> sleepTime_list = new ArrayList<>();
    public List<Double> exerciseTime_list = new ArrayList<>();
    public List<Double> carb_list = new ArrayList<>();
    public List<Double> protein_list = new ArrayList<>();
    public List<Double> fat_list = new ArrayList<>();
    public List<Double> water_list = new ArrayList<>();
    public List<String> schedule_date = new ArrayList<>();
    public List<String> schedule_note = new ArrayList<>();
    public List<Integer> score_list = new ArrayList<>();

    public void setSchedule(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.date = year + "/" + month + "/" + day;
    }

    public void addNote() {
        int idx = schedule_date.indexOf(date);
        if (idx != -1) {
            String existingNote = schedule_note.get(idx);
            if (existingNote != null && !existingNote.trim().equals("메모 없음")) {
                JOptionPane.showMessageDialog(null, "이미 등록된 일정입니다.");
                return;
            }
            String newNote = JOptionPane.showInputDialog(null, "메모를 추가합니다:");
            if (newNote != null && !newNote.trim().isEmpty()) {
                schedule_note.set(idx, newNote.trim());
            }
        } else {
            String note = JOptionPane.showInputDialog(null, "메모를 입력하세요:");
            if (note != null && !note.trim().isEmpty()) {
                schedule_date.add(date);
                schedule_note.add(note.trim());
                sleepTime_list.add(0.0);
                exerciseTime_list.add(0.0);
                carb_list.add(0.0);
                protein_list.add(0.0);
                fat_list.add(0.0);
                water_list.add(0.0);
                score_list.add(0);
            }
        }
    }

    public void setNote(String note) {
        if (schedule_date.contains(date)) {
            int idx = schedule_date.indexOf(date);
            schedule_note.set(idx, note);
        } else {
            schedule_date.add(date);
            schedule_note.add(note);
            sleepTime_list.add(0.0);
            exerciseTime_list.add(0.0);
            carb_list.add(0.0);
            protein_list.add(0.0);
            fat_list.add(0.0);
            water_list.add(0.0);
            score_list.add(0);
        }
    }

    public void saveHealth() {
        if (schedule_date.contains(date)) {
            JOptionPane.showMessageDialog(null, "기존 데이터를 덮어씁니다.");
            for (int i = 0; i < schedule_date.size(); i++) {
                if (schedule_date.get(i).equals(date)) {
                    sleepTime_list.set(i, Double.parseDouble(this.sleepTime));
                    exerciseTime_list.set(i, Double.parseDouble(this.exerciseTime));
                    carb_list.set(i, Double.parseDouble(this.carb));
                    protein_list.set(i, Double.parseDouble(this.protein));
                    fat_list.set(i, Double.parseDouble(this.fat));
                    water_list.set(i, Double.parseDouble(this.water));
                    return;
                }
            }
        } else {
            schedule_date.add(date);
            schedule_note.add("메모 없음");
            sleepTime_list.add(Double.parseDouble(this.sleepTime));
            exerciseTime_list.add(Double.parseDouble(this.exerciseTime));
            carb_list.add(Double.parseDouble(this.carb));
            protein_list.add(Double.parseDouble(this.protein));
            fat_list.add(Double.parseDouble(this.fat));
            water_list.add(Double.parseDouble(this.water));
            score_list.add(0);
        }
    }

    public String getNote() {
        for (int i = 0; i < schedule_date.size(); i++) {
            if (schedule_date.get(i).equals(date)) {
                return "<html>" +
                        "수면시간: " + sleepTime_list.get(i) + "<br>" +
                        "운동시간: " + exerciseTime_list.get(i) + "<br>" +
                        "탄수화물: " + carb_list.get(i) + "<br>" +
                        "단백질: " + protein_list.get(i) + "<br>" +
                        "지방: " + fat_list.get(i) + "<br>" +
                        "물: " + water_list.get(i) + "<br>" +
                        "메모: " + schedule_note.get(i) +
                        "</html>";
            }
        }
        return null;
    }

    public void removeNote() {
        for (int i = 0; i < schedule_date.size(); i++) {
            if (schedule_date.get(i).equals(date)) {
                schedule_date.remove(i);
                schedule_note.remove(i);
                sleepTime_list.remove(i);
                exerciseTime_list.remove(i);
                carb_list.remove(i);
                protein_list.remove(i);
                fat_list.remove(i);
                water_list.remove(i);
                score_list.remove(i);
                return;
            }
        }
    }

    // Setter 메서드
    public void setSleepTime(String sleep) { this.sleepTime = sleep; }
    public void setExcerciseTime(String exercise) { this.exerciseTime = exercise; }
    public void setCarb(String carb) { this.carb = carb; }
    public void setProtein(String protein) { this.protein = protein; }
    public void setFat(String fat) { this.fat = fat; }
    public void setWater(String water) { this.water = water; }
    public void setGender(boolean isMale) { this.isMale = isMale; }

    // Score 객체 생성을 위한 getter 메서드
    public Score getScore() {
        int idx = schedule_date.indexOf(date);
        if (idx != -1) {
            return new Score(
                sleepTime_list.get(idx),
                exerciseTime_list.get(idx),
                carb_list.get(idx),
                protein_list.get(idx),
                fat_list.get(idx),
                water_list.get(idx),
                isMale
            );
        }
        return new Score(0, 0, 0, 0, 0, 0, isMale);
    }

    // 특정 날짜의 점수 반환
    public int getScoreForToday() {
        int idx = schedule_date.indexOf(date);
        if (idx != -1 && idx < score_list.size()) {
            return score_list.get(idx);
        }
        return 0;
    }

    public void setScoreForToday(int score) {
        int idx = schedule_date.indexOf(date);
        if (idx != -1) {
            while (score_list.size() <= idx) score_list.add(0);
            score_list.set(idx, score);
        }
    }
} 

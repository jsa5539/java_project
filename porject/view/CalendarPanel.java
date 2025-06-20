package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Savepoint;
import javax.swing.*;

import model.DateManager;
import model.Schedule;
import model.Score;



public class CalendarPanel extends JPanel {
    private final DateManager dateManager = new DateManager();
    public final Schedule schedule = new Schedule();
    private final JLabel titleLabel = new JLabel();
    private final JButton[] dateButtons = new JButton[42]; //날짜버튼튼
    public JButton addButton = new JButton(); //메모추가버튼
    public JButton removeButton = new JButton(); //메모삭제 버튼튼
    private final JLabel note = new JLabel(); //메모를 표시할 레이블
    private final JLabel socreNote = new JLabel();
    private final JButton go_month = new JButton(); //월별표시로 전환환
    private final JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 행, 열, 간격

    private final JTextField sleepTime = new JTextField();
    private final JTextField exerciseTime = new JTextField();
    private final JTextField mealCount = new JTextField();
    private final JButton saveButton = new JButton("save");

    private final JTextField carbField = new JTextField();
    private final JTextField proteinField = new JTextField();
    private final JTextField fatField = new JTextField();
    private final JTextField waterField = new JTextField();
    private final JRadioButton maleButton = new JRadioButton("남성");
    private final JRadioButton femaleButton = new JRadioButton("여성");
    private final ButtonGroup genderGroup = new ButtonGroup();


    public CalendarPanel() {
        setLayout(null);
        setupTitle();
        setupNavigationButtons();
        setupDateButtons();
        updateCalendar();
        setupFormPanel();
        AddButtonListener();
        removeButtonListener();
        
    }

    private void setupTitle() {
        titleLabel.setBounds(200, 20, 200, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
    }

    private void setupNavigationButtons() {
        JButton prevButton = new JButton("<"); //이전달
        JButton nextButton = new JButton(">"); //다음달

        prevButton.setBounds(100, 20, 60, 30);
        nextButton.setBounds(420, 20, 60, 30);

        add(prevButton);
        add(nextButton);

        prevButton.addActionListener(e -> {
                dateManager.changeMonth(-1);
                updateCalendar();

        });

        nextButton.addActionListener(e -> { //다음달

                dateManager.changeMonth(1);
                updateCalendar();
        
        });
    }

    private void setupDateButtons() {

        for (int i = 0; i < 42; i++) {            

            JButton btn = new JButton();
            btn.setBounds((i % 7) * 70 + 50, (i / 7) * 50 + 70, 70, 50);

            btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            for (JButton b : dateButtons) {
                b.setVisible(false);
            }
            dateManager.setDay(btn.getText());//날짜버튼을 눌렀을때 날짜 설정정
            schedule.setSchedule(dateManager.getYear(), dateManager.getMonth(), dateManager.getDay());//누른 날짜를 보냄냄
            //System.out.println(dateManager.getDay());
            updateDate();
            }
        });
        go_month.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCalendar();
            }
        });

            add(go_month);
            dateButtons[i] = btn;
            add(btn);
        }
    }

    private void updateCalendar() {//월별 달력 업데이트트
        int year = dateManager.getYear();
        int month = dateManager.getMonth();
        titleLabel.setText(year + "/" + month);
        formPanel.setVisible(false);
        go_month.setVisible(false);
        addButton.setVisible(false);
        removeButton.setVisible(false);
        note.setVisible(false);
        saveButton.setVisible(false);
        socreNote.setVisible(false);
        
        int startIndex = dateManager.getFirstDayOfWeek() - 1;
        int lastDate = dateManager.getLastDateOfMonth();

        for (int i = 0; i < 42; i++) { //버튼 보이거나 안보이거나 설정
            if (i >= startIndex && i < startIndex + lastDate) {
                int day = i - startIndex + 1;
                String thisDate = year + "/" + month + "/" + day;
                dateButtons[i].setText(String.valueOf(i - startIndex + 1));
                dateButtons[i].setEnabled(true);
                dateButtons[i].setVisible(true);

                schedule.setSchedule(year, month, day);
                int score = schedule.getScoreForToday();
                if (!schedule.schedule_date.contains(thisDate)) {
                    dateButtons[i].setBackground(new Color(173, 216, 230)); // 아직 입력 안된 날
                } else if (score >= 80) {
                    dateButtons[i].setBackground(java.awt.Color.GREEN);
                } else if (score >= 50) {
                    dateButtons[i].setBackground(java.awt.Color.ORANGE);
                } else {
                    dateButtons[i].setBackground(java.awt.Color.RED);
                }
            } else {
                dateButtons[i].setText("");
                dateButtons[i].setEnabled(false);
                dateButtons[i].setVisible(false);
            }
        }
    }

private void setupFormPanel() {
        maleButton.setSelected(true);
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        formPanel.add(new JLabel("성별"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel);

        saveButton.setBounds(450, 70, 80, 30);
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBounds(150, 100, 300, 240);

        formPanel.add(new JLabel("수면시간 (h)"));
        formPanel.add(sleepTime);
        formPanel.add(new JLabel("운동시간 (min)"));
        formPanel.add(exerciseTime);
        formPanel.add(new JLabel("탄수화물 (g)"));
        formPanel.add(carbField);
        formPanel.add(new JLabel("단백질 (g)"));
        formPanel.add(proteinField);
        formPanel.add(new JLabel("지방 (g)"));
        formPanel.add(fatField);
        formPanel.add(new JLabel("물 (mL)"));
        formPanel.add(waterField);

        saveButton.addActionListener(e -> {
            String sleepInput = sleepTime.getText().trim();
            String exerciseInput = exerciseTime.getText().trim();
            String carbInput = carbField.getText().trim();
            String proteinInput = proteinField.getText().trim();
            String fatInput = fatField.getText().trim();
            String waterInput = waterField.getText().trim();

            if (sleepInput.isEmpty()) sleepInput = "0.0";
            if (exerciseInput.isEmpty()) exerciseInput = "0.0";
            if (carbInput.isEmpty()) carbInput = "0.0";
            if (proteinInput.isEmpty()) proteinInput = "0.0";
            if (fatInput.isEmpty()) fatInput = "0.0";
            if (waterInput.isEmpty()) waterInput = "0.0";

            try {
                schedule.setSleepTime(sleepInput);
                schedule.setExcerciseTime(exerciseInput);
                schedule.setCarb(carbInput);
                schedule.setProtein(proteinInput);
                schedule.setFat(fatInput);
                schedule.setWater(waterInput);
                schedule.saveHealth();

                Score score = new Score(
                    Double.parseDouble(sleepInput),
                    Double.parseDouble(exerciseInput),
                    Double.parseDouble(carbInput),
                    Double.parseDouble(proteinInput),
                    Double.parseDouble(fatInput),
                    Double.parseDouble(waterInput),
                    maleButton.isSelected()
                );
                int totalScore = score.totalScore();
                schedule.setScoreForToday(totalScore);



                updateDate();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "입력값이 잘못되었습니다.\n숫자로 입력하세요. (소수점 가능)",
                        "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(saveButton);
        add(formPanel);
                        

        saveButton.setVisible(false);
        formPanel.setVisible(false);
        socreNote.setVisible(false);
    }




    public void updateDate() {
        int year = dateManager.getYear();
        int month = dateManager.getMonth();
        int good_day = dateManager.getDay();
        titleLabel.setText(month + "/" + good_day);
        go_month.setVisible(true);
        go_month.setText("Back");
        go_month.setBounds(500, 500, 60, 30);
        go_month.setFont(new Font("Dialog", Font.PLAIN, 10));        
        formPanel.setVisible(true);
        saveButton.setVisible(true);
        socreNote.setVisible(true);
                
        addButton.setText("add_note");
        removeButton.setText("remove");

        addButton.setBounds(150, 500, 90, 30);
        removeButton.setBounds(250, 500, 80, 30);

        add(addButton);
        add(removeButton);
        addButton.setVisible(true);
        removeButton.setVisible(true);

        sleepTime.setText("");
        exerciseTime.setText("");
        carbField.setText("");
        proteinField.setText("");
        fatField.setText("");
        waterField.setText("");

        note.setText(schedule.getNote());
        note.setBounds(150, 340, 300, 150);
        note.setVisible(true);
        add(note);

        // 점수 표시
        int totalScore = schedule.getScoreForToday();
        socreNote.setText("Score: " + totalScore);
        socreNote.setBounds(150, 70, 300, 30);
        socreNote.setVisible(true);
        add(socreNote);


    }
    private void AddButtonListener() {
    addButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            schedule.addNote();
            updateDate();
        }
    });
}
    private void removeButtonListener() {
    removeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            schedule.removeNote();
            updateDate();
        }
    });

}




}
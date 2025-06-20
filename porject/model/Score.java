package model;

public class Score {
    double exerciseTime;
    double sleepTime;
    double carb;
    double protein;
    double fat;
    double water;
    boolean isMale = true; // 기본값은 남성

    // 생성자에서 해당 날짜의 모든 건강 데이터를 전달받아 초기화
    public Score(double sleepTime, double exerciseTime,
                 double carb, double protein, double fat, double water, boolean isMale) {
        this.sleepTime = sleepTime;
        this.exerciseTime = exerciseTime;
        this.carb = carb; //탄수화물
        this.protein = protein;
        this.fat = fat;
        this.water = water;
        this.isMale = isMale;
    }

    public int sleepScore() {
        //수면 점수 계산
        //https://www.doctorsnews.co.kr/news/articleView.html?idxno=150127 점수참고
        if (sleepTime > 7 && sleepTime <= 8) {
            return 10;
        } else if (sleepTime >= 6 && sleepTime < 7){
            return 8;
        } else if ((sleepTime >= 5 && sleepTime < 6) || (sleepTime > 8 && sleepTime <= 9)) {
            return 5;
        } else if (sleepTime == 0) {
            return 0;
        } else {
            return 2;
        }
    }

    public int exerciseScore() {
        //운동 점수 계산
        //https://hqcenter.snu.ac.kr/archives/30733 점수참고
        //주 권장 시간인 150을 주말빼고 월~금으로 나눠서 계산.
        //첫 15분까지는 운동효과가 있기에 점수 부여.
        if (exerciseTime >= 30) {
            return 10;
        } else if (exerciseTime >= 15 && exerciseTime < 30) {
            return 8;
        } else if (exerciseTime >= 5 && exerciseTime < 15) {
            return 5;
        } else if (exerciseTime == 0) {
            return 0;
        } else {
            return 2;
        }
    }
    //영양소별 점수참고
    //http://www.kns.or.kr/FileRoom/FileRoom_view.asp?mode=mod&restring=%252FFileRoom%252FFileRoom.asp%253Dxsearch%253D0%253D%253Dxrow%253D10%253D%253DBoardID%253DKdr%253D%253Dpage%253D1&idx=108&page=1&BoardID=Kdr&xsearch=1&cn_search=
    //최대 점수인 10점 연구결과 참고 
    public int carbScore() {
        //19~29세 기준. 탄수화물
        if (carb >= 130) return 10;
        else if (carb >= 100) return 7;
        else if (carb >= 70) return 4;
        else return 0;
    }

    public int proteinScore() {
        //19~29세 기준. 단백질
        if (isMale) {
            if (protein >= 50) return 10;
            else if (protein >= 40) return 7;
            else if (protein >= 30) return 4;
            else return 0;
        }
        else {
            if (protein >= 40) return 10;
            else if (protein >= 30) return 7;
            else if (protein >= 20) return 4;
            else return 0;
        }
    }

    public int fatScore() {
        //19~29세 기준. 지방
        if (fat <= 25 && fat >= 20) {
            return 10;
        }
        else {
            return 7;
        }
    }

    public int waterScore() {
        //19~29세 기준. 수분
        if (isMale) {
            if (water >= 981) {
                return 10;
            }
            else if (water >= 800) return 7;
            else if (water >= 600) return 4;
            else return 0;
        }
        else {
            if (water >= 709) {
                return 10;
            }
            else if (water >= 500) return 7;
            else if (water >= 300) return 4;
            else return 0;
        }
    }

    public int totalScore() {
        //100점 만점 총 점수 계산
        int total = 0;
        total += sleepScore();
        total += exerciseScore();
        total += carbScore();
        total += proteinScore();
        total += fatScore();
        total += waterScore();

        // 최대 60점이므로 100점 만점으로 환산 (ex: 60점 → 100점)
        double normalized = (total / 60.0) * 100.0;
        int score = (int) Math.round(normalized);

        return score;
    }
}

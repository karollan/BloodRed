package com.example.bloodred;

import java.util.ArrayList;

public class Score {

    private static final int MAX_SCORE_STAR_AMOUNT = 5;

    private final static ArrayList<Boolean> stars = new ArrayList<>();

    private static int goldStars;

    private static boolean scoreAdded = false;

    public static int getStarAmount() {return MAX_SCORE_STAR_AMOUNT;}

    public static int getGoldStarsAmount() {return goldStars;}

    public static void addGoldStars(int stars) {
        if (!scoreAdded) {
            goldStars = stars;
            scoreAdded = true;
        }
    }

    public static void resetScore() {
        stars.clear();
        scoreAdded = false;
    }

    public static Boolean isStarGranted(int i) {
        return stars.get(i);
    }

    public static void setGrantedStars() {
        if (stars.size() < MAX_SCORE_STAR_AMOUNT) {
            stars.add(stars.size() < goldStars);
        }
    }

}

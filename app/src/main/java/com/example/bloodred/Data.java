package com.example.bloodred;

import com.example.bloodred.gamepanel.BloodType;

import java.util.Random;

/**
 * Data is a collection of enum types data
 *
 **/

public class Data {

        public enum ColliderPosition {
                CENTER,
                BOTTOM,
                RIGHT,
                LEFT,
                TOP,
                TOP_LEFT,
                TOP_RIGHT,
                BOTTOM_LEFT,
                BOTTOM_RIGHT,
                CENTER_LEFT,
                CENTER_RIGHT,
                CENTER_TOP,
                CENTER_BOTTOM,
                CENTER_TOP_RIGHT,
                CENTER_TOP_LEFT,
                CENTER_BOTTOM_LEFT,
                CENTER_BOTTOM_RIGHT,
        }

        public enum BloodGroups {
                A,
                B,
                AB,
                Zero;


                private static final BloodGroups[] GROUPS = values();
                private static final int SIZE = GROUPS.length;
                private static final Random RANDOM = new Random();

                public static BloodGroups getRandomBloodGroup() {
                        return GROUPS[RANDOM.nextInt(SIZE)];
                }
        }

        public enum Rh {
                RhPlus,
                RhMinus;


                private static final Rh[] GROUPS = values();
                private static final int SIZE = GROUPS.length;
                private static final Random RANDOM = new Random();

                public static Rh getRandomRh() {
                        return GROUPS[RANDOM.nextInt(SIZE)];
                }
        }

}


package com.viettel.mocha.rmlivestream.player.info_live.reaction.reactionamination;


import com.viettel.mocha.util.Log;

import java.util.Random;


public class RandomUtil {

    /**
     * Generates the random between two given integers.
     */
    public static int generateRandomBetween(int start, int end) {

        Random random = new Random();
        int rand = random.nextInt(Integer.MAX_VALUE - 1) % end;
        Log.d("RandomUtil", "Random reaction emoji: "+rand);
        if (rand < start) {
            rand = start;
        }
        return rand;
    }
}
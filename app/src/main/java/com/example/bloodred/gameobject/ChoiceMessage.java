package com.example.bloodred.gameobject;

import android.content.Context;

import com.example.bloodred.R;
import com.example.bloodred.gameobject.Sprite;

/**
 * ChoiceMessage is an interactive element of background which cannot be moved
 * It is used as information message
 * ChoiceMessage class in an extension of a Sprite, which is an extension of GameObject
 **/

public class ChoiceMessage extends Sprite {

    private boolean isCreated;

    public ChoiceMessage(boolean state, Context context, int mWidth, int mHeight) {
        super(context, state ? R.drawable.goodchoice : R.drawable.badchoice, mWidth/2, mHeight/2, 0.5f);

        isCreated = true;
    }

    public void update(){}


    public boolean isCreated() {
        return isCreated;
    }


}

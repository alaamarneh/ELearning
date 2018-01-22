package com.ala.elearning.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

/**
 * Created by alaam on 12/5/2017.
 */

public class MyTextToSpeech extends TextToSpeech {
    private MyTextToSpeech(Context context, OnInitListener listener) {
        super(context, listener);
    }

    public static MyTextToSpeech getInstance(Context context, OnInitListener listener) {
        return new MyTextToSpeech(context, listener);
    }
}

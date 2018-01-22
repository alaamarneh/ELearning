package com.ala.elearning.FirebaseHolders;

import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ala.elearning.R;
import com.ala.elearning.util.MyColors;
import com.bumptech.glide.Glide;

import java.util.Locale;

/**
 * Created by alaam on 11/20/2017.
 */

public class QuestionHolder extends RecyclerView.ViewHolder {
    private TextView mTextViewText;
    private RadioButton mRadioButtonO1,mRadioButtonO2,mRadioButtonO3;
    private IListener mListener;
    private Button mButtonPlay;
    private TextToSpeech mTextToSpeech;
    private ImageView mImageView;
    public void setmListener(IListener mListener) {
        this.mListener = mListener;
    }

    public QuestionHolder(View itemView) {
        super(itemView);
        mTextViewText = itemView.findViewById(R.id.tvText);
        mRadioButtonO1 = itemView.findViewById(R.id.rbO1);
        mRadioButtonO2 = itemView.findViewById(R.id.rbO2);
        mRadioButtonO3 = itemView.findViewById(R.id.rbO3);
        mButtonPlay = itemView.findViewById(R.id.btnPlay);
        mImageView = itemView.findViewById(R.id.img1);

        mTextToSpeech = new TextToSpeech(itemView.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                mTextToSpeech.setLanguage(Locale.UK);
            }
        });
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextToSpeech.speak(mTextViewText.getText().toString() +
                    ", answer number 1:" + mRadioButtonO1.getText().toString() +
                        ", answer number 2:" + mRadioButtonO2.getText().toString() +
                        ", answer number 3:" + mRadioButtonO3.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH,null
                );
            }
        });
        mRadioButtonO1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b && mListener != null)
                    mListener.onAnswerSelect(1);

            }
        });
        mRadioButtonO2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b && mListener != null)
                    mListener.onAnswerSelect(2);

            }
        });
        mRadioButtonO3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b && mListener != null)
                    mListener.onAnswerSelect(3);

            }
        });
    }
    public void showAnswer(int ans,int correctAns){
        if(ans != correctAns) {
            if (ans == 1) {
                mRadioButtonO1.setTextColor(MyColors.Wrong);
                mRadioButtonO2.setTextColor(Color.BLACK);
                mRadioButtonO3.setTextColor(Color.BLACK);


            } else if (ans == 2) {
                mRadioButtonO1.setTextColor(Color.BLACK);
                mRadioButtonO2.setTextColor(MyColors.Wrong);
                mRadioButtonO3.setTextColor(Color.BLACK);
            } else if (ans == 3) {
                mRadioButtonO1.setTextColor(Color.BLACK);
                mRadioButtonO2.setTextColor(Color.BLACK);
                mRadioButtonO3.setTextColor(MyColors.Wrong);
            }
        }else{
            if (ans == 1) {
                mRadioButtonO1.setTextColor(MyColors.Correct);
                mRadioButtonO2.setTextColor(Color.BLACK);
                mRadioButtonO3.setTextColor(Color.BLACK);


            } else if (ans == 2) {
                mRadioButtonO1.setTextColor(Color.BLACK);
                mRadioButtonO2.setTextColor(MyColors.Correct);
                mRadioButtonO3.setTextColor(Color.BLACK);
            } else if (ans == 3) {
                mRadioButtonO1.setTextColor(Color.BLACK);
                mRadioButtonO2.setTextColor(Color.BLACK);
                mRadioButtonO3.setTextColor(MyColors.Correct);
            }
        }

        mRadioButtonO1.setEnabled(false);
        mRadioButtonO2.setEnabled(false);
        mRadioButtonO3.setEnabled(false);
    }
    public void setText(String text){
        mTextViewText.setText(text);
    }
    public void setO1(String o){
        mRadioButtonO1.setText(o);
    }
    public void setO2(String o){
        mRadioButtonO2.setText(o);
    }
    public void setO3(String o){
        mRadioButtonO3.setText(o);
    }
    public void setImage(String url){
        Glide.with(itemView).load(url).into(mImageView);
    }
    public interface IListener{
        void onAnswerSelect(int answer);
    }

}

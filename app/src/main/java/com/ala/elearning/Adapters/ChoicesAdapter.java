package com.ala.elearning.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.ala.elearning.Beans.Choice;
import com.ala.elearning.ITriger;
import com.ala.elearning.R;
import com.ala.elearning.SessionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/1/2017.
 */

public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ChoiceHolder> {
    private List<Choice> choices;
    private ChoiceHolder mChoiceHolderSelected = null;
    private ITriger<Choice> triger;
    private Boolean showAnswers = false;

    public ChoicesAdapter(List<Choice> choices, ITriger<Choice> triger, Boolean showAnswers) {
        this.choices = choices;
        this.triger = triger;
        this.showAnswers = showAnswers;
    }

    @Override
    public ChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_choice, parent, false);
        return new ChoiceHolder(v);
    }

    @Override
    public void onBindViewHolder(final ChoiceHolder holder, final int position) {
        Choice choice = choices.get(position);
        holder.setRadioButton(choice.getText());
        holder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) return;
                Log.d("tag","click");
                if(mChoiceHolderSelected != null && holder != mChoiceHolderSelected){ // set prev holder false
                    mChoiceHolderSelected.setChecked(false);
                }
                mChoiceHolderSelected = holder;
                if(triger != null)
                    triger.onResponse(choices.get(position));
            }
        });


        holder.showAnswers(showAnswers,choice.getStatus() == 1);
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    class ChoiceHolder extends RecyclerView.ViewHolder implements Checkable{
        private RadioButton mRadioButton;
        public ChoiceHolder(View itemView) {
            super(itemView);
            mRadioButton = itemView.findViewById(R.id.radioButton);
        }
        void setRadioButton(String text){mRadioButton.setText(text);}
        void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener){
            mRadioButton.setOnCheckedChangeListener(listener);
        }
        void showAnswers(boolean b, boolean r){
            if(b){
                mRadioButton.setEnabled(false);
                if(r)
                    mRadioButton.setTextColor(Color.GREEN);
                else
                    mRadioButton.setTextColor(Color.RED);
            }else{
                mRadioButton.setEnabled(true);
                mRadioButton.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void setChecked(boolean b) {
            mRadioButton.setChecked(b);
        }

        @Override
        public boolean isChecked() {
            return mRadioButton.isChecked();
        }

        @Override
        public void toggle() {
            mRadioButton.setChecked(!mRadioButton.isChecked());
        }
    }
}

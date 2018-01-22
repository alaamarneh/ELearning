package com.ala.elearning.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Adapters.ChoicesAdapter;
import com.ala.elearning.Beans.Choice;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.RunningExam;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.ITriger;
import com.ala.elearning.R;
import com.ala.elearning.controllers.ExamController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExamActivity extends AppCompatActivity implements IResponseTriger<RunningExam>{

    private static final String ARG_EXAM = "exam";
    private Exam mExam;
    private RecyclerView mRecyclerView;

    private Button mButtonSubmit;
    private TextToSpeech mTextToSpeech;
    private String mName;
    private TextView mTextViewParagraph;
    private TextView mTextViewSessionName;
    private TextView tvEndDate;
    private MyAdapter myAdapter;
    private API api = WebDummy.getInstance(this);
    private ProgressDialog mProgressDialog;
    private RunningExam mRunningExam;
    public static Intent getIntent(Context ctx, Exam exam){
        Intent intent = new Intent(ctx,ExamActivity.class);
        intent.putExtra(ARG_EXAM,exam);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        mExam = getIntent().getParcelableExtra(ARG_EXAM);

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewParagraph = findViewById(R.id.tvParagraph);
        mTextViewSessionName = findViewById(R.id.tvSessionNamae);
        tvEndDate = findViewById(R.id.tvEndDate);

        mButtonSubmit = findViewById(R.id.btnSubmit);
        mButtonSubmit.setVisibility(View.GONE);
        mProgressDialog = new ProgressDialog(this);

        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                mTextToSpeech.setLanguage(Locale.UK);
            }
        });
        bind(mExam);
        loadExam();
    }

    private void bind(Exam mExam) {
        setTitle(mExam.getName());
        mTextViewSessionName.setText(mExam.getName());
        tvEndDate.setText(DateUtils.formatDateTime(this,mExam.getEndDate().getTime(),DateUtils.FORMAT_SHOW_TIME));
    }
    private void loadExam(){
        activityLoading(true);
        api.getExamQuestions(mExam,this,this);
    }

    @Override
    public void onResponse(RunningExam value) {
        mRunningExam = value;
        mTextViewParagraph.setText(value.getParagraph());
        myAdapter = new MyAdapter(value.getQuestions());
        mRecyclerView.setAdapter(myAdapter);

        activityLoading(false);
    }

    @Override
    public void onError(String err) {
        Toast.makeText(this, "Error loading exam", Toast.LENGTH_SHORT).show();
    }

    private void activityLoading(boolean loading){
        if(loading){
            mButtonSubmit.setVisibility(View.GONE);
        }else {
            mButtonSubmit.setVisibility(View.VISIBLE);
        }
    }

    public void onClickSubmit(View view) {

        submitCommand();
    }
    private void setFreeze(boolean freeze){
        if(freeze){
            mButtonSubmit.setEnabled(false);

        }else {
            mButtonSubmit.setEnabled(true);
        }
    }
    private void submitCommand() {
        setFreeze(true);
        mProgressDialog.setMessage("Submitting..");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        api.submitAnswers(myAdapter.getSubmissions(), new IResponseTriger<Boolean>() {
            @Override
            public void onResponse(Boolean value) {
                // answers submitted
                if(value){
                    myAdapter.showAnswers(true);
                    mProgressDialog.dismiss();
                    showDialog();
                }else {
                    onError("error");
                }
            }

            @Override
            public void onError(String err) {
                mProgressDialog.dismiss();
                Toast.makeText(ExamActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_result);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View btn = dialog.findViewById(R.id.okBtn);
        TextView textView = (TextView)dialog.findViewById(R.id.textView);
        textView.setText(myAdapter.calculateResult() + " / " + myAdapter.getQuestionsNumber());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void onPlayClick(View view) {
        if(mRunningExam == null)
            return;
        mTextToSpeech.speak(mRunningExam.getParagraph(),TextToSpeech.QUEUE_FLUSH,null);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> implements ITriger<Choice>{
        private List<Ques> questions;
        private Map<Integer,Choice> mapAnswers = new HashMap<>();
        private Boolean showAnswers = false;
        public MyAdapter(List<Ques> questions) {
            this.questions = questions;
        }
        public void showAnswers(boolean b){
            showAnswers = b;
            notifyDataSetChanged();
        }
        public List<Submission> getSubmissions(){
            return ExamController.generateSubmissions(ExamActivity.this,questions,mapAnswers);
        }
        public int calculateResult(){
            int c =0;
            for (Ques q :
                    questions) {

                if(mapAnswers.containsKey(q.getId())){
                    Choice choice = mapAnswers.get(q.getId());
                    if(choice.isCorrect())
                        c++;
                }
            }
            return c;
        }
        public int getQuestionsNumber(){
            return questions.size();
        }
        @Override
        public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_question1, parent, false);
            return new MyAdapter.MyHolder(v);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyHolder holder, final int position) {
            final Ques q = questions.get(position);
            holder.bind(q,position);

            if(q.getChoices() != null)
                holder.setAdapter(new ChoicesAdapter(q.getChoices(),this,showAnswers));
        }
        String getText(Ques q,int p){
            StringBuilder a = new StringBuilder("Question ").append(p+1).append(q.getText());
            int i=0;
            for (Choice c :
                    q.getChoices()) {
                i++;
                a.append(",Answer #").append(i).append(c.getText());
            }
            return a.toString();
        }
        @Override
        public int getItemCount() {
            return questions.size();
        }

        @Override
        public void onResponse(Choice response) {
            mapAnswers.put(response.getQid(),response);
        }

        @Override
        public void onError(String error) {

        }

        class MyHolder extends RecyclerView.ViewHolder{
            private TextView mTextViewText, tvQuestionNumber;
            private RecyclerView mRecyclerView;
            View mButtonPlay,btnStop;

            public MyHolder(View itemView) {
                super(itemView);
                mTextViewText = itemView.findViewById(R.id.tvText);
                mRecyclerView = itemView.findViewById(R.id.rv);
                tvQuestionNumber = itemView.findViewById(R.id.tvQuestionNumber);
                mButtonPlay = itemView.findViewById(R.id.btnPlay);
                btnStop = itemView.findViewById(R.id.btnStop);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            }
            public void bind(final Ques ques, final int position){
                tvQuestionNumber.setText("Q" + (position+1));
                mTextViewText.setText(ques.getText());

                mButtonPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTextToSpeech.speak(getText(ques,position)
                        ,TextToSpeech.QUEUE_FLUSH,null);
                        btnStop.setVisibility(View.VISIBLE);
                    }
                });
                btnStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTextToSpeech.stop();
                        btnStop.setVisibility(View.GONE);
                    }
                });
            }
            void setAdapter(ChoicesAdapter choicesAdapter){mRecyclerView.setAdapter(choicesAdapter);}
        }
    }
}

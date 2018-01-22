package com.ala.elearning;

import android.app.Dialog;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ala.elearning.API.WebService;
import com.ala.elearning.Adapters.ChoicesAdapter;
import com.ala.elearning.Beans.Choice;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.Session;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.util.MyTextToSpeech;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SessionActivity extends AppCompatActivity implements ITriger<Choice>{
    private RecyclerView mRecyclerView;
    private Map<Integer,Choice> map = new HashMap<>();
    private List<Ques> questions;
    private String mId = "default";
    private Button mButtonSubmit;
    private WebService mWebService = WebService.getInstance(this);
    private TextToSpeech mTextToSpeech;
    private String mName;
    private TextView mTextViewParagraph;
    private TextView mTextViewSessionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTextViewParagraph = findViewById(R.id.tvParagraph);
        mTextViewSessionName = findViewById(R.id.tvSessionNamae);

        mButtonSubmit = findViewById(R.id.btnSubmit);
        mButtonSubmit.setVisibility(View.GONE);
        mId = getIntent().getStringExtra("id");
        mName = getIntent().getStringExtra("name");

        mWebService.getSession(new ITriger<ArrayList<Session>>() {
            @Override
            public void onResponse(ArrayList<Session> response) {
                Session session = response.get(0);
                mTextViewParagraph.setText(session.getDescription());
                mTextViewSessionName.setText(session.getName());
            }

            @Override
            public void onError(String error) {
               showErr("Unable to connect");
            }
        });

        mWebService.getQuestions(1, new ITriger<List<Ques>>() {
            @Override
            public void onResponse(List<Ques> response) {
                MyAdapter adapter = new MyAdapter(response,SessionActivity.this,false);
                mRecyclerView.swapAdapter(adapter,false);
                questions = response;
                mButtonSubmit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String error) {
                showErr("Unable to get questions");
            }
        });

        mTextToSpeech = MyTextToSpeech.getInstance(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                mTextToSpeech.setLanguage(Locale.UK);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void showErr(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Choice response) {
        map.put(response.getQid(),response);
    }

    @Override
    public void onError(String error) {
    }

    private ArrayList<Submission> generateSubmissions(){
        ArrayList<Submission> submissions = new ArrayList<>();
        for (Ques q :
                questions) {

            if(map.containsKey(q.getId())){
                Submission s = new Submission();

                Choice choice = map.get(q.getId());
                s.setCid(choice.getId());
                s.setUid(mId);
                s.setQ_id(q.getId());
                s.setUname(mName);
                submissions.add(s);
            }
        }
        return submissions;
    }

    private void submit(){
     mWebService.submit(generateSubmissions(), new ITriger<Boolean>() {
         @Override
         public void onResponse(Boolean response) {


         }

         @Override
         public void onError(String error) {
             showErr("Something Wrong");
         }
     });
    }
    private int calculateResult(){
        int c =0;
        for (Ques q :
                questions) {

            if(map.containsKey(q.getId())){
                Choice choice = map.get(q.getId());
                if(choice.isCorrect())
                    c++;
            }
        }
        return c;
    }
    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_result);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View btn = dialog.findViewById(R.id.okBtn);
        TextView textView = (TextView)dialog.findViewById(R.id.textView);
        textView.setText(calculateResult() + " / " + questions.size());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void onClickSubmit(View view) {
        MyAdapter adapter = new MyAdapter(questions,null,true);
        mRecyclerView.swapAdapter(adapter,false);
        showDialog();
        mButtonSubmit.setEnabled(false);
        submit();

    }

    public void onClickPlay(View view) {
        mTextToSpeech.speak(mTextViewParagraph.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
        List<Ques> questions;
        ITriger<Choice> triger;
        Boolean showAnswers = false;
        public MyAdapter(List<Ques> questions, ITriger<Choice> triger, Boolean showAnswers) {
            this.questions = questions;
            this.triger = triger;
            this.showAnswers = showAnswers;


        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_question1, parent, false);
            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            final Ques q = questions.get(position);
            holder.setQuestionText("Question #" +(position +1) +": " +q.getText());
            if(q.getChoices() != null)
            holder.setAdapter(new ChoicesAdapter(q.getChoices(),triger,showAnswers));
            holder.mButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTextToSpeech.speak(getText(q ,position),TextToSpeech.QUEUE_FLUSH,null);
                }
            });
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

        class MyHolder extends RecyclerView.ViewHolder{
            private TextView mTextViewText;
            private RecyclerView mRecyclerView;
            ImageButton mButtonPlay;

            public MyHolder(View itemView) {
                super(itemView);
                mTextViewText = itemView.findViewById(R.id.tvText);
                mRecyclerView = itemView.findViewById(R.id.rv);
                mButtonPlay = itemView.findViewById(R.id.btnPlay);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            }
            void setQuestionText(String q){
                mTextViewText.setText(q);


            }
            void setAdapter(ChoicesAdapter choicesAdapter){mRecyclerView.setAdapter(choicesAdapter);}
        }
    }
}

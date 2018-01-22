package com.ala.elearning;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ala.elearning.Beans.Paragraph;
import com.ala.elearning.Beans.Question;
import com.ala.elearning.FirebaseHolders.QuestionHolder;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class SecondActivity extends AppCompatActivity{
    private SparseIntArray answers = new SparseIntArray();
    private SparseIntArray correctAnswers = new SparseIntArray();
    private TextView mTextViewParagraph;
    private ImageView mImageView;
    public final static String P_0 = "0";
    private TextToSpeech mTextToSpeech;
    DatabaseReference mData;
    RecyclerView mRecyclerView;
    private String mId,mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTextViewParagraph = findViewById(R.id.tvParagraph);
        mImageView = findViewById(R.id.img1);

        mData = FirebaseDatabase.getInstance().getReference().child("questions").child(P_0);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        setTitle("Paragraph 1");
        mId = getIntent().getStringExtra("id");
        mName = getIntent().getStringExtra("name");

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        updateRecycler(false);

        getParagraph();

        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                mTextToSpeech.setLanguage(Locale.UK);
            }
        });
    }

    private void updateRecycler(final boolean showAns) {
        FirebaseRecyclerAdapter<Question,QuestionHolder> adapter = new FirebaseRecyclerAdapter<Question, QuestionHolder>(
                Question.class,
                R.layout.row_question,
                QuestionHolder.class,
                mData

        ) {
            @Override
            protected void populateViewHolder(QuestionHolder viewHolder, Question model, final int position) {
                viewHolder.setText("Question #"+ (position+1) + ": " + model.getText());
                viewHolder.setO1(model.getO1());
                viewHolder.setO2(model.getO2());
                viewHolder.setO3(model.getO3());
                if(model.getImgurl() != null)
                viewHolder.setImage(model.getImgurl());

                correctAnswers.put(position,model.getAnswer());
                viewHolder.setmListener(new QuestionHolder.IListener() {
                    @Override
                    public void onAnswerSelect(int answer) {
                        answers.put(position,answer);

                    }
                });

                if(showAns){
                    viewHolder.showAnswer(answers.get(position,-1),model.getAnswer());
                }
            }
        };
        mRecyclerView.swapAdapter(adapter,true);

    }

    private void getParagraph() {
        DatabaseReference mDataParagraph = FirebaseDatabase.getInstance().getReference().child("paragraphs").child(P_0);
        mDataParagraph.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Paragraph mParagraph = dataSnapshot.getValue(Paragraph.class);
                mTextViewParagraph.setText(mParagraph.getText());
                if(mParagraph.getImgurl() != null)
                    Glide.with(SecondActivity.this).load(mParagraph.getImgurl()).into(mImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                doneClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doneClick() {
        updateRecycler(true);
        int c = 0;
        TextView mTextViewResult = findViewById(R.id.tvResult);
        for (int i = 0;i<correctAnswers.size();i++){
            int a1 = answers.get(i,-1);
            int a2 = correctAnswers.get(i,-1);
            if(a1 == a2) c++;
        }
        mTextViewResult.setText("Your result is " + c + "/" + correctAnswers.size());
        findViewById(R.id.layoutResult).setVisibility(View.VISIBLE);
        uploadResult(c,correctAnswers.size());
    }

    private void uploadResult(int c, int size) {
        DatabaseReference mDataResult = FirebaseDatabase.getInstance().getReference().child("answers").child(P_0).child(mId);
        Map<String,Object> map = new HashMap<>();
        map.put("result",c);
        map.put("max",size);
        map.put("name",mName);

        mDataResult.setValue(map);
    }

    public void onClick(View view) {
        mTextToSpeech.speak(mTextViewParagraph.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

    }
    @Override
    public void onPause(){
        if(mTextToSpeech !=null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onPause();
    }

    public void onClickSubmit(View view) {
        doneClick();
    }
}

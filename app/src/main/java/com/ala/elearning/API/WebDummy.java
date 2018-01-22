package com.ala.elearning.API;

import android.content.Context;
import android.os.Handler;

import com.ala.elearning.Beans.Choice;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.RunningExam;
import com.ala.elearning.Beans.Session;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.Beans.User;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.ITriger;
import com.ala.elearning.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ala.elearning.Beans.Exam.STATUS_COMPLETED;
import static com.ala.elearning.Beans.Exam.STATUS_NEW;

/**
 * Created by alaam on 12/28/2017.
 */

public class WebDummy implements API{
    public static WebDummy instance;
    private long DELAY = 1000;
    private Context mContext;
    private WebDummy(Context mContext) {
        this.mContext = mContext;
    }

    public static WebDummy getInstance(Context context) {
        if(instance == null)
            instance = new WebDummy(context);
        return instance;
    }
    @Override
    public void getAvailableCourses(final IResponseTriger<List<Course>> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Course> courses = new ArrayList<>();
                for(int i =0;i<4;i++){
                    Course course = new Course();
                    course.setId(i);
                    course.setName("course #"+i);
                    course.setSummary("Summary " +i);
                    User inst = new User();
                    inst.setName("instr"+i);
                    course.setInstructor(inst);

                    courses.add(course);

                }
                triger.onResponse(courses);
            }
        },DELAY);

    }

    @Override
    public void getAvaiableExams(final Course course, final IResponseTriger<List<Exam>> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Exam> exams = new ArrayList<>();
                int s = STATUS_NEW;
                for(int i =0;i<4;i++){
                    Exam exam = new Exam();
                    Course c = new Course();
                    c.setName("Android");
                    exam.setCourse(c);
                    exam.setDate(new Date(System.currentTimeMillis() - (i*1000*60)));
                    exam.setName("name");
                    exam.setId(i);
                    exam.setMark(0);
                    exam.setEndDate(new Date());
                    exam.setMaxMark(5);
                    exam.setStatus(s);
                    exams.add(exam);
                    s = s==STATUS_NEW?STATUS_COMPLETED:STATUS_NEW;

                }
                triger.onResponse(exams);
            }
        },DELAY);
    }

    @Override
    public void getExamResults(final Course course, final IResponseTriger<List<Exam>> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Exam> exams = new ArrayList<>();
                for(int i =0;i<4;i++){
                    Exam exam = new Exam();
                    exam.setCourse(course);
                    exam.setDate(new Date(System.currentTimeMillis() - (i*1000*60)));
                    exam.setName("name " +i);
                    exam.setId(i);
                    exam.setMark(i);
                    exam.setMaxMark(5);
                    exam.setStatus(Exam.STATUS_COMPLETED);
                    exams.add(exam);

                }
                Exam exam = new Exam();
                exam.setCourse(course);
                exam.setDate(new Date(System.currentTimeMillis() + (1000*60*5)));
                exam.setName("name");
                exam.setId(4);

                exam.setMark(4);
                exam.setMaxMark(5);
                exam.setStatus(Exam.STATUS_COMPLETED);
                exams.add(exam);

                triger.onResponse(exams);
            }
        },DELAY);
    }

    @Override
    public void getMessages(final Course course, final IResponseTriger<List<MSG>> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<MSG> msgs = new ArrayList<>();
                for(int i =0;i<4;i++){
                    Course c = new Course();
                    c.setName("cource jma,ef");
                    User user1 =new User();
                    user1.setName("ALa"+i);
                    c.setInstructor(user1);
                   MSG msg = new MSG();
                   msg.setDate(new Date());
                   if(course == null)
                   msg.setCourse(c);
                   else
                       msg.setCourse(course);
                   User user = new User();
                   user.setName("ala");

                   msg.setFrom(user);
                   msg.setTo(user);
                   msg.setText("text "+i);

                   msgs.add(msg);

                }
                triger.onResponse(msgs);
            }
        },DELAY);
    }

    @Override
    public void getHomeworks(final Course course, final IResponseTriger<List<HomeWork>> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<HomeWork> homeWorks = new ArrayList<>();
                for(int i =0;i<4;i++){
                    HomeWork homeWork = new HomeWork();
                    homeWork.setCourse(course);
                    homeWork.setDate(new Date());
                    homeWork.setEndDate(new Date());
                    homeWork.setId(i);
                    homeWork.setTitle("tite");
                    homeWork.setText("text " + i);
                    homeWorks.add(homeWork);

                }
                triger.onResponse(homeWorks);
            }
        },DELAY);
    }

    @Override
    public void getExamQuestions(final Exam exam, final IResponseTriger<RunningExam> triger, final Context context) {

                WebService.getInstance(context).getQuestions(1, new ITriger<List<Ques>>() {
                    @Override
                    public void onResponse(List<Ques> response) {
                        RunningExam runningExam = new RunningExam();
                        runningExam.setExam(exam);
                        runningExam.setTitle("title");
                        runningExam.setParagraph(context.getString(R.string.large_text));
                        runningExam.setQuestions(response);

                        triger.onResponse(runningExam);
                    }

                    @Override
                    public void onError(String error) {
                        triger.onError(error);
                    }
                });

    }

    @Override
    public void sendMSG(MSG msg, final IResponseTriger<Boolean> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(triger != null)
                triger.onResponse(true);
            }
        },DELAY);
    }

    @Override
    public void submitAnswers(List<Submission> submissions, final IResponseTriger<Boolean> triger) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(triger != null)
                    triger.onResponse(true);
            }
        },DELAY);
    }


}

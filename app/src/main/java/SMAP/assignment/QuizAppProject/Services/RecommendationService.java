package SMAP.assignment.QuizAppProject.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;
import SMAP.assignment.QuizAppProject.R;

public class RecommendationService extends Service {
    private static final String TAG = "RecommendationService";
    private static final String SERVICE_CHANNEL_RECOMMENDATION = "recommendationChannel";
    private static final int NOTIFICATION_ID = 69;

    private Random random;
    private ExecutorService execService;
    private Repository repository;
    private NotificationManager notificationManager;
    private boolean started = false;
    private List<Quiz> quizzes;

    public RecommendationService(){
        repository = Repository.getInstance();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        repository = Repository.getInstance();
        random = new Random();
        Log.d(TAG, "onStartCommand: Run!");
        startBackgroundRecommending();

        repository.getAllSharedQuizzes().observeForever(new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzesUpdated) {
                quizzes = quizzesUpdated;
            }
        });

        return START_STICKY;
    }

    private void startBackgroundRecommending(){
        if(!started){
            started = true;
            recommendRecursive(10000);
        }
    }

    private void recommendRecursive(final int sleepTime){
        Log.d(TAG, "onStartCommand: Run!");
        if(execService == null){
            execService = Executors.newSingleThreadExecutor();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(SERVICE_CHANNEL_RECOMMENDATION, "RecommendationService", NotificationManager.IMPORTANCE_LOW);
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        // Pick random quiz to recommend.
        if(quizzes != null) {
            Quiz randomQuiz = quizzes.get(random.nextInt(quizzes.size()));
            Log.d(TAG, "onStartCommand: Run inside quiz not null!");

            Notification recommendNotification = new NotificationCompat.Builder(this, SERVICE_CHANNEL_RECOMMENDATION)
                    .setContentTitle("New Quiz recommendation!")
                    .setContentText("Quiz name: " + randomQuiz.getName())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setTicker("Random shared quiz recommendation notifier!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();

            startForeground(NOTIFICATION_ID, recommendNotification);
        }

        // Loop.
        execService.submit(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    Log.d(TAG, "run: Error!", e);
                }

                if(started){
                    recommendRecursive(sleepTime);
                }

            }
        });

    }


}

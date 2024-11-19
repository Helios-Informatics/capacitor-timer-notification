package simple.workout.log;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import java.io.IOException;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.media.AudioFocusRequest;
import android.media.AudioAttributes;

public class TimerService extends Service {
    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "timer_channel";
    private NotificationManager notificationManager;
    private long remainingTime;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private boolean isPaused = false;

    private static TimerService instance;
    private static final MutableLiveData<Long> remainingTimeLiveData = new MutableLiveData<>();

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        instance = this;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); // AudioManager to control volume
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "START_TIMER":
                    remainingTime = intent.getLongExtra("TIMER_DURATION", 0);
                    startTimer();
                    break;
                case "STOP_TIMER":
                    stopTimer();
                    break;
                case "PAUSE_TIMER":
                    pauseTimer();
                    break;
                case "RESUME_TIMER":
                    resumeTimer();
                    break;
            }
        }
        return START_STICKY;
    }

    private void startTimer() {
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        isPaused = false;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0 && !isPaused) {
                    remainingTimeLiveData.postValue(remainingTime); // Emit remaining time
                    updateNotification();
                    remainingTime--;
                    timerHandler.postDelayed(this, 1000); // Update every second
                } else {
                    playFinishSound(); // Play sound when timer finishes
                    stopTimer();
                }
            }
        };

        timerHandler.post(timerRunnable);
        Notification notification = createNotification(remainingTime, "Running");
        startForeground(NOTIFICATION_ID, notification);
    }

    private void pauseTimer() {
        isPaused = true;
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    private void resumeTimer() {
        isPaused = false;
        startTimer();
    }

    private void stopTimer() {
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        remainingTimeLiveData.postValue(0L); // Notify subscribers timer is stopped
        stopForeground(true);
        stopSelf();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Timer Notifications", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification(long remainingTime, String statusText) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Timer Running")
                .setContentText(statusText + ": " + convertSecondsToMS(remainingTime))
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(0)
                .build();
    }

    private void updateNotification() {
        Notification notification = createNotification(remainingTime, "Running");
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private String convertSecondsToMS(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public void playFinishSound() {
        // Get AudioManager instance
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus for media
        AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA) // Treat as media sound
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // Music content type
                        .build())
                .setOnAudioFocusChangeListener(focusChange -> {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Handle focus loss if needed
                    }
                })
                .build();

        int focusResult = audioManager.requestAudioFocus(audioFocusRequest);
        if (focusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Audio focus granted, proceed to play the sound
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // You can customize this URI

                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA) // Play as media sound
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // Music content type
                        .build());

                mediaPlayer.setDataSource(getApplicationContext(), soundUri);
                mediaPlayer.prepare();

                // Play the sound
                mediaPlayer.setOnCompletionListener(mp -> {
                    // Release resources when playback is complete
                    mp.release();
                    audioManager.abandonAudioFocusRequest(audioFocusRequest);
                });
                mediaPlayer.start();

            } catch (Exception e) {
                e.printStackTrace();
                audioManager.abandonAudioFocusRequest(audioFocusRequest);
            }
        } else {
            // Failed to gain audio focus
            System.err.println("Failed to gain audio focus for media sound.");
        }
    }




    public static TimerService getInstance() {
        return instance;
    }

    public static LiveData<Long> getRemainingTimeLiveData() {
        return remainingTimeLiveData;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

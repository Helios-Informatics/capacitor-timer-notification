package simple.workout.log;

import android.content.Intent;
import com.getcapacitor.PluginCall;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

@CapacitorPlugin(name = "TimerNotification")
public class TimerNotificationPlugin extends Plugin {

    // LiveData to communicate with Vue
    private MutableLiveData<String> buttonClicked = new MutableLiveData<>();

    @PluginMethod()
    public void startTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("START_TIMER");
        long duration = (long) call.getInt("duration", 0);
        serviceIntent.putExtra("TIMER_DURATION", duration);
        getContext().startService(serviceIntent);
        call.success();
    }

    @PluginMethod()
    public void updateNotification(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("UPDATE_NOTIFICATION");
        long remainingTime = (long) call.getInt("duration", 0);
        String statusText = call.getString("statusText", "Running");
        serviceIntent.putExtra("TIMER_DURATION", remainingTime);
        serviceIntent.putExtra("STATUS_TEXT", statusText);
        getContext().startService(serviceIntent);
        call.success();
    }

    @PluginMethod()
    public void stopTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("STOP_TIMER");
        getContext().startService(serviceIntent);
        call.success();
    }

    public LiveData<String> getButtonClicked() {
        return buttonClicked;
    }

    // Method to notify button clicked
    public void notifyButtonClicked(String action) {
        buttonClicked.postValue(action);
    }
}

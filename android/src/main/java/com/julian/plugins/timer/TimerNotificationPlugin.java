package simple.workout.log;

import android.content.Intent;
import com.getcapacitor.PluginCall;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
<<<<<<< Updated upstream
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
=======
import com.getcapacitor.JSObject;

>>>>>>> Stashed changes

@CapacitorPlugin(name = "TimerNotification")
public class TimerNotificationPlugin extends Plugin {

    // LiveData to communicate with Vue
    private MutableLiveData<String> buttonClicked = new MutableLiveData<>();

    @PluginMethod()
    public void startTimer(PluginCall call) {
        long duration = (long) call.getInt("duration", 0);
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("START_TIMER");
        serviceIntent.putExtra("TIMER_DURATION", duration);
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

<<<<<<< Updated upstream
    public LiveData<String> getButtonClicked() {
        return buttonClicked;
    }

    // Method to notify button clicked
    public void notifyButtonClicked(String action) {
        buttonClicked.postValue(action);
=======
    // Method to retrieve the remaining time
    @PluginMethod()
    public void getRemainingTime(PluginCall call) {
        TimerService timerService = TimerService.getInstance();
        if (timerService != null) {
            long remainingTime = timerService.getRemainingTime();
            JSObject ret = new JSObject();
            ret.put("remainingTime", remainingTime); // Add the long value to JSObject
            call.resolve(ret); // Return the JSObject
        } else {
            call.reject("TimerService not running");
        }
>>>>>>> Stashed changes
    }
}

package simple.workout.log;

import android.content.Intent;
import com.getcapacitor.PluginCall;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;


@CapacitorPlugin(name = "TimerNotification")
public class TimerNotificationPlugin extends Plugin {
    @PluginMethod()
    public void startTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("START_TIMER");

        // Pass the duration as an extra
        int durationInSeconds = call.getInt("duration", 0); // Default to 0 if not passed
        long duration = (long) durationInSeconds; // Cast to long if you need it as a long
         // Default to 0 if not passed
        serviceIntent.putExtra("TIMER_DURATION", duration);

        getContext().startService(serviceIntent);
        call.success();
    }


    @PluginMethod()
    public void pauseTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("PAUSE_TIMER");
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
}

export interface TimerNotificationPlugin {
  startTimer(options: { duration: number }): Promise<void>;
  stopTimer(): Promise<void>;
  getRemainingTime(): Promise<{ remainingTime: number }>; // Updated to return an object with `remainingTime`
}

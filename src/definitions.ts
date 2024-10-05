export interface TimerNotificationPlugin {
  startTimer(options: { duration: number }): Promise<void>;
  pauseTimer(): Promise<void>;
  stopTimer(): Promise<void>;
}

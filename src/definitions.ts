export interface TimerNotificationPlugin {
  startTimer(options: { duration: number }): Promise<void>;
  updateNotification(options: { duration: number; statusText: string }): Promise<void>;
  stopTimer(): Promise<void>;
}

export interface TimerNotificationPlugin {
<<<<<<< Updated upstream
  startTimer(options: { duration: number }): Promise<void>;
  updateNotification(options: { duration: number; statusText: string }): Promise<void>;
  stopTimer(): Promise<void>;
=======
  startTimer(options: {
      duration: number;
  }): Promise<void>;
  stopTimer(): Promise<void>;
  getRemainingTime(): Promise<{ remainingTime: number }>;  // Updated to return an object with `remainingTime`
>>>>>>> Stashed changes
}

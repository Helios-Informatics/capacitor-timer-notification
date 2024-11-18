import { WebPlugin } from '@capacitor/core';
import type { TimerNotificationPlugin } from './definitions';

export class TimerNotificationWeb extends WebPlugin implements TimerNotificationPlugin {
  async startTimer(options: { duration: number }): Promise<void> {
    console.log('Timer started with duration:', options.duration);
  }

  async stopTimer(): Promise<void> {
    console.log('Timer stopped');
  }

  async pauseTimer(): Promise<void> {
    console.log('Timer paused');
  }

  async resumeTimer(): Promise<void> {
    console.log('Timer resumed');
  }

  async getRemainingTime(): Promise<{ remainingTime: number }> {
    console.log('Fetching remaining time...');
    return { remainingTime: 0 }; // Simulate a default remaining time
  }

  removeAllListeners(): Promise<void> {
    console.log('All listeners removed');
    return super.removeAllListeners(); // Call the base class's implementation
  }
}

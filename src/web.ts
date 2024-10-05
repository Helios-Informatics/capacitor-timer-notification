import { WebPlugin } from '@capacitor/core';
import type { TimerNotificationPlugin } from './definitions';

export class TimerNotificationWeb extends WebPlugin implements TimerNotificationPlugin {
  async startTimer(): Promise<void> {
    console.log('Timer started');
    // Implement start logic or keep it as a stub for now
  }

  async pauseTimer(): Promise<void> {
    console.log('Timer paused');
    // Implement pause logic or keep it as a stub for now
  }

  async stopTimer(): Promise<void> {
    console.log('Timer stopped');
    // Implement stop logic or keep it as a stub for now
  }
}

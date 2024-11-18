import type { PluginListenerHandle } from '@capacitor/core';

export interface TimerNotificationPlugin {
  /**
   * Starts the timer with the given duration in seconds.
   * @param options - Object containing the duration of the timer.
   */
  startTimer(options: { duration: number }): Promise<void>;

  /**
   * Stops the currently running timer.
   */
  stopTimer(): Promise<void>;

  /**
   * Pauses the currently running timer.
   */
  pauseTimer(): Promise<void>;

  /**
   * Resumes the paused timer.
   */
  resumeTimer(): Promise<void>;

  /**
   * Listens for updates on the remaining time of the timer.
   * @param eventName - The name of the event, e.g., "remainingTimeUpdate".
   * @param listenerFunc - Callback function that receives the remaining time.
   */
  addListener(
    eventName: 'remainingTimeUpdate',
    listenerFunc: (data: { remainingTime: number }) => void,
  ): Promise<PluginListenerHandle>;

  /**
   * Removes all listeners for a given event.
   * @param eventName - The name of the event to remove listeners for.
   */
  removeAllListeners(eventName: string): Promise<void>;
}

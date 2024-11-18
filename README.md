## capacitor-timer-notification

A Capacitor plugin to create a timer notification that displays a countdown timer in the notification bar.

Stable Version is 0.24!!

## Usage

Add the following to your `android/app/src/main/AndroidManifest.xml` file to declare the necessary permissions and service:

````xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />

<service
  android:name=".TimerService"
  android:enabled="true"
  android:exported="false"
  android:foregroundServiceType="mediaPlayback"/>




## Install

```bash
npm install capacitor-timer-notification
npx cap sync
````

## API

<docgen-index>

* [`startTimer(...)`](#starttimer)
* [`stopTimer()`](#stoptimer)
* [`pauseTimer()`](#pausetimer)
* [`resumeTimer()`](#resumetimer)
* [`addListener('remainingTimeUpdate', ...)`](#addlistenerremainingtimeupdate-)
* [`removeAllListeners(...)`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startTimer(...)

```typescript
startTimer(options: { duration: number; }) => Promise<void>
```

Starts the timer with the given duration in seconds.

| Param         | Type                               | Description                                    |
| ------------- | ---------------------------------- | ---------------------------------------------- |
| **`options`** | <code>{ duration: number; }</code> | - Object containing the duration of the timer. |

--------------------


### stopTimer()

```typescript
stopTimer() => Promise<void>
```

Stops the currently running timer.

--------------------


### pauseTimer()

```typescript
pauseTimer() => Promise<void>
```

Pauses the currently running timer.

--------------------


### resumeTimer()

```typescript
resumeTimer() => Promise<void>
```

Resumes the paused timer.

--------------------


### addListener('remainingTimeUpdate', ...)

```typescript
addListener(eventName: 'remainingTimeUpdate', listenerFunc: (data: { remainingTime: number; }) => void) => Promise<PluginListenerHandle>
```

Listens for updates on the remaining time of the timer.

| Param              | Type                                                       | Description                                           |
| ------------------ | ---------------------------------------------------------- | ----------------------------------------------------- |
| **`eventName`**    | <code>'remainingTimeUpdate'</code>                         | - The name of the event, e.g., "remainingTimeUpdate". |
| **`listenerFunc`** | <code>(data: { remainingTime: number; }) =&gt; void</code> | - Callback function that receives the remaining time. |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners(...)

```typescript
removeAllListeners(eventName: string) => Promise<void>
```

Removes all listeners for a given event.

| Param           | Type                | Description                                      |
| --------------- | ------------------- | ------------------------------------------------ |
| **`eventName`** | <code>string</code> | - The name of the event to remove listeners for. |

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>

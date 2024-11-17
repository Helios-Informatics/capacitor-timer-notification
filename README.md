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
<<<<<<< Updated upstream
* [`updateNotification(...)`](#updatenotification)
* [`stopTimer()`](#stoptimer)
=======
* [`stopTimer()`](#stoptimer)
* [`getRemainingTime()`](#getremainingtime)
>>>>>>> Stashed changes

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startTimer(...)

```typescript
startTimer(options: { duration: number; }) => Promise<void>
```

| Param         | Type                               |
| ------------- | ---------------------------------- |
| **`options`** | <code>{ duration: number; }</code> |

--------------------


### stopTimer()

```typescript
stopTimer() => Promise<void>
```

--------------------


<<<<<<< Updated upstream
### stopTimer()

```typescript
stopTimer() => Promise<void>
```

=======
### getRemainingTime()

```typescript
getRemainingTime() => Promise<{ remainingTime: number; }>
```

**Returns:** <code>Promise&lt;{ remainingTime: number; }&gt;</code>

>>>>>>> Stashed changes
--------------------

</docgen-api>

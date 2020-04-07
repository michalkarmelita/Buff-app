# Buff UP

## Part A: Android Application

A Host Android Application that has a single activity playing a full screen video with the source:

https://buffup-public.s3.eu-west-2.amazonaws.com/video/toronto+nba+cut+3.mp4


## Part B: Android SDK

SDK used by the Android App Uses to display content (Buff's as we call them) over the existing video.

### SDK Responsibilities

- Expose a view that the host App will add in the UI over the video stream to display the Buffs
- Handle all the business and UI logic to display the Buffs over the video in the view

### Notes:

Application is written in Kotlin using MVVM architecture. LiveData is used to update the UI in a clean way. Multithreading with coroutines.

#### Improvements
- error handling, application have minimal but very robust error handling mechanism. Currently all errors are exposed as Error object, but it would be straightforward to add more detail and make it robust. It's a strong foundation to easily extend it.
- improve test coverage - due to limited time tests are focused on areas of the app that were difficult to test on the UI. Mostly `safeApiCall` function.
- dependency injection - for simplicity and time saving dependency framework was not configured, normally I'd use Dagger 2 or Koin for it. Sample DI configuration can be revieved in this project https://github.com/michalkarmelita/CatFacts
- improve UI, add selection do answer

Some of the Libraries used:
Retrofit, Kotlin Coroutines, Androidx, Mockito, Mockk, JUnit4


#### Requirements
- Codebase require Android Studio 3.6 to run.
- Application was tested on Pixel 2 (Android 9.0)

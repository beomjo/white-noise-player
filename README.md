<h1 align="center">White Noise Player</h1>

<p align="center">
<b>Listen to various white noises that help you study, sleep and concentrate</b>
</p>
<p align="center">  
You can't concentrate when you study, concentrate, or sleep?
Join the White Noise Player!<br>
The sounds of nature, the sounds of traffic, the sounds commonly heard in the house, the sounds of travel, the sounds of animals, etc.
You can hear sounds that can help you study, concentrate, and sleep.
</p>
</br>  

![image](https://user-images.githubusercontent.com/39984656/115113874-6531c180-9fc7-11eb-8547-829ab9d9035f.jpeg)


## Download
<b>[Google Play Store Link](https://play.google.com/store/apps/details?id=com.beomjo.whitenoise)</b>

<img src="/previews/preview.gif" align="right" width="32%"/>  

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
  - [Dagger 2](https://dagger.dev/dev-guide) - dependency injection.
- [Firebase] - FireStore, Storage
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Shimmer-Android](https://github.com/facebook/shimmer-android) - unobtrusive loading indicator, developed from Facebook 
- [Bindables](https://github.com/skydoves/Bindables) - DataBinding kit for notifying data changes from Model layers to UI layers.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Media Control](https://developer.android.com/jetpack/androidx/releases/media) - Share media contents and controls with other apps.

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

## Unit Testing
Unit Tests verify the interactions of viewmodels between repositories.
- [MockK](https://mockk.io/) - mocking library for Kotlin  

![image](https://user-images.githubusercontent.com/39984656/115115204-0d4a8900-9fce-11eb-9366-40dceb438785.png)


## Architecture
WhiteNoisePlayer is based on MVVM architecture and a repository pattern.
![image](https://user-images.githubusercontent.com/39984656/115115521-e2613480-9fcf-11eb-84a3-d9787999e22f.png)


## Audio Overview
![image](https://user-images.githubusercontent.com/39984656/115116340-f60e9a00-9fd3-11eb-8d3e-91a4370a7f4b.png)


## Dagger Graph
![image](https://user-images.githubusercontent.com/39984656/115116768-69191000-9fd6-11eb-95e0-bd3d9d4ebe4f.png)
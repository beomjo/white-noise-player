<h1 align="center">White Noise Player</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://github.com/beomjo/white-noise-player/actions/workflows/android.yml"><img alt="Build Status" src="https://github.com/beomjo/white-noise-player/actions/workflows/android.yml/badge.svg"/></a>
  <a href="https://ktlint.github.io/"><img alt="ktlint" src="https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg"/></a>
</p>

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
  - [Dagger Hilt](https://dagger.dev/hilt/) - dependency injection.  
- Firebase - FireStore, Storage
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Shimmer-Android](https://github.com/facebook/shimmer-android) - unobtrusive loading indicator, developed from Facebook 
- [Bindables](https://github.com/skydoves/Bindables) - DataBinding kit for notifying data changes from Model layers to UI layers.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Media Control](https://developer.android.com/jetpack/androidx/releases/media) - Share media contents and controls with other apps.  


## MAD Score
![summary](https://user-images.githubusercontent.com/39984656/126381975-dd1996b5-396a-46c3-8b31-e2242dbbe3a0.png)
![kotlin](https://user-images.githubusercontent.com/39984656/126381980-2f26e9aa-e017-4f81-b66f-267cf258f05e.png)


## Unit Testing
Unit Tests verify the interactions of viewmodels between repositories.
- [MockK](https://mockk.io/) - mocking library for Kotlin  
![image](https://user-images.githubusercontent.com/39984656/115115204-0d4a8900-9fce-11eb-9366-40dceb438785.png)


## Architecture
WhiteNoisePlayer is based on MVVM architecture and a repository pattern.
![image](https://user-images.githubusercontent.com/39984656/115115521-e2613480-9fcf-11eb-84a3-d9787999e22f.png)


## Audio Overview
The most basic architecture of an audio app is the client/server design.  
End implementation within `MediaBrowserService` UI and Media Controller run with `MediaBrowser`.
![image](https://user-images.githubusercontent.com/39984656/115116340-f60e9a00-9fd3-11eb-8d3e-91a4370a7f4b.png)


## Dagger Graph
Dagger graph represented by [Scabboard](https://arunkumar9t2.github.io/scabbard/)
![image](https://user-images.githubusercontent.com/39984656/115116768-69191000-9fd6-11eb-95e0-bd3d9d4ebe4f.png)


## LICENSE
```xml
Designed and developed by 2021 beomjo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

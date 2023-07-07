![VlogX](https://github.com/rommansabbir/VLogX/assets/25950083/0390236c-6ac1-4200-b046-35cc744edeea)

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

 <p align="center">
    <a href="https://android-arsenal.com/details/1/8160"><img alt="Maintained" src="https://img.shields.io/badge/Android%20Arsenal-VLogX-green.svg?style=flat" height="20"/></a>
</p>

 <p align="center">
     <a href="https://github.com/rommansabbir/VLogX"><img alt="Maintained" src="https://img.shields.io/badge/Maintained_Actively%3F-Yes-green.svg" height="20"/></a>
</p>

 <p align="center">
     <a href="https://jitpack.io/#rommansabbir/VLogX"><img alt="JitPack" src="https://img.shields.io/badge/JitPack-Yes-green.svg?style=flat" height="20"/></a>
</p>

<h1 align="center"> ⚡ Latest Version: 1.0.0 🔰</h1>

- Send log to VLogX while the application is in **Debug Mode** only.
- Simple APIs to show and manage logs.
- Allows to clear existing logs before sending new a log.
- Auto close the log view by calling specific API or press the close button.

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

<h1 align="center">Installation</h1>

## ➤ Step 1:

Add the JitPack repository to your build file .

```gradle
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

## ➤ Step 2:

Add the dependency.

```gradle
    dependencies {
            implementation 'com.github.rommansabbir:vlogx:1.0.0'
    }
```

## ➤ Step 3:
Initialize `VLogX` from your `Application.onCreate()`
```` kotlin
    VLogXService.init(this)
````

## ➤ Step 4:
- Send a new log.
````kotlin
    sendLog("This is our first log :D *-*")
````

- Clear existing logs.

````kotlin
    clearLog()
````
- Clear existing and exit VLogX.

````kotlin
    clearAndCloseLog()
````

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)


<h1 align="center">Notes:</h1>

- **VLogX** is desgined to work only in **Debug Mode** to prevent showing senstive logs to the product users.


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

<h1 align="center">Contributors:</h1>

- Amanullah Sarker
- Romman Sabbir


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)



### License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````html
Copyright (C) 2023 VLogX

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````

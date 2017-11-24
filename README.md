# PacmanView
Made in [![Appus Studio](https://github.com/alexey-kubas-appus/PacmanView/blob/master/images/appus.png)](https://appus.software)

PacmanView is a small library that allows you to add Pacman to your app. For example you can use it instead of ProgressBar.

* [Customization](#customization)
* [Demo](#demo)
* [Getting Started](#getting-started)
* [Info](#info)

# Customization

* pacman_color - Pacman color
* pacman_speed - Pacman animation speed
* circles_count - circles count
* circles_color - circles color
* circles_random_color - true/false. If true - circles_color will be ignored
* circles_speed - circles speed

# Demo

![](https://github.com/alexey-kubas-appus/PacmanView/blob/master/images/main.gif)

![](https://github.com/alexey-kubas-appus/PacmanView/blob/master/images/customized.gif)

# Getting Started

##Setup:

   Just add dependence to main build.gradle:

        dependencies {
                compile 'pro.appus:pacman-view:1.0.0'
        }
   
##Usage example:

   Just add view into your layout's .xml.
   
        <pro.appus.pacmanview.PacmanView
            android:layout_width="250dp"
            android:layout_height="70dp"
            app:pacman_color="@color/violet_dark"
            app:pacman_speed="10"
            app:circles_count="5"
            app:circles_color="@color/violet"
            app:circles_speed="3.4"/>

# Info

## Developed By

Ostapenko Yuriy, [Appus Studio](https://appus.software)]

## License

```
Copyright 2015 Appus Studio.

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

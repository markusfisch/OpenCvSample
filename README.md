OpenCV Camera Sample
====================

A minimal batteries-included OpenCV camera app for Android.

System Requirements
-------------------

* [Android SDK][android]
* [OpenCV Android SDK][opencv]

Please put the Android platform tools from the SDK in your PATH:

	export PATH=path/to/android/sdk/platform-tools:$PATH

Make sure OPEN_CV_HOME is set and points to the OpenCV SDK:

	export OPEN_CV_HOME=path/to/OpenCV-x.x.x-android-sdk

Running
-------

First enable [adb debugging][debugging] on the mobile device and plug it in.

On a system with a Unix shell and [make][make] simply run:

	$ make

The (short and very readable) Makefile covers building, running and more.

On everything else, just import the project into [Android Studio][android]
and click run.

[android]: https://developer.android.com/sdk/
[opencv]: http://opencv.org/
[make]: https://en.wikipedia.org/wiki/Make_(software)
[debugging]: https://developer.android.com/tools/help/adb.html#Enabling

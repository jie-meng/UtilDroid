# UtilDroid -- Util class and useful compound views for android.

## How to import UtilDroid library into Android project

1. Add the JitPack repository to your build file
    - gradle

        Add it in your root build.gradle at the end of repositories:

        ```
        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }
        ```

    - maven

        ```
        <repositories>
            <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
            </repository>
        </repositories>
        ```

2. Add the dependency
    - gradle

        ```
        dependencies {
                implementation 'com.github.jie-meng:UtilDroid:V1.1.1'
        }
        ```

    - maven

        ```
        <dependency>
            <groupId>com.github.jie-meng</groupId>
            <artifactId>UtilDroid</artifactId>
            <version>V1.1.1</version>
        </dependency>
        ```

## How to run UtilDroid Application

Execute command line (Make sure you have npm installed)

```
npm install -g mockserver

./mockserver.sh

```

Run UtilDroid on emulator with Android Studio

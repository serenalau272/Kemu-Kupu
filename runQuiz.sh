#!/bin/bash

/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Djdk.gtk.version=2 --module-path /home/student/javafx-sdk-11.0.2/javafx --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -Dfile.encoding=UTF-8 -classpath quiz.jar application.Main



#!/bin/bash

find . -name "*.class" -type "f" -print | xargs -I {} rm "{}"

echo "-=[ Generating talk.jar ]=-"
javac "./example/talk/Talker.java" "./example/talk/spi/Speech.java" "./example/talk/spi/SpeechProvider.java" "./example/talk/spi/DefaultSpeech.java"
[[ -f "./talk.jar" ]] && rm "./talk.jar"
cat <<EOCAT >"Manifest.txt"

EOCAT
jar cfm "./talk.jar" "Manifest.txt" "./example/talk/Talker.class" "./example/talk/spi/Speech.class" "./example/talk/spi/SpeechProvider.class" "./example/talk/spi/DefaultSpeech.class"

echo "-=[ Generating talk-boss.jar ]=-"
javac -cp "./talk.jar" "./example/talk/spi/impl/BossSpeech.java"
[[ -f "./talk-boss.jar" ]] && rm "./talk-boss.jar"
cat <<EOCAT >"Manifest.txt"

EOCAT
mkdir -p "./META-INF/services"
cat <<EOCAT >"./META-INF/services/example.talk.spi.Speech"
example.talk.spi.impl.BossSpeech
EOCAT
jar cfm "./talk-boss.jar" "Manifest.txt" "./example/talk/spi/impl/BossSpeech.class" "./META-INF/services/example.talk.spi.Speech"

echo "-=[ Generating talk-dog.jar ]=-"
javac "./example/talk/spi/impl/DogSpeech.java"
[[ -f "./talk-dog.jar" ]] && rm "./talk-dog.jar"
cat <<EOCAT >"Manifest.txt"

EOCAT
mkdir -p "./META-INF/services"
cat <<EOCAT >"./META-INF/services/example.talk.spi.Speech"
example.talk.spi.impl.DogSpeech
EOCAT
jar cfm "./talk-dog.jar" "Manifest.txt" "./example/talk/spi/impl/DogSpeech.class" "./META-INF/services/example.talk.spi.Speech"

echo "-=[ Generating application.jar ]=-"
javac -cp "./talk.jar" "./example/Talk.java"
[[ -f "./application.jar" ]] && rm "./application.jar"
cat <<EOCAT >"Manifest.txt"
Main-Class: example.Talk
EOCAT
jar cfm "./application.jar" "Manifest.txt" "./example/Talk.class"

echo "-=[ Running application without JAR files ]=-"
java -cp "./application.jar:./talk.jar" "example.Talk"

echo "'=[ Running application with talk-boss.jar JAR file ]=-"
java -cp "./application.jar:./talk.jar:./talk-boss.jar" "example.Talk"

echo "-=[ Running application with talk-dog.jar JAR file ]=-"
java -cp "./application.jar:./talk.jar:./talk-dog.jar" "example.Talk"

echo "-=[ Running application with talk-dog.jar and talk-boss.jar JAR files ]=-"
java -cp "./application.jar:./talk.jar:./talk-dog.jar:./talk-boss.jar" "example.Talk"

echo "-=[ Running application with talk-boss.jar and talk-dog.jar JAR files ]=-"
java -cp "./application.jar:./talk.jar:./talk-boss.jar:./talk-dog.jar" "example.Talk"

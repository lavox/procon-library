#!/bin/bash
TMP_DIR="tmp"

mkdir -p ${TMP_DIR}
rm -rf ${TMP_DIR}/*.class
rm -rf ${TMP_DIR}/*.java
rm -rf ${TMP_DIR}/*.jar

echo "[INFO] Copy all .java flatly"
find lib -name '*.java' -exec cp -p {} "$TMP_DIR/" \;
find test/oj-verify -name '*.java' -exec cp -p {} "$TMP_DIR/" \;
find test/unittest -name '*.java' -exec cp -p {} "$TMP_DIR/" \;

cp dependency/junit-platform-console-standalone-1.13.2.jar "$TMP_DIR/"

cd "$TMP_DIR"

oj-verify run

javac -cp junit-platform-console-standalone-1.13.2.jar *.java
java -jar junit-platform-console-standalone-1.13.2.jar execute --class-path . --scan-classpath
cd ..

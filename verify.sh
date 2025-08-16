#!/bin/bash
set -e
SRC_DIR="lib"
JUNIT_DIR="test/unittest"

# 一時ディレクトリ作成
TMP_DIR=$(mktemp -d)
echo "Using temporary directory: $TMP_DIR"

# JUnit JAR のパス
JUNIT_JAR="./dependency/junit-platform-console-standalone-1.13.2.jar"

CLASSPATH="$TMP_DIR:$JUNIT_JAR"

# Java ファイルをコンパイル
echo "Compiling Java files..."
find $SRC_DIR $JUNIT_DIR -name "*.java" > /tmp/java_files.txt
javac -d "$TMP_DIR" -cp "$CLASSPATH" @/tmp/java_files.txt

# テスト実行
echo "Running tests..."
java -jar $JUNIT_JAR execute --class-path "$CLASSPATH" --scan-classpath

# 一時ディレクトリ削除
rm -rf "$TMP_DIR"
echo "Done."

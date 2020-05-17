#!/usr/bin/env bash
set -e

JDK_PATH="/tmp/amazon-corretto-8.jdk"
export JAVA_HOME="$JDK_PATH/Contents/Home"

if [ ! -d "$JAVA_HOME" ]; then
  pushd "$(dirname "$JDK_PATH")" || exit
  wget "https://corretto.aws/downloads/latest/amazon-corretto-8-x64-macos-jdk.tar.gz"
  tar xvf amazon-corretto-8-x64-macos-jdk.tar.gz
  popd || exit
fi
bash ./mvnw package

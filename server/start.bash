#!/bin/bash

mvn clean package

java -jar ./target/server-1.0-SNAPSHOT.jar
#!/bin/bash

mvn clean package

java -jar ./target/client-1.0-SNAPSHOT.jar
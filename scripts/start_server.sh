#!/bin/sh

cd /home/ec2-user/app
nohup java -jar target/junkyard-0.0.1-SNAPSHOT.jar > nohup.out 2>&1 &

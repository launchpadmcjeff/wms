#!/bin/sh

APP_PID=$(ps -ef | grep [j]ava | awk '{print $2}')

if [ $APP_PID ]
then
	kill $APP_PID
fi


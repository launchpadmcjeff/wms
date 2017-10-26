# Warez-my-stuff: a warehousing app

## What is it
A warehousing app for individuals possbily extensible to shops?

## Features
* unit tests
* integration tests
* jmeter performance tests
* deployment to AWS

## Prerequisites
1. Java - [homepage and download](https://java.oracle.com "Java's Homepage")
2. Maven 
3. Wildfly

## Test Environment

###### Java

```bash
$ java -version
java version "1.8.0_40"
Java(TM) SE Runtime Environment (build 1.8.0_40-b26)
Java HotSpot(TM) 64-Bit Server VM (build 25.40-b25, mixed mode)
```
###### Maven

```
$ mvn -v
Apache Maven 3.2.1 (ea8b2b07643dbb1b84b6d16e1f08391b666bc1e9; 2014-02-14T09:37:52-08:00)
Maven home: C:\apache-maven-3.2.1
Java version: 1.8.0_40, vendor: Oracle Corporation
Java home: C:\Java\x64\jdk1.8.0_40\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 7", version: "6.1", arch: "amd64", family: "dos"

```

###### Wildfly
* wildfly-10.1.0.Final


## Running
clone the repo

```bash
mvn clean package
```

deploy to wildfly

## TODO
* AWS deployment pipeline

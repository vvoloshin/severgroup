#!/bin/bash
#specified-path:
mkdir -p -- csvparsing/inbox
mkdir -p -- csvparsing/outboxcsv
mkdir -p -- csvparsing/outboxxml
mkdir -p -- csvparsing/parsed
mkdir -p -- csvparsing/log
mkdir -p -- csvparsing/error

#path-to-config-file:
config=config.properties

java -jar csvhandler.jar $config

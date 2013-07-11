#!/bin/sh

#crude

./compile.sh

rm -rf ./doc/*
javadoc -d ./doc/ -classpath ./lib/jna-3.3.0.jar:./classes/ -sourcepath ./src/ ch.lowres.jlo

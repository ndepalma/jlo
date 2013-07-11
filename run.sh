#!/bin/sh

java -cp ./dist/jlo.jar:./lib/jna-3.3.0.jar $@

#java -cp ./classes/:./lib/jna-3.3.0.jar $@

#liblo must be installed at a standard location (/usr/lib/ or /usr/local/lib)

#if that doesn't work, locate and copy liblo so files and symbolic links 
#to the ./lib dir and start like

#LD_LIBRARY_PATH=./lib/ java -cp .:./classes/:./lib/jna-3.3.0.jar $@

#./lib:
#jna-3.3.0.jar
#liblo.so.7.0.0
#liblo.so.7 -> liblo.so.7.0.0
#liblo.so -> liblo.so.7.0.0
#jna-3.3.0-sources.jar

#example calls for the test classes:
#./run.sh ch.lowres.jlo.test.NetAddressTest
#./run.sh ch.lowres.jlo.test.MessageTest
#./run.sh ch.lowres.jlo.test.BlobTest
#./run.sh ch.lowres.jlo.test.ServerTest
#./run.sh ch.lowres.jlo.test.SendTest


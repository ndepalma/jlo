#!/bin/sh

#crude

./compile.sh

mkdir -p ./dist

cur=`pwd`

now=`date +"%s"`

cd ./classes
echo "creating jar..."
jar cfv jlo_${now}.jar ch/
ls -l jlo_${now}.jar
echo "copy jlo_${now}.jar to build dir..."
cp jlo_${now}.jar "$cur"/dist
rm -f "$cur"/dist/jlo.jar
ln -s "$cur"/dist/jlo_${now}.jar "$cur"/dist/jlo.jar
rm -f jlo_${now}.jar

cd ..
cd ./src
echo "creating source jar..."
jar cfv jlo_${now}_src.jar ch/
ls -l jlo_${now}_src.jar
echo "copy jlo_${now}_src.jar to build dir..."
cp jlo_${now}_src.jar "$cur"/dist
rm -f jlo_${now}_src.jar

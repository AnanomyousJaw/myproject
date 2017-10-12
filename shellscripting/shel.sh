#! /bin/bash


shell1=$(date +%F)

echo $shell1
echo "hello    world"
echo "hello"   world
echo 'hello    world'
echo hello   world

prefix=26nov16batch
suffix1=$(date +%F)
suffix2=$(date +%H)
suffix3=$(date +%M)
sp="-"
echo $prefix
echo $suffix1
echo $suffix2
echo $sp
filename=$prefix$suffix1$sp$suffix2$sp$suffix3
cp -r /home/gopi/Desktop/shellscripting /home/gopi/Documents/$filename
echo "Back up successful"


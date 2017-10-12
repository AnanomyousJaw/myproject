#!/bin/sh
sleep 10
sudo rm -rf /tmp/*.avi
sudo service motion restart
python /home/pi/Desktop/smartAgriRobo/smartAgriRobo.py &
sudo rm -rf /tmp/*.avi
while true;do
	sleep 10
	sudo rm -rf /tmp/*.jpg
done
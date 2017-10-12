#!/usr/bin/env python
import time
import os
import spidev
import httplib, urllib
import RPi.GPIO as GPIO
import lcd
import IoTSend
import socket

LCD_LINE_1 = 0x80 # LCD RAM address for the 1st line
LCD_LINE_2 = 0xC0 # LCD RAM address for the 2nd line

MOTOR1_POS = 12
MOTOR1_NEG = 16
MOTOR2_POS = 20
MOTOR2_NEG = 21
RELAY_MOTOR = 23
RELAY_SPARY = 24
IR_SENSOR   = 18


spi = spidev.SpiDev()
spi.open(0,0)

global tempError
global bpError
global ecgError

def readChannel(channel):
  adc = spi.xfer2([1,(8+channel)<<4,0])
  data = ((adc[1]&3) << 8) + adc[2]
  return data

def convertTemp(data,places):
 
  # ADC Value
  # (approx)  Temp  Volts
  #    0      -50    0.00
  #   78      -25    0.25
  #  155        0    0.50
  #  233       25    0.75
  #  310       50    1.00
  #  465      100    1.50
  #  775      200    2.50
  # 1023      280    3.30
 
  temp = (((data * 330)/float(1023))+5)#-50
  temp = round(temp,places)
  return temp


def readTempSensor():
    temp = readChannel(0)
    print temp
    temp = convertTemp(temp,2)
    return temp

def readSoilInfoSensor():
    x_data = readChannel(1)
    return x_data

def Forword():
  global MOTOR1_POS
  global MOTOR1_NEG
  global MOTOR2_POS
  global MOTOR2_NEG
  GPIO.output(MOTOR1_POS,GPIO.HIGH)
  GPIO.output(MOTOR1_NEG, GPIO.LOW)
  GPIO.output(MOTOR2_POS, GPIO.HIGH)
  GPIO.output(MOTOR2_NEG, GPIO.LOW)

def Reverse():
  global MOTOR1_POS
  global MOTOR1_NEG
  global MOTOR2_POS
  global MOTOR2_NEG
  GPIO.output(MOTOR1_POS,GPIO.LOW)
  GPIO.output(MOTOR1_NEG, GPIO.HIGH)
  GPIO.output(MOTOR2_POS, GPIO.LOW)
  GPIO.output(MOTOR2_NEG, GPIO.HIGH)


def Left():
  global MOTOR1_POS
  global MOTOR1_NEG
  global MOTOR2_POS
  global MOTOR2_NEG
  GPIO.output(MOTOR1_POS,GPIO.HIGH)
  GPIO.output(MOTOR1_NEG, GPIO.LOW)
  GPIO.output(MOTOR2_POS, GPIO.LOW)
  GPIO.output(MOTOR2_NEG, GPIO.LOW)

def Right():
  global MOTOR1_POS
  global MOTOR1_NEG
  global MOTOR2_POS
  global MOTOR2_NEG
  GPIO.output(MOTOR1_POS,GPIO.LOW)
  GPIO.output(MOTOR1_NEG, GPIO.LOW)
  GPIO.output(MOTOR2_POS, GPIO.HIGH)
  GPIO.output(MOTOR2_NEG, GPIO.LOW)

def Stop():
  global MOTOR1_POS
  global MOTOR1_NEG
  global MOTOR2_POS
  global MOTOR2_NEG
  GPIO.output(MOTOR1_POS,GPIO.LOW)
  GPIO.output(MOTOR1_NEG, GPIO.LOW)
  GPIO.output(MOTOR2_POS, GPIO.LOW)
  GPIO.output(MOTOR2_NEG, GPIO.LOW)   

def main_program():
  global MOTOR1_POS
  global MOTOR1_NEG
  global MOTOR2_POS
  global MOTOR2_NEG
  global RELAY_MOTOR
  global RELAY_SPARY
  global IR_SENSOR
  delayCounter = 0
  internetData = 0
  roboState= 0
  GPIO.setmode(GPIO.BCM)
  GPIO.setup(RELAY_MOTOR,GPIO.OUT)
  GPIO.setup(RELAY_SPARY,GPIO.OUT)
  GPIO.setup(MOTOR1_POS,GPIO.OUT)
  GPIO.setup(MOTOR1_NEG,GPIO.OUT)
  GPIO.setup(MOTOR2_POS,GPIO.OUT)
  GPIO.setup(MOTOR2_NEG,GPIO.OUT)
  GPIO.setup(IR_SENSOR,GPIO.IN)

  
  GPIO.output(RELAY_MOTOR, GPIO.LOW)
  GPIO.output(RELAY_SPARY, GPIO.LOW)
  GPIO.output(MOTOR1_POS,GPIO.LOW)
  GPIO.output(MOTOR1_NEG, GPIO.LOW)
  GPIO.output(MOTOR2_POS, GPIO.LOW)
  GPIO.output(MOTOR2_NEG, GPIO.LOW)

  lcd.lcd_init()
  # Send some test
  lcd.lcd_string("Agriculture ",LCD_LINE_1)
  lcd.lcd_string("Robo System",LCD_LINE_2)
  time.sleep(3)
  while True: 
      temp = readTempSensor()
      print 'temp:' + str(temp) + '    '
      soilInfo = readSoilInfoSensor()
      print 'soil:' + str(soilInfo)
      lcdLine1 = 'TEMP:' + str(temp)
      lcdLine2 = 'SOIL:' + str(soilInfo)
      if GPIO.input(IR_SENSOR):
        if internetData == '1':
          Stop()
        lcdLine1 = lcdLine1 + ' AD'
      lcd.lcd_string(lcdLine1,LCD_LINE_1)
      lcd.lcd_string(lcdLine2,LCD_LINE_2)
      if roboState == 0:
        if int(soilInfo) > 500:
          GPIO.output(RELAY_MOTOR, GPIO.LOW)
        else:
          delayCounter = delayCounter + 1
        if delayCounter > 5:  
          GPIO.output(RELAY_MOTOR, GPIO.HIGH)
          delayCounter = 0


      print 'IR:'+ str(GPIO.input(IR_SENSOR))

      try:
          socketId = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
          host= '54.255.248.231'
          port =3011
          # connection to hostname on the port.
          socketId.connect((host, port))
          socketId.settimeout(2.0)
          if GPIO.input(IR_SENSOR):
            if internetData == '1':
              Stop()
          internetData = socketId.recv(1)      
          print data
          socketId.settimeout(None)
          socketId.close()
      except:
          print 'timeout'
      if internetData == '1':
        if GPIO.input(IR_SENSOR) == 0:
            Forword()
        else:
            Stop()
        roboState = 1
        GPIO.output(RELAY_MOTOR, GPIO.LOW)
      if internetData == '2':
        Reverse()
        roboState = 1
        GPIO.output(RELAY_MOTOR, GPIO.LOW)
      if internetData == '3':
        Left()
        roboState = 1
        GPIO.output(RELAY_MOTOR, GPIO.LOW)
      if internetData == '4':
        Right()
        roboState = 1
        GPIO.output(RELAY_MOTOR, GPIO.LOW)
      if internetData == '5':
        Stop()
        roboState = 0 
      if internetData == '6':
        GPIO.output(RELAY_SPARY, GPIO.HIGH)
      if internetData == '7':
        GPIO.output(RELAY_SPARY, GPIO.LOW)
      if GPIO.input(IR_SENSOR):
        if internetData == '1':
          Stop()  
      IoTSend.send_IoTData(temp,soilInfo)
      time.sleep(1)
  return

main_program()

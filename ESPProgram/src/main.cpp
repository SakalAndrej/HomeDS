#include <Arduino.h>
#include <IRremoteESP8266.h>
#include "IRsend.h"


  IRsend irsender(0);
  //IRsend panasonic64(0);

void setup() {
  irsender.begin();
  Serial.begin(9600);
  Serial.println("START");
}

void loop() {

/*
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);
  irsender.sendPanasonic(0x4004, 0x0190ED7C);*/
  Serial.print("first");
  delay(1000);

  //irsender.sendPanasonic64(0x40040190ED7C, PANASONIC_BITS, 10);

  delay(1000);

}

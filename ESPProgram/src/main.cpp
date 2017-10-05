#include <Arduino.h>
#include <IRremoteESP8266.h>
#include "IRsend.h"


  IRsend panasonic(0);
  //IRsend panasonic64(0);

void setup() {
  panasonic.begin();
  Serial.begin(9600);
  Serial.println("START");
}

void loop() {


  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  panasonic.sendPanasonic(0x4004, 0x0190ED7C);
  Serial.print("first");
  delay(1000);

  panasonic.sendPanasonic64(0x40040190ED7C, PANASONIC_BITS, 10);

  delay(1000);

}

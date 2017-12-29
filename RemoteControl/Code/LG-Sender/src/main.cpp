#include <IRremoteESP8266.h>
#include <IRrecv.h>
#include <IRutils.h>
#include "IRsend.h"

uint16_t SEND_PIN = 2;

IRsend irsend(SEND_PIN);

void setup()
{
  Serial.println("Setup initiated ...");
  irsend.begin();
  Serial.begin(9600);
}

void loop() {
irsend.sendNEC(0x20df10ef, 32);
delay(5000);
Serial.print(".");
}

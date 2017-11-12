#include <IRremoteESP8266.h>
#include <IRrecv.h>
#include <IRutils.h>
#include "IRsend.h"

uint16_t RECV_PIN = 4; //an IR detector connected to D4

IRrecv irrecv(RECV_PIN);
IRsend irsend(0);


decode_results results;

void setup()
{
  Serial.println("Setup initiated ...");
  irsend.begin();
  Serial.begin(9600);
  irrecv.enableIRIn(); // Start the receiver
}

void loop() {

  Serial.println("Waiting for IR-Signal ...");
  if (irrecv.decode(&results)) {
    for(int i = 0; i < 5; i++) {
      delay(1000);
      Serial.print(".");
    }

    Serial.print("Empfangen: ");
    serialPrintUint64(results.value, HEX);


    irsend.sendLG(results.value);
      delay(3000);
    Serial.println("Sent 1st signal");
    irsend.sendLG(results.value);
      delay(3000);
      Serial.println("Sent 2nd signal");
    irsend.sendLG(results.value);
      delay(3000);
      Serial.println("Sent 3rd signal");


    Serial.println("i made it 2");
    irrecv.resume(); // Receive the next value
  }
}

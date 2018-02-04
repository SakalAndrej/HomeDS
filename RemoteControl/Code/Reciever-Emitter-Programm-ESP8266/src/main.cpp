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
  Serial.println("Waiting for IR-Signal ");  
}

void loop() {
  Serial.print(".");

  if (irrecv.decode(&results)) {
    for(int i = 0; i < 5; i++) {
      delay(1000);
      Serial.print(".");
    }

    Serial.print("Empfangen: ");
    serialPrintUint64(results.value, HEX);

    if(results.decode_type == NEC)
    {
      Serial.println("\nNEC");
    }
    if (results.decode_type == UNKNOWN)
    {
      Serial.println("Unknown");
    }

    irsend.sendRaw((uint16*)results.rawbuf,results.rawlen,38);
    delay(3000);
    Serial.println("Send 3 times");

    irsend.sendRaw((uint16*)results.rawbuf,results.rawlen,38);
    delay(3000);
    Serial.println("Send another 3 times");

    /*irsend.sendLG(results.value);
      delay(3000);
    Serial.println("Sent 1st signal");
    irsend.sendLG(results.value);
      delay(3000);
      Serial.println("Sent 2nd signal");
    irsend.sendLG(results.value);
      delay(3000);
      Serial.println("Sent 3rd signal");*/

    irrecv.resume(); // Receive the next value
  }
}

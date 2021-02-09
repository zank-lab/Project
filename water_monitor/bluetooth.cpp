#include "bluetooth.h"

bluetooth::bluetooth(int pin){
  pinMode(pin,INPUT);
  _pin=pin;
  Serial1.begin(9600);
}

void bluetooth::send_bluetooth(String msg){
//  if( digitalRead(_pin)==HIGH ){
//    Serial.print("Master sent : ");
// Serial.println(msg);
    Serial1.println(msg);
//  }
//  else{ //
//Serial.println("Bluetooth not connected");
   }
}

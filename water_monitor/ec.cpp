#include "ec.h"
#include "Arduino.h"


ec::ec(int pin){
  _pin=pin;
    ecS.begin();
}

String ec::electric_con(float temperature_c){
  
  voltage = analogRead(_pin)/1023.0*5000;  // read the voltage
  temperature=temperature_c;
  ecValue =  ecS.readEC(voltage,temperature);
  Serial.print("    ec is:"); 
 Serial.println(ecValue);
  return String(ecValue); 
}

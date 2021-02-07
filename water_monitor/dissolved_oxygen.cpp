#include "dissolved_oxygen.h"
#include "Arduino.h"


dissolved_oxygen::dissolved_oxygen(int pin){
  _pin=pin;
}

float dissolved_oxygen::func_do(int temperature_c){
  
  ADC_Raw = analogRead(_pin);
  voltage_mv = uint32_t(5000) * ADC_Raw / 1023;
  V_saturation= (int16_t)40*temperature_c+907; // napiecie nasycenie w mv
  do_val=(voltage_mv * DO_Table[temperature_c] / V_saturation);
 Serial.println("do:" + String(do_val));
  return float(do_val); 
}

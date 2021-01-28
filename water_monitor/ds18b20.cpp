#include <OneWire.h> 
#include <DallasTemperature.h>
#include "ds18b20.h"


ds18b20::ds18b20(int pin){
  this->oneWire = new OneWire(pin);
  this->sensors= new DallasTemperature(oneWire);
}

String ds18b20::func_temp(){
 (*sensors).requestTemperatures();  
 temp=String((*sensors).getTempCByIndex(0));
 
 return temp; 
}

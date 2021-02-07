#include "bluetooth.h"
#include "ds18b20.h"
#include "sensors.h"
#include "ph.h"
#include "turbidity.h"
#include "dissolved_oxygen.h"



sensors::sensors(int state_bluetooth, int pin_ds18, int pin_ph,int pin_turbidity, int pin_do, int pin_ec){
  this->bluetooth_adapter = new bluetooth(state_bluetooth);
  this->ds18= new ds18b20(pin_ds18);
  this->ph_adapter = new ph(pin_ph);
  this->turbidity_adapter = new turbidity(pin_turbidity);
  this->tlen_adapter = new dissolved_oxygen(pin_do);
  this->ec_adapter = new ec(pin_ec);
}

void sensors::result(){
  temperature=(*ds18).func_temp();
  (*bluetooth_adapter).send_bluetooth(" " + temperature + "t");
  delay(100);
  ph_val = (*ph_adapter).func_ph();
  (*bluetooth_adapter).send_bluetooth(" " + ph_val + "ph");
  delay(100);
  turbidity_val = (*turbidity_adapter).func_turbidity();
  (*bluetooth_adapter).send_bluetooth(" " + turbidity_val + "zm");
  
  delay(100);
  tlen_val = (*tlen_adapter).func_do( (int)round(temperature.toFloat())) / 1000;
  (*bluetooth_adapter).send_bluetooth(" " + String(tlen_val) + "do");
 // Serial.println(tlen_val);

  delay(100);
  ec_val = (*ec_adapter).electric_con(temperature.toFloat());
  (*bluetooth_adapter).send_bluetooth(" "+ ec_val + "ec");
  
}

  String sensors::get_temperature(){
  return temperature;
  }

  String sensors::get_turbidity_val(){
  return turbidity_val;
  }

  String sensors::get_ph_val(){
  return ph_val;
  }

  String sensors::get_ec_val(){
  return ec_val;
  }

  float sensors::get_do(){
  return tlen_val;
  }

#pragma once
#ifndef sensors_h
#define sensors_h

#include "ph.h"
#include "bluetooth.h"
#include "ds18b20.h"
#include "turbidity.h"
#include "dissolved_oxygen.h"


class sensors
{
  public:
  sensors(int state_bluetooth, int pin_ds18, int pin_ph , int pin_turbidity, int pin_do);
  
  void result();
  String get_temperature();
  String get_ph_val();
  String get_turbidity_val();
  float get_do();
  
  private:
  bluetooth* bluetooth_adapter;
  ds18b20* ds18;
  ph* ph_adapter;
  turbidity* turbidity_adapter;
  dissolved_oxygen* tlen_adapter;
  String temperature;
  String ph_val;
  String turbidity_val;
  float tlen_val;
};

#endif

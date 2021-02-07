#pragma once
#ifndef ec_h
#define ec_h

#include "DFRobot_EC10.h"
#include "Arduino.h"
class ec
{
  public:
  ec(int pin);
  String electric_con(float temperature_c);
  private:
  int _pin;
  float voltage,ecValue,temperature;
  DFRobot_EC10 ecS;
};
#endif

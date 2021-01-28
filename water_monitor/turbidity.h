#pragma once
#ifndef turbidity_h
#define turbidity_h
#include "Arduino.h"
#define ArrayLenth_ntu 800

class turbidity
{
  public:
  turbidity(int pin);
  String func_turbidity();
  private:
  int _pin;
  float voltage_ntu;
  float ntu;
};

#endif

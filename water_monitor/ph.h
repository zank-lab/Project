#pragma once
#ifndef ph_h
#define ph_h

#include "Arduino.h"
#define ArrayLenth 10
#define median_offset 2
class ph
{
  public:
  ph(int pin);
  String func_ph();
  private:
  int _pin;
  unsigned long int avgValue;  //Store the average value of the sensor feedback
  float phValue;
  int buf[ArrayLenth],temp;
  
};

#endif

#pragma once
#ifndef dissolved_oxygen_h
#define dissolved_oxygen_h
#include "Arduino.h"
class dissolved_oxygen
{
  public:
  dissolved_oxygen(int pin);
  float func_do(int temperature_c);
  private:
  int _pin;
  uint16_t ADC_Raw;
  uint32_t voltage_mv;
  int16_t do_val;
  uint16_t V_saturation;
  const int DO_Table[41] = {
    14460, 14220, 13820, 13440, 13090, 12740, 12420, 12110, 11810, 11530,
    11260, 11010, 10770, 10530, 10300, 10080, 9860, 9660, 9460, 9270,
    9080, 8900, 8730, 8570, 8410, 8250, 8110, 7960, 7820, 7690,
    7560, 7430, 7300, 7180, 7070, 6950, 6840, 6730, 6630, 6530, 6410};
};
  const int CAL1_V = 1788,CAL1_T=22,CAL2_V=1427,CAL2_T=13;

#endif

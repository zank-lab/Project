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
  const int DO_Table[36] = {
14602,14198,13813,13445,13094,12757,12436,12127,11832,11549,11277,11016,10766,10525,10294,10072,9858,9651,9453,9261,9077,8989,8726,8560,8400,8244,8094,7949,7808,7671,7539,7411,7287,7166,7049,6935
};
};
//  const int CAL1_V = 1788,CAL1_T=22,CAL2_V=1427,CAL2_T=13;

#endif

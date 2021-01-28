#pragma once
#ifndef ds18b20_h
#define ds18b20_h
#include <OneWire.h> 
#include <DallasTemperature.h>


class ds18b20
{
  public:
  ds18b20(int pin);
  String func_temp();
  private:
	String temp;
	OneWire* oneWire;
  DallasTemperature* sensors;
};

#endif

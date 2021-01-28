#pragma once
#ifndef bluetooth_h
#define bluetooth_h

#include "Arduino.h"

class bluetooth
{
  public:
  bluetooth(int pin);
  void send_bluetooth(String msg);
  private:
  int _pin;
};

#endif

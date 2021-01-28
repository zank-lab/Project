#include "turbidity.h"
#include "Arduino.h"

turbidity::turbidity(int pin){
  _pin=pin;
  voltage_ntu=0;
}

// x1=3.08 - 630, x2=4.78 - 978.

String turbidity::func_turbidity(){
  
    for(int i=0; i<ArrayLenth_ntu; i++)
    {
        voltage_ntu += ((float)analogRead(_pin)/1023)*5;
    }
    voltage_ntu=voltage_ntu/ArrayLenth_ntu;
   voltage_ntu=round(voltage_ntu*10)/10;
//   Serial.print("    read is:"); 
   voltage_ntu=voltage_ntu+0.2;
 //  Serial.println(voltage_ntu);
   delay(10);
   if(voltage_ntu < 2.5 ){
      return String(3000);
    }else if (voltage_ntu > 4.2){
       return String("0");
    }
  ntu = -1120.4*square(voltage_ntu)+5742.3*voltage_ntu-4353.9;
  ntu=round(ntu);
//  Serial.print("    ntu is:"); 
//  Serial.println(ntu);
  return String(ntu);
}

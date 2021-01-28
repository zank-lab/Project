#include "ph.h"
#include "Arduino.h"


ph::ph(int pin){
  _pin=pin;
  
}

String ph::func_ph(){

     for(int i=0;i<ArrayLenth;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(_pin);
    delay(10);
  }
  for(int i=0;i<(ArrayLenth-1);i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<ArrayLenth;j++)
    {
      if(buf[i]>buf[j])
      {
        temp=buf[i];
        buf[i]=buf[j];
        buf[j]=temp;
      }
    }
  }
  avgValue=0;
  for(int i=median_offset;i<(ArrayLenth-median_offset);i++)                      //take the average value of 6 center sample
    avgValue+=buf[i];
  float phValue=(float)avgValue*5.0/1023/(ArrayLenth-2*median_offset); //convert the analog into millivolt
// Serial.print("    vol is:");  
//  Serial.print(phValue,2);
 // Serial.println(" ");
  phValue=3.5*phValue;                      //convert the millivolt into pH value
 // Serial.print("    pH:");  
//  Serial.print(phValue,2);
//  Serial.println(" ");
  return String(phValue);
  
}

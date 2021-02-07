#include "ph.h"
#include "Arduino.h"


ph::ph(int pin){
  _pin=pin;
  
}

String ph::func_ph(){
  
for(int i=0;i<ArrayLenth;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(_pin);
    delay(1);
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
 for(int i=median_offset;i<(ArrayLenth-median_offset);i++)                      
 avgValue+=buf[i];
 float phValue=(float)avgValue*5000.0/1023/(ArrayLenth-2*median_offset); 

 phValue=phValue/1000;
 phValue=3.55*phValue-0.04;                      
 return String(phValue);
  
}

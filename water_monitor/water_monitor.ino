#include "sensors.h"
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);

sensors sensors(4,12, A2, A0, A3);

void setup(void) 
{ 
 Serial.begin(9600);
 setup_lcd();
} 

void loop(void) 
{ 
  sensors.result();
  print_lcd(0, "Temp: " + sensors.get_temperature() );
  print_lcd(1, "ph: " + sensors.get_ph_val() + "    ");
  delay(5000);
  print_lcd(0, "ntu: " + sensors.get_turbidity_val() + "  " );
  print_lcd(1, "do: " + String(sensors.get_do()) + " mg/l" );
   delay(5000);
   
} 



void print_lcd(int row, String tekst){
  lcd.setCursor(0,row); //Second line
  lcd.print(tekst);
  }

  void setup_lcd(){
  lcd.begin(16,2);   // iInit the LCD for 16 chars 2 lines
  lcd.backlight();   // Turn on the backligt (try lcd.noBaklight() to turn it off)
  lcd.setCursor(0,0); //First line
  lcd.print("Water");
  lcd.setCursor(0,1); //Second line
  lcd.print("Monitor");
 }

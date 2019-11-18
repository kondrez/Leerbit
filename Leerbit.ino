/*
   Schooljaar: 2019-2020
   Opleiding: ICT NSE Leerjaar 1
   Project: "The Challenge"
   Porduct: Leerbit
   Projectgroep: A
   Klas: D1

   Deelnemers:
   Marko Estrada Rodriguez 16138643
   Christiaan Calf 18129390
   Daniël Bleeker 19046278
   Lucas Dekker 19038364
   Eddy Al Bazze 19085834
*/


#include <Arduino.h>
#include <U8x8lib.h>
#include <U8g2lib.h>
#include <SD.h>
#include <SPI.h>
#include <Wire.h>
#include <RTClib.h>




/* Allocation I2C (RTC)
   SD card attached to I2C bus on arduino mega as follows:
 ** SDA - 20
 ** SCL - 21
  /*


  /*Allocation Oled Display*/
U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2(U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ U8X8_PIN_NONE);   // All Boards without Reset of the Display

/* Allocation sd card reader
   SD card attached to SPI bus on arduino mega as follows:
 ** MOSI - pin 51
 ** MISO - pin 50
 ** CLK - pin 52
 ** CS - pin 53
  /*

  //Constants

  /*Allocation Leds*/
const int led_1_G = 46;
const int led_1_R = 47;
const int led_2_G = 44;
const int led_2_R = 45;
const int led_3_G = 42;
const int led_3_R = 43;
const int led_4_G = 40;
const int led_4_R = 41;
const int led_5_G = 38;
const int led_5_R = 39;
const int led_6_G = 36;
const int led_6_R = 37;
const int led_7_G = 34;
const int led_7_R = 35;
const int led_8_G = 32;
const int led_8_R = 33;
const int led_9_G = A14;
const int led_9_R = A15;
const int led_10_G = A12;
const int led_10_R = A13;
const int led_11_G = A10;
const int led_11_R = A11;
const int led_12_G = A8;
const int led_12_R = A9;
const int led_13_G = A6;
const int led_13_R = A7;
const int led_14_G = A4;
const int led_14_R = A5;
const int led_15_G = A2;
const int led_15_R = A3;


/*Allocation buttons*/
const int buttonPinA = 19;
const int buttonPinB = 17;
const int buttonPinC = 15;
const int buttonPinD = 13;

/*Allocation Touch buttons*/
const int buttonPinLeft = 31;
const int buttonPinRight = 3;

/*Allocation light Sensor*/
const int photocellPin = A0;


//Variables
int lightValue;  // variable for storing the value from photoresistor (0-1023)

int buttonState_A = 0;         // variable for reading the pushbutton A status
int buttonState_B = 0;         // variable for reading the pushbutton B status
int buttonState_C = 0;         // variable for reading the pushbutton C status
int buttonState_D = 0;         // variable for reading the pushbutton D status

int buttonState_Left = 0;         // variable for reading the left touchbutton status
int buttonState_Right = 0;        // variable for reading the right touchbutton status


void setup() {
  Serial.begin(9600); // Start serial communication
  u8g2.begin();  // Start oled display

  pinMode(led_1_G, OUTPUT); // Declare the LED 1 GREEN as an output
  pinMode(led_1_R, OUTPUT); // Declare the LED 1 RED as an output
  pinMode(led_2_G, OUTPUT); // Declare the LED 2 GREEN as an output
  pinMode(led_2_R, OUTPUT); // Declare the LED 2 RED as an output
  pinMode(led_3_G, OUTPUT); // Declare the LED 3 GREEN as an output
  pinMode(led_3_R, OUTPUT); // Declare the LED 3 RED as an output
  pinMode(led_4_G, OUTPUT); // Declare the LED 4 GREEN as an output
  pinMode(led_4_R, OUTPUT); // Declare the LED 4 RED as an output
  pinMode(led_5_G, OUTPUT); // Declare the LED 5 GREEN as an output
  pinMode(led_5_R, OUTPUT); // Declare the LED 5 RED as an output
  pinMode(led_6_G, OUTPUT); // Declare the LED 6 GREEN as an output
  pinMode(led_6_R, OUTPUT); // Declare the LED 6 RED as an output
  pinMode(led_7_G, OUTPUT); // Declare the LED 7 GREEN as an output
  pinMode(led_7_R, OUTPUT); // Declare the LED 7 RED as an output
  pinMode(led_8_G, OUTPUT); // Declare the LED 8 GREEN as an output
  pinMode(led_8_R, OUTPUT); // Declare the LED 8 RED as an output
  pinMode(led_9_G, OUTPUT); // Declare the LED 9 GREEN as an output
  pinMode(led_9_R, OUTPUT); // Declare the LED 9 RED as an output
  pinMode(led_10_G, OUTPUT); // Declare the LED 10 GREEN as an output
  pinMode(led_10_R, OUTPUT); // Declare the LED 10 RED as an output
  pinMode(led_11_G, OUTPUT); // Declare the LED 11 GREEN as an output
  pinMode(led_11_R, OUTPUT); // Declare the LED 11 RED as an output
  pinMode(led_12_G, OUTPUT); // Declare the LED 12 GREEN as an output
  pinMode(led_12_R, OUTPUT); // Declare the LED 12 RED as an output
  pinMode(led_13_G, OUTPUT); // Declare the LED 13 GREEN as an output
  pinMode(led_13_R, OUTPUT); // Declare the LED 13 RED as an output
  pinMode(led_14_G, OUTPUT); // Declare the LED 14 GREEN as an output
  pinMode(led_14_R, OUTPUT); // Declare the LED 14 RED as an output
  pinMode(led_15_G, OUTPUT); // Declare the LED 15 GREEN as an output
  pinMode(led_15_R, OUTPUT); // Declare the LED 15 RED as an output



  pinMode(buttonPinA, INPUT); // Initialize the pushbutton A pin as an input
  pinMode(buttonPinB, INPUT); // Initialize the pushbutton B pin as an input
  pinMode(buttonPinC, INPUT); // Initialize the pushbutton C pin as an input
  pinMode(buttonPinD, INPUT); // Initialize the pushbutton D pin as an input

  pinMode(buttonPinLeft, INPUT); // Initialize the Touch button left pin as an input
  pinMode(buttonPinRight, INPUT); // Initialize the Touch button right pin as an input


  pinMode(photocellPin, INPUT); // Initialize the Light sensor pin as an input



}

void loop() {
  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_ncenB08_tr); // choose a suitable font
  u8g2.drawStr(0, 10, "Hello World!"); // write something to the internal memory
  u8g2.sendBuffer();          // transfer internal memory to the display


  digitalWrite(led_1_G, HIGH); // Turn the LED 1 GREEN on
  digitalWrite(led_2_R, HIGH); // Turn the LED 2 RED on


  lightValue = analogRead(photocellPin);  //Store the value from the Light sensor in the variable

  buttonState_A = digitalRead(buttonPinA);  //Store the state of button A in the variable
  buttonState_B = digitalRead(buttonPinB);  //Store the state of button B in the variable
  buttonState_C = digitalRead(buttonPinC);  //Store the state of button C in the variable
  buttonState_D = digitalRead(buttonPinD);  //Store the state of button D in the variable

  buttonState_Left = digitalRead(buttonPinLeft); //Store the state of left touch button in the variable
  buttonState_Right = digitalRead(buttonPinRight); //Store the state of right touch button in the variable

  delay(1000);
}

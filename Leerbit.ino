#include <Arduino.h>
#include <U8x8lib.h>
#include <U8g2lib.h>
#include <SD.h>
#include <SPI.h>
#include <Wire.h>
#include <RTClib.h>


//helloooooooooooosdfsdfsdf

/* Allocation I2C (RTC)
 * SD card attached to I2C bus on arduino mega as follows:
 ** SDA - 20
 ** SCL - 21
/*


/*Allocation Oled Display*/
U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2(U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ U8X8_PIN_NONE);   // All Boards without Reset of the Display

/* Allocation sd card reader
 * SD card attached to SPI bus on arduino mega as follows:
 ** MOSI - pin 51
 ** MISO - pin 50
 ** CLK - pin 52
 ** CS - pin 53 
/*


/*Allocation Leds*/
int led_1_G = 2;
int led_1_R = 3;
int led_2_G = 4;
int led_2_R = 5;
int led_3_G = 6;
int led_3_R = 7;
int led_4_G = 8;
int led_4_R = 9;
int led_5_G = 10;
int led_5_R = 11;
int led_6_G = 12;
int led_6_R = 13;
int led_7_G = 14;
int led_7_R = 15;
int led_8_G = 16;
int led_8_R = 17;
int led_9_G = 18;
int led_9_R = 19;
int led_10_G = 20;
int led_10_R = 21;
int led_11_G = 22;
int led_11_R = 23;
int led_12_G = 24;
int led_12_R = 25;
int led_13_G = 26;
int led_13_R = 27;
int led_14_G = 28;
int led_14_R = 29;
int led_15_G = 30;
int led_15_R = 31;


/*Allocation buttons*/
const int buttonPinA = 33;
const int buttonPinB = 34;
const int buttonPinC = 35;
const int buttonPinD = 36;

/*Allocation Touch buttons*/
const int buttonPinLeft = 37;
const int buttonPinRight = 38;

/*Allocation light Sensor*/
const int photocellPin = 39;


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
  delay(1000);

  digitalWrite(led_1_G, HIGH); // Turn the LED 1 GREEN on
  digitalWrite(led_2_R, HIGH); // Turn the LED 2 RED on


}

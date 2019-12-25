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

File myFile;




/* Allocation I2C (RTC)
** SDA - 20
** SCL - 21
** Reset - 22
*/

/*Allocation Oled Display*/
U8G2_SSD1306_128X64_NONAME_F_SW_I2C u8g2(U8G2_R0, /* clock=*/ SCL, /* data=*/ SDA, /* reset=*/ 22);   // All Boards without Reset of the Display


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
const int buttonPinA = 29;
const int buttonPinB = 11;
const int buttonPinC = 9;
const int buttonPinD = 7;

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
int menu_state;
int vak_state;
int resultaten_state;


// Startup screen
#define gear_width 30
#define gear_height 30
static const unsigned char gear_logo[] U8X8_PROGMEM = {
  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x07, 0x00,
  0x00, 0x1C, 0x0F, 0x00, 0x00, 0xBC, 0x0F, 0x00, 0x00, 0xFC, 0xCF, 0x00,
  0xE0, 0xFE, 0xFF, 0x01, 0xF0, 0x3F, 0xFF, 0x01, 0xE0, 0x07, 0xF8, 0x00,
  0xE0, 0x03, 0xF0, 0x00, 0xC0, 0x01, 0xE0, 0x01, 0xE0, 0x00, 0xC0, 0x0F,
  0xFC, 0x00, 0xC0, 0x0F, 0xFC, 0x00, 0xC0, 0x0F, 0xFC, 0x00, 0x80, 0x03,
  0xF8, 0x00, 0x80, 0x01, 0xE0, 0x00, 0xC0, 0x03, 0xE0, 0x00, 0xC0, 0x07,
  0xF0, 0x00, 0xC0, 0x0F, 0xF8, 0x01, 0xE0, 0x07, 0xF8, 0x03, 0xF0, 0x07,
  0xF0, 0x07, 0x78, 0x00, 0x00, 0x3F, 0x7F, 0x00, 0x00, 0xFF, 0xFF, 0x00,
  0x00, 0xFF, 0xFF, 0x00, 0x00, 0xC7, 0x63, 0x00, 0x00, 0xC2, 0x03, 0x00,
  0x00, 0xC0, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
};








void setup() {
  Serial.begin(9600); // Start serial communication
  u8g2.begin(7, U8X8_PIN_NONE, U8X8_PIN_NONE, 31, 3, 29); // Start oled display


  Serial.print("Initializing SD card...");
  if (!SD.begin(53)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");

  // open the file. note that only one file can be open at a time,
  // so you have to close this one before opening another.
  myFile = SD.open("user.txt");

  // if the file opened okay, write to it:
  if (myFile) {
    Serial.println("File opened!!");
    Serial.println("user.txt:");
    while (myFile.available()) {
      Serial.write(myFile.read());
    }


  } else {
    // if the file didn't open, print an error:
    Serial.println("error opening!!");
  }


  // close the file:
  myFile.close();










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



  pinMode(buttonPinA, INPUT_PULLUP); // Initialize the pushbutton A pin as an input and enable the internal pull-up resistor
  pinMode(buttonPinB, INPUT_PULLUP); // Initialize the pushbutton B pin as an input and enable the internal pull-up resistor
  pinMode(buttonPinC, INPUT_PULLUP); // Initialize the pushbutton C pin as an input and enable the internal pull-up resistor
  pinMode(buttonPinD, INPUT_PULLUP); // Initialize the pushbutton D pin as an input and enable the internal pull-up resistor

  pinMode(buttonPinLeft, INPUT_PULLUP); // Initialize the Touch button left pin as an input
  pinMode(buttonPinRight, INPUT_PULLUP); // Initialize the Touch button right pin as an input


  pinMode(photocellPin, INPUT); // Initialize the Light sensor pin as an input

  init_Screen(); //startup display

  delay(2000);

}

void loop() {


  lightValue = analogRead(photocellPin);  //Store the value from the Light sensor in the variable

  menu_state = menu_Screen();
  Serial.println(menu_state);



  if (menu_state == 1) {

    vak_state = vakken_screen();
    if (vak_state == 2) {
      while (1) {
        vraag_Screen();
      }

    }
  }

  else if (menu_state == 2) {

    profiel_screen();
  }



  else if (menu_state == 3) {
    resultaten_state = resultaten_screen();
    if (resultaten_state == 1) {
      while (1) {

        score_screen();
      }
    }
    else if (resultaten_state == 2) {
      while (1) {

        score_screen();
      }
    }
    if (resultaten_state == 3) {
      while (1) {

        score_screen();
      }
    }
    else if (resultaten_state == 4) {
      while (1) {

        score_screen();
      }
    }
    else if (resultaten_state == 5) {
      while (1) {

        score_screen();
      }
    }
    else if (resultaten_state == 6) {
      while (1) {

        score_screen();
      }
    }
    else if (resultaten_state == 7) {
      while (1) {

        score_screen();
      }
    }
  }
  // vraag_Screen();



  /*
    // Serial.println(lightValue);
    digitalWrite(led_1_R, HIGH); // Turn the LED 1 GREEN on
    //digitalWrite(led_1_G, HIGH); // Turn the LED 2 RED on
    //digitalWrite(led_2_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_2_G, HIGH); // Turn the LED 2 RED on
    digitalWrite(led_3_R, HIGH); // Turn the LED 1 GREEN on
    //digitalWrite(led_3_G, HIGH); // Turn the LED 2 RED on
    //digitalWrite(led_4_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_4_G, HIGH); // Turn the LED 2 RED on
    // digitalWrite(led_5_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_5_G, HIGH); // Turn the LED 2 RED on
    digitalWrite(led_6_R, HIGH); // Turn the LED 1 GREEN on
    //digitalWrite(led_6_G, HIGH); // Turn the LED 2 RED on
    digitalWrite(led_7_R, HIGH); // Turn the LED 1 GREEN on
    //digitalWrite(led_7_G, HIGH); // Turn the LED 2 RED on
    digitalWrite(led_8_R, HIGH); // Turn the LED 1 GREEN on
    // digitalWrite(led_8_G, HIGH); // Turn the LED 2 RED on
    // digitalWrite(led_9_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_9_G, HIGH); // Turn the LED 2 RED on
    // digitalWrite(led_10_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_10_G, HIGH); // Turn the LED 2 RED on
    // digitalWrite(led_11_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_11_G, HIGH); // Turn the LED 2 RED on
    digitalWrite(led_12_R, HIGH); // Turn the LED 1 GREEN on
    // digitalWrite(led_12_G, HIGH); // Turn the LED 2 RED on
    //  digitalWrite(led_13_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_13_G, HIGH); // Turn the LED 2 RED on
    //  digitalWrite(led_14_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_14_G, HIGH); // Turn the LED 2 RED on
    //digitalWrite(led_15_R, HIGH); // Turn the LED 1 GREEN on
    digitalWrite(led_15_G, HIGH); // Turn the LED 2 RED on
  */




  buttonState_A = digitalRead(buttonPinA);  //Store the state of button A in the variable
  buttonState_B = digitalRead(buttonPinB);  //Store the state of button B in the variable
  buttonState_C = digitalRead(buttonPinC);  //Store the state of button C in the variable
  buttonState_D = digitalRead(buttonPinD);  //Store the state of button D in the variable

  buttonState_Left = digitalRead(buttonPinLeft); //Store the state of left touch button in the variable
  buttonState_Right = digitalRead(buttonPinRight); //Store the state of right touch button in the variable


}







void init_Screen() {
  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_helvR14_tf); // choose a suitable font
  u8g2.drawStr(35, 18, "LeerBit"); // write something to the internal memory
  u8g2.setDrawColor(1);
  u8g2.setBitmapMode(1 /* solid */);
  u8g2.drawXBMP(50, 25, gear_width, gear_height, gear_logo);
  u8g2.sendBuffer();          // transfer internal memory to the display



}


int menu_Screen() {
  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_t0_12_me); // choose a suitable font
  return u8g2.userInterfaceSelectionList("Hoofdmenu", 1, "Vakken\nProfielen\nResultaten");
  u8g2.sendBuffer();          // transfer internal memory to the display





}

int vakken_screen() {

  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_t0_12_me); // choose a suitable font
  return  u8g2.userInterfaceSelectionList("Vakken", 1, "Nederlands\nRekenen\nBiologie\nEngels\nKunst\nOrientatie\nAlgemene kennis");
  u8g2.sendBuffer();          // transfer internal memory to the display




}
int profiel_screen() {

  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_t0_12_me); // choose a suitable font
  return  u8g2.userInterfaceSelectionList("Profielen", 1, "jan\nPiet\nMarieke\nKees\nLaura\nAnne\nKlaas");
  u8g2.sendBuffer();          // transfer internal memory to the display




}
int resultaten_screen() {

  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_t0_12_me); // choose a suitable font
  return  u8g2.userInterfaceSelectionList("Resultaten", 1, "Rekenen\nBiologie\nEngels\nKunst\nOrientatie\nAlgemene kennis");
  u8g2.sendBuffer();          // transfer internal memory to the display





}
void score_screen() {
  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_helvB12_tr); // choose a suitable font
  u8g2.setCursor(0, 12);
  u8g2.print( "vak."); // write something to the internal memory
  u8g2.setCursor(0, 30);
  u8g2.print("Score.");

  u8g2.sendBuffer();          // transfer internal memory to the display


}

void vraag_Screen() {
  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_profont10_tf); // choose a suitable font
  u8g2.drawStr(0, 8, "1."); // write something to the internal memory
  u8g2.drawStr(20, 8, "Wat is 20-5x7?"); // write something to the internal memory
  u8g2.drawStr(0, 45, "A.105"); // write something to the internal memory
  u8g2.drawStr(0, 64, "B.-15"); // write something to the internal memory
  u8g2.drawStr(75, 45, "C.100"); // write something to the internal memory
  u8g2.drawStr(75, 64, "D.85"); // write something to the internal memory
  u8g2.sendBuffer();          // transfer internal memory to the display



}

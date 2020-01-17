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





const size_t BUFFER_SZ = 40;

char woord[BUFFER_SZ];
char username[BUFFER_SZ];

char lines[15][200];

char vraag[60];
char antwoord1[60];
char antwoord2[60];
char antwoord3[60];
char antwoord4[60];
char juisteAntwoord[60];





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
int profiel_state;

int user_Id;


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


  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }


  Serial.print("Initializing SD card...");

  if (!SD.begin(53)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");

  linesFromFile(); // read from sd card







  u8g2.begin(7, U8X8_PIN_NONE, U8X8_PIN_NONE, 31, 3, 29); // Start oled display


  Serial.println("Initializing Leerbit...");



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

  delay(1500);

  Serial.println("initialization done.");

}

void loop() {


  lightValue = analogRead(photocellPin);  //Store the value from the Light sensor in the variable

  menu_state = menu_Screen();
  Serial.println(menu_state);




  if (menu_state == 1) {

    vak_state = vakken_screen();
    if (vak_state == 1) {
      for (int i = 1; i <= 15; i++) {
        for (int m = 0; m < 60; m++) {
          vraag[m] = (char)0;
          antwoord1[m] = (char)0;
          antwoord2[m] = (char)0;
          antwoord3[m] = (char)0;
          antwoord4[m] = (char)0;
          juisteAntwoord[m] = (char)0;
        }
        parseLine(i - 1);
        Serial.println(juisteAntwoord);
        vraag_Screen(i, vraag, antwoord1, antwoord2, antwoord3, antwoord4, juisteAntwoord[0]);

        // vraag_Screen(i, "Wat is het doel?", "niks", "wat", "hoe", "yup", 'b');
      }
    }

    else if (vak_state == 2) {
      for (int i = 1; i <= 15; i++) {
        vraag_Screen(i, "Wat is het woord?", "niks", "wat", "hoe", "yup", 'b');
      }
    }

    else if (vak_state == 3) {
      for (int i = 1; i <= 15; i++) {
        vraag_Screen(i, "Wat is het getal?", "een", "twee", "vijf", "zeven", 'b');
      }
    }

    else if (vak_state == 4) {
      for (int i = 1; i <= 15; i++) {
        vraag_Screen(i, "Wat is het?", "niks", "wat", "hoe", "yup", 'b');
      }
    }

    else if (vak_state == 5) {
      for (int i = 1; i <= 15; i++) {
        vraag_Screen(i, "Wat is het antwoord?", "niks", "wat", "hoe", "yup", 'b');
      }
    }



    delay(1000);
    clear_leds();
  }


  else if (menu_state == 2) {

    profiel_state = profiel_screen();

    if (profiel_state == 1) {
      user_Id = 1;
    }

    else if (profiel_state == 2) {
      user_Id = 2;
    }

    else if (profiel_state == 3) {
      user_Id = 3;
    }

    else if (profiel_state == 4) {
      user_Id = 4;
    }




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




}





void parseLine(int vrgNr) {
  int i = 0;
  int mode = 1;
  int cnt = 0;

  while (lines[vrgNr][i] != '\0') {
    //    Serial.print("c=");
    //    Serial.println(lines[vrgNr][i]);
    if (lines[vrgNr][i] != ',') {
      switch (mode) {
        case 1: // vraag
          vraag[cnt++] = lines[vrgNr][i];
          break;
        case 2: // antw 1
          antwoord1[cnt++] = lines[vrgNr][i];
          break;
        case 3: // antw 2
          antwoord2[cnt++] = lines[vrgNr][i];
          break;
        case 4: // antw 3
          antwoord3[cnt++] = lines[vrgNr][i];
          break;
        case 5: // antw 4
          antwoord4[cnt++] = lines[vrgNr][i];
          break;
        case 6: // antw 4
          juisteAntwoord[cnt++] = lines[vrgNr][i];
          break;
      }
    } else {
      mode++;
      cnt = 0;
    }
    i++;
    //    Serial.print("i=");
    //    Serial.println(i);
  }
  //  Serial.println(vraag);
  //  Serial.println(antwoord1);
  //  Serial.println(antwoord2);
  //  Serial.println(antwoord3);
  //  Serial.println(antwoord4);
  //  Serial.println(juisteAntwoord);

}

void linesFromFile() {
  int woordTeller = 0;
  int linenr = 0;

  File myFile;
  myFile = SD.open("test.txt");

  if (myFile) {
    int pos = 0;    // current write position in buffer
    int c;

    // While we can read a character:
    while ((c = myFile.read()) != -1) {
      if (c != '\n') {
        // zet in de array
        lines[linenr][pos] = c;
        //        Serial.print("linenr=");
        //        Serial.print(linenr);
        //        Serial.print(", pos=");
        //        Serial.print(pos);
        //        Serial.print(" : ");
        //        Serial.println(lines[linenr][pos]);
        pos++;
      } else {
        lines[linenr++][pos] = '\0';      // terminate the string
        pos = 0;
      }
    }
    // close the file:
    myFile.close();
  } else {
    // if the file didn't open, print an error:
    Serial.println("error opening test.txt");
  }

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

void vraag_Screen(int vraagNr, char vraag[], char a1[], char a2[], char a3[], char a4[] , char Antwoord) {



  u8g2.clearBuffer();          // clear the internal memory
  u8g2.setFont(u8g2_font_profont10_tf); // choose a suitable font
  u8g2.setCursor(0, 8);
  u8g2.print(vraagNr);
  u8g2.print(".");

  u8g2.setCursor(0, 16);
  u8g2.print(vraag); // write something to the internal memory

  u8g2.setCursor(0, 45);
  u8g2.print("A.");
  u8g2.print(a1); // write something to the internal memory
  u8g2.setCursor(0, 60);
  u8g2.print("B.");
  u8g2.print(a2); // write something to the internal memory
  u8g2.setCursor(75, 45);
  u8g2.print("C.");
  u8g2.print(a3); // write something to the internal memory
  u8g2.setCursor(75, 60);
  u8g2.print("D.");
  u8g2.print(a4); // write something to the internal memory
  u8g2.sendBuffer();          // transfer internal memory to the display

  check_vraag(Antwoord, vraagNr);

}

void check_vraag(char juisteAntwoord, int vraagNummer) {
  char ant = 'e';



  while (ant == 'e') {

    buttonState_A = digitalRead(buttonPinA);  //Store the state of button A in the variable
    buttonState_B = digitalRead(buttonPinB);  //Store the state of button B in the variable
    buttonState_C = digitalRead(buttonPinC);  //Store the state of button C in the variable
    buttonState_D = digitalRead(buttonPinD);  //Store the state of button D in the variable

    if (buttonState_A == 0) {
      ant = 'a';
    }

    else if (buttonState_B == 0) {
      ant = 'b';
    }

    else if (buttonState_C == 0) {
      ant = 'c';
    }

    else if (buttonState_D == 0) {
      ant = 'd';
    }
  }



  if  (ant == juisteAntwoord) {
    turn_Led(vraagNummer, 1);
  }

  else {
    turn_Led(vraagNummer, 0);
  }

}

void turn_Led(int ledNummer, int ledkleur) {

  if (ledNummer == 1 && ledkleur == 0) {
    digitalWrite(led_1_R, HIGH);
    digitalWrite(led_1_G, LOW);
  }

  else if (ledNummer == 1 && ledkleur == 1) {
    digitalWrite(led_1_G, HIGH);
    digitalWrite(led_1_R, LOW);
  }

  else if (ledNummer == 2 && ledkleur == 0) {
    digitalWrite(led_2_R, HIGH);
    digitalWrite(led_2_G, LOW);
  }

  else if (ledNummer == 2 && ledkleur == 1) {
    digitalWrite(led_2_G, HIGH);
    digitalWrite(led_2_R, LOW);
  }

  else if (ledNummer == 3 && ledkleur == 0) {
    digitalWrite(led_3_R, HIGH);
    digitalWrite(led_3_G, LOW);
  }

  else if (ledNummer == 3 && ledkleur == 1) {
    digitalWrite(led_3_G, HIGH);
    digitalWrite(led_3_R, LOW);
  }

  else if (ledNummer == 4 && ledkleur == 0) {
    digitalWrite(led_4_R, HIGH);
    digitalWrite(led_4_G, LOW);
  }

  else if (ledNummer == 4 && ledkleur == 1) {
    digitalWrite(led_4_G, HIGH);
    digitalWrite(led_4_R, LOW);
  }

  else if (ledNummer == 5 && ledkleur == 0) {
    digitalWrite(led_5_R, HIGH);
    digitalWrite(led_5_G, LOW);
  }

  else if (ledNummer == 5 && ledkleur == 1) {
    digitalWrite(led_5_G, HIGH);
    digitalWrite(led_5_R, LOW);
  }

  else if (ledNummer == 6 && ledkleur == 0) {
    digitalWrite(led_6_R, HIGH);
    digitalWrite(led_6_G, LOW);
  }

  else if (ledNummer == 6 && ledkleur == 1) {
    digitalWrite(led_6_G, HIGH);
    digitalWrite(led_6_R, LOW);
  }

  else if (ledNummer == 7 && ledkleur == 0) {
    digitalWrite(led_7_R, HIGH);
    digitalWrite(led_7_G, LOW);
  }

  else if (ledNummer == 7 && ledkleur == 1) {
    digitalWrite(led_7_G, HIGH);
    digitalWrite(led_7_R, LOW);
  }

  else if (ledNummer == 8 && ledkleur == 0) {
    digitalWrite(led_8_R, HIGH);
    digitalWrite(led_8_G, LOW);
  }

  else if (ledNummer == 8 && ledkleur == 1) {
    digitalWrite(led_8_G, HIGH);
    digitalWrite(led_8_R, LOW);
  }

  else if (ledNummer == 9 && ledkleur == 0) {
    digitalWrite(led_9_R, HIGH);
    digitalWrite(led_9_G, LOW);
  }

  else if (ledNummer == 9 && ledkleur == 1) {
    digitalWrite(led_9_G, HIGH);
    digitalWrite(led_9_R, LOW);
  }

  else if (ledNummer == 10 && ledkleur == 0) {
    digitalWrite(led_10_R, HIGH);
    digitalWrite(led_10_G, LOW);
  }

  else if (ledNummer == 10 && ledkleur == 1) {
    digitalWrite(led_10_G, HIGH);
    digitalWrite(led_10_R, LOW);

  }

  else if (ledNummer == 11 && ledkleur == 0) {
    digitalWrite(led_11_R, HIGH);
    digitalWrite(led_11_G, LOW);
  }

  else if (ledNummer == 11 && ledkleur == 1) {
    digitalWrite(led_11_G, HIGH);
    digitalWrite(led_11_R, LOW);
  }

  else if (ledNummer == 12 && ledkleur == 0) {
    digitalWrite(led_12_R, HIGH);
    digitalWrite(led_12_G, LOW);
  }

  else if (ledNummer == 12 && ledkleur == 1) {
    digitalWrite(led_12_G, HIGH);
    digitalWrite(led_12_R, LOW);
  }

  else if (ledNummer == 13 && ledkleur == 0) {
    digitalWrite(led_13_R, HIGH);
    digitalWrite(led_13_G, LOW);
  }

  else if (ledNummer == 13 && ledkleur == 1) {
    digitalWrite(led_13_G, HIGH);
    digitalWrite(led_13_R, LOW);
  }

  else if (ledNummer == 14 && ledkleur == 0) {
    digitalWrite(led_14_R, HIGH);
    digitalWrite(led_14_G, LOW);
  }

  else if (ledNummer == 14 && ledkleur == 1) {
    digitalWrite(led_14_G, HIGH);
    digitalWrite(led_14_R, LOW);
  }

  else if (ledNummer == 15 && ledkleur == 0) {
    digitalWrite(led_15_R, HIGH);
    digitalWrite(led_15_G, LOW);
  }

  else if (ledNummer == 15 && ledkleur == 1) {
    digitalWrite(led_15_G, HIGH);
    digitalWrite(led_15_R, LOW);
  }

}


void clear_leds() {

  digitalWrite(led_1_G, LOW);
  digitalWrite(led_1_R, LOW);
  digitalWrite(led_2_G, LOW);
  digitalWrite(led_2_R, LOW);
  digitalWrite(led_3_G, LOW);
  digitalWrite(led_3_R, LOW);
  digitalWrite(led_4_G, LOW);
  digitalWrite(led_4_R, LOW);
  digitalWrite(led_5_G, LOW);
  digitalWrite(led_5_R, LOW);
  digitalWrite(led_6_G, LOW);
  digitalWrite(led_6_R, LOW);
  digitalWrite(led_7_G, LOW);
  digitalWrite(led_7_R, LOW);
  digitalWrite(led_8_G, LOW);
  digitalWrite(led_8_R, LOW);
  digitalWrite(led_9_G, LOW);
  digitalWrite(led_9_R, LOW);
  digitalWrite(led_10_G, LOW);
  digitalWrite(led_10_R, LOW);
  digitalWrite(led_11_G, LOW);
  digitalWrite(led_11_R, LOW);
  digitalWrite(led_12_G, LOW);
  digitalWrite(led_12_R, LOW);
  digitalWrite(led_13_G, LOW);
  digitalWrite(led_13_R, LOW);
  digitalWrite(led_14_G, LOW);
  digitalWrite(led_14_R, LOW);
  digitalWrite(led_15_G, LOW);
  digitalWrite(led_15_R, LOW);



}






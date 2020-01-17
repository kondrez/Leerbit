/*
  SD card read/write

  This example shows how to read and write data to and from an SD card file
  The circuit:
   SD card attached to SPI bus as follows:
 ** MOSI - pin 11
 ** MISO - pin 12
 ** CLK - pin 13
 ** CS - pin 4 (for MKRZero SD: SDCARD_SS_PIN)

  created   Nov 2010
  by David A. Mellis
  modified 9 Apr 2012
  by Tom Igoe

  This example code is in the public domain.

*/

#include <SPI.h>
#include <SD.h>


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

void setup() {
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
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
  int vrgNr = 5;
  parseLine(vrgNr);
}

//  Serial.println(vraag);
void loop() {

  delay(1000);
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
  Serial.println(vraag);
  Serial.println(antwoord1);
  Serial.println(antwoord2);
  Serial.println(antwoord3);
  Serial.println(antwoord4);
  Serial.println(juisteAntwoord);

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

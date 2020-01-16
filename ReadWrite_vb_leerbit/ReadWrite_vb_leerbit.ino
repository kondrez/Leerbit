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


  const size_t BUFFER_SZ = 20;
  
  char buffer[BUFFER_SZ];
  char woord[BUFFER_SZ];

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

  
}

void loop() {
  // nothing happens after setup
  

  wordFromFile(2, buffer);

  for (int i = 0; i <= BUFFER_SZ; i++) {
    woord[i] = buffer[i];
  }

  Serial.println(woord);

   delay(1000);
}

void wordFromFile(int welkWoord, char *buffer) {
int woordTeller = 0;

File myFile;
myFile = SD.open("test.txt");

  if (myFile) {
    
    size_t pos = 0;    // current write position in buffer
    int c;
    
    // While we can read a character:
    while ((c = myFile.read()) != -1) {

      // If we get a delimiter, then we have a complete element.
      if (c == ',' || c == '\n') {
        buffer[pos] = '\0';         // terminate the string
      //Serial.println(buffer);     // write the word to serial monitor

      // als het het woord is wat we willen, return
      if (woordTeller == welkWoord) {
        return;
      }
      woordTeller++;
        pos = 0;                    // ready for next element
      }

      // Otherwise, store the character in the buffer.
      else if (pos < BUFFER_SZ - 1) {
        buffer[pos++] = c;
      }
    }
    
    // close the file:
    myFile.close();
  } else {
    // if the file didn't open, print an error:
    Serial.println("error opening test.txt");
  }

}

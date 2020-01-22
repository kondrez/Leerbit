int leerlingNummer = 1;
int vakNummer = 2;
int score = 13;
char scoreFile[5][6];

int aantalLeerlingen = 5;
int aantalTekensInRegel = 6;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);


  for (int i = 0; i < aantalLeerlingen; i++) {
    for (int t = 0; t < aantalTekensInRegel; t++) {
      // komma's plaatsen
      if (t = 1 || t = 3) {
        scoreFile[i][t] = ",";
      }

      // leerlingnummer op plek 1
      if (t = 0) {
        scoreFile[i][t] = leerlingNummer;
      }
      // vaknummer op plek 3
      if (t = 2) {
        scoreFile[i][t] = vakNummer;
      }
      // eerste score getal plaatsen
      if (t = 4) {
        if (score <= 10) {
          scoreFile[i][t] = 1;
        }
        else {
          scoreFile[i][t] = 1;
        }
      }
      //tweede score getal plaatsen
      if (t = 5) {
        scoreFile[i][t] = score % 10;
      }
    }
  }

  Serial.print(scoreFile);
}

void loop() {
  // put your main code here, to run repeatedly:

}

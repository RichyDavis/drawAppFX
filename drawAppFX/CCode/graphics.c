#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include "graphics.h"

void stepOn() {
  printf("ST\n");
}

void stepOff() {
  printf("SF\n");
}

void setBackgroundSize(int x, int y) {
  printf("BS %i %i\n", x, y);
}

void setBackgroundColour(colour c) {
  printf("BC @%s\n",translateColour(c));
}

void clear(colour c) {
  printf("CA @%s\n",translateColour(c));
}

void drawLine(int x1, int x2, int x3, int x4) {
  printf("DL %i %i %i %i\n", x1, x2, x3, x4);
}

void drawRect(int x1, int x2, int x3, int x4) {
  printf("DR %i %i %i %i\n", x1, x2, x3, x4);
}

void drawOval(int x, int y, int width, int height) {
  printf("DO %i %i %i %i\n", x, y, width, height);
}

void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
  printf("DA %i %i %i %i %i %i\n", x, y, width, height, startAngle, arcAngle);
}

void fillRect(int x1, int x2, int x3, int x4) {
  printf("FR %i %i %i %i\n", x1, x2, x3, x4);
}

void fillOval(int x, int y, int width, int height) {
  printf("FO %i %i %i %i\n", x, y, width, height);
}

void drawString(char* s, int x, int y) {
  printf("DS %i %i @%s\n", x, y, s);
}

void drawImage(char* s, int x1, int x2, int x3, int x4) {
  printf("DI %i %i %i %i @%s\n", x1, x2, x3, x4, s);
}

void postString(char* s) {
if (s[strlen(s)-1] == '\n')
  printf("PS @%s",s);
else
  printf("PS @%s\n",s);
}

void postClear() {
  printf("PC\n");
}

void setColour(colour c) {
  printf("SC @%s\n",translateColour(c));
}

void addStroke(colour c) {
  printf("AS @%s\n",translateColour(c));
}

void setGradient(colour c1, colour c2, int x, int y) {
  printf("SG %i %i @%s,%s\n",x,y,translateColour(c1),translateColour(c2));
}

void setRadialGradient(colour c1, colour c2) {
  printf("SR @%s,%s\n",translateColour(c1),translateColour(c2));
}

char* translateColour(colour c) {
  char* colourName;
  switch(c) {
    case black : colourName = "black"; break;
    case blue : colourName = "blue"; break;
    case cyan : colourName = "cyan"; break;
    case darkgray : colourName = "darkgray"; break;
    case gray : colourName = "gray"; break;
    case green : colourName = "green"; break;
    case lightgray : colourName = "lightgray"; break;
    case magenta : colourName = "magenta"; break;
    case orange : colourName = "orange"; break;
    case pink : colourName = "pink"; break;
    case red : colourName = "red"; break;
    case white : colourName = "white"; break;
    case yellow : colourName = "yellow"; break;
    default : colourName = ""; break;
  }
  return colourName;
}

void turtleModeOn(int x, int y) {
  printf("TT %i %i\n",x , y);
}

void turtleModeOff() {
  printf("TF\n");
}

void forward(int distance) {
  printf("TM %i\n",distance);
}

void turn(int degrees) {
  printf("TR %i\n",degrees);
}

void setPosition(int x, int y) {
  printf("TP %i %i\n", x, y);
}

void setAngle(int degrees) {
  printf("TA %i\n",degrees);
}
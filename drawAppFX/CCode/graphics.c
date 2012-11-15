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

void setBackgroundSize(double x, double y) {
  printf("BS %f %f\n", x, y);
}

void setBackgroundColour(colour c) {
  printf("BC @%s\n",translateColour(c));
}

void clear(colour c) {
  printf("CA @%s\n",translateColour(c));
}

void drawLine(double x1, double x2, double x3, double x4) {
  printf("DL %f %f %f %f\n", x1, x2, x3, x4);
}

void drawRect(double x1, double x2, double x3, double x4) {
  printf("DR %f %f %f %f\n", x1, x2, x3, x4);
}

void drawOval(double x, double y, double width, double height) {
  printf("DO %f %f %f %f\n", x, y, width, height);
}

void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle) {
  printf("DA %f %f %f %f %f %f\n", x, y, width, height, startAngle, arcAngle);
}

void drawQuadCurve(double x1, double y1, double x2, double y2, double xc, double yc) {
  printf("DQ %f %f %f %f %f %f\n", x1, y1, x2, y2, xc, yc);
}

void fillRect(double x1, double x2, double x3, double x4) {
  printf("FR %f %f %f %f\n", x1, x2, x3, x4);
}

void fillOval(double x, double y, double width, double height) {
  printf("FO %f %f %f %f\n", x, y, width, height);
}

void drawString(char* s, double x, double y) {
  printf("DS %f %f @%s\n", x, y, s);
}

void drawImage(char* s, double x1, double x2, double x3, double x4) {
  printf("DI %f %f %f %f @%s\n", x1, x2, x3, x4, s);
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

void setLineWidth(double width) {
  printf("SW %f\n",width);
}

void setGradient(colour c1, colour c2, double x, double y) {
  printf("SG %f %f @%s,%s\n",x,y,translateColour(c1),translateColour(c2));
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

void turtleModeOn(double x, double y) {
  printf("TT %f %f\n", x, y);
}

void turtleModeOff() {
  printf("TF\n");
}

void forward(double distance) {
  printf("TM %f\n",distance);
}

void turn(double degrees) {
  printf("TR %f\n",degrees);
}

void setPosition(double x, double y) {
  printf("TP %f %f\n", x, y);
}

void setAngle(double degrees) {
  printf("TA %f\n",degrees);
}
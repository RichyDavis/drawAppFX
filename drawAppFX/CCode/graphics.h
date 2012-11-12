enum colour {black,blue,cyan,darkgray,gray,green,lightgray,magenta,orange,pink,red,white,yellow};
typedef enum colour colour;

void stepOn();
void stepOff();
void drawLine(int,int,int,int);
void drawRect(int,int,int,int);
void drawOval(int,int,int,int);
void drawArc(int,int,int,int,int,int);
void fillRect(int,int,int,int);
void drawString(char*,int,int);
void postString(char* s);
void postClear();
void drawImage(char* s, int x1, int x2, int x3, int x4);
char* translateColour(colour c);
void setColour(colour);
void setStroke(colour);
void setGradient(colour c1, colour c2, int x, int y);
void setRadialGradient(colour c1, colour c2);
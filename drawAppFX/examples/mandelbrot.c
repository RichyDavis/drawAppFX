#include "graphics.h"

int main(void)
{
  const int width = 500;
  const int height = 300;
  const int iterations = 128;
  const int limit = 6;
  const double yOffset = 1.2;
  const double xOffset = 2.5;
  const double scale = 0.008;
  const int numberOfColours = 9;
  const colour colours[] = {lightgray, red, orange, yellow, cyan, green, blue, darkgray, black};
  
  int column;
  int row;
  for (column = 0 ; column < width ; column++)
  {
    for (row = 0 ; row < height ; row++) 
    {
      double x = 0.0;
      double y = 0.0;
      int count;
      for (count = 0 ; count < iterations ; count++)
      {
        if ((x * x + y * y) > limit)
        {
          setColour(colours[count % numberOfColours]);
          drawLine(column, row, column, row);
          break;
        }
        double newy = 2 * x * y + row * scale - yOffset;
        x = x * x - y * y + column * scale - xOffset;
        y = newy;
      }
    }
  }
}
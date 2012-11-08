#include "graphics.h"  

int main(void)
{  
  int n;
  for (n = 0 ; n < 300 ; n += 5)
  {  
    drawOval(50, 50, n, n + 40); 
  }
  return 0;
}
#include <GL/glut.h>
#include <iostream>

using namespace std;

//Pont típus létrehozása
struct Pont {
  GLfloat x, y;
  Pont(GLfloat x = 0, GLfloat y = 0): x(x), y(y) {}
};

//Kirajzolás meghívása
void display(void) {
	 glClear(GL_COLOR_BUFFER_BIT);
	
	float x=1;
	float y=1;
	int r=0;
	
	glBegin(GL_POINTS);
	
	for (int k = 0; k < 100; k++) {	
	r = random() % 8;	
    switch(r) {
        case 0:
            x=x/3+1/3;
            y=y/3+1/3;
            break;
        case 1:
            x=x/3+1/3;
            y=y/3;
            break;
        case 2:
            x=x/3;
            y=y/3+1/3;
            break;
        case 3:
            x=x/3+2/3;
            y=y/3;
            break;
        case 4:
            x=x/3;
            y=y/3+2/3;
            break;     
        case 5:
            x=x/3+2/3;
            y=y/3+2/3;
            break;
        case 6:
            x=x/3+1/3;
            y=y/3+2/3;        
        case 7:
            x=x/3+2/3;
            y=y/3+1/3; 
            break; 
        case 8:
            x=x/3+1/3;
            y=y/3+1/3;
            break;              
       }
       glVertex2f(x,y);
	}	 
    glEnd(); 	 
	glFlush();	 
}

void init() {
  glClearColor(0.0, 0.0, 0.0, 0.0); //Háttérszín beállítása
  glColor3f(0.6, 1.0, 0.0);			//Az ecset színének a beállítása

  //Az ablak alapbeállításai, a kamera nézőpontjainak a beállításai
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  glOrtho(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
}


int main(int argc, char **argv)
{
	glutInit(&argc, argv);//initializalja az open gl-t
	glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(40, 40);
	glutCreateWindow("Sierpinski negyszog");//letrehozza az ablakot
	glutDisplayFunc(display);//ha az ablakot ujra kell rajzolni meghivja a display fugvenyt
	init();
	glutMainLoop();//elinditja a programszalat
	return 0;
} 

#include <GL/glut.h>
#include <iostream>

using namespace std;

//Pont típus létrehozása
struct Pont {
  GLfloat x, y;
  Pont(GLfloat x = 0, GLfloat y = 0): x(x), y(y) {}
  Pont pontKoord(Pont p) {return Pont((2.0*x+p.x)/3.0,(2.0*y+p.y)/3.0);};
  Pont pontKoord2(Pont p) {return Pont((x+2.0*p.x)/3.0,(y+2.0*p.y)/3.0);};
};

void drawRect(Pont p1, Pont p2) {
	glBegin(GL_POINTS);	
		glVertex2f(p1.x,p1.y);
		glVertex2f(p2.x,p1.y);
		glVertex2f(p2.x,p2.y);
		glVertex2f(p1.x,p2.y);	
	 glEnd();		
}

void drawSquare(int n,Pont p1,Pont p2) {
	drawRect(p1.pontKoord(p2),p1.pontKoord2(p2));
	
	
	if (n>0)
	{
		//Bal átlósan lefele
        drawSquare(n - 1, Pont(p1.x, p1.y),Pont((2 * p1.x + p2.x) / 3.0, (2 * p1.y + p2.y) / 3.0));
        //Lefele rajzolja ki
        drawSquare(n - 1, Pont((2 * p1.x + p2.x) / 3.0, p1.y),Pont((p1.x + 2 * p2.x) / 3.0,(2 * p1.y + p2.y) / 3.0));
        //Jobb átlósan lefele
        drawSquare(n - 1, Pont((p1.x + 2 * p2.x) / 3.0, p1.y),Pont(p2.x,(2 * p1.y + p2.y) / 3.0));
        //A négyzettől balra rajzol
        drawSquare(n - 1, Pont(p1.x,(2 * p1.y + p2.y) / 3.0),Pont((2 * p1.x + p2.x) / 3.0,(p1.y + 2 * p2.y) / 3.0));
        //A négyzettől jobbra levőt rajzolja ki
        drawSquare(n - 1, Pont((p1.x + 2 * p2.x) / 3.0, (2 * p1.y + p2.y) / 3.0),Pont(p2.x, (p1.y + 2 * p2.y) / 3.0));
        //A bal átlósan a négyzet fölött rajzol
        drawSquare(n - 1, Pont(p1.x , (p1.y + 2 * p2.y) / 3.0),Pont((2 * p1.x + p2.x) / 3.0,  p2.y));
        //A négyzet fölött rajzol
        drawSquare(n - 1, Pont((2 * p1.x + p2.x) / 3.0, (p1.y + 2 * p2.y) / 3.0),Pont((p1.x + 2 * p2.x) / 3.0,p2.y));
        //Jobb átlósan a négyzet fölött
        drawSquare(n - 1, Pont((p1.x + 2 * p2.x) / 3.0, (p1.y + 2 * p2.y) / 3.0),Pont(p2.x,p2.y));
	}
}

//Kirajzolás meghívása
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	 static Pont p = Pont(0,0);		 
	 static Pont p1 = Pont(500,500);
	 
	 drawSquare(4,p,p1);
	 
	 glFlush();	 
}

void init() {
  glClearColor(0.0, 0.0, 0.0, 0.0); //Háttérszín beállítása
  glColor3f(0.6, 1.0, 0.0);			//Az ecset színének a beállítása

  //Az ablak alapbeállításai, a kamera nézőpontjainak a beállításai
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  glOrtho(0.0, 500.0, 0.0, 500.0, 0.0, 1.0);
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

#include <GL/glut.h>

//Pont típus létrehozása
struct Pont {
  GLfloat x, y;
  Pont(GLfloat x = 0, GLfloat y = 0): x(x), y(y) {}
  Pont midPont(Pont p) {return Pont((x + p.x) / 2.0, (y + p.y) / 2.0);}
};

//Kirajzolás meghívása
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	//A kezdeti háromszög csúcspontjainak a beállítása (a nagy háromszög)
	static Pont csucs[] = {Pont(0, 0), Pont(250, 500), Pont(500, 0)};
	static Pont p = csucs[0];
	
	glBegin(GL_POINTS);
	for (int k = 0; k < 100000; k++) {
		p = p.midPont(csucs[rand() % 3]);
		glVertex2f(p.x, p.y);
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
  glOrtho(0.0, 500.0, 0.0, 500.0, 0.0, 1.0);
}


int main(int argc, char **argv)
{
	glutInit(&argc, argv);//initializalja az open gl-t
	glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(40, 40);
	glutCreateWindow("Sierpinski haromszog");//letrehozza az ablakot
	glutDisplayFunc(display);//ha az ablakot ujra kell rajzolni meghivja a display fugvenyt
	init();
	glutMainLoop();//elinditja a programszalat
	return 0;
} 

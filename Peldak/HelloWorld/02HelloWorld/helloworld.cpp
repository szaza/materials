#include <GL/glut.h>

void display(void) 
{
	glClear(GL_COLOR_BUFFER_BIT);//torli a parametereket
	//GL_COLOR_BUFFER_BIT -a kepernyo buffere
	//GL_DEPHT_BUFFER_BIT -a melyseg buffer
	glBegin(GL_QUADS); //begin end teg, meg kell adni a rajzolni kivant primitivek tipusat pl GL_TRIANGLES
		glColor3f(0.0, 0.0, 1.0);//aktualis szinbeallitas
		glVertex2i(-1,-1);//csucspontok koordinataja
		glColor3f(0.0, 1.0, 0.0);
		glVertex2i(1,-1);
		glColor3f(1.0, 0.0, 0.0);
		glVertex2i(1,1);
		glColor3f(.0, 0.0, 1.0);
		glVertex2i(-1,1);		
	glEnd();
	glFlush();//kikenyszeriti a rajzolast
}

int main(int argc, char **argv)
{
	glutInit(&argc, argv);//initializalja az open gl-t
	glutCreateWindow("Hello Vilag!");//letrehozza az ablakot
	glutDisplayFunc(display);//ha az ablakot ujra kell rajzolni meghivja a display fugvenyt
	glutMainLoop();//elinditja a programszalat
	return 0;
}

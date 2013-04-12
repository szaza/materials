#include <GL/glut.h>

void display(void) {}

int main(int argc, char **argv)
{
	glutInit(&argc, argv);//initializalja az open gl-t
	glutCreateWindow("Hello Vilag!");//letrehozza az ablakot
	glutDisplayFunc(display);//ha az ablakot ujra kell rajzolni meghivja a display fugvenyt
	glutMainLoop();//elinditja a programszalat
	return 0;
}

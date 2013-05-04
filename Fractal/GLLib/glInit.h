/*
 * glInit.h
 *
 *  Created on: May 3, 2013
 *      Author: Szabó Zoltán
 */

#ifndef GLINIT_H_
#define GLINIT_H_

#include <math.h>	          //Matematikai muveletek
#include <GL/gl.h>           //OpenGl
#include <gtk/gtk.h>         //Gtk kezelofelulet
#include <gtkgl/gtkglarea.h> //Gtkglarea

//A rajzterulet beallitasai
int init(GtkWidget *widget) {
	if (gtk_gl_area_make_current(GTK_GL_AREA(widget) )) {
		glViewport(0, 0, widget->allocation.width, widget->allocation.height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 100, 100, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	return TRUE;
}

//Rajzolas a rajzpanelre
int draw(GtkWidget *widget, GdkEventExpose *event) {
	if (event->count > 0)
		return TRUE;

	if (gtk_gl_area_make_current(GTK_GL_AREA(widget) )) {
		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		glColor3f(1, 1, 1);
		glBegin(GL_TRIANGLES);
		glVertex2f(10, 10);
		glVertex2f(10, 90);
		glVertex2f(90, 90);
		glEnd();
		gtk_gl_area_swap_buffers(GTK_GL_AREA(widget) );
	}

	return TRUE;
}

//Ha az ablakot ujrameretezzuk, akkor ez a fuggveny meghivodik
int reshape(GtkWidget *widget, GdkEventConfigure *event) {
	if (gtk_gl_area_make_current(GTK_GL_AREA(widget) ))
		glViewport(0, 0, widget->allocation.width, widget->allocation.height);

	return TRUE;
}


#endif /* GLINIT_H_ */


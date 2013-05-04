/*
 * gtkInit.h
 *
 *  Created on: May 3, 2013
 *      Author: Szabó Zoltán
 */
#include "../GLLib/glInit.h"
#include "addTriangleDialog.h"

#ifndef GTKINIT_H_
#define GTKINIT_H_

int gtkInit(int argc,char** argv) {
	GtkWidget *window, *glArea,*hBox,*vBox,*table1,*label1;
	GtkWidget *axEdit,*bxEdit,*cxEdit,*axLabel,*bxLabel,*cxLabel;
	GtkWidget *ayEdit,*byEdit,*cyEdit,*ayLabel,*byLabel,*cyLabel;
	GtkWidget *menuBar,*fileMenu,*comboBox1,*addButton;

		int attrlist[] = { GDK_GL_RGBA, GDK_GL_RED_SIZE, 1, GDK_GL_GREEN_SIZE, 1,
				GDK_GL_BLUE_SIZE, 1, GDK_GL_DOUBLEBUFFER, GDK_GL_NONE };

		//Meghivjuk az init fuggvenyt
		gtk_init(&argc, &argv);
		if (gdk_gl_query() == FALSE)
			return 0;

		//Letrehozunk egy uj ablakot
		window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
		//Megadjuk az ablak cimet
		gtk_window_set_title(GTK_WINDOW(window), "Fractal");
		//Beallitjuk a szelek vastagsagat
		gtk_container_set_border_width(GTK_CONTAINER(window), 0);
		//Kilepeskor meghivja a delete_event-t
		g_signal_connect(window, "delete_event", G_CALLBACK (gtk_main_quit), NULL);
		gtk_quit_add_destroy(1, GTK_OBJECT(window) );

		//Letrehozom az OpenGl rajzfeluletet
		glArea = GTK_WIDGET(gtk_gl_area_new (attrlist));
		gtk_widget_set_size_request(GTK_WIDGET(glArea), 800, 600);

		//Az ablakra vontakozo esemenyek eseten a `glArea`-t is ujrarajzoljuk
		gtk_widget_set_events(GTK_WIDGET(glArea),
				GDK_EXPOSURE_MASK | GDK_BUTTON_PRESS_MASK);

		g_signal_connect(glArea, "expose_event", G_CALLBACK(draw), NULL);
		g_signal_connect(glArea, "configure_event", G_CALLBACK(reshape), NULL);
		g_signal_connect(glArea, "realize", G_CALLBACK(init), NULL);

		//Horizonal box
		vBox = gtk_vbox_new(false,0);
		hBox = gtk_hbox_new(false,0);

		//Menusor letrehozasa
		menuBar = gtk_menu_bar_new();
		fileMenu = gtk_menu_item_new_with_label("File");
		gtk_menu_bar_append(GTK_MENU_BAR(menuBar),fileMenu);

		//Control Panel
		comboBox1 = gtk_combo_new();
		GList *glist = g_list_append(NULL,gpointer("1"));
		glist = g_list_append(glist,gpointer("2"));
		glist = g_list_append(glist,gpointer("3"));

		gtk_combo_set_popdown_strings(GTK_COMBO(comboBox1),glist);
		gtk_combo_set_use_arrows(GTK_COMBO(comboBox1),true);

		label1 = gtk_label_new("A háromszög adatai:");
		axLabel = gtk_label_new("Ax pozició:");
		ayLabel = gtk_label_new("Ay pozició:");
		bxLabel = gtk_label_new("Bx pozició:");
		byLabel = gtk_label_new("By pozició:");
		cxLabel = gtk_label_new("Cx pozició:");
		cyLabel = gtk_label_new("Cy pozició:");

		axEdit = gtk_entry_new();
		gtk_entry_set_text(GTK_ENTRY(axEdit),"0");
		ayEdit = gtk_entry_new();
		gtk_entry_set_text(GTK_ENTRY(ayEdit),"0");
		bxEdit = gtk_entry_new();
		gtk_entry_set_text(GTK_ENTRY(bxEdit),"0");
		byEdit = gtk_entry_new();
		gtk_entry_set_text(GTK_ENTRY(byEdit),"0");
		cxEdit = gtk_entry_new();
		gtk_entry_set_text(GTK_ENTRY(cxEdit),"0");
		cyEdit = gtk_entry_new();
		gtk_entry_set_text(GTK_ENTRY(cyEdit),"0");

		addButton = gtk_button_new_with_label("Új háromszög");
		g_signal_connect(G_OBJECT(addButton),"clicked",G_CALLBACK(gtkAddTriangle),NULL);

		//Table
		table1 = gtk_table_new(9,2,false);
		gtk_table_attach(GTK_TABLE(table1),label1,0,2,0,1,GTK_SHRINK,GTK_SHRINK,0,20);
		gtk_table_attach(GTK_TABLE(table1),comboBox1,0,2,1,2,GTK_SHRINK,GTK_SHRINK,0,20);

		gtk_table_attach(GTK_TABLE(table1),axLabel,0,1,2,3,GTK_SHRINK,GTK_SHRINK,5,5);
		gtk_table_attach(GTK_TABLE(table1),axEdit,1,2,2,3,GTK_SHRINK,GTK_SHRINK,5,5);

		gtk_table_attach(GTK_TABLE(table1),ayLabel,0,1,3,4,GTK_SHRINK,GTK_SHRINK,5,5);
		gtk_table_attach(GTK_TABLE(table1),ayEdit,1,2,3,4,GTK_SHRINK,GTK_SHRINK,5,5);


		gtk_table_attach(GTK_TABLE(table1),bxLabel,0,1,4,5,GTK_SHRINK,GTK_SHRINK,5,5);
		gtk_table_attach(GTK_TABLE(table1),bxEdit,1,2,4,5,GTK_SHRINK,GTK_SHRINK,5,5);

		gtk_table_attach(GTK_TABLE(table1),byLabel,0,1,5,6,GTK_SHRINK,GTK_SHRINK,5,5);
		gtk_table_attach(GTK_TABLE(table1),byEdit,1,2,5,6,GTK_SHRINK,GTK_SHRINK,5,5);

		gtk_table_attach(GTK_TABLE(table1),cxLabel,0,1,6,7,GTK_SHRINK,GTK_SHRINK,5,5);
		gtk_table_attach(GTK_TABLE(table1),cxEdit,1,2,6,7,GTK_SHRINK,GTK_SHRINK,5,5);

		gtk_table_attach(GTK_TABLE(table1),cyLabel,0,1,7,8,GTK_SHRINK,GTK_SHRINK,5,5);
		gtk_table_attach(GTK_TABLE(table1),cyEdit,1,2,7,8,GTK_SHRINK,GTK_SHRINK,5,5);

		gtk_table_attach(GTK_TABLE(table1),addButton,0,2,8,9,GTK_SHRINK,GTK_SHRINK,10,10);

		gtk_box_pack_start(GTK_BOX(vBox),menuBar,false,false,false);
		gtk_box_pack_start(GTK_BOX(hBox),glArea,true,true,true);
		gtk_box_pack_start(GTK_BOX(hBox),table1,true,false,false);
		gtk_box_pack_start(GTK_BOX(vBox),hBox,true,true,true);

		//Az ablakhoz hozzaadok egy tarolot, majd ehhez a tarolohoz egy rajzfeluletet
		gtk_container_add(GTK_CONTAINER(window), GTK_WIDGET(vBox));
		gtk_widget_show_all(GTK_WIDGET(window));

		gtk_main();
		return 0;
}


#endif /* GTKINIT_H_ */


/*
 * addTriangleDialog.h
 *
 *  Created on: May 4, 2013
 *      Author: Szabó Zoltán
 */

#include <gtk/gtk.h>         //Gtk kezelofelulet
#include <gtkgl/gtkglarea.h> //Gtkglarea

#ifndef ADDTRIANGLEDIALOG_H_
#define ADDTRIANGLEDIALOG_H_

void gtkAddTriangle(GtkWidget *widget, gpointer data) {
	GtkWidget *addDialog,*label1,*table1,*hBox,*okButton,*cencelButton;
	GtkWidget *axEdit,*bxEdit,*cxEdit,*axLabel,*bxLabel,*cxLabel;
	GtkWidget *ayEdit,*byEdit,*cyEdit,*ayLabel,*byLabel,*cyLabel;

	label1 = gtk_label_new("Adja meg a háromszög adatait!");
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

	okButton = gtk_button_new_with_label("Ok");
	cencelButton = gtk_button_new_with_label("Mégse");

	gtk_widget_set_size_request(GTK_WIDGET(okButton),100,26);
	gtk_widget_set_size_request(GTK_WIDGET(cencelButton),100,26);

	table1 = gtk_table_new(8,2,false);
	hBox = gtk_hbox_new(true,0);
	gtk_table_attach(GTK_TABLE(table1),label1,0,2,0,1,GTK_SHRINK,GTK_SHRINK,0,20);

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

	gtk_box_pack_start(GTK_BOX(hBox),okButton,false,false,false);
	gtk_box_pack_start(GTK_BOX(hBox),cencelButton,false,false,false);

	addDialog = gtk_dialog_new();
	gtk_window_set_title(GTK_WINDOW(addDialog),"Új háromszög");

	gtk_box_pack_start(GTK_BOX(GTK_DIALOG(addDialog)->vbox),GTK_WIDGET(table1),true,true,0);
	gtk_box_pack_start(GTK_BOX(GTK_DIALOG(addDialog)->vbox),GTK_WIDGET(hBox),true,true,0);
	gtk_widget_show_all(GTK_WIDGET(addDialog));

	g_signal_connect_swapped(G_OBJECT(cencelButton),"clicked",G_CALLBACK(gtk_widget_destroy),G_OBJECT(addDialog));
}


#endif /* ADDTRIANGLEDIALOG_H_ */

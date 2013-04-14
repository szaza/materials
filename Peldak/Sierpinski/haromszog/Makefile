#Makefile 
CC = g++
APPS = sierpinski
OBJ = $(APPS).o
SRC = $(APPS).cpp

CFLAGS = $(C_OPTS) -I/usr/include
#Az lxi opció gondot okozhat egyes disztribucióknál
LIBS = -L/usr/X11R6/lib -lX11 -lglut -lGL -lGLU -lm -lpthread 
#Nállam Linux Mint alatt az lxi kapcsoló hibát okozott ezért kivettem

application:$(APPS)

clean:
	rm -f $(APPS) *.raw *.o core a.out

realclean:	clean
	rm -f *~ *.bak *.BAK

.SUFFIXES: cpp o
.cpp.o:
	$(CC) -c $(CFLAGS) $<

$(APPS): $(OBJ) 
	$(CC) -o $(APPS) $(CFLAGS) $(OBJ) $(LIBS)

depend:
	makedepend -- $(CFLAGS) $(SRC)


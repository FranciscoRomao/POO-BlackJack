CC=javac	
OBJS=blackjack/*.java
MAIN=main/Main.java
RUN=java
INT=-i 5 75 1000 4 50
DBUG=-d 5 75 1000 shoe-file.txt cmd-file.txt
#ARGS=$(INT)
ARGS=$(DBUG)

run:
	$(CC) $(OBJS) $(MAIN)
	$(RUN) main.Main $(ARGS)

clean:
	rm blackjack/*.class main/*.class

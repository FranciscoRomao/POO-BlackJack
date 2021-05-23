CC=javac
OBJS=blackjack/*.java
MAIN=main/Main.java
RUN=java
INT=-i 5 75 500 4 60
DBUG=-d 5 50 500 shoe-file-prof.txt cmd-file-prof.txt
SIM=-s 5 50 500 8 50 50000 HL
CLEAN=rm blackjack/*.class main/*.class
runi:
	$(CC) $(OBJS) $(MAIN)
	$(RUN) main.Main $(INT)
	$(CLEAN)
rund:
	$(CC) $(OBJS) $(MAIN)
	$(RUN) main.Main $(DBUG)
	$(CLEAN)
runs:
	$(CC) $(OBJS) $(MAIN)
	$(RUN) main.Main $(SIM) > sim.sim
	$(CLEAN)

clean:
	rm blackjack/*.class main/*.class

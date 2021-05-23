CC=javac
OBJS=blackjack/*.java
MAIN=main/Main.java
RUN=java
INT=-i 5 75 500 4 60
DBUG=-d 5 50 1000 testShoe.txt testCmd.txt
SIM=-s 5 75 500 4 50 2 BS-AS
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
	$(RUN) main.Main $(SIM) > sim.txt
	$(CLEAN)

clean:
	rm blackjack/*.class main/*.class

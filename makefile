CC=javac	
OBJS=blackjack/*.java
MAIN=main/Main.java
RUN=java
INT=-i 5 75 500 4 50
DBUG=-d 5 50 1000 testShoe.txt testCmd.txt
CLEAN=rm blackjack/*.class main/*.class
runi:
	$(CC) $(OBJS) $(MAIN)
	$(RUN) main.Main $(INT)
	$(CLEAN)
rund:
	$(CC) $(OBJS) $(MAIN)
	$(RUN) main.Main $(DBUG)
	$(CLEAN)

clean:
	rm blackjack/*.class main/*.class

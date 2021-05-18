# POO - blackjack

### TODO

- [ ] Implementar função Advice no HiLo
- [ ] Incluir HiLo no código
- [x] Implementar função Advice no Basic
- [ ] Incluir Basic no código
- [ ] Implementar função Advice no Ace5
- [ ] Incluir Ace5 no código
- [ ] Implementar função Advice no Std_bet
- [ ] Incluir Std_bet no código
- [x] Implementar função Play do Player
- [x] Implementar função Debug do Player
- [ ] Implementar função Sim do Player
- [x] Implementar função hit do Player
- [x] Implementar função Bet do Player
- [x] Implementar função Stand do PDlayer
- [x] Implementar função Insurance do Player
- [x] Implementar função Surrender do Player
- [ ] Implementar função Split do Player
- [ ] Implementar função DoubleDown do Player
- [ ] Implementar função Advice do Player - G
- [ ] Implementar função Statistics do Player
- [x] Implementar constructor do Shoe
- [x] Implementar função Shuffle do Shoe
- [x] Implementar função getCard do Shoe
- [x] Leitura do file para o shoe
- [x] Implementar constructor do Dealer
- [x] Implementar constructor do Hand
- [x] Implementar função hit do Dealer
- [x] Implementar função dealCards do Dealer
- [x] Implementar função checkBJ do Dealer
- [x] Ler input e inicializar o jogo
- [ ] Implementar o main

## Discutir

- Porque é que hand tem as cartas private?

## Notas

- Link para compilar UML: https://www.planttext.com/ ou então usar extenção do PlantUML para o VS Code
- Perguntar à prof sobre mudar a aposta
- Temos de confirmar o que acontece quando o player perde insurance
- Quando fazes insurance tira te dinheiro? (acho que sim se nao dava se insurance sempre)


## HiLo

|Player\Dealer|1     |2     |3      |4     |5     |6     |7     |8     |9      |10      |11     |12     |13     |
|:-----------:|:----:|:----:|:-----:|:----:|:----:|:----:|:----:|:----:|:-----:|:------:|:-----:|:-----:|:-----:|
|1            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|2            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|3            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|4            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|5            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|6            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|7            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|8            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|9            |      |      |       |      |      |      |      |      |       |        |       |       |       |
|10           |      |      |       |      |      |      |      |      |       |4,Double|       |       |       |
|11           |      |      |       |      |      |      |      |      |       |        |       |       |       |
|12           |      |      |2,Stand|      |      |      |      |      |       |       |        |       |       |       |
|13           |      |      |       |      |      |      |      |      |       |        |       |       |       |
|14           |      |      |       |      |      |      |      |      |       |        |       |       |       |
|15           |      |      |       |      |      |      |      |      |       |4,Stand |       |       |       |
|16           |      |      |       |      |      |      |      |      |       |0,Stand |       |       |       |
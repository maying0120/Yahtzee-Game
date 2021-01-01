# Yahtzee-Game


In a round, each player takes a turn. On each turn, a player rolls the dice with to get them to one of the 13 categories listed. If the first roll doesn’t fulfill one of the categories, the player may choose to roll any or all of the dice again. If the second roll fails, the player may roll any or all of the dice again. By the end of the third roll, however, the player must assign the final dice configuration to one of the thirteen categories on the scorecard. If the dice configuration meets the criteria for that category, the player receives the appropriate score for that category; otherwise the score for that category is 0. Since there are thirteen categories and each category is used exactly once, a game consists of thirteen rounds. After the thirteenth round, all players will have received scores for all categories. The player with the total highest score is declared the winner. 






## Scoring

The scoring is described here, based on the Yahtzee rules described in Wikipedia: https://en.wikipedia.org/wiki/Yahtzee


## Online game 

You can play this game in this webiste the rule is the same: https://cardgames.io/yahtzee/.

## Rules

We divide the sections into Upper and Lower Sections.
The Upper Section can have any combination of dice and the score relies on the face of the
dice.

![](https://github.com/maying0120/Yahtzee-Game/blob/main/upper.png)

If a player scores a total of 63 or more points in these six boxes, a bonus of 35 is added to the upper section score. Although 63 points corresponds to scoring exactly three-of-a-kind for each of the six boxes, a common way to get the bonus is by scoring four-of-a-kind for some numbers so that fewer of other numbers are needed. A player can earn the bonus even if they score a
"0" in an upper section box. The Lower Section requires the five dice to fit a specific pattern for each category.

![](https://github.com/maying0120/Yahtzee-Game/blob/main/lower.png)

The astute observer will notice that a Yahtzee is also a “Full House”, or can fulfill a “chance” box, and that all of the Lower Section categories could easily be fit into any of the upper section categories. The official rules require that the categories be filled in the following way:

* If the corresponding Upper Section box is unused then that category must be used.
* If the corresponding Upper Section box has been used already, a Lower Section box must be used.The Yahtzee acts as a Joker so that the Full House, Small Straight and
Large Straight categories can be used to score 25, 30 or 40 (respectively).
* If the corresponding Upper Section box and all Lower Section boxes have been used, an unused Upper Section box must be used, scoring 0.

## Save and Load

![](https://github.com/maying0120/Yahtzee-Game/blob/main/saveandload.png)

To retrieve a saved game, the client should send a request to the server. The server should send a list of games indexed by player name and time saved. When the user selects a game, it sends the request to a server, the server receives the requests, gets the data from a database, marshalls it into an object, and sends the object back to the client.The client then receives the object and adds its data to the game, and the player picks up where he left off.

![](https://github.com/maying0120/Yahtzee-Game/blob/main/load.png)

## Game Gui Screenshoot
Input your name, and when you finished. Your name is display at the top of GUI. Also, there is a hint to notice the user what should to do.

![](https://github.com/maying0120/Yahtzee-Game/blob/main/gamegui1.png)

The red digit show the possible score of each category.

![](https://github.com/maying0120/Yahtzee-Game/blob/main/gamegui2.png)

When reopen the game, input your name and then select load game.

You will notice there is list, it display the saved games of the current user. You can choose every one from the time list. After your choose it. The system will reload the game status your saved.

![](https://github.com/maying0120/Yahtzee-Game/blob/main/gamegui3.png)

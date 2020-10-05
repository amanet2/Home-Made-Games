#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int inGame = 1; //set to 0 if user wants to quit, stops the game

int sword = 0;	//manage the player's inventory
int shield = 0;
int lamp = 0;

//define what is ahead of player at each 4 directions
//0 is wall/nothing, 1 is ogre, 2 is dragon, 3 is sword, 4 is shield, 5 is door, 6 is bottomless pit, 7 is lamp, 8 is WinRAR, 9 is spider
//10 is minotaur, 11 is demon
int north = 0;
int south = 0;
int east = 0;
int west = 0;

int goDirec = -1;
int lookDirec = -1;
int room = -1;

int numberOfTries = 0;
int numberOfAttempts = 0;

void printHelp(){
	int c;
	FILE * fp;
	fp = fopen("../helpDoc.txt","r");
	if(fp){
		while((c=getc(fp)) != EOF)
			putchar(c);
		fclose(fp);
	}
	
}

void initGame(int type){
	if(type == 0 || type == 1)
		printf("\nTry again? y/n\n");
	
	if(numberOfTries != 0){
		char * choice = (char*)malloc(sizeof(char)*256+1);
		fgets(choice, 256, stdin);
		if(strcmp("y\n",choice)==0){
			numberOfAttempts++;
			printf("\nNumber of attempts: %i\n", numberOfAttempts);
			numberOfTries = 0;		
		}
		else if(strcmp("n\n",choice)==0){ 
			printf("\nSee you later!\n");
			inGame = 0;			
			system("exit");
			}
		else
		{
			printf("\nPlease don't be a jackass\n");
			initGame(0);
		}
	}
		
	room = 0;
	sword = 0;
	lamp = 0;
	shield = 0;
	numberOfTries++;
}

void welcomeScreen(){
    printf("\n********      ****      ***     ***   ******\n");
    printf("********     **  **     ****   ****   ****** \n");
    printf("**           **  **     ** ** ** **   **     \n");
    printf("**  ****     ******     **  ***  **   ****    \n");
    printf("**  ****     ******     **   *   **   ****  \n");
    printf("**    **     **  **     **       **   **\n");
    printf("********     **  **     **       **   ******\n");
    printf("********     **  **     **       **   ******\n");
}

void execGo(int direc){
	char * direction = malloc(sizeof(char)*256 +1);
	char * typeOfMonster = malloc(sizeof(char)*256 +1);
	int whatsHere = -1;

	if(direc == 0){	
		direction = "north";
		whatsHere = north;
	}	
	else if(direc == 1){
		direction = "south";
		whatsHere = south;	
	}
	else if(direc == 2){
		direction = "east";
		whatsHere = east;}
	else if(direc == 3){
		direction = "west";
		whatsHere = west;}

	if(whatsHere == 1 || whatsHere == 10 || whatsHere == 11){
		if(whatsHere == 1) typeOfMonster = "Ogre";
		else if(whatsHere == 10) typeOfMonster = "Minotaur";
		else if(whatsHere == 11) typeOfMonster = "Demon";

		if(sword == 0){printf("\n	You run headlong into the side of an enraged %s.\nYou reflect on how your bumbling nature had always caused you problems...\n... as he frees your head from your body.\n", typeOfMonster);
		initGame(0);}
		else{
			printf("\nYou bump into a vile %s, yet use your sword to strike the beast.\nThe blade has snapped off the hilt during the altercation.\nIt is knocked back from the blow, but will soon come to its wits and chase you\nBetter get away and find another sword...",typeOfMonster);
			sword=0;}
	}
	else if(whatsHere == 2 || whatsHere == 9){
		typeOfMonster = "Wyvern";
		if(shield == 0){printf("\n	You run directly into the open mouth of a starving %s.\nYou are digested over the period of 1,000 years.\n...Plenty of time to think about how you should look where you are going.\n", typeOfMonster);
		initGame(0);}
		else{
			printf("\n	You bump into the nose of an angry %s.  Luckily, you have your trusty shield,\nwhich protects your body from his fiery breath.\nBest not to walk toward the creature again.\n", typeOfMonster);
			shield = 0;}
	}
	else if(whatsHere == 6){printf("\nYou fall into a bottomless pit and die of starvation.\n");
					initGame(0);}
	else if(whatsHere == 5){
		printf("\nYou go through the door on your %s.\n", direction);
		if(direc == 2)
			room++;
		else if(direc == 3)
			room--;
		else if(direc == 0)
			room = room+4;
		else if(direc == 1)
			room = room-4;
	}
	else if(whatsHere == 0){printf("\nLuckily, your skull protects your brain from hemmoraging as you run into a stone wall at 7.5 mph.\n");}
	else if(whatsHere == 4){printf("\nYou pick up a solid diamond shield.  It should keep you protected from fire.\n"); shield = 1;}
	else if(whatsHere == 3){printf("\nYou pick up a rusty sword, this may prove useful.\n");
				sword = 1;}
	else if(whatsHere == 7){printf("\nYou pick up a nice-looking lamp, which will guide you through your journey.\n");
	lamp =1;}
	else if(whatsHere == 8){
	printf("\n**      **    *******    ***      **\n");
	printf("**      **    *******    ****     ** \n");
	printf("**      **      ***      ****     **\n");
	printf("**      **      ***      ** **    **\n");
	printf("**  **  **      ***      **  **   **  \n");
	printf("**  **  **      ***      **   **  **   \n");
	printf("** **** **    *******    **    ** **\n");
	printf("**** *****    *******    **     ****\n");
	initGame(0);
	}
}

void execLook(int direc){
	char * direction = malloc(sizeof(char)*256 +1);
	int whatsHere = -1;
	if(direc == 0){
		direction = "north";
		whatsHere = north;	
	}	
	else if(direc == 1){
		direction = "south";
		whatsHere = south;
	}	
	else if(direc == 2){
		direction = "east";
		whatsHere = east;
	}	
	else if(direc == 3){
		direction = "west";
		whatsHere = west;
	}
	char * typeOfMonster = malloc(sizeof(char)*256+1);
	
	if(lamp == 1){
		if(whatsHere == 1 || whatsHere == 10 || whatsHere == 11 || whatsHere == 2 || whatsHere == 9){
			if(whatsHere == 1) typeOfMonster = "Ogre";
		else if(whatsHere == 10) typeOfMonster = "Minotaur";
		else if(whatsHere == 11) typeOfMonster = "Demon";
		else if(whatsHere == 2) typeOfMonster = "Wyvern";
		else if(whatsHere == 9) typeOfMonster = "Spider";
			printf("\nThere is a mean looking %s leaning to the wall on your %s.  Luckily he is asleep, it would be a bad situation to have an awake one on your hands.\n", typeOfMonster, direction);
		}
		else if(whatsHere == 6){
			printf("\nThere is a bottomless pit to your %s, best not to walk into it...\n",direction);
		}
		else if(whatsHere == 5 || whatsHere == 8){printf("\nThere is a very old-looking door to your %s.\n", direction);}
		else if(whatsHere == 0){printf("\nYou stare %s for 135 minutes at a dusty wall covered in spiders' webs.\n", direction);}
		else if(whatsHere == 3){printf("\nA pile of rusty old swords lies to your %s.  Maybe you should pick one up.\n", direction);}
		else if(whatsHere == 4){printf("\nThere is a shiny diamond shield to your %s.\n", direction);}	
		else if(whatsHere == 7){printf("\nThere is a shiny lamp to your %s which is inexplicably still alight.\n", direction);}	
		else
			printf("\n%i at %s\n",whatsHere,direction);
	}
	else{
		printf("\nDarkness nullifies your entire sense of sight.\nWithout a lamp, there's no way of knowing what's around you.\n");
		if(north == 1 || south == 1 || east == 1 || west == 1 || north == 10 || south == 10 || east == 10 || west == 10 || north == 11 || south == 11 || east == 11 || west == 11) printf("You can smell and hear some kind of creature in the room with you.\n");
		else printf("\n");	
	}
}

void processCommand(char string[]){
	//printf("\n%s\n",string);
	
	
	int validinput = 1;	

	char *args[256];
	char ** next = args;

	char *temp = strtok(string, " ");
	while(temp != NULL){
		*next++ = temp;
		temp = strtok(NULL, " ");	
	}
	*next = '\0';

	if(strcmp("help\n",string)==0)
		printHelp();
	else if((strcmp("quit\n",string)==0) || (strcmp(string, "q\n") == 0))
	{
		printf("\nThank you for playing!\n");
		inGame = 0;
	}
	else if(strcmp("restart\n",string)==0){
		initGame(1);
		welcomeScreen();	
	}
	else if(strcmp("go", args[0])==0){ //if we want to go, we need to go <somewhere>
		if(strcmp("north\n", args[1])==0)
			goDirec = 0;
		else if(strcmp("south\n", args[1])==0)
			goDirec = 1;
		else if(strcmp("east\n", args[1])==0)
			goDirec = 2;
		else if(strcmp("west\n", args[1])==0)
			goDirec = 3;
		else validinput=0;	

		if(validinput == 1)
			execGo(goDirec);
	}
	else if(strcmp("look", args[0])==0){
		if(strcmp("north\n", args[1])==0)
			lookDirec = 0;
		else if(strcmp("south\n", args[1])==0)
			lookDirec = 1;
		else if(strcmp("east\n", args[1])==0)
			lookDirec = 2;
		else if(strcmp("west\n", args[1])==0)
			lookDirec = 3;
		else validinput=0;	

		if(validinput == 1)				
			execLook(lookDirec);	
	}
	else validinput=0;
	
	if(validinput==0){
		printf("\nSorry, please enter a valid string.\n");
		/*int i;
		for(i=0;i< sizeof(args);i++){
			if(args[i] != NULL) printf(" %s", args[i]);
		}
		printf(".\n");*/
	}	
	
}

void main(){
	
	char * playerName = (char*)malloc(sizeof(char)*256 + 1);
	printf("\n\n ENTER YOUR NAME: ");	//get player's name
	fgets(playerName, 256, stdin);	

		if((strlen(playerName)>0) && (playerName[strlen(playerName)-1]== '\n'))
		playerName[strlen(playerName)-1] = '\0'; //add null terminator


	welcomeScreen(); //print welcome screen
	
	//starting room has following properties
	initGame(2); //reset all stats
	


	while(inGame == 1){ //while in game process comms
		switch(numberOfTries){
			case 1 : printf("\n%s wakes up in a dungeon.\n",playerName);
				 numberOfTries++;		
		}
		//printf("\nYou are in room %i\n", room);
			if(room==0){	
				//printf("\nhere0\n");
				north = 0;	
				south = 6;
				east = 5;
				west = 1;}	
			else if(room==1){
				//printf("\nhere1\n");
				north = 5;
				south = 0;
				east = 5;
				west = 5;}
			else if(room==2){
				//printf("\nhere2\n");
				north = 3;
				south = 2;
				east = 7;
				west = 5;}
			else if(room==3){
				//printf("\nhere1\n");
				north = 5;
				south = 4;
				east = 10;
				west = 6;}
			else if(room==4){
				//printf("\nhere1\n");
				north = 6;
				south = 4;
				east = 5;
				west = 11;}
			else if(room==5){
				//printf("\nhere1\n");
				north = 0;
				south = 5;
				east = 5;
				west = 5;}
			else if(room==6){
				//printf("\nhere1\n");
				north = 5;
				south = 4;
				east = 5;
				west = 5;}
			else if(room==7){
				//printf("\nhere1\n");
				north = 1;
				south = 5;
				east = 3;
				west = 5;}
			else if(room==8){
				//printf("\nhere1\n");
				north = 5;
				south = 0;
				east = 5;
				west = 0;}
			else if(room==9){
				//printf("\nhere1\n");
				north = 0;
				south = 0;
				east = 5;
				west = 5;}
			else if(room==10){
				//printf("\nhere1\n");
				north = 0;
				south = 5;
				east = 0;
				west = 5;}
			else if(room==11){
				//printf("\nhere1\n");
				north = 0;
				south = 0;
				east = 0;
				west = 0;}
			else if(room==12){
				//printf("\nhere1\n");
				north = 0;
				south = 5;
				east = 5;
				west = 0;}
			else if(room==13){
				//printf("\nhere1\n");
				north = 0;
				south = 0;
				east = 5;
				west = 5;}
			else if(room==14){
				//printf("\nhere1\n");
				north = 8;
				south = 0;
				east = 0;
				west = 5;}
			else if(room==15){
				//printf("\nhere1\n");
				north = 0;
				south = 0;
				east = 0;
				west = 0;}	


		char command[1024];

		printf("\nEnter Command [Or type 'help' for help]: ");
		fgets(command,256,stdin);
		processCommand(command);
	}

	if(inGame==0){system("exit");}
}

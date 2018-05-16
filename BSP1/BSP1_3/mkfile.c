/*
* Prompt user for input and create an empty  
  data with this name and access rights 0700
*
* Nhat Khanh Huy Tran, Tri Pham  # 09.04.2018
*
*/

#include <fcntl.h>
#include <memory.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main () {
	// set constants 
	const int size = 30;
	const int mode = 0700;
	
	// get user's input 
	char filename[size];
	printf("Name der neuen Datei: ");
	fgets(filename, size, stdin); 

	// remove newline from input  
	int last_index = strlen(filename) - 1;
	filename[last_index] = '\0';

	// create pathname 
	char initial_path[] = "./";
	char *pathname = malloc(strlen(initial_path)+ strlen(filename) + 1);
	strcpy(pathname, initial_path);
        strcat(pathname, filename);

    // open file
	int fd = creat(pathname, mode);	
	printf("Die Datei %s wurde erfolgreich angelegt!\n", filename);

	// close file 
	close(fd);
	return 0;
}

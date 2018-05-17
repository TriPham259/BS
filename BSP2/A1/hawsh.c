/*
 * A C-Program to implement/mimic a simple shell.
 *
 * Tri Pham & Nhat Khanh Huy Tran, 17.5.2017
 *
 * gcc -o hawsh hawsh.c
 */


#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

void prompt_command();
int read_command();
void help();
void version();

const int command_size = 100;

// Main function. 
int main(int argc, char *argv[]) {
	int command_in_background = 0; 
	int status = 0; 
	char command[command_size];

	while (true) {
		prompt_command();

		command_in_background = read_command(command);
		// exit shell
		if (strcmp(command, "quit") == 0) {
			printf("... und Tschüß!\n");
			exit(EXIT_SUCCESS);
		}
		// print help
		if (strcmp(command, "help") == 0) {
			help();
		}
		// print version
		else if (strcmp(command, "version") == 0) {
			version();
		}
		// change directory, if command starts with "/"
		else if (strncmp(command, "/", 1) == 0) {
			if(chdir(command) == -1) {
                printf("Failed to change directory.");
            }
		}
		// execute built-in commands of shell (without options, arguments)
		else {
			// create child process
			int PIDstatus = fork();

			if (PIDstatus < 0) {
				printf("Unable to fork.\n");
				continue;
			}

			if (PIDstatus > 0) { // parent process
				// let parent process wait for child process
				if (command_in_background == 0) {
					waitpid(PIDstatus, &status, 0);
				}
			} else { // child process
				// execute the command 
				int returnVal; 
				returnVal = execlp(command, command, NULL); 

				// print error message if command is not possible
				if (returnVal == -1) {
					printf("Unknown command.\n");
					exit(0);
				}
			}
		}
	}
}

// Prompt user for command. 
void prompt_command() {
	// get current working directory
	char cwd[300];
	getcwd(cwd, sizeof(cwd));

	// print prompt string
	printf("%s@%s > ", getenv("USER"), cwd);          // for MacOS und Linux
	//printf("%s@%s > ", getenv("USERNAME"), cwd);    // for Window and Linux
}

// Read command user types in.
// Return 0 for command without &. 
// Return 1 for command with &. (command waits for background process)  
int read_command(char* command) {
	fgets(command, command_size, stdin);

	// remove newline from command 
	command[strlen(command) - 1] = '\0';

	if (command[strlen(command) - 1] == '&') {
		// remove '&' from command
		command[strlen(command) - 1] = '\0';
		return 1;
	} 
	else {
		return 0;
	}
}

// Show list of commands and their usage.
void help() {
	printf("HAW-Shell is a shell, which can execute built-in commands (without options, arguments) and following built-in commands:\n");
    printf("quit        - close HAW-Shell\n");
    printf("version     - show the current version of HAW-Shell\n");
    printf("/[pathname] - change the current working directory to pathname\n");
    printf("help        - show this help message\n");
}

// Print current version of HAW-Shell. 
void version() {
	printf("HAW-Shell Version 1.0 - Author: Huy Tran & Tri Pham\n");
}
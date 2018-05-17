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
//#include <wait.h>

/**
 * Prints the Prompt String
 */
void type_prompt() {
    char cwd[256];
    getcwd(cwd, sizeof(cwd));
    //printf("%s@%s > ", getenv("USERNAME"), cwd);    // for window and linux
    printf("%s@%s > ", getenv("USER"), cwd);        // for macos
}


/**
 * Reads the keyboard input Stream and saves the input into param command
 *
 * @param command: Holds the user input
 * @return Return 0 for command without &. (command waits for background process)
 *			 Return 1 for command with &.
 */
int read_command(char* command) {
    fgets(command, 128, stdin);
    
    // remove newline at the end of command
    command[strlen(command) - 1] = '\0';
    
    if (command[strlen(command) - 1] == '&') {
        command[strlen(command) - 1] = '\0';
        return 1;
    } else {
        return 0;
    }
}

/**
 * Prints help text
 */
void usage() {
    printf("hawsh is a shell, which can execute the following built-in commands:\n");
    printf("help - shows this help message\n");
    printf("version - shows the current version of hawsh\n");
    printf("/[pathname] - change the current working directory to pathname\n");
    printf("quit - closes hawsh");
}

/**
 * Prints version info
 */
void version() {
    printf("1.0\n");
}


/**
 * Main function
 * @param argc
 * @param argv
 * @return
 */
int main(int argc, char *argv[]) {

    int command_in_background = 0;
    int status = 0;
    char command[128];

    while (true) {
        // print promt string at the start of each line
        type_prompt();

        //read command from keyboard
        command_in_background = read_command(command);

        // print usage when command  == "help"
        if (strcmp(command, "help") == 0) {
            usage();
        } 
        // print version when command == "version"
        else if (strcmp(command, "version") == 0) {
            version();
        } 
        // quit
        else if (strcmp(command, "quit") == 0) {
            printf("Tschüss!\n");
            exit(EXIT_SUCCESS);
        } 
        // change directory when command starts with "/" (if directory is real)
        else if (strncmp(command, "/", 1) == 0) {
            // change directory, prompt error if failed
            if(chdir(command) == -1) {
                printf("failed to change directory");
            }
        } 
        // execute a non-built-in command
        else {
            int PIDstatus = fork();              
            if (PIDstatus < 0){
                printf("Unable to fork\n");      
                continue;                        
            }
            if(PIDstatus > 0){
                // let parennt process wait for child process
                if(command_in_background == 0){
                    waitpid(PIDstatus, &status, 0);   
                }
            } else {
                // execute the command
                int returnVal = 0;
                returnVal = execlp(command, command, NULL);

                // prompt message if the command isn't available
                if(returnVal == -1)
                {
                    printf("unknown command\n");
                    exit(0);
                }

            }
        }
    }
}
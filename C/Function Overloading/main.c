#include <stdio.h>
#include <stdlib.h>
#include "array.h"

int main(int argc, char** argv) {
    int length = 2;
    char **array = (char **) malloc(sizeof(char *) * length);
    
    array[0] = "Sick";
    array[1] = "Sad";
    
    array_print(array_concat("Hello", array, 2), 3);
    array_print(array_concat(array, "World", 2), 3);
    
    exit(EXIT_SUCCESS);
}


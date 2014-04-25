#include "scream.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char *__scream_to_upper(const char *text){
	char *upper_text;
	int size, i;
	upper_text = strdup(text);
	size = strlen(upper_text);
	for (i = 0; i < size; i++) {
		upper_text[i] = toupper(upper_text[i]);
	}

	return upper_text;
}
void scream_println(const char *text)
{
	char *upper_text = __scream_to_upper(text);
	printf("%s\n", upper_text);
	free(upper_text);
}


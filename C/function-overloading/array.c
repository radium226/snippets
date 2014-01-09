#include "array.h"

#include <stdlib.h>
#include <stdio.h> 
#include <string.h>

char **__array_concat_at_begin(void *v1, void *v2, int length)
{
	char *first_text = (char *) v1;
	char **following_texts = (char **) v2;
	
	char **all_texts = malloc(sizeof(char *) * (length + 1));
	
	size_t char_ptr_size = sizeof(char *);
	*all_texts = first_text;
	memcpy(all_texts + 1, following_texts, char_ptr_size * length);
	return all_texts;
}

char **__array_concat_at_end(void *v1, void *v2, int length)
{
	char *last_text = (char *) v2;
	char **preceding_texts = (char **) v1;
	
	char **all_texts = malloc(sizeof(char *) * (length + 1));
	
	size_t char_ptr_size = sizeof(char *);
	*(all_texts + length) = last_text;
	memcpy(all_texts, preceding_texts, char_ptr_size * length);
	return all_texts;
}

void array_print(char **array, int length)
{
	int index;
	char *separator;
	char *item;
	printf("[");
	for (index = 0; index < length; index++)
	{
		separator = index > 0 ? " | " : "";
		item = *(array + index);
		printf("%s%s", separator, item);
	}
	printf("]\n");
}
#include "text.h"

#include <stdio.h>
#include <stdarg.h>

char** __text_print_all(void *first_argument, ...)
{
	va_list arguments;
	int i = 0;
	char *argument = NULL;
	char *separator = NULL;
	printf("< ");
	va_start(arguments, first_argument);
	for (i = 0; ; i++)
	{
		separator = i == 0 ? "" : " / ";
		argument = va_arg(arguments, char *);
		if (argument == NULL)
		{
			break;
		}
		printf("%s%s", separator, argument);
	}
	va_end(arguments);
	printf(" >\n");
	
	return NULL;
}

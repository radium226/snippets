#include "snippet.h"

#include "text.h"
#include <stdlib.h>
#include <stdio.h>

int main(int UNUSED(argc), char **UNUSED(argv))
{
	text_print_all("Hello", ", ", "World", "!");
	text_print_all();

	exit(EXIT_SUCCESS);
}

#include "./api/api.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv) {
	
	API *api = api_init();
	char *version = api_version(api);
	printf("version=%s\n", version);
	free(api);

	return EXIT_SUCCESS;
}

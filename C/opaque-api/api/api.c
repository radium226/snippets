#include "./api.h"

#include <stdlib.h>

struct __API {
	char *version;
};

API *api_init(void) {
	API *api = (API *)malloc(sizeof(API));
	api -> version = "1.2";
	return api;
}

char *api_version(API *api) {
	return api -> version;
}



#ifndef API_H
#define API_H

typedef struct __API API;

API *api_init(void);

char *api_version(API *api);

#endif

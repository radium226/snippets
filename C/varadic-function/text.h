
#define text_print_all(...) __text_print_all(NULL, ##__VA_ARGS__, NULL)

char** __text_print_all(void *first_argument, ...);


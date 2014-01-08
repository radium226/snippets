// Concat an array. 
#define array_concat(a, b, c) \
	__builtin_choose_expr( \
		__builtin_types_compatible_p(typeof(*a), char), \
		__array_concat_at_begin(a, b, c), \
		__array_concat_at_end(a, b, c) \
	)
char **__array_concat_at_begin(void *first_text, void *following_texts, int length);
char **__array_concat_at_end(void *preceding_texts, void *last_text, int length);

// Print an array. 
void array_print(char **array, int length);

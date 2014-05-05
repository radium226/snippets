#ifndef SNIPPET_H
#define SNIPPET_H

#define KEYBITS 256

#define UNUSED(x) UNUSED_ ## x __attribute__((__unused__))

char *snippet_encrypt(const char *plain_text, const char *password);
char *snippet_decrypt(const char *encrypted_text, const char *password);

int main(int argc, char **argv);

#endif

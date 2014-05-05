#include "snippet.h"

#include <stdio.h>
#include <stdlib.h>
#include "rijndael.h"
#include <string.h>

#define BLOCK_SIZE 16

char **__snippet_split_to_blocks(const char*text)
{
    int size = strlen(text);
    int count = size / BLOCK_SIZE + 1;
    int i, j, k;
    char **blocks = (char **) malloc(sizeof(char *) * (count + 1));
    for (i = 0; i < count; i++) {
        char *block = (char *) malloc(sizeof(char) * BLOCK_SIZE);
        for (j = 0; j < BLOCK_SIZE; j++) {
            k = BLOCK_SIZE * i + j;
            char character = k < size ? *(text + k) : '\0';
            *(block + j) = character;
        }
        *(blocks + i) = block;
    }
    *(blocks + count) = NULL;
    return blocks;
}

void **__snippet_print_blocks(char **blocks)
{
    char *block = NULL;
    int i;
    for(i = 0, block = *blocks; block != NULL; i++, block = *++blocks)
    {
        printf("%i: %s\n", i, block); 
        if (i == 10) break;
    }
}

int __snippet_block_count(char **blocks)
{
    int count;
    char *block = NULL;
    for(count = 0, block = *blocks; block != NULL; count++, block = *++blocks);
    return count;
}

char **__snippet_encrypt_blocks(const unsigned long  *rk, int nrounds, char **plain_blocks)
{
    int index = 0;
    int count = __snippet_block_count(plain_blocks);
    printf("%i\n", count);
    char *cipher_block, plain_block;
    char **cipher_blocks = (char **)malloc(sizeof(char *) * (count + 1));
    for (index = 0; index < count; index++)
    {
        printf("!!\n");
        plain_block = *(plain_blocks + index);
        printf("lkjsd\n");
        printf("%s\n", plain_block);
        cipher_block = (char *)malloc(sizeof(char) * BLOCK_SIZE);
        rijndaelEncrypt(rk, nrounds, (const char*)plain_block, (const char*)cipher_block);
        printf("%s --> %s", plain_block, cipher_block);
    }
    
    
    
}

char *snippet_encrypt(const char *plain_text, const char *password)
{
    unsigned long rk[RKLENGTH(KEYBITS)];
    unsigned char key[KEYLENGTH(KEYBITS)];
    int i;
    int nrounds;
    for (i = 0; i < sizeof(key); i++)
    {
        key[i] = *password != 0 ? *password++ : 0;
    }
    
    nrounds = rijndaelSetupEncrypt(rk, key, KEYBITS);    
    char **blocks = __snippet_split_to_blocks(plain_text);
    __snippet_encrypt_blocks(rk, nrounds, blocks);
    return NULL;
}

char *snippet_decrypt(const char *cipher_text, const char *password)
{
    unsigned long rk[RKLENGTH(KEYBITS)];
    unsigned char key[KEYLENGTH(KEYBITS)];
    int i;
    int nrounds;
    for (i = 0; i < sizeof(key); i++)
    {
        key[i] = *password != 0 ? *password++ : 0;
    }
    
    nrounds = rijndaelSetupDecrypt(rk, key, KEYBITS);
    
    char *plain_text = (char *)malloc(sizeof(char) * strlen(cipher_text));
    rijndaelDecrypt(rk, nrounds, cipher_text, plain_text);
    return plain_text;
    
}

int main(int UNUSED(argc), char **UNUSED(argv))
{
    const char *plain_text = "abcdefghijklmnopqrstuvwxyz0123456789";
    const char *password = "DealWithItDealWithItDealWithItDealWithItDealWithItDealWithItDealWithItDealWithItDealWithItDealWithIt";
    const char *cipher_text = snippet_encrypt(plain_text, password);
    //plain_text = snippet_decrypt(cipher_text, password);
    
    printf("[%s] = [%s]\n", plain_text, cipher_text);
    
    free(cipher_text);
    //free(plain_text);
    exit(EXIT_SUCCESS);
}

#include "snippet.h"

#include <alloca.h>
#include <stdio.h>
#include <stdlib.h>

Complex *complex_new(int real, int imaginary) {
	Complex *complex = (Complex *) alloca(sizeof(Complex));
	complex -> real = real;
	complex -> imaginary = imaginary;
	return complex;
}

void complex_print(Complex *complex) {
	printf("%d + %di\n", complex -> real, complex -> imaginary);
}

int main(int UNUSED(argc), char **UNUSED(argv)) {
	Complex *square = (Complex *) alloca(sizeof(Complex));
	square -> real = 1;
	square -> imaginary = 1;
	
	Complex *zero = C(0, 0);
	Complex *one = C(1, 0);
	Complex *i = C(0, 1);
	
	complex_print(zero);
	complex_print(one);
	complex_print(i);
	
	complex_print(square);

	exit(EXIT_SUCCESS);
}


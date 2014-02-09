#define UNUSED(x) UNUSED_ ## x __attribute__((__unused__))

int main(int argc, char **argv);

struct Complex {
	int real;
	int imaginary;
};

typedef struct Complex Complex;

inline Complex *complex_new(int real, int imaginary) __attribute__((always_inline));

Complex *complex_new(int real, int imaginary);

#define C(r, i) complex_new(r, i)

void complex_print(Complex *complex);

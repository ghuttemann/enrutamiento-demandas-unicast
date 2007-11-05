#include "Random.h"
#include <cstdlib>
#include <ctime>

Random::Random() {
	srand(time(NULL));
}

Random::~Random() {
}

int Random::nextInt(int n) {
	double max = (double) RAND_MAX;
	double div = rand() / max;
	int next   = ((int) div) * n;
	return next;
}

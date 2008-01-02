#ifndef UTILS_H_
#define UTILS_H_

#include <cstdlib>
#include <iostream>

using namespace std;

#define terminar(s) cerr << "[ERROR]" << endl, \
					cerr << "=======" << endl, \
					cerr << "Archivo: " << __FILE__ << endl, \
					cerr << "Linea  : " << __LINE__ << endl, \
					cerr << endl, \
					cerr << s, \
					exit(0)

#endif /*UTILS_H_*/

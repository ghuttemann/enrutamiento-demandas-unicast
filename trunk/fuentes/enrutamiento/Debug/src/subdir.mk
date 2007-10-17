################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/Grafo.cpp \
../src/GrafoLista.cpp \
../src/GrafoMatriz.cpp \
../src/prueba.cpp 

OBJS += \
./src/Grafo.o \
./src/GrafoLista.o \
./src/GrafoMatriz.o \
./src/prueba.o 

CPP_DEPS += \
./src/Grafo.d \
./src/GrafoLista.d \
./src/GrafoMatriz.d \
./src/prueba.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '



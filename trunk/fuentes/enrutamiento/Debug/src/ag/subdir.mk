################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/ag/Cromosoma.cpp \
../src/ag/Demanda.cpp 

OBJS += \
./src/ag/Cromosoma.o \
./src/ag/Demanda.o 

CPP_DEPS += \
./src/ag/Cromosoma.d \
./src/ag/Demanda.d 


# Each subdirectory must supply rules for building sources it contributes
src/ag/%.o: ../src/ag/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '



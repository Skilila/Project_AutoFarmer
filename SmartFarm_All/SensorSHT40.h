// [파일 설명]
// 이 파일은 SHT40 센서를 초기화하고 온도 및 습도를 측정하는 기능을 제공합니다.
#ifndef SHT40_H
#define SHT40_H

#include "SensorData.h"

void initSHT40();
void readSHT40(SensorData &data);

#endif
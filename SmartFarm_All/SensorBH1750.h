// [파일 설명]
// 이 파일은 BH1750 조도 센서를 초기화하고 조도 데이터를 측정하는 기능을 제공합니다.
#ifndef BH1750_H
#define BH1750_H

#include "SensorData.h"

void initBH1750();
void readBH1750(SensorData &data);

#endif
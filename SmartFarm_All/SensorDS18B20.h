// [파일 설명]
// 이 파일은 DS18B20 디지털 온도 센서를 통해 토양 온도를 측정하는 기능을 제공합니다.
#ifndef DS18B20_H
#define DS18B20_H

#include "SensorData.h"

void initDS18B20();
void readDS18B20(SensorData &data);

#endif
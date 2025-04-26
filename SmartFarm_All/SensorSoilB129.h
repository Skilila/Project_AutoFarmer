// [파일 설명]
// 이 파일은 B129 아날로그 토양 수분 센서로부터 토양의 습도값(%)을 측정하는 기능을 제공합니다.
#ifndef SOIL_B129_H
#define SOIL_B129_H

#include "SensorData.h"

void initB129();
void readB129(SensorData &data);

#endif
// [파일 설명]
// 이 파일은 OLED 디스플레이를 초기화하고 센서 데이터를 시각적으로 출력하는 기능을 제공합니다.
#ifndef DISPLAY_OLED_H
#define DISPLAY_OLED_H

#include "SensorData.h"

void initOLED();
void updateOLED(const SensorData &data);

#endif
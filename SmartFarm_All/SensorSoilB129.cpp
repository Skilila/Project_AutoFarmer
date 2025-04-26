// [파일 설명]
// 이 파일은 B129 아날로그 토양 수분 센서로부터 토양의 습도값(%)을 측정하는 기능을 제공합니다.
#include "SensorSoilB129.h"
#include <Arduino.h>

#define SOIL_PIN 1

void initB129() {
  pinMode(SOIL_PIN, INPUT);
  Serial.println("✅ Cảm biến B129 sẵn sàng");
}

void readB129(SensorData &data) {
  int raw = analogRead(SOIL_PIN);
  int moisture = map(raw, 4095, 1600, 0, 100);
  moisture = constrain(moisture, 0, 100);
  data.soilMoisture = moisture;
}
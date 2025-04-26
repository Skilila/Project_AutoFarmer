// [파일 설명]
// 이 파일은 SHT40 센서를 초기화하고 온도 및 습도를 측정하는 기능을 제공합니다.
#include "SensorSHT40.h"
#include <Adafruit_SHT4x.h>
#include <Wire.h>

Adafruit_SHT4x sht4;
bool shtReady = false;

void initSHT40() {
  Wire.begin(8, 9);
  if (sht4.begin()) {
    sht4.setPrecision(SHT4X_HIGH_PRECISION);
    Serial.println("✅ SHT40 ready");
    shtReady = true;
  } else {
    Serial.println("⚠️ Không tìm thấy SHT40");
  }
}

void readSHT40(SensorData &data) {
  if (!shtReady) return;
  sensors_event_t humidity, temp;
  if (sht4.getEvent(&humidity, &temp)) {
    data.airTemp = temp.temperature;
    data.airHumidity = humidity.relative_humidity;
  }
}
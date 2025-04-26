// [파일 설명]
// 이 파일은 BH1750 조도 센서를 초기화하고 조도 데이터를 측정하는 기능을 제공합니다.
#include "SensorBH1750.h"
#include <BH1750.h>
#include <Wire.h>

TwoWire I2C_BH1750 = TwoWire(1);
BH1750 lightMeter;
bool bhReady = false;

void initBH1750() {
  I2C_BH1750.begin(6, 7);
  if (lightMeter.begin(BH1750::CONTINUOUS_HIGH_RES_MODE, 0x23, &I2C_BH1750)) {
    Serial.println("✅ BH1750 ready");
    bhReady = true;
  } else {
    Serial.println("⚠️ BH1750 không phản hồi");
  }
}

void readBH1750(SensorData &data) {
  if (!bhReady) return;
  data.lux = lightMeter.readLightLevel();
}
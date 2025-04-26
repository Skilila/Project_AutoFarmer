// [파일 설명]
// 이 파일은 DS18B20 디지털 온도 센서를 통해 토양 온도를 측정하는 기능을 제공합니다.
#include "SensorDS18B20.h"
#include <DallasTemperature.h>
#include <OneWire.h>

#define DS18B20_PIN 5
OneWire oneWire(DS18B20_PIN);
DallasTemperature sensors(&oneWire);

void initDS18B20() {
  sensors.begin();
  Serial.println("✅ DS18B20 ready");
}

void readDS18B20(SensorData &data) {
  sensors.requestTemperatures();
  float temp = sensors.getTempCByIndex(0);
  if (temp > -100 && temp < 100) data.soilTemp = temp;
}
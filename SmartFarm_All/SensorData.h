// 이 파일은 모든 센서에서 읽은 데이터를 저장하는 구조체(SensorData)를 정의합니다.
// 각 센서의 온도, 습도, 조도, 토양 수분 정보를 포함합니다.

#ifndef SENSORDATA_H
#define SENSORDATA_H

struct SensorData {
  float airTemp = -99;
  float airHumidity = -1;
  float soilTemp = -99;
  int soilMoisture = -1;
  float lux = -1;
};

#endif
// [파일 설명]
// 이 메인 파일은 모든 센서와 디스플레이를 통합하여 데이터를 수집하고 출력하는 전체 시스템을 구성합니다.
// 각각의 센서를 초기화하고, 데이터를 읽고, OLED 및 시리얼 모니터에 출력합니다.
#include "SensorData.h"
#include "SensorSHT40.h"
#include "SensorDS18B20.h"
#include "SensorBH1750.h"
#include "SensorSoilB129.h"
#include "DisplayOLED.h"

SensorData data;

void setup() {
  Serial.begin(115200);
  delay(2000);  // chờ Serial ổn định
  Serial.println("⚙️ Bắt đầu setup...");

  initSHT40();
  initDS18B20();
  initBH1750();
  initB129();
  //initOLED();

  Serial.println("✅ Setup hoàn tất.");
}


void loop() {
  readSHT40(data);
  readDS18B20(data);
  readBH1750(data);
  readB129(data);

  Serial.println("🔁 센서 데이터 읽기:");

  Serial.print("🌡️ 대기 온도: ");
  if (data.airTemp != -99) Serial.print(data.airTemp); else Serial.print("N/A");
  Serial.println(" °C");

  Serial.print("💧 대기 습도: ");
  if (data.airHumidity != -1) Serial.print(data.airHumidity); else Serial.print("N/A");
  Serial.println(" %");

  Serial.print("🌱 토양 온도: ");
  if (data.soilTemp != -99) Serial.print(data.soilTemp); else Serial.print("N/A");
  Serial.println(" °C");

  Serial.print("💦 토양 습도: ");
  if (data.soilMoisture != -1) Serial.print(data.soilMoisture); else Serial.print("N/A");
  Serial.println(" %");

  Serial.print("💡 조도: ");
  if (data.lux != -1) Serial.print(data.lux); else Serial.print("N/A");
  Serial.println(" lux");

  Serial.println("---------------------------");

  //updateOLED(data);  // OLED에도 업데이트
  delay(3000);       // 3초마다 업데이트
}

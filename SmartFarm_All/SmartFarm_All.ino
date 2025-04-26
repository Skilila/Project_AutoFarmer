// [파일 설명]
// 이 메인 파일은 모든 센서와 디스플레이를 통합하여 데이터를 수집하고 출력하는 전체 시스템을 구성합니다.
// 각각의 센서를 초기화하고, 데이터를 읽고, OLED 및 시리얼 모니터에 출력합니다.
#include <WiFi.h> // 와이파이 연결 라이브러리
#include <HTTPClient.h> // HTTP 요청 라이브러리
#include <ArduinoJson.h> // Json 생성 라이브러리
#include "SensorData.h"
#include "SensorSHT40.h"
#include "SensorDS18B20.h"
#include "SensorBH1750.h"
#include "SensorSoilB129.h"
#include "DisplayOLED.h"

SensorData data;

// 와이파이 정보
const char *ssid = "U+NetEB58";  
const char *password = "KAEDF9@8CF";   

void connectWiFi() {
  WiFi.begin(ssid, password);
  Serial.print("Wi-Fi 연결 중");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\n Wi-Fi 연결 완료!");
}

void sendSensorData(SensorData data) {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin(serverURL);
    http.addHeader("Content-Type", "application/json");

    // JSON 만들기
    StaticJsonDocument<256> jsonDoc;
    jsonDoc["airTemp"] = data.airTemp;
    jsonDoc["airHumidity"] = data.airHumidity;
    jsonDoc["soilTemp"] = data.soilTemp;
    jsonDoc["soilMoisture"] = data.soilMoisture;
    jsonDoc["lux"] = data.lux;

    String jsonString;
    serializeJson(jsonDoc, jsonString);

    // POST 요청
    int httpResponseCode = http.POST(jsonString);

    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.print(" 서버 응답 코드: ");
      Serial.println(httpResponseCode);
      Serial.print(" 서버 응답: ");
      Serial.println(response);
    } else {
      Serial.print(" HTTP 요청 실패: ");
      Serial.println(httpResponseCode);
    }

    http.end();
  } else {
    Serial.println(" Wi-Fi 연결 안 됨.");
  }
}

void setup() {
  Serial.begin(115200);
  delay(2000);  // chờ Serial ổn định
  connectWiFi();
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

  // 서버로 데이터 보내기
  sendAirTemp(data.airTemp);           
  sendAirHumidity(data.airHumidity);    
  sendSoilTemp(data.soilTemp);          
  sendSoilMoisture(data.soilMoisture);  
  sendLux(data.lux);                    

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

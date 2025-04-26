// [파일 설명]
// 이 파일은 OLED 디스플레이를 초기화하고 센서 데이터를 시각적으로 출력하는 기능을 제공합니다.
#include "DisplayOLED.h"
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include <Wire.h>

#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64

TwoWire I2C_OLED = TwoWire(2);
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &I2C_OLED, -1);

void initOLED() {
  I2C_OLED.begin(4, 10);
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {
    Serial.println("⚠️ Không khởi động được OLED");
  } else {
    display.clearDisplay();
    display.setTextSize(1);
    display.setTextColor(SSD1306_WHITE);
    display.setCursor(0, 0);
    display.println("OLED ready");
    display.display();
    delay(500);
  }
}

void updateOLED(const SensorData &data) {
  display.clearDisplay();
  display.setTextSize(1);
  display.setCursor(0, 0);

  display.print("Nhiet: ");
  if (data.airTemp != -99) display.print(data.airTemp, 1); else display.print("N/A");
  display.println(" C");

  display.print("Do am: ");
  if (data.airHumidity != -1) display.print(data.airHumidity, 0); else display.print("N/A");
  display.println(" %");

  display.print("Dat: ");
  if (data.soilTemp != -99) display.print(data.soilTemp, 1); else display.print("N/A");
  display.println(" C");

  display.print("Am dat: ");
  if (data.soilMoisture != -1) display.print(data.soilMoisture); else display.print("N/A");
  display.println(" %");

  display.print("Lux: ");
  if (data.lux != -1) display.print(data.lux, 0); else display.print("N/A");
  display.display();
}
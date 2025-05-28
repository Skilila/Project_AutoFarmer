#include <Wire.h>
#include <Adafruit_SHT4x.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <BH1750.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

// ===== OLED =====
#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64
#define RELAY_MOTOR 10  // mô tơ tưới nước
#define RELAY_MIST 11   // máy phun sương

Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);

// ===== SHT40 =====
Adafruit_SHT4x sht4;

// ===== BH1750 =====
BH1750 lightMeter;
bool bhReady = false;

// ===== DS18B20 =====
#define ONE_WIRE_PIN 5
OneWire oneWire(ONE_WIRE_PIN);
DallasTemperature ds18b20(&oneWire);

// ===== B129 analog (độ ẩm đất) =====
#define SOIL_PIN 1

// ===== Biến dữ liệu =====
float airTemp = -99, airHum = -1;
float soilTemp = -99, lux = -1;
int soilMoist = -1;
unsigned long lastDisplay = 0;
int screen = 0;

void setup() {
  Serial.begin(115200);
  delay(3000);
  Serial.println("🚀 Khởi động hệ thống...");

  // Khởi tạo I2C chung SDA=4, SCL=12
  Wire.begin(4, 12);

  // OLED
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {
    Serial.println("❌ OLED error!");
  } else {
    display.clearDisplay();
    display.setTextSize(1);
    display.setTextColor(SSD1306_WHITE);
    display.setCursor(0, 0);
    display.println("✅ OLED OK");
    display.display();
  }

  // SHT40
  if (sht4.begin(&Wire)) {
    sht4.setPrecision(SHT4X_HIGH_PRECISION);
    Serial.println("✅ SHT40 OK");
  } else {
    Serial.println("❌ SHT40 error !");
  }

  // BH1750
  if (lightMeter.begin(BH1750::CONTINUOUS_HIGH_RES_MODE, 0x23, &Wire)) {
    bhReady = true;
    Serial.println("✅ BH1750 OK");
  } else {
    Serial.println("❌ BH1750. error !");
  }

  // DS18B20
  ds18b20.begin();
  Serial.println("✅ DS18B20 OK");

  // B129 analog
  pinMode(SOIL_PIN, INPUT);
  Serial.println("✅ B129 OK");

  Serial.println("✅ done .");

  // tự động hoá.
  pinMode(RELAY_MOTOR, OUTPUT);
  pinMode(RELAY_MIST, OUTPUT);
  digitalWrite(RELAY_MOTOR, HIGH);  // relay OFF (kích LOW để bật)
  digitalWrite(RELAY_MIST, HIGH);
}

void loop() {
  // Đọc SHT40
  sensors_event_t hum, temp;
  if (sht4.getEvent(&hum, &temp)) {
    airTemp = temp.temperature;
    airHum = hum.relative_humidity;
  } else {
    Serial.println("⚠️ error SHT40!");
  }

  // Đọc DS18B20
  ds18b20.requestTemperatures();
  float t = ds18b20.getTempCByIndex(0);
  if (t > -100 && t < 100) soilTemp = t;

  // Đọc BH1750
  if (bhReady) {
    lux = lightMeter.readLightLevel();
    if (lux < 0) {
      Serial.println("⚠️ BH1750 error ");
      lux = -1;
    }
  }

  // Đọc B129 analog
  int raw = analogRead(SOIL_PIN);
  soilMoist = constrain(map(raw, 4095, 1600, 0, 100), 0, 100);

  // Ghi log Serial
  Serial.println("📊 Dữ liệu cảm biến:");
  Serial.print("🌡️ Nhiệt độ KK: ");
  Serial.println(airTemp);
  Serial.print("💧 Độ ẩm KK   : ");
  Serial.println(airHum);
  Serial.print("🌱 Nhiệt đất  : ");
  Serial.println(soilTemp);
  Serial.print("💦 Độ ẩm đất  : ");
  Serial.println(soilMoist);
  Serial.print("💡 Ánh sáng   : ");
  Serial.println(lux);
  Serial.println("-------------------------");

  // Hiển thị tuần tự trên OLED (Tiếng Anh + viết tắt tiếng Việt)
  if (display.width() > 0 && millis() - lastDisplay > 2000) {
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(0, 0);

    switch (screen) {
      case 0:
        display.setTextSize(1);
        display.println("Air Temp (NDKK)");  // NDKK = Nhiệt độ 공기 온도
        display.setTextSize(2);
        display.setCursor(0, 20);
        display.print(airTemp);
        display.print(" C");
        break;
      case 1:
        display.println("Air Humid (DAKK)");  // DAKK = Độ ẩm  공기 습도
        display.setTextSize(2);
        display.setCursor(0, 20);
        display.print(airHum);
        display.print(" %");
        break;
      case 2:
        display.println("Soil Temp (ND)");  // NDD = nhiệt độ đất 토양 온도
        display.setTextSize(2);
        display.setCursor(0, 20);
        display.print(soilTemp);
        display.print(" C");
        break;
      case 3:
        display.println("Soil Moist (DAD)");  // DAD = do am dat 토양 습도
        display.setTextSize(2);
        display.setCursor(0, 20);
        display.print(soilMoist);
        display.print(" %");
        break;
      case 4:
        display.println("Light (AS)");  // AS = Ánh sáng 조도
        display.setTextSize(2);
        display.setCursor(0, 20);
        display.print(lux);
        display.print(" lx");
        break;
    }

    display.display();
    screen = (screen + 1) % 5;
    lastDisplay = millis();
  }

  // Tự động điều khiển mô tơ tưới nước theo độ ẩm đất tự động hoá 
  if (soilMoist < 40) {
    digitalWrite(RELAY_MOTOR, LOW);  // bật relay
    Serial.println("💧 Tưới nước: ON");
  } else {
    digitalWrite(RELAY_MOTOR, HIGH);  // tắt relay
    Serial.println("💧 Tưới nước: OFF");
  }

  // Tự động điều khiển máy phun sương theo độ ẩm KK và nhiệt độ
  if (airHum < 60 || airTemp > 30) {
    digitalWrite(RELAY_MIST, LOW);  // bật relay
    Serial.println("🌫️ Phun sương: ON");
  } else {
    digitalWrite(RELAY_MIST, HIGH);  // tắt relay
    Serial.println("🌫️ Phun sương: OFF");
  }


  delay(3000);
}

import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, ActivityIndicator, Alert } from 'react-native';
import PushNotification from 'react-native-push-notification';

const API_KEY = '948cca00bed4f917800c626aba79a4cc'; // 발급되는대로 OpenWeatherMap API 키로 교체
const CITY = 'Seoul'; // 원하는 도시 이름, 부산대신 일단은 서울로

// 날씨 데이터 타입 정의
interface WeatherData {
  name: string;
  main: {
    temp: number;
    feels_like: number;
    temp_min: number;
    temp_max: number;
    humidity: number;
  };
  weather: Array<{
    main: string;
    description: string;
  }>;
}

// 예보 데이터 타입 정의
interface ForecastData {
  list: Array<{
    dt: number;
    main: {
      temp: number;
      temp_min: number;
      temp_max: number;
      humidity: number;
    };
    weather: Array<{
      main: string;
      description: string;
    }>;
  }>;
}

const MainScreen = () => {
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null);
  const [tomorrowWeather, setTomorrowWeather] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchWeatherData = async () => {
    try {
      setLoading(true);
      
      // 현재 날씨 api 가져오기
      const currentResponse = await fetch(
        `https://api.openweathermap.org/data/2.5/weather?q=${CITY}&appid=${API_KEY}&units=metric&lang=kr`
      );
      
      // 5일 예보 api 가져오기, "내일 날씨"를 스크린에 표기하기 위해 필요한 api. 
      const forecastResponse = await fetch(
        `https://api.openweathermap.org/data/2.5/forecast?q=${CITY}&appid=${API_KEY}&units=metric&lang=kr`
      );
      
      if (!currentResponse.ok || !forecastResponse.ok) {
        throw new Error('날씨 정보를 가져오는데 실패했습니다');
      }
      
      const currentData = await currentResponse.json();
      const forecastData: ForecastData = await forecastResponse.json();
      
      setWeatherData(currentData);
      
      // 내일 날씨 찾기 (24시간 후 데이터)
      const tomorrow = new Date();
      tomorrow.setDate(tomorrow.getDate() + 1);
      tomorrow.setHours(12, 0, 0, 0); // 내일 정오 기준
      
      const tomorrowTimestamp = Math.floor(tomorrow.getTime() / 1000);
      
      // 가장 가까운 내일 데이터 찾기
      const tomorrowData = forecastData.list.find(item => {
        const itemDate = new Date(item.dt * 1000);
        const targetDate = new Date(tomorrow);
        return itemDate.getDate() === targetDate.getDate();
      }) || forecastData.list[8]; // 약 24시간 후 데이터 (3시간 간격 * 8 = 24시간)
      
      setTomorrowWeather(tomorrowData);
      setError(null);
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : '알 수 없는 오류가 발생했습니다';
      setError(errorMessage);
      console.log('날씨 API 에러:', errorMessage);
      // Alert.alert('에러', errorMessage); // 필요시 주석 해제
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchWeatherData();
    
    // 30분마다 날씨 데이터 업데이트
    const interval = setInterval(() => {
      fetchWeatherData();
    }, 30 * 60 * 1000);
    
    return () => clearInterval(interval);
  }, []);

  return (
    <View style={styles.container}>
      {/* 헤더1 */}
      <View style={styles.header}>
        <Text style={styles.headerText}>30일에 재배할 내 토마토 - 1</Text>
      </View>

      {/* 내일 날씨 헤더 */}
      <View style={styles.subHeader}>
        <TouchableOpacity 
          style={styles.tomorrowWeatherContainer}
          onPress={fetchWeatherData}
        >
          {loading ? (
            <View style={styles.tomorrowWeatherContent}>
              <ActivityIndicator color="#666" size="small" />
              <Text style={styles.loadingText}>로딩중...</Text>
            </View>
          ) : tomorrowWeather ? (
            <View style={styles.tomorrowWeatherContent}>
              <Text style={styles.tomorrowWeatherText}>
                내일: {Math.round(tomorrowWeather.main.temp)}°C
              </Text>
              <Text style={styles.tomorrowWeatherDesc}>
                {tomorrowWeather.weather[0].description}
              </Text>
            </View>
          ) : (
            <Text style={styles.subHeaderText}>날씨 정보 없음</Text>
          )}
        </TouchableOpacity>
      </View>

      {/* 받는 데이터 */}
      <View style={styles.metricsContainer}>
        <View style={styles.metricsLeft}>
          <Text style={styles.metricLabel}>습도</Text>
          <Text style={styles.metricLabel}>광량</Text>
          <Text style={styles.metricLabel}>온도</Text>
        </View>
        <View style={styles.metricsRight}>
          <Text style={styles.dateText}>D-21</Text>
          <View style={styles.circleGraph} />
        </View>
      </View>

      {/* 오늘 날씨 */}
      <View style={styles.currentDateContainer}>
        <TouchableOpacity 
          style={styles.currentDateButton}
          onPress={fetchWeatherData}
        >
          {loading ? (
            <View style={styles.weatherContainer}>
              <ActivityIndicator color="#333" size="small" />
              <Text style={styles.loadingText}>날씨 정보 로딩중...</Text>
            </View>
          ) : weatherData ? (
            <View style={styles.weatherContainer}>
              <Text style={styles.currentDateText}>
                오늘 {weatherData.name} {Math.round(weatherData.main.temp)}°C
              </Text>
              <Text style={styles.weatherDescription}>
                {weatherData.weather[0].description} | 습도 {weatherData.main.humidity}%
              </Text>
              <Text style={styles.weatherDetail}>
                체감온도 {Math.round(weatherData.main.feels_like)}°C
              </Text>
            </View>
          ) : (
            <Text style={styles.currentDateText}>현재 날씨</Text>
          )}
        </TouchableOpacity>
      </View>

      {/* 더보기 */}
      <View style={styles.moreButtonContainer}>
        <TouchableOpacity style={styles.moreButton}>
          <Text style={styles.moreButtonText}>더보기</Text>
        </TouchableOpacity>
      </View>

      {/* 좌우로 스크롤 가능한 카드 */}
      <ScrollView horizontal style={styles.taskCardsContainer}>
        {/* 레드 */}
        <View style={[styles.taskCard, styles.pinkCard]}>
          <Text style={styles.cardTitle}>내 작물1:</Text>
          <Text style={styles.cardSubtitle}>토마토</Text>
          <Text style={styles.cardDate}>수확일 D-21</Text>
          <Text style={styles.cardStatus}>최근 7일간</Text>
          <Text style={styles.cardStatus}>상태:</Text>
          <Text style={styles.cardStatus}>건강해요</Text>
          <Text style={styles.cardMore}>더 자세히</Text>
        </View>

        {/* 블루 */}
        <View style={[styles.taskCard, styles.blueCard]} />

        {/* 초록 */}
        <View style={[styles.taskCard, styles.greenCard]} />
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    padding: 10,
    backgroundColor: '#f0f0f0',
  },
  headerText: {
    fontSize: 16,
    color: '#999',
  },
  subHeader: {
    padding: 10,
    backgroundColor: '#e8e8e8',
  },
  subHeaderText: {
    fontSize: 14,
    color: '#666',
    textAlign: 'right',
  },
  // 내일 날씨 스타일 추가
  tomorrowWeatherContainer: {
    alignItems: 'flex-end',
  },
  tomorrowWeatherContent: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  tomorrowWeatherText: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#333',
    marginRight: 8,
  },
  tomorrowWeatherDesc: {
    fontSize: 12,
    color: '#666',
  },
  metricsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    paddingVertical: 15,
  },
  metricsLeft: {
    justifyContent: 'space-around',
    height: 100,
  },
  metricLabel: {
    fontSize: 18,
    fontWeight: 'bold',
    marginVertical: 5,
  },
  metricsRight: {
    alignItems: 'flex-end',
  },
  dateText: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  circleGraph: {
    width: 80,
    height: 80,
    borderRadius: 40,
    borderWidth: 10,
    borderColor: '#b8d1e0',
    marginTop: 10,
  },
  currentDateContainer: {
    alignItems: 'center',
    paddingVertical: 10,
  },
  currentDateButton: {
    backgroundColor: '#e0e0e0',
    paddingHorizontal: 30,
    paddingVertical: 15,
    borderRadius: 10,
    minWidth: 250,
    alignItems: 'center',
  },
  currentDateText: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  weatherContainer: {
    alignItems: 'center',
  },
  weatherDescription: {
    fontSize: 12,
    color: '#555',
    marginTop: 4,
  },
  weatherDetail: {
    fontSize: 11,
    color: '#777',
    marginTop: 2,
  },
  loadingText: {
    fontSize: 12,
    color: '#555',
    marginTop: 5,
  },
  moreButtonContainer: {
    alignItems: 'center',
    paddingVertical: 5,
  },
  moreButton: {
    backgroundColor: '#e0e0e0',
    paddingHorizontal: 20,
    paddingVertical: 5,
    borderRadius: 15,
  },
  moreButtonText: {
    fontSize: 12,
  },
  taskCardsContainer: {
    padding: 10,
    marginTop: 20,
  },
  taskCard: {
    width: 180,
    height: 220,
    borderRadius: 10,
    padding: 15,
    marginRight: 10,
  },
  pinkCard: {
    backgroundColor: '#f0c0c0',
  },
  blueCard: {
    backgroundColor: '#c0d8e8',
  },
  greenCard: {
    backgroundColor: '#c0e8c0',
  },
  cardTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  cardSubtitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 15,
  },
  cardDate: {
    fontSize: 16,
    marginBottom: 15,
  },
  cardStatus: {
    fontSize: 14,
  },
  cardMore: {
    fontSize: 14,
    marginTop: 20,
  },
  // 날씨 카드 스타일 추가
  weatherCardContent: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  weatherCardTitle: {
    fontSize: 14,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#333',
  },
  weatherCardTemp: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#2c5aa0',
    marginBottom: 5,
  },
  weatherCardDesc: {
    fontSize: 12,
    color: '#555',
    marginBottom: 8,
  },
  weatherCardDetail: {
    fontSize: 11,
    color: '#666',
    marginVertical: 1,
  },
});

export default MainScreen;
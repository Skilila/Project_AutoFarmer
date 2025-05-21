import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';

const API_KEY = 'YOUR_API_KEY'; // 발급되는대로 OpenWeatherMap API 키로 교체
const CITY = 'Seoul'; // 원하는 도시 이름, 부산대신 일단은 서울로

const MainScreen = () => {
  return (
    <View style={styles.container}>
      {/* 헤더1 */}
      <View style={styles.header}>
        <Text style={styles.headerText}>30일에 재배할 내 토마토 - 1</Text>
      </View>

      {/* 피쳐 헤더2 사용처가 정해지지 않는다면 삭제 */}
      <View style={styles.subHeader}>
        <Text style={styles.subHeaderText}>피쳐1</Text>
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

      {/* 날씨 */}
      <View style={styles.currentDateContainer}>
        <TouchableOpacity style={styles.currentDateButton}>
          <Text style={styles.currentDateText}>현재 날씨</Text>
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
  },
  currentDateText: {
    fontSize: 16,
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
});

export default MainScreen;
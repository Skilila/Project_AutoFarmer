import PushNotification from 'react-native-push-notification';

export function notifyIfRainTomorrow(tomorrowWeather: any) {
  const weatherMain = tomorrowWeather?.weather?.[0]?.main?.toLowerCase();
  if (weatherMain?.includes('rain')) {
    PushNotification.localNotification({
      title: '습도 장치를 끄거나 하우스를 밀실로 만드는게 좋습니다다 ☔',
      message: '내일은 비가 올 예정입니다!',
    });
  }
}
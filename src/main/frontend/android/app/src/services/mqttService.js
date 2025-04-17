import MQTT from 'sp-react-native-mqtt';

const MQTT_URI='mqtt://test.mosquitto.org:1883';

const SmartFarmWeather=()=>{
    useEffect(()=>{
        MQTT.createClient({
            uri:MQTT_URI,
            clientId:'smartfarm_weather_'+Math.random().toString(16).substr(2,8)
        }).then(async client=>{
            client.on('closed',()=>{
                console.log('mqtt.event.closed');
            });
            client.on('error',msg=>{
                console.log('mqtt.event.error',msg);
            });
            client.on('message',msg=>{
                console.log('mqtt.event.message',msg);
            });
            client.on('connect',async()=>{
                console.log('connected');
                client.subscribe('weather/kma',0);

                const weatherData=await fetchWeather();
                client.publish('weather/kma',JSON.stringify(weatherData),0,false);
            });
            client.connect();
        });
    }, []);

    return null;
};

export default SmartFarmWeather;
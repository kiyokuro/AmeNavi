package gr.jp.java_conf.kzstudio.amenavi.Util;

/**
 * Created by kiyokazu on 16/01/25.
 */
public class FutureWeather {
    private String _imgUrl;
    private String _weather;
    private String _rain;//降水確率、数字％
    private String _snow;//降雪確率、数字％
    private String _temperture;
    private String _cloudCover;
    private String _time;

    public FutureWeather(String imgUrl, String weather, String rain, String snow, String temperture, String cloudCover, String time){
        this._imgUrl = imgUrl;
        this._weather = weather;
        this._rain = rain;
        this._snow = snow;
        this._temperture = temperture;
        this._cloudCover = cloudCover;
        this._time = time;
    }

    public String get_imgUrl(){
        return _imgUrl;
    }

    public void set_imgUrl(String imgUrl){
        this._imgUrl = imgUrl;
    }

    public String get_weather() {
        return _weather;
    }

    public void set_weather(String weather) {
        this._weather = weather;
    }

    public String get_rain() {
        return _rain;
    }

    public void set_rain(String rain) {
        this._rain = rain;
    }

    public String get_snow() {
        return _snow;
    }

    public void set_snow(String snow) {
        this._snow = snow;
    }

    public String get_temperture() {
        return _temperture;
    }

    public void set_temperture(String temperture) {
        this._temperture = temperture;
    }

    public String get_cloudCover() {
        return _cloudCover;
    }

    public void set_cloudCover(String cloudCover) {
        this._cloudCover = cloudCover;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String time) {
        this._time = time;
    }
}

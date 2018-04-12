package com.example.gifo.testapplication.weatherService;

/**
 * Created by gifo on 12.04.2018.
 */

public class CurrentWeather {

    private static CurrentWeather instance = null;
    private CurrentWeather() {}

    public static final CurrentWeather getInstance() {
        instance = (instance!=null) ? instance : new CurrentWeather();
        return instance;
    }

    public void setData(String key, String data) {
        if (key.equals("...")) { }
    }

    public Coord coord = new Coord();
    public static class Coord {
        private String lon = "145.77";
        private String lat = "-16.92";

        public float getLon() { return Float.parseFloat(lon); }
        public float getLat() { return Float.parseFloat(lat); }
    }

    public Weather weather = new Weather();
    public static class Weather {
        private String id = "803";
        private String main = "Clouds";
        private String description = "brokeh clouds";
        private String icon = "04n";

        public int getId() { return Integer.parseInt(id); }
        public String getMain() { return main; }
        public String getDescription() { return description; }
        public String getIcon() { return icon; }
    }

    private String base = "cmc stations";
    public String getBase() { return base; }

    public Main main = new Main();
    public static class Main {
        private String temp = "293.25";
        private String pressure = "1019";
        private String humidity = "83";
        private String temp_min = "289.82";
        private String temp_max = "295.37";

        public float getTemp() { return Float.parseFloat(temp); }
        public int getPressure() { return Integer.parseInt(pressure); }
        public int getHumidity() { return Integer.parseInt(humidity); }
        public float getTempMin() { return Float.parseFloat(temp_min); }
        public float getTempMax() { return Float.parseFloat(temp_max); }
    }

    public Wind wind = new Wind();
    public static class Wind {
        private String speed = "5.1";
        private String deg = "150";

        public float getSpeed() { return Float.parseFloat(speed); }
        public float getDeg() { return Float.parseFloat(deg); }
    }

    public Clouds clouds = new Clouds();
    public static class Clouds {
        private String all = "75";

        public int getAll() { return Integer.parseInt(all); }
    }

    public Rain rain = new Rain();
    public static class Rain {
        private String _3h = "3";

        public int get3h() { return Integer.parseInt(_3h); }
    }

    private String dt = "1435658272";
    public long getDt() { return Long.parseLong(dt); }

    public Sys sys = new Sys();
    public static class Sys {
        private String type = "1";
        private String id = "8166";
        private String message = "0.0166";
        private String country = "AU";
        private String sunrise = "1435610796";
        private String sunset = "1435650870";

        public int getType() { return Integer.parseInt(type); }
        public int getId() { return Integer.parseInt(id); }
        public double getMessage() { return Double.parseDouble(message); }
        public String getCountry() { return country; }
        public long getSunrise() { return Long.parseLong(sunrise); }
        public long getSunset() { return Long.parseLong(sunset); }
    }

    private String id = "2172797";
    public long getId() { return Long.parseLong(id); }

    private String name = "Cairns";
    public String getName() { return name; }

    private String cod = "200";
    public int getCod() { return Integer.parseInt(cod); }
}

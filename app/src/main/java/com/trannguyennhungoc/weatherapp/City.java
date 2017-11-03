package com.trannguyennhungoc.weatherapp;

/**
 * Created by thuyb on 18-Sep-17.
 */

public class City {
    private int _Id;
    private String _Name;
    private String _Country;
    private Coord _Coord;

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public String get_Country() {
        return _Country;
    }

    public void set_Country(String _Country) {
        this._Country = _Country;
    }

    public Coord get_Coord() {
        return _Coord;
    }

    public void set_Coord(Coord _Coord) {
        this._Coord = _Coord;
    }

    public City(int _Id, String _Name, String _Country, Coord _Coord) {
        this._Id = _Id;
        this._Name = _Name;
        this._Country = _Country;
        this._Coord = _Coord;
    }
    public City() {
        this._Id = 0;
        this._Country = null;
        this._Name = null;
        this._Coord = new Coord();
    }

    protected static class Coord {
        private double _Longitude;
        private double _Latitude;

        public double get_Longitude() {
            return _Longitude;
        }

        public void set_Longitude(double _Longitude) {
            this._Longitude = _Longitude;
        }

        public double get_Latitude() {
            return _Latitude;
        }

        public void set_Latitude(double _Latitude) {
            this._Latitude = _Latitude;
        }

        public Coord(double _Longitude, double _Latitude) {
            this._Longitude = _Longitude;
            this._Latitude = _Latitude;
        }

        public Coord(){
            this._Latitude = 0;
            this._Longitude = 0;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("\n Longitude: " + this._Longitude);
            builder.append("\n Latitude: " + this._Latitude);
            return builder.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n Id: " + this._Id);
        builder.append("\n Name: " + this._Name);
        builder.append("\n Country: " + this._Country);
        builder.append("\n Coord: " + this._Coord.toString());
        return builder.toString();
    }
}

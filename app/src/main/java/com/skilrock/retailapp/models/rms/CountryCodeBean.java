package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryCodeBean {

    @SerializedName("Countries")
    @Expose
    private ArrayList<Country> countries = null;

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public static class Country {

        @SerializedName("code")
        @Expose
        private String code;

        @SerializedName("name")
        @Expose
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return "Country{" + "code='" + code + '\'' + ", name='" + name + '\'' + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "CountryCodeBean{" + "countries=" + countries + '}';
    }
}

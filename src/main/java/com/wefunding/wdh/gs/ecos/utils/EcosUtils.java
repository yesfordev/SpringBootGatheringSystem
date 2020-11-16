package com.wefunding.wdh.gs.ecos.utils;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yes on 2020/11/13
 */
public class EcosUtils {

    /**
     * [참고] JSONObject return type 추가
     * @param
     * @return
     * @throws IOException
     */
    public JSONObject connectUrlReturnObject(String urlstr) throws IOException {
        URL url = new URL(urlstr);

        JSONObject resObject;

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        resObject = (JSONObject) JSONValue.parse(br);

        urlConnection.disconnect();

        return resObject;
    }

}

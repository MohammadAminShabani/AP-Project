package divar.service;

import divar.config.AppConfig;
import divar.util.HttpClientUtil;

public class ApiService {

    protected String get(String endpoint) throws Exception{

        return HttpClientUtil.get(AppConfig.BASE_URL + endpoint);
    }

    protected String post(String endpoint,String json)
            throws Exception{

        return HttpClientUtil.post(AppConfig.BASE_URL + endpoint, json);
    }

}
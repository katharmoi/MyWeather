package com.kadirkertis.myweather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kadirkertis.myweather.forecastrestservice.ForecastApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Kadir Kertis on 1.8.2017.
 */

@Module
public class HttpModule {

    @MyWeatherAppScope
    @Provides
    public OkHttpClient provideClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }



    @MyWeatherAppScope
    @Provides
    public ObjectMapper provideObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        return objectMapper;
    }

    @MyWeatherAppScope
    @Provides
    public JacksonConverterFactory provideJacksonFactory(ObjectMapper objectMapper){
        return JacksonConverterFactory.create(objectMapper);
    }

    @MyWeatherAppScope
    @Provides
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @MyWeatherAppScope
    @Provides
    public Retrofit provideRetrofit(JacksonConverterFactory factory,RxJava2CallAdapterFactory rxFactory,OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/00fce851af6af69eba605c1a62f6744d/")
                .addCallAdapterFactory(rxFactory)
                .addConverterFactory(factory)
                .client(client)
                .build();
    }

    @MyWeatherAppScope
    @Provides
    public ForecastApi provideForecastApi(Retrofit retrofit){
        return  retrofit.create(ForecastApi.class);
    }
}

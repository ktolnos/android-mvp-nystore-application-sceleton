package com.evgeek.skeletonapplication.data.remote;


import com.evgeek.skeletonapplication.data.model.DataExample;
import com.evgeek.skeletonapplication.data.model.Token;
import com.evgeek.skeletonapplication.data.model.User;
import com.evgeek.skeletonapplication.di.ApplicationScoped;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

@ApplicationScoped
public class MockApi implements API {

    public static final String MOCK_API_ERROR_PREFIX = "MOCK_API_ERROR#";
    @Inject
    SimpleDateFormat simpleDateFormat;
    private String[] emails = {"user@example.com", "a@a.a"};
    private String[] passwords = {"123", "1"};
    private int responseTime = 3000;

    @Inject
    public MockApi() {
    }

    @Override
    public Single<Token> auth(String email, String password) {
        for (int i = 0; i < emails.length; i++) {
            if (emails[i].equals(email) && passwords[i].equals(password)) {
                Token token = new Token();
                token.setJwt(email + "###" + password);
                return Single.just(token).delay(responseTime, TimeUnit.MILLISECONDS);
            }
        }


        return Single
                .error(new Exception(MOCK_API_ERROR_PREFIX + 404))
                .delay(responseTime, TimeUnit.MILLISECONDS, true)
                .map(o -> new Token());
    }

    @Override
    public Single<User> getUserProfile(String bearer) {
        if (bearer == null || bearer.isEmpty() || !bearer.contains("###"))
            return Single.error(new Exception(MOCK_API_ERROR_PREFIX + 401))
                    .delay(responseTime, TimeUnit.MILLISECONDS, true)
                    .map(o -> new User());
        User user = new User();
        user.setEmail(bearer.substring(bearer.indexOf(' ') + 1, bearer.indexOf("###")));
        user.setName(user.getEmail().substring(0, user.getEmail().indexOf('@')) + " Pupkin");
        user.setId(0);
        return Single.just(user).delay(responseTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public Single<List<DataExample>> getExampleData(String page) {

        int size = 35;
        int resultsPerPage = 10;

        int pageInt;
        if (page == null || page.isEmpty()) {
            pageInt = 0;
        } else {
            pageInt = Integer.valueOf(page) - 1;
        }
        List<DataExample> l = new ArrayList<>(size);
        if (pageInt > size / resultsPerPage) {
            return Single.just(l).delay(responseTime, TimeUnit.MILLISECONDS);
        }
        for (int i = pageInt * resultsPerPage; i < (pageInt + 1) * resultsPerPage && i < size; i++) {
            l.add(createDataExample(i));
        }
        return Single.just(l).delay(responseTime, TimeUnit.MILLISECONDS);
    }


    private DataExample createDataExample(int i) {
        DataExample dataExample = new DataExample();
        dataExample.setSomeString("#" + i + " fetched at " + simpleDateFormat.format(new Date()));
        dataExample.setSomeInt(i * 10);
        dataExample.setSomeDouble((double) i / 100);
        String[] list = {i + "'s item 1", i + "'s item 2", i + "'s item 3"};
        dataExample.setSomeList(Arrays.asList(list));
        return dataExample;
    }
}

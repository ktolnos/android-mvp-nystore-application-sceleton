package com.evgeek.skeletonapplication.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by evgeek on 10/8/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScoped {
}

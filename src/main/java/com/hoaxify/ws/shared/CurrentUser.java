package com.hoaxify.ws.shared;

import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Target({PARAMETER})
@Retention(RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {

}

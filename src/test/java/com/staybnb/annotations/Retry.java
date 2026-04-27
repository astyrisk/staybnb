package com.staybnb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a test method or class for automatic retry on failure.
 * Method-level annotations take precedence over class-level ones.
 *
 * Usage:
 *   @Retry                    // retries once (2 total attempts)
 *   @Retry(maxAttempts = 3)   // retries twice (3 total attempts)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Retry {
    int maxAttempts() default 2;
}

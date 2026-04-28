package com.staybnb.extensions;

import com.staybnb.annotations.Retry;
import com.staybnb.config.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Retries flaky tests up to {@link Retry#maxAttempts()} times.
 *
 * Between attempts the driver is quit and @BeforeEach methods are re-run so
 * the browser starts fresh. The final exception is rethrown so
 * ScreenshotOnFailureExtension still captures a screenshot on last failure.
 */
public class RetryExtension implements InvocationInterceptor {
    private static final Logger log = LogManager.getLogger(RetryExtension.class);

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext context) throws Throwable {
        int maxAttempts = resolveMaxAttempts(context);

        Throwable lastFailure = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                if (attempt == 1) {
                    invocation.proceed();
                } else {
                    log.warn("[Retry {}/{}] Restarting browser and retrying: {}",
                            attempt, maxAttempts, context.getDisplayName());
                    DriverFactory.quitDriver();
                    runBeforeEachHierarchy(context);
                    invokeTestMethod(invocationContext, context);
                }
                return;
            } catch (Throwable t) {
                lastFailure = unwrap(t);
                if (attempt < maxAttempts) {
                    log.warn("[Retry {}/{}] Failed — {}: {}",
                            attempt, maxAttempts,
                            lastFailure.getClass().getSimpleName(), lastFailure.getMessage());
                }
            }
        }
        throw lastFailure;
    }

    private int resolveMaxAttempts(ExtensionContext context) {
        Retry onMethod = context.getRequiredTestMethod().getAnnotation(Retry.class);
        if (onMethod != null) return onMethod.maxAttempts();
        Retry onClass = context.getRequiredTestClass().getAnnotation(Retry.class);
        if (onClass != null) return onClass.maxAttempts();
        return 3;
    }

    private void runBeforeEachHierarchy(ExtensionContext context) throws Exception {
        Object instance = context.getRequiredTestInstance();
        for (Method m : collectBeforeEach(context.getRequiredTestClass())) {
            m.setAccessible(true);
            m.invoke(instance);
        }
    }

    /** Collects @BeforeEach methods from superclass down to the concrete class. */
    private List<Method> collectBeforeEach(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        if (clazz == null || clazz == Object.class) return methods;
        methods.addAll(collectBeforeEach(clazz.getSuperclass()));
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(BeforeEach.class)) {
                methods.add(m);
            }
        }
        return methods;
    }

    private void invokeTestMethod(ReflectiveInvocationContext<Method> invocationContext,
                                  ExtensionContext context) throws Throwable {
        Method method = invocationContext.getExecutable();
        method.setAccessible(true);
        try {
            method.invoke(context.getRequiredTestInstance(),
                    invocationContext.getArguments().toArray());
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private Throwable unwrap(Throwable t) {
        return (t instanceof InvocationTargetException ite && ite.getCause() != null)
                ? ite.getCause() : t;
    }
}

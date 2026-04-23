package com.staybnb.extensions;

import com.staybnb.config.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {
    private static final Logger log = LogManager.getLogger(ScreenshotOnFailureExtension.class);

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }
        WebDriver driver = DriverFactory.getDriver();
        if (!(driver instanceof TakesScreenshot ts)) {
            return;
        }
        try {
            byte[] bytes = ts.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    context.getDisplayName() + " - Failure Screenshot",
                    "image/png",
                    new ByteArrayInputStream(bytes),
                    ".png"
            );
            log.info("Screenshot attached to Allure report for: {}", context.getDisplayName());
        } catch (Exception e) {
            log.error("Failed to attach screenshot for: {}", context.getDisplayName(), e);
        }
    }
}

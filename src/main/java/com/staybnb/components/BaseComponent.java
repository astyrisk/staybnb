package com.staybnb.components;

import com.staybnb.core.SeleniumBase;
import org.openqa.selenium.WebDriver;

public abstract class BaseComponent extends SeleniumBase {
    protected BaseComponent(WebDriver driver) {
        super(driver);
    }
}

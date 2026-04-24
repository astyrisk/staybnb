package com.staybnb.tests.ui.createproperty;

import com.staybnb.data.MediaPaths;
import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;

abstract class CreatePropertyBaseTest extends BaseTest {
    protected CreatePropertyPage createPropertyPage;

    @BeforeEach
    public void setup() {
        createPropertyPage = new CreatePropertyPage(driver);
        loginAsUser();
    }

    protected void loadCreatePage() {
        createPropertyPage.navigateTo();
    }

    protected void goToStep2WithValidStep1() {
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
    }

    protected void goToStep3WithValidStep1AndStep2() {
        goToStep2WithValidStep1();
        createPropertyPage.fillValidStep2();
        createPropertyPage.clickNext();
    }

    protected void goToStep4WithValidStep1ToStep3() {
        goToStep3WithValidStep1AndStep2();
        createPropertyPage.clickNext();
    }

    protected void goToStep3() {
        loadCreatePage();
        goToStep3WithValidStep1AndStep2();
    }

    protected void goToStep4() {
        loadCreatePage();
        goToStep4WithValidStep1ToStep3();
    }

    protected void goToStep2AndBack() {
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
    }

    protected void goToStep3ThenBack() {
        goToStep3();
        createPropertyPage.clickBack();
    }

    protected void goToStep5WithValidStep1ToStep4() {
        goToStep4WithValidStep1ToStep3();
        createPropertyPage.clickNext();
    }

    protected void goToStep6WithValidStep1ToStep5() {
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.hasAtLeastNImagePreviews(2);
        createPropertyPage.clickNext();
    }
}

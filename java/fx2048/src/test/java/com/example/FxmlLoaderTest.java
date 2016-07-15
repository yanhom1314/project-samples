package com.example;

import javafx.application.Application;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FxmlLoaderTest {

    @Test
    public void testMessage() {
        assertTrue(true);
        Application.launch(DemoTestApp.class, new String[]{});
    }
}

package com.selenium.test;

import org.junit.*;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.monte.media.math.Rational;
import org.monte.media.Format;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScreenRecorderTest {

    private static WebDriver driver;
    private static ScreenRecorder screenRecorder;

    @BeforeClass
    public static void setUp() throws IOException, AWTException {

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                                      .getDefaultScreenDevice()
                                                      .getDefaultConfiguration();

        screenRecorder = new ScreenRecorder(gc,
                                            new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                                            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                                       CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                                                       DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
                                                       QualityKey, 1.0f,
                                                       KeyFrameIntervalKey, (int) (15 * 60)),
                                            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
                                                       FrameRateKey, Rational.valueOf(30)),
                                            null);
        
        //Uncomment to restrict the recording time to 60 seconds intervals 
        //screenRecorder.setMaxRecordingTime(60000);

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void cleanUp(){
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Before
    public void beforeTest() throws IOException {
        screenRecorder.start();
    }

    @After
    public void afterTest() throws IOException {
        screenRecorder.stop();
        List<File> createdMovieFiles = screenRecorder.getCreatedMovieFiles();
        int i=0;
        for(File movie : createdMovieFiles){
            i++;
            System.out.println("Recording " + i + ": " + movie.getAbsolutePath());
        }
    }

    @Test
    public void testApp() throws InterruptedException {
        driver.get("http://app.marinsoftware.com/");
        Thread.sleep(10000);
    }

    @Test
    public void testApp2() throws InterruptedException {
        driver.get("http://app2.marinsoftware.com/");
        Thread.sleep(10000);
    }
    
    @Test
    public void testAppleX() throws InterruptedException {
        driver.get("https://www.youtube.com/watch?v=mW6hFttt_KE");
        Thread.sleep(60000);//TODO: add actions instead
    }
    
}
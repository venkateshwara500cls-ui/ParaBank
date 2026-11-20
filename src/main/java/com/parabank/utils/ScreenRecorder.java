package com.parabank.utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenRecorder {

    private static final Logger log = LogManager.getLogger(ScreenRecorder.class);

    // ✅ Recording state
    private static boolean isRecording = false;
    private static Thread recordingThread;
    private static List<BufferedImage> frames = new ArrayList<>();
    private static String testName;

    // ✅ Folder to save screenshots/frames
    public static String recordingPath = System.getProperty("user.dir") + "/recordings/";

    // ✅ Start recording
    public static void startRecording(String name) throws AWTException {

        testName = name;
        frames.clear();
        isRecording = true;

        log.info("Starting screen recording: " + name);
        System.out.println("🎥 Recording started: " + name);

        // Create folder
        File folder = new File(recordingPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // ✅ Capture screenshots in background thread
        recordingThread = new Thread(() -> {
            try {
                Robot robot = new Robot();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle captureSize = new Rectangle(screenSize);

                while (isRecording) {
                    // Capture screenshot every 200ms = 5 frames per second
                    BufferedImage screenshot = robot.createScreenCapture(captureSize);
                    frames.add(screenshot);
                    Thread.sleep(200);
                }
            } catch (AWTException | InterruptedException e) {
                log.error("Recording error: " + e.getMessage());
            }
        });

        recordingThread.start();
        log.info("Recording thread started");
        System.out.println("✅ Recording is running...");
    }

    // ✅ Stop recording and save frames as images
    public static void stopRecording() throws IOException, InterruptedException {

        isRecording = false;

        // Wait for thread to finish
        if (recordingThread != null) {
            recordingThread.join();
        }

        log.info("Saving " + frames.size() + " frames...");
        System.out.println("🛑 Recording stopped - saving frames...");

        // ✅ Create folder with test name and timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String frameFolderPath = recordingPath + testName + "_" + timestamp + "/";

        File frameFolder = new File(frameFolderPath);
        if (!frameFolder.exists()) {
            frameFolder.mkdirs();
        }

        // ✅ Save each frame as PNG image
        for (int i = 0; i < frames.size(); i++) {
            File frameFile = new File(frameFolderPath + "frame_" + String.format("%04d", i) + ".png");
            ImageIO.write(frames.get(i), "PNG", frameFile);
        }

        log.info("Frames saved at: " + frameFolderPath);
        System.out.println("📁 Frames saved at: " + frameFolderPath);
        System.out.println("📊 Total frames captured: " + frames.size());
    }
}
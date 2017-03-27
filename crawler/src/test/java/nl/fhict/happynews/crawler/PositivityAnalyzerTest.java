package nl.fhict.happynews.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by daan_ on 27-3-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PositivityAnalyzerTest {


    private static final String text = "When pararescuer Mike Maroney of the U.S. Air Force picked up LaShay and her family from the waters of Hurricane Katrina in 2005, the little girl gave her rescuer a big hug that he never forgot.\n" +
            "Katrina girl hug takes rescuer to dance\n" +
            "Maroney, who was battling PTSD at the time of LaShay's rescue, carried the photo with him on difficult deployments to Iraq and Afghanistan, to remind him of that special moment.\n" +
            "Eventually, he tried to find the little girl who changed his life, even launching a #FindKatrinaGirl social media campaign.\n" +
            "Finally, 10 years after the storm, the two were reunited on the set of a TV show.\n" +
            "Upon their reunion, Maroney told LaShay, \"You rescued me more than I rescued you.\"\n" +
            "Since that day, the former pararescuer has visited the Brown family in Mississippi, even teaching LaShay how to swim, and has spoken to LaShay on the phone weekly.\n" +
            "Maroney's encouragement has been so important to LaShay that she decided to join the Junior Reserve Officer Training Corps or (JROTC), a pre-training program sponsored by the United States Armed Forces at Bay High School in Waveland, Mississippi.\n" +
            "This Saturday, LaShay, 14, will bring Maroney to her Junior ROTC ball.\n" +
            "\"I'm going because I would do anything to repay the hug to LaShay and her family,\" Maroney told People. \"They mean as much to me as my own.\"";


    @Autowired
    PositivityAnalyzer positivityAnalyzer;

    @Test
    public void testAnalyzeText(){
        int score = positivityAnalyzer.analyzeText(text);
        System.out.println("Score"+score);
    }
}

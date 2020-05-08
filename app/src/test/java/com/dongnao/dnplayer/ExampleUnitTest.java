package com.dongnao.dnplayer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        System.load("/Users/xiang/Library/Developer/Xcode/DerivedData/JniLibrary-caxsgjxymjrfjjgzzqtiebbhqfrq/Build/Products/Debug/libJniLibrary.dylib");
        System.out.println(test(200));
    }


    native String test(int i);

}
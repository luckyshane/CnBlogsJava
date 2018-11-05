package com.luckyshane.cnblogs;

import com.luckyshane.cnblogs.util.Parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test() {
        String url = "http://images0.cnblogs.com/news_topic///images0.cnblogs.com/news_topic/200782215744993.gif";
        String url2 = "http://images0.cnblogs.com/news_topic/https://img2018.cnblogs.com/news_topic/20181019121101783-952728829.gif";
        assertEquals(Parser.parseImageUrl(url), "http://images0.cnblogs.com/news_topic/200782215744993.gif");
        assertEquals(Parser.parseImageUrl(url2), "https://img2018.cnblogs.com/news_topic/20181019121101783-952728829.gif");
    }

}
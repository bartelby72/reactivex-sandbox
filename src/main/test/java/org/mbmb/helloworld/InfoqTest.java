package org.mbmb.helloworld;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @see https://www.infoq.com/articles/Testing-RxJava2
 */
public class InfoqTest {
    private static final List<String> WORDS = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");

    @Test
    public void testInSameThread() {
        // given:
        List<String> results = new ArrayList<>();
        Observable<String> observable = Observable.fromIterable(WORDS)
            .zipWith(Observable.range(1, Integer.MAX_VALUE),
                (string, index) -> String.format("%2d. %s", index, string));

        // when:
        observable.subscribe(results::add);

        // then:
        assertFalse(results.isEmpty());
        assertEquals(WORDS.size(), results.size());
        assertTrue(results.contains(" 4. fox"));
    }
}

package org.mbmb.helloworld;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * from https://www.baeldung.com/rx-java
 */
public class BasicObservableTest {
    private String result;

    @BeforeEach
    public void setup() {
        this.result = "";
    }

    @AfterEach
    public void tearDown() {
        this.result = null;
    }

    @Test
    public void hello() {
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> result = s);

        assertTrue(result.equals("Hello"));
    }

    @Test
    public void methodBreakout() {
        Observable<String> observable = Observable.just("a", "b", "c", "d", "e", "f", "g");
        observable.subscribe(
            i -> result += i,  //OnNext
            Throwable::printStackTrace, //OnError
            () -> result += "_Completed" //OnCompleted
        );
        assertTrue(result.equals("abcdefg_Completed"));
    }

    @Test
    public void map() {
        Observable.just("a", "b", "c", "d", "e", "f", "g")
            .map(String::toUpperCase)
            .subscribe(letter -> result += letter);
        assertTrue(result.equals("ABCDEFG"));
    }

    /**
     * https://medium.com/appunite-edu-collection/rxjava-flatmap-switchmap-and-concatmap-differences-examples-6d1f3ff88ee0
     */
    @Test
    public void flatmap() {
//        final TestScheduler scheduler = new TestScheduler();
//
//        Observable.just("a", "b", "c", "d", "e", "f", "g")
//            .flatMap(s -> {
//                final int delay = new Random().nextInt(10);
//                return Observable.just(s + "x")
//                    .delay(delay, TimeUnit.SECONDS, scheduler);
//            })
//            .toList()
//            .doOnNext(l -> {
//                l.sort(String::compareTo);
//                l.forEach(s -> result += s);
//            })
//            .subscribe();
//
//        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
//        assertEquals("axbxcxdxexfx", result);
    }

    /**
     * https://medium.com/appunite-edu-collection/rxjava-flatmap-switchmap-and-concatmap-differences-examples-6d1f3ff88ee0
     */
    @Test
    public void switchMap() {
//        final List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f");
//
//        final TestScheduler scheduler = new TestScheduler();
//
//        Observable.from(items)
//            .switchMap(s -> {
//                final int delay = new Random().nextInt(10);
//                return Observable.just(s + "x")
//                    .delay(delay, TimeUnit.SECONDS, scheduler);
//            })
//            .toList()
//            .doOnNext(l -> {
//                l.sort(String::compareTo);
//                l.forEach(s -> result += s);
//            })
//            .subscribe();
//
//        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
//        assertEquals("fx", result);
    }

    /**
     * https://medium.com/appunite-edu-collection/rxjava-flatmap-switchmap-and-concatmap-differences-examples-6d1f3ff88ee0
     */
    @Test
    public void concatMap() {
//        final List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f");
//
//        final TestScheduler scheduler = new TestScheduler();
//
//        Observable.from(items)
//            .concatMap(s -> {
//                final int delay = new Random().nextInt(10);
//                return Observable.just(s + "x")
//                    .delay(delay, TimeUnit.SECONDS, scheduler);
//            })
//            .toList()
//            .doOnNext(l -> l.forEach(s -> result += s))
//            .subscribe();
//
//        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
//        assertEquals("axbxcxdxexfx", result);
    }

    @Test
    public void scan() {
//        String[] letters = {"a", "b", "c"};
//        Observable.from(letters)
//            .scan(new StringBuilder(), StringBuilder::append)
//            .subscribe(total -> result += total.toString());
//        assertTrue(result.equals("aababc"));
    }

    @Test
    public void filterOdds() {
//        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//        Observable.from(numbers)
//            .filter(i -> (i % 2 == 1))
//            .subscribe(i -> result += i);
//
//        assertTrue(result.equals("13579"));
    }
}

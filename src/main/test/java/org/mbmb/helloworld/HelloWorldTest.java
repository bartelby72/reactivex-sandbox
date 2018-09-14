package org.mbmb.helloworld;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rx.Observable;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldTest {
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
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
        Observable<String> observable = Observable.from(letters);
        observable.subscribe(
            i -> result += i,  //OnNext
            Throwable::printStackTrace, //OnError
            () -> result += "_Completed" //OnCompleted
        );
        assertTrue(result.equals("abcdefg_Completed"));
    }
}

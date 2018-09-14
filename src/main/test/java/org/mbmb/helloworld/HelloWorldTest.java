package org.mbmb.helloworld;

import org.junit.jupiter.api.Test;
import rx.Observable;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldTest {

    @Test
    public void hello() {
        final String[] result = {""};
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> result[0] = s);

        assertTrue(result[0].equals("Hello"));
    }
}

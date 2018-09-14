package org.mbmb.vogella;

import rx.Observable;

public class Mbmb {
    public static void main(String[] args) {
//		Observable<String> observer = Observable.just("Hello");
//		observer.subscribe(System.out::println);

        Observable<String> observable = Observable.just("hello", "world");
        observable.subscribe(System.out::println);
    }
}

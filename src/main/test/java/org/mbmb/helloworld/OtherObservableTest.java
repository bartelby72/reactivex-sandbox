package org.mbmb.helloworld;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherObservableTest {

    @Test
    public void connectableObservable() throws InterruptedException {
        final String[] result = {""};
        ConnectableObservable<Long> connectable
            = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        connectable.subscribe(i -> result[0] += i);
        assertFalse(result[0].equals("01"));

        connectable.connect();
        Thread.sleep(500);

        assertTrue(result[0].equals("01"));
    }

    Integer subscriber1 = 0;
    Integer subscriber2 = 0;

    Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                subscriber1 += value;
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onComplete() {

            }
        };
    }

    Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                subscriber2 += value;
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Test
    public void subscriber() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(getFirstObserver());
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.subscribe(getSecondObserver());
        subject.onNext(4);
        subject.onComplete();

        assertTrue(subscriber1 + subscriber2 == 14);
    }

    @Test
    public void resourceUsing() {
        String[] result = {""};
        Observable<Character> values = Observable.using(
            () -> "MyResource",
            r -> Observable.create(o -> {
                for (Character c : r.toCharArray()) {
                    o.onNext(c);
                }
                o.onComplete();
            }),
            r -> System.out.println("Disposed: " + r)
        );
        values.subscribe(
            v -> result[0] += v,
            e -> result[0] += e
        );
        assertTrue(result[0].equals("MyResource"));
    }
}

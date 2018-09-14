package org.mbmb.backpressure;

import java.util.stream.IntStream;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class Driver {

    public static void coldObservable() throws InterruptedException {
        Observable.range(1, 10)
            .observeOn(Schedulers.computation())
            .subscribe(ComputeFunction::compute);
        Thread.sleep(11000);
    }

    public static void hotObservable() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.observeOn(Schedulers.computation())
            .subscribe(ComputeFunction::compute, Throwable::printStackTrace);

        IntStream.range(1, 10).forEach(i -> {
            source.onNext(i);
            System.out.println(i);
        });

        Thread.sleep(11000);
    }

    public static void main(String[] args) throws InterruptedException {
        coldObservable();
//        hotObservable();
    }
}

package org.mbmb.backpressure;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.stream.IntStream;

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

    public static void bufferbatch() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.buffer(2)
            .observeOn(Schedulers.computation())
            .subscribe(ComputeFunction::computeList, Throwable::printStackTrace);
        IntStream.range(1, 10).forEach(i -> source.onNext(i));
        Thread.sleep(5000);
    }

    public static void bufferOverflowbatch() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.buffer(2)
            .observeOn(Schedulers.computation())
            .subscribe(ComputeFunction::slowComputeList, Throwable::printStackTrace);
        IntStream.range(1, 1_000_000).forEach(i -> source.onNext(i));
        Thread.sleep(5000);
    }

    public static void main(String[] args) throws InterruptedException {
//        coldObservable();
//        hotObservable();
//        bufferbatch();
        bufferOverflowbatch();
    }

}

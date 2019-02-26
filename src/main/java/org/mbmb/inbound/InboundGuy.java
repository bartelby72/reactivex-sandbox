package org.mbmb.inbound;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class InboundGuy {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(2);
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        Observable<Integer> observeMe = Observable.<Integer>create(emitter -> {
                integers.forEach(emitter::onNext);
                emitter.onComplete();
            }
        )
//            .observeOn(Schedulers.newThread())
//            .subscribeOn(Schedulers.newThread())
            .doOnError(Throwable::printStackTrace)
//            .share()
            ;
//        Observable<Integer> observeMe = Observable.just(1, 2, 3, 4, 5)
////            .observeOn(Schedulers.io())
//            .doOnError(Throwable::printStackTrace)
//            .share();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observeMe
//                .observeOn(Schedulers.newThread())
                .doOnNext(integer -> {
                    final String threadName = "d0.thread";
                    Thread.currentThread().setName(threadName);
                    System.out.println(Thread.currentThread().getName() + ": " + integer);
                    System.out.flush();
                })
                .doOnComplete(done::countDown)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
        );
        compositeDisposable.add(observeMe
//                .observeOn(Schedulers.newThread())
                .doOnNext(integer -> {
                    final String threadName = "d1.thread";
                    Thread.currentThread().setName(threadName);
                    System.out.println(Thread.currentThread().getName() + ": " + integer);
                    System.out.flush();
                })
                .doOnComplete(done::countDown)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
        );
        done.await(3, TimeUnit.SECONDS);
    }
}

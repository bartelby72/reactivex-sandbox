package org.mbmb.inbound;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class InboundGuy {

    private void testWithCountdownLatch() throws InterruptedException {
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

    private void testObserveOn() {
        Observable.create(emitter -> {
            Arrays.asList(1, 2, 3, 4, 5)
                .forEach(i -> {
                    System.err.println(Thread.currentThread().getName() + ": testObserveOn: emitting: " + i);
                    emitter.onNext(i);
                });
        })
            .observeOn(Schedulers.newThread())
            .doOnNext(o -> {
                System.err.println(Thread.currentThread().getName() + ": testObserveOn: onNext: " + o);
            })
            .subscribe();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSubjectObserveOn() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        publishSubject.observeOn(Schedulers.newThread())
            .doOnNext(o -> {
                System.err.println(Thread.currentThread().getName() + ": testSubjectObserveOn: onNext: " + o);
            })
            .subscribe();
        integers.forEach(i -> {
            System.err.println(Thread.currentThread().getName() + ": testSubjectObserveOn: emitting: " + i);
            publishSubject.onNext(i);
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSubscribeOn() {
        Observable.create(emitter -> {
            Arrays.asList(1, 2, 3, 4, 5)
                .forEach(i -> {
                    System.err.println(Thread.currentThread().getName() + ": testSubscribeOn: emitting: " + i);
                    emitter.onNext(i);
                });
        })
            .subscribeOn(Schedulers.newThread())
            .doOnNext(o -> {
                System.err.println(Thread.currentThread().getName() + ": testSubscribeOn: onNext: " + o);
            })
            .subscribe();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSubjectSubscribeOn() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        publishSubject.subscribeOn(Schedulers.newThread())
            .doOnNext(o -> {
                System.err.println(Thread.currentThread().getName() + ": testSubjectSubscribeOn: onNext: " + o);
            })
            .subscribe();
        integers.forEach(i -> {
            System.err.println(Thread.currentThread().getName() + ": testSubjectSubscribeOn: emitting: " + i);
            publishSubject.onNext(i);
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InboundGuy inboundGuy = new InboundGuy();

//        inboundGuy.testWithCountdownLatch();
        inboundGuy.testObserveOn();
        inboundGuy.testSubjectObserveOn();
        inboundGuy.testSubscribeOn();
        inboundGuy.testSubjectSubscribeOn();
    }
}

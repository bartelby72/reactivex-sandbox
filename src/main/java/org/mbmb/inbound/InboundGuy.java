package org.mbmb.inbound;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("ALL")
public class InboundGuy {
    private final static int OBS_COUNT = 5;

    private void testObserveOn() {
        final AtomicInteger createCount = new AtomicInteger(0);
        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            createCount.getAndIncrement();
            Arrays.asList(1, 2, 3, 4, 5)
                .forEach(i -> {
                    System.err.println(Thread.currentThread().getName() + "<" + createCount.get() + ">: testObserveOn: emitting: " + i);
                    emitter.onNext(i);
                });
        })
            .observeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv + 1;
            observable
                .doOnSubscribe(disposable -> System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testObserveOn: onSubscribe"))
                .doOnNext(o -> {
                    System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testObserveOn: onNext: " + o);
                })
                .subscribe();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSubjectObserveOn() {
        final AtomicInteger createCount = new AtomicInteger(0);
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Observable<Integer> observable = publishSubject.observeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv + 1;
            observable
                .doOnSubscribe(disposable -> System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubjectObserveOn: onSubscribe"))
                .doOnNext(o -> {
                    System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubjectObserveOn: onNext: " + o);
                })
                .subscribe();
        }
        //
        createCount.getAndIncrement();
        integers.forEach(i -> {
            System.err.println(Thread.currentThread().getName() + "<" + createCount.get() + ">: testSubjectObserveOn: emitting: " + i);
            publishSubject.onNext(i);
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSubscribeOn() {
        final AtomicInteger createCount = new AtomicInteger(0);
        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            createCount.getAndIncrement();
            Arrays.asList(1, 2, 3, 4, 5)
                .forEach(i -> {
                    System.err.println(Thread.currentThread().getName() + "<" + createCount.get() + ">: testSubscribeOn: emitting: " + i);
                    emitter.onNext(i);
                });
        })
            .subscribeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv + 1;
            observable
                .doOnSubscribe(disposable -> System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubscribeOn: onSubscribe"))
                .doOnNext(o -> {
                    System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubscribeOn: onNext: " + o);
                })
                .subscribe();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testSubjectSubscribeOn() {
        final AtomicInteger createCount = new AtomicInteger(0);
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Observable<Integer> observable = publishSubject.subscribeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv + 1;
            observable
                .doOnSubscribe(disposable -> System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubjectSubscribeOn: onSubscribe"))
                .doOnNext(o -> {
                    System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubjectObserveOn: onNext: " + o);
                })
                .subscribe();
        }
        //
        createCount.getAndIncrement();
        integers.forEach(i -> {
            System.err.println(Thread.currentThread().getName() + "<" + createCount.get() + ">: testSubjectSubscribeOn: emitting: " + i);
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

        inboundGuy.testObserveOn();
        System.err.println("///////////////////////////////////////////////////////////////////////////////////////////");
        inboundGuy.testSubjectObserveOn();
        System.err.println("///////////////////////////////////////////////////////////////////////////////////////////");
        inboundGuy.testSubscribeOn();
        System.err.println("///////////////////////////////////////////////////////////////////////////////////////////");
        inboundGuy.testSubjectSubscribeOn();
    }
}

package org.mbmb.inbound;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class InboundGuy {
    private final static int OBS_COUNT = 5;

    private void testObserveOn() {
        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            Arrays.asList(1, 2, 3, 4, 5)
                .forEach(i -> {
                    System.err.println(Thread.currentThread().getName() + ": testObserveOn: emitting: " + i);
                    emitter.onNext(i);
                });
        })
            .observeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv;
            observable
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
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Observable<Integer> observable = publishSubject.observeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv;
            observable
                .doOnNext(o -> {
                    System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubjectObserveOn: onNext: " + o);
                })
                .subscribe();
        }
        //
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
        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            Arrays.asList(1, 2, 3, 4, 5)
                .forEach(i -> {
                    System.err.println(Thread.currentThread().getName() + ": testSubscribeOn: emitting: " + i);
                    emitter.onNext(i);
                });
        })
            .subscribeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv;
            observable
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
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Observable<Integer> observable = publishSubject.subscribeOn(Schedulers.newThread());
        //
        for (int lcv = 0; lcv < OBS_COUNT; lcv++) {
            int flcv = lcv;
            observable
                .doOnNext(o -> {
                    System.err.println(Thread.currentThread().getName() + "[" + flcv + "]: testSubjectObserveOn: onNext: " + o);
                })
                .subscribe();
        }
        //
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

        inboundGuy.testObserveOn();
        System.err.println("/////////////////////////////////////////////////");
        inboundGuy.testSubjectObserveOn();
        System.err.println("/////////////////////////////////////////////////");
        inboundGuy.testSubscribeOn();
        System.err.println("/////////////////////////////////////////////////");
        inboundGuy.testSubjectSubscribeOn();
    }
}

package org.mbmb.simple;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Driver {
    static Integer subscriber1 = 0;
    static Integer subscriber2 = 0;

    static Observer<Integer> getFirstObserver() {
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

    static Observer<Integer> getSecondObserver() {
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

    public static void main(String[] args) throws InterruptedException {
//		PublishSubject<Integer> subject = PublishSubject.create();
//		subject.subscribe(getFirstObserver());
//		subject.onNext(1);
//		subject.onNext(2);
//		subject.onNext(3);
//		subject.subscribe(getSecondObserver());
//		subject.onNext(4);
//		subject.onCompleted();
        String[] result = {""};
        Observable<Character> values = Observable.using(
            () -> "MyResource",
            r -> Observable.create(o -> {
                for (Character c : r.toCharArray()) {
                    o.onNext(c);
                }
            }),
            r -> System.out.println("Disposed: " + r)
        );
        values.subscribe(
            v -> result[0] += v,
            e -> result[0] += e
        );
        System.out.println(result[0]);
    }
}

package org.mbmb.simple;

import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;

public class Driver {

	public static void main(String[] args) {
		AtomicReference<String> result = new AtomicReference<>("");
		Observable<String> observer = Observable.just("Hello");
		observer.subscribe(result::set);
		System.out.println(result);
	}
}

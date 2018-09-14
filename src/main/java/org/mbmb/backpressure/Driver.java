package org.mbmb.backpressure;

import java.util.stream.IntStream;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class Driver {

	public static void main(String[] args) {
//		Observable.range(1, 10)
//				.observeOn(Schedulers.computation())
//				.subscribe(ComputeFunction::compute);
		PublishSubject<Integer> source = PublishSubject.<Integer>create();

		source.observeOn(Schedulers.computation())
				.subscribe(ComputeFunction::compute, Throwable::printStackTrace);

		IntStream.range(1, 1_000_000).forEach(source::onNext);
	}
}

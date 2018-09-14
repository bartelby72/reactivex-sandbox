package org.mbmb.transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class StringToInteger implements ObservableTransformer<String, Integer> {

    @Override
    public ObservableSource<Integer> apply(Observable<String> upstream) {
        return upstream.map(StringToInteger::transform);
    }

    private static Integer transform(final String integerString) {
        try {
            return Integer.parseInt(integerString);
        } catch (NumberFormatException e) {
            return (-1);
        }
    }

    public static void main(String[] args) {
        final int[] result = {0};
        Observable.just("1", "2", "3")
            .compose(new StringToInteger())
            .subscribe(s -> result[0] += s);
        System.out.println(result[0]);
    }
}

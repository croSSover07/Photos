package developer.com.photos.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({Order.LATEST, Order.OLDEST, Order.POPULAR})
public @interface Order {
    String LATEST = "latest";
    String OLDEST = "oldest";
    String POPULAR = "popular";
}
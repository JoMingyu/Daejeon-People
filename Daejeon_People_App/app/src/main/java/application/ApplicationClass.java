package application;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by JoMingyu on 2017-07-05.
 */

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumSquareR.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumSquareB.ttf"));
    }
}

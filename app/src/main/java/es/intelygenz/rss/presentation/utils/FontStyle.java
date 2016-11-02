package es.intelygenz.rss.presentation.utils;

import es.intelygenz.rss.R;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public enum FontStyle {

    Small(R.style.FontStyle_Small, "Small"),
    Medium(R.style.FontStyle_Medium, "Medium"),
    Large(R.style.FontStyle_Large, "Large"),
    ExtraLarge(R.style.FontStyle_ExtraLarge, "Extra Large");

    private int resId;
    private String title;

    public int getResId() {
        return resId;
    }

    public String getTitle() {
        return title;
    }

    FontStyle(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

}

package com.teachonmars.modules.widget.circularImageView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;

class CustomDrawable extends InsetDrawable {
    public CustomDrawable(Drawable baseDrawable, int inset) {
        super(baseDrawable, inset);
    }
}

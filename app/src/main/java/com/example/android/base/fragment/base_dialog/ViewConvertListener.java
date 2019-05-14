package com.example.android.base.fragment.base_dialog;

import java.io.Serializable;

public interface ViewConvertListener extends Serializable {
    long serialVersionUID = System.currentTimeMillis();

    void convertView(ViewHolder holder, BaseDialog dialog);
}

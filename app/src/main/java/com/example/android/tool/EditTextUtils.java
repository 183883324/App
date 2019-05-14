package com.example.android.tool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.imview.PowerfulEditText;

public class EditTextUtils {

    /**
     * ditText 屏蔽选择、复制、粘贴等一切剪切板的操作
     *
     * @param EditText
     * @return
     */
    public static void EditTextUtils(PowerfulEditText EditText) {

        EditText.setLongClickable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // call that method
            EditText.setCustomInsertionActionModeCallback(new ActionModeCallbackInterceptor());
        }
    }
    public static class ActionModeCallbackInterceptor implements ActionMode.Callback, android.view.ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }


        public void onDestroyActionMode(ActionMode mode) {
        }

        @Override
        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode mode) {

        }
    }
}


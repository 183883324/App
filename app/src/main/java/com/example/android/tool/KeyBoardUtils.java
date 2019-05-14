package com.example.android.tool;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * 软件盘操作
 * */
public class KeyBoardUtils {
    /**
     * 打开软件盘
     */
    public static void openKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软件盘
     */
    public static void closeKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    public static int scrollToPosition = 0;

    public static void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

}

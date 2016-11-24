package yu.jishang.com.imageadd;

import android.view.View;
import android.widget.GridView;

/**
 * 图片九宫格
 *
 * @author wangliang Jul 27, 2016
 */
public class GridViewNonRolling extends GridView {
    private boolean isOnMeasure;

    public GridViewNonRolling(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

    public boolean isOnMeasure() {
        return isOnMeasure;
    }


}

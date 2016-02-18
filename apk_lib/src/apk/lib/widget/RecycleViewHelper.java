package apk.lib.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class RecycleViewHelper {
	/**
     * 获取第一条展示的位置
     *
     * @return
     */
    public static int getFirstVisiblePosition(RecyclerView rv) {
        int position;
        if (rv.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) rv.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (rv.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager)rv.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (rv.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)rv.getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private static int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    public static int getLastVisiblePosition(RecyclerView rv) {
        int position;
        if (rv.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
        } else if (rv.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
        } else if (rv.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) rv.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = rv.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private static int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }
}

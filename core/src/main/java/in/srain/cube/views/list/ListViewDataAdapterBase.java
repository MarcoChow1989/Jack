package in.srain.cube.views.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import in.srain.cube.util.CLog;

import java.util.HashSet;

/**
 * A adapter using View Holder to display the item of a list view;
 *
 * @param <ItemDataType>
 * @author http://www.liaohuqiu.net
 */
public abstract class ListViewDataAdapterBase<ItemDataType> extends BaseAdapter {

    private static String LOG_TAG = "cube_list";

    protected ViewHolderCreator<ItemDataType> mViewHolderCreator;
    protected HashSet<Integer> mCreatedTag = new HashSet<Integer>();
    private boolean mEnableCreateViewForMeasure = true;

    /**
     * @param viewHolderCreator The view holder creator will create a View Holder that extends {@link ViewHolderBase}
     */
    public ListViewDataAdapterBase(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        mViewHolderCreator = viewHolderCreator;
    }

    public void setEnableCreateViewForMeasure(boolean enable) {
        mEnableCreateViewForMeasure = enable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mEnableCreateViewForMeasure && convertView == null) {

        }
        if (CLog.DEBUG_LIST) {
            Log.d(LOG_TAG, String.format("getView %s", position));
        }
        ItemDataType itemData = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ViewHolderBase<ItemDataType> holderBase = mViewHolderCreator.createViewHolder();

            if (holderBase != null) {
                convertView = holderBase.createView(inflater);
                if (convertView != null) {
                    holderBase.setItemData(position);
                    holderBase.showData(position, itemData);
                    convertView.setTag(holderBase);
                }
            }
        } else {
            ViewHolderBase<ItemDataType> holderBase = (ViewHolderBase<ItemDataType>) convertView.getTag();
            if (holderBase != null) {
                holderBase.setItemData(position);
                holderBase.showData(position, itemData);
            }
        }
        return convertView;
    }

    @Override
    public abstract ItemDataType getItem(int position);
}

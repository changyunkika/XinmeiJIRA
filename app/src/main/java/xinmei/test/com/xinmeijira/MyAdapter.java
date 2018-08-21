package xinmei.test.com.xinmeijira;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    private ListBean bean;
    /**
     *
     * */
    private static RecyclerView.Adapter Adapter;
    private static OnItemClickListener mOnItemClickListener;

    //设置点击和长按事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position); //设置点击事件

        void onItemLongClick(View view, int position); //设置长按事件
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public MyAdapter(Context context, ListBean bean) {
        this.context = context;
        this.bean = bean;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.mBug_id.setText(bean.getIssues().get(position).getKey() + "");
        holder.mBug_time.setText(bean.getIssues().get(position).getFields().getCreated() + "");
        holder.mBug_context.setText(bean.getIssues().get(position).getFields().getSummary() + "");
        holder.mBug_staff.setText(bean.getIssues().get(position).getFields().getAssignee().getDisplayName() + "");
        holder.mBug_status.setText(bean.getIssues().get(position).getFields().getStatus().getName() + "");
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() { //itemview是holder里的所有控件，可以单独设置比如ImageButton Button等
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { //长按事件
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bean.getIssues().size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mBug_id, mBug_time, mBug_context, mBug_staff, mBug_status;

        public MyHolder(View itemView) {
            super(itemView);
            mBug_id = (TextView) itemView.findViewById(R.id.bug_id);
            mBug_time = (TextView) itemView.findViewById(R.id.bug_time);
            mBug_context = (TextView) itemView.findViewById(R.id.bug_context);
            mBug_staff = (TextView) itemView.findViewById(R.id.bug_staff);
            mBug_status = (TextView) itemView.findViewById(R.id.bug_status);
        }
    }
}
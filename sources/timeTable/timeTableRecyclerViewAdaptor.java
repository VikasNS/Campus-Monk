package timeTable;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.campusmonk.vikas.msrit.C0418R;
import java.util.ArrayList;

public class timeTableRecyclerViewAdaptor extends Adapter<viewHolder> {
    ArrayList<timeTableVariable> arrayList;

    public class viewHolder extends ViewHolder {
        TextView C1;
        TextView C2;
        TextView C3;

        public viewHolder(View itemView) {
            super(itemView);
            this.C1 = (TextView) itemView.findViewById(C0418R.id.C1);
            this.C2 = (TextView) itemView.findViewById(C0418R.id.C2);
            this.C3 = (TextView) itemView.findViewById(C0418R.id.C3);
        }
    }

    public timeTableRecyclerViewAdaptor(ArrayList<timeTableVariable> arrayList) {
        this.arrayList = arrayList;
    }

    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(C0418R.layout.timetable_recycler_view_adapter, parent, false));
    }

    public void onBindViewHolder(viewHolder holder, int position) {
        holder.C1.setText(((timeTableVariable) this.arrayList.get(position)).getC1());
        holder.C2.setText(((timeTableVariable) this.arrayList.get(position)).getC2());
        holder.C3.setText(((timeTableVariable) this.arrayList.get(position)).getC3());
    }

    public int getItemCount() {
        return this.arrayList.size();
    }
}

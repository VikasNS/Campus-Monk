package events;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.campusmonk.vikas.msrit.C0418R;
import java.util.ArrayList;

public class events_recycler_view_adapter extends Adapter<viewHolder> {
    ArrayList<events_variable> arrayList;

    public class viewHolder extends ViewHolder {
        TextView date;
        TextView day;
        TextView title;

        public viewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(C0418R.id.date);
            this.day = (TextView) itemView.findViewById(C0418R.id.day);
            this.title = (TextView) itemView.findViewById(C0418R.id.title);
        }
    }

    public events_recycler_view_adapter(ArrayList<events_variable> arrayList) {
        this.arrayList = arrayList;
    }

    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(C0418R.layout.events_recycler_view_layout, parent, false));
    }

    public void onBindViewHolder(viewHolder holder, int position) {
        holder.date.setText(((events_variable) this.arrayList.get(position)).getDate());
        holder.day.setText(((events_variable) this.arrayList.get(position)).getDay());
        holder.title.setText(((events_variable) this.arrayList.get(position)).getTitle());
    }

    public int getItemCount() {
        return this.arrayList.size();
    }
}

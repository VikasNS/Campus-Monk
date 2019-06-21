package notes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.campusmonk.vikas.msrit.C0418R;
import com.campusmonk.vikas.msrit.DownloadIntentService;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import sell.Sell_notes_form;

public class Notes_recycler_view_adaptor extends Adapter<my_place_holder> {
    Activity activity;
    ArrayList<Notes_variables_template> arrayList;
    CheckNetwork checkNetwork;
    Editor editor = this.sharedPreferences.edit();
    SharedPreferences sharedPreferences;
    String subject_name;

    public class my_place_holder extends ViewHolder {
        Button download_button;
        Button sell_button;
        TextView unit_name;

        public my_place_holder(View itemView) {
            super(itemView);
            this.unit_name = (TextView) itemView.findViewById(C0418R.id.unit_name);
            this.download_button = (Button) itemView.findViewById(C0418R.id.download_button);
            this.sell_button = (Button) itemView.findViewById(C0418R.id.sell_button);
            this.sell_button.setOnClickListener(new OnClickListener(Notes_recycler_view_adaptor.this) {
                public void onClick(View view) {
                    Intent intent = new Intent(Notes_recycler_view_adaptor.this.activity.getApplicationContext(), Sell_notes_form.class);
                    intent.putExtra("Notes_name", ((Notes_variables_template) Notes_recycler_view_adaptor.this.arrayList.get(Integer.parseInt(String.valueOf(my_place_holder.this.getAdapterPosition())))).getUnit_name());
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                    Notes_recycler_view_adaptor.this.activity.getApplicationContext().startActivity(intent);
                }
            });
            this.download_button.setOnClickListener(new OnClickListener(Notes_recycler_view_adaptor.this) {
                public void onClick(View view) {
                    if (!Notes_recycler_view_adaptor.this.sharedPreferences.getString(((Notes_variables_template) Notes_recycler_view_adaptor.this.arrayList.get(my_place_holder.this.getAdapterPosition())).getUnit_name() + Notes_recycler_view_adaptor.this.subject_name, "not_set").equals("not_set")) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.parse(Notes_recycler_view_adaptor.this.sharedPreferences.getString(((Notes_variables_template) Notes_recycler_view_adaptor.this.arrayList.get(my_place_holder.this.getAdapterPosition())).getUnit_name() + Notes_recycler_view_adaptor.this.subject_name, "")), "application/pdf");
                        Notes_recycler_view_adaptor.this.activity.startActivity(intent);
                    } else if (Notes_recycler_view_adaptor.this.checkNetwork.isOnline()) {
                        my_place_holder.this.download_button.setText("Wait");
                        Intent DownloadServiceIntent = new Intent(Notes_recycler_view_adaptor.this.activity.getApplicationContext(), DownloadIntentService.class);
                        DownloadServiceIntent.putExtra("UnitName", ((Notes_variables_template) Notes_recycler_view_adaptor.this.arrayList.get(my_place_holder.this.getAdapterPosition())).getUnit_name());
                        DownloadServiceIntent.putExtra("SubjectName", Notes_recycler_view_adaptor.this.subject_name);
                        DownloadServiceIntent.putExtra("DownloadUrl", ((Notes_variables_template) Notes_recycler_view_adaptor.this.arrayList.get(Integer.parseInt(String.valueOf(my_place_holder.this.getAdapterPosition())))).getDownload_link());
                        DownloadServiceIntent.addFlags(DriveFile.MODE_READ_ONLY);
                        Notes_recycler_view_adaptor.this.activity.startService(DownloadServiceIntent);
                    } else {
                        Snackbar.make(Notes_recycler_view_adaptor.this.activity.getCurrentFocus(), "Check Your Network", -1).show();
                    }
                }
            });
        }
    }

    public Notes_recycler_view_adaptor(ArrayList<Notes_variables_template> arrayList, Activity activity, String subject_name) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.subject_name = subject_name;
        this.sharedPreferences = activity.getSharedPreferences("my_preferences", 0);
        this.checkNetwork = new CheckNetwork(activity);
    }

    public my_place_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new my_place_holder(LayoutInflater.from(parent.getContext()).inflate(C0418R.layout.notes_recycler_view_template, parent, false));
    }

    public void onBindViewHolder(my_place_holder holder, int position) {
        Log.e("position", position + "");
        if (((Notes_variables_template) this.arrayList.get(position)).getDownload_link().equals("")) {
            holder.unit_name.setText(((Notes_variables_template) this.arrayList.get(position)).getUnit_name());
            holder.download_button.setText("Comming Soon");
            holder.download_button.setEnabled(false);
            return;
        }
        holder.unit_name.setText(((Notes_variables_template) this.arrayList.get(position)).getUnit_name());
        if (this.sharedPreferences.getString(((Notes_variables_template) this.arrayList.get(position)).getUnit_name() + this.subject_name, "not_set").equals("Downloading")) {
            holder.download_button.setText("Downloading");
            Log.v("inside notes", "Downloading");
        } else if (!this.sharedPreferences.getString(((Notes_variables_template) this.arrayList.get(position)).getUnit_name() + this.subject_name, "not_set").equals("not_set")) {
            Log.v("inside notes", "Open");
            holder.download_button.setText("Open");
        }
    }

    public int getItemCount() {
        Log.e("size", this.arrayList.size() + "");
        return this.arrayList.size();
    }
}

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

public class question_paper_recycler_adapter extends Adapter<view_holder> {
    Activity activity;
    ArrayList<question_paper_variable_template> arrayList;
    CheckNetwork checkNetwork;
    Editor editor = this.sharedPreferences.edit();
    SharedPreferences sharedPreferences;
    String subject_name;

    public class view_holder extends ViewHolder {
        Button download_question_paper_button;
        TextView subject_title;

        public view_holder(View itemView) {
            super(itemView);
            this.subject_title = (TextView) itemView.findViewById(C0418R.id.question_paper_title);
            this.download_question_paper_button = (Button) itemView.findViewById(C0418R.id.download_question_paper_button);
            this.download_question_paper_button.setOnClickListener(new OnClickListener(question_paper_recycler_adapter.this) {
                public void onClick(View v) {
                    if (!question_paper_recycler_adapter.this.sharedPreferences.getString(((question_paper_variable_template) question_paper_recycler_adapter.this.arrayList.get(view_holder.this.getAdapterPosition())).getTitle() + question_paper_recycler_adapter.this.subject_name, "not_set").equals("not_set")) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setDataAndType(Uri.parse(question_paper_recycler_adapter.this.sharedPreferences.getString(((question_paper_variable_template) question_paper_recycler_adapter.this.arrayList.get(view_holder.this.getAdapterPosition())).getTitle() + question_paper_recycler_adapter.this.subject_name, " ")), "application/pdf");
                        question_paper_recycler_adapter.this.activity.startActivity(intent);
                    } else if (question_paper_recycler_adapter.this.checkNetwork.isOnline()) {
                        view_holder.this.download_question_paper_button.setText("Wait");
                        Intent DownloadServiceIntent = new Intent(question_paper_recycler_adapter.this.activity.getApplicationContext(), DownloadIntentService.class);
                        DownloadServiceIntent.putExtra("UnitName", ((question_paper_variable_template) question_paper_recycler_adapter.this.arrayList.get(view_holder.this.getAdapterPosition())).getTitle());
                        DownloadServiceIntent.putExtra("SubjectName", question_paper_recycler_adapter.this.subject_name);
                        DownloadServiceIntent.putExtra("DownloadUrl", ((question_paper_variable_template) question_paper_recycler_adapter.this.arrayList.get(Integer.parseInt(String.valueOf(view_holder.this.getAdapterPosition())))).getDownload_link());
                        DownloadServiceIntent.addFlags(DriveFile.MODE_READ_ONLY);
                        question_paper_recycler_adapter.this.activity.startService(DownloadServiceIntent);
                    } else {
                        Snackbar.make(question_paper_recycler_adapter.this.activity.getCurrentFocus(), "Check Your Network", -1).show();
                    }
                }
            });
        }
    }

    public question_paper_recycler_adapter(ArrayList<question_paper_variable_template> arrayList, Activity activity, String subject_name) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.subject_name = subject_name;
        this.sharedPreferences = activity.getSharedPreferences("my_preferences", 0);
        this.checkNetwork = new CheckNetwork(activity);
    }

    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new view_holder(LayoutInflater.from(parent.getContext()).inflate(C0418R.layout.question_paper_recycler_adapter_layout, parent, false));
    }

    public void onBindViewHolder(view_holder holder, int position) {
        Log.v("Download button", Boolean.toString(holder.download_question_paper_button.isEnabled()));
        if (((question_paper_variable_template) this.arrayList.get(position)).getDownload_link().equals("")) {
            holder.subject_title.setText(((question_paper_variable_template) this.arrayList.get(position)).getTitle());
            holder.download_question_paper_button.setText("Comming Soon");
            holder.download_question_paper_button.setEnabled(false);
            return;
        }
        holder.subject_title.setText(((question_paper_variable_template) this.arrayList.get(position)).getTitle());
        if (this.sharedPreferences.getString(((question_paper_variable_template) this.arrayList.get(position)).getTitle() + this.subject_name, "not_set").equals("Downloading")) {
            holder.download_question_paper_button.setText("Downloading");
        } else if (!this.sharedPreferences.getString(((question_paper_variable_template) this.arrayList.get(position)).getTitle() + this.subject_name, "not_set").equals("not_set")) {
            holder.download_question_paper_button.setText("Open");
        }
    }

    public int getItemCount() {
        return this.arrayList.size();
    }
}

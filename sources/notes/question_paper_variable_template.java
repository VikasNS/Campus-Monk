package notes;

import android.app.Activity;

public class question_paper_variable_template {
    public Activity activity;
    public String download_link;
    public String title;

    public question_paper_variable_template(Activity activity, String title, String download_link) {
        this.title = title;
        this.download_link = download_link;
        this.activity = activity;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDownload_link() {
        return this.download_link;
    }
}

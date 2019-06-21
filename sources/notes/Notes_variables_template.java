package notes;

public class Notes_variables_template {
    String buy_link;
    String download_link;
    String sell_link;
    String unit_name;

    public Notes_variables_template(String unit_name, String download_link) {
        this.unit_name = unit_name;
        this.download_link = download_link;
    }

    public String getUnit_name() {
        return this.unit_name;
    }

    public String getDownload_link() {
        return this.download_link;
    }
}

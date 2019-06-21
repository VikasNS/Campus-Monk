package events;

public class events_variable {
    String date;
    String day;
    String title;

    public events_variable(String date, String day, String title) {
        this.date = date;
        this.day = day;
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public String getDay() {
        return this.day;
    }

    public String getTitle() {
        return this.title;
    }
}

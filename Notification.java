import java.util.Date;

// Notification object used for reminders or important library updates.
public class Notification {
    private String notificationID;
    private String message;
    private Date sentDate;
    private String status;

    public Notification(String notificationID, String message, Date sentDate, String status) {
        this.notificationID = notificationID;
        this.message = message;
        this.sentDate = sentDate;
        this.status = status;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public String getMessage() {
        return message;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public String getStatus() {
        return status;
    }

    public void sendReminder() {
        this.status = "Reminder Sent";
    }

    public void sendOverdueNotice() {
        this.status = "Overdue Notice Sent";
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID='" + notificationID + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

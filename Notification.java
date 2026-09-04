import java.util.Date;

// Notification object para sa paalala at mahahalagang update ng library.
public class Notification {
    // Natatanging notification identifier.
    private String notificationID;
    // Mensaheng ipinapadala sa user.
    private String message;
    // Petsa at oras kung kailan ipinadala.
    private Date sentDate;
    // Kasalukuyang estado ng notification.
    private String status;
    private String recipientID;

    // Gumagawa ng notification kasama ang mensahe at status.
    public Notification(String notificationID, String message, Date sentDate, String status) {
        this(notificationID, message, sentDate, status, "");
    }

    public Notification(String notificationID, String message, Date sentDate, String status, String recipientID) {
        this.notificationID = notificationID;
        this.message = message;
        this.sentDate = sentDate;
        this.status = status;
        this.recipientID = recipientID;
    }

    // Ibinabalik ang notification ID.
    public String getNotificationID() {
        return notificationID;
    }

    // Ibinabalik ang notification message.
    public String getMessage() {
        return message;
    }

    // Ibinabalik ang oras ng pagpapadala.
    public Date getSentDate() {
        return sentDate;
    }

    // Ibinabalik ang notification status.
    public String getStatus() {
        return status;
    }

    public String getRecipientID() {
        return recipientID;
    }

    // Minamarkahan ang notification bilang reminder na naipadala.
    public void sendReminder() {
        this.status = "Reminder Sent";
    }

    // Minamarkahan ang notification bilang overdue notice na naipadala.
    public void sendOverdueNotice() {
        this.status = "Overdue Notice Sent";
    }

    // Gumagawa ng readable notification representation.
    @Override
    public String toString() {
        return "Notification{" +
                "notificationID='" + notificationID + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

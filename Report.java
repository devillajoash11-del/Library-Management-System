import java.util.Date;

// Report used by administrators to review library activity.
public class Report {
    private String reportID;
    private String reportType;
    private String reportContent;
    private Date generateDate;

    public Report(String reportID, String reportType, String reportContent, Date generateDate) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.reportContent = reportContent;
        this.generateDate = generateDate;
    }

    public String getReportID() {
        return reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReportContent() {
        return reportContent;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public String generateBorrowReport() {
        return "Borrow report generated for " + reportType + " on " + generateDate;
    }

    public String generateOverdueReport() {
        return "Overdue report generated for " + reportType + " on " + generateDate;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportID='" + reportID + '\'' +
                ", reportType='" + reportType + '\'' +
                ", generateDate=" + generateDate +
                '}';
    }
}

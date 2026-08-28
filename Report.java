import java.util.Date;

// Report na ginagamit ng administrator para suriin ang library activity.
public class Report {
    // Natatanging report identifier.
    private String reportID;
    // Uri ng report.
    private String reportType;
    // Nilalaman o buod ng report.
    private String reportContent;
    // Petsa at oras kung kailan ginawa ang report.
    private Date generateDate;

    // Gumagawa ng report gamit ang identifier, uri, nilalaman, at petsa.
    public Report(String reportID, String reportType, String reportContent, Date generateDate) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.reportContent = reportContent;
        this.generateDate = generateDate;
    }

    // Ibinabalik ang report ID.
    public String getReportID() {
        return reportID;
    }

    // Ibinabalik ang uri ng report.
    public String getReportType() {
        return reportType;
    }

    // Ibinabalik ang nilalaman ng report.
    public String getReportContent() {
        return reportContent;
    }

    // Ibinabalik ang petsa ng paggawa ng report.
    public Date getGenerateDate() {
        return generateDate;
    }

    // Gumagawa ng text para sa borrow report.
    public String generateBorrowReport() {
        return "Borrow report generated for " + reportType + " on " + generateDate;
    }

    // Gumagawa ng text para sa overdue report.
    public String generateOverdueReport() {
        return "Overdue report generated for " + reportType + " on " + generateDate;
    }

    // Gumagawa ng readable report representation.
    @Override
    public String toString() {
        return "Report{" +
                "reportID='" + reportID + '\'' +
                ", reportType='" + reportType + '\'' +
                ", generateDate=" + generateDate +
                '}';
    }
}

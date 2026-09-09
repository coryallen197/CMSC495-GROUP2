package Week8;

public class TriageResult {

    private String category;
    private String priority;
    private String summary;
    private String department;

    public TriageResult(String category, String priority,
                        String summary, String department) {

        this.category = category;
        this.priority = priority;
        this.summary = summary;
        this.department = department;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getSummary() {
        return summary;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Category: " + category +
               "\nPriority: " + priority +
               "\nSummary: " + summary +
               "\nDepartment: " + department;
    }
}
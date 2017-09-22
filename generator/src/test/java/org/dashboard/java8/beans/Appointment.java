package org.dashboard.java8.beans;

public class Appointment {
    private String category;
    private String status;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

package DTO;

import java.time.LocalDateTime;

public class DateRangeDTO {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public DateRangeDTO() {
    }

    public DateRangeDTO(LocalDateTime fromDate, LocalDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }
}

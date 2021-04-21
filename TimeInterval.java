package calendar;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeInterval {
	
	 LocalDate startDate;
	 LocalDate endDate;
	 LocalTime startTime;
	 LocalTime endTime;
	 
	 /**
	  * 
	  * @param date
	  * @param startTime
	  * @param endTime
	  */
	 public TimeInterval(LocalDate date, LocalTime startTime, LocalTime endTime) {
	       this.startDate = this.endDate = date;
	       this.startTime = startTime;
	       this.endTime = endTime;
	   }
	 
	 /**
	  * 
	  * @param startDate
	  * @param endDate
	  * @param startTime
	  * @param endTime
	  */
	 public TimeInterval(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
	       this.startDate = startDate;
	       this.endDate = endDate;
	       this.startTime = startTime;
	       this.endTime = endTime;
	   }
	 
	 /**
	  * 
	  * @return
	  */
	 public LocalDate getStartDate() {
	       return startDate;
	   }
	 
	 /**
	  * 
	  * @return
	  */
	 public LocalDate getEndDate() {
	       return endDate;
	   }
	 
	 /**
	  * 
	  * @return
	  */
	 public LocalTime getStartTime() {
	       return startTime;
	   }
	 
	 /**
	  * 
	  * @return
	  */
	 public LocalTime getEndTime() {
	       return endTime;
	   }
	 
	 /**
	  * 
	  * @param timeInterval
	  * @return
	  */
	 public boolean doesOverlap(TimeInterval timeInterval) {
	       if (this.getStartDate().equals(timeInterval.getStartDate())) {
	           return this.getStartTime().isBefore(timeInterval.getEndTime()) && timeInterval.getStartTime().isBefore(this.getEndTime());
	       }
	       return false;
	   }
	 
	 /**
	  * 
	  * @param other
	  * @return
	  */
	 public int compareTo(TimeInterval other) {
	       return this.getStartTime().compareTo(other.getStartTime());
	   }
	
}

package calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

enum EventType {
	   ONETIME, REGULAR;
	}

public class Event implements Comparable<Event> {
	
	   String name;
	   TimeInterval time;
	   EventType etype;
	   
	   /**
	    * 
	    * @param name
	    * @param startDate
	    * @param endDate
	    * @param startTime
	    * @param endTime
	    * @param etype
	    */
	   public Event(String name, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, EventType etype) {
	       this.name = name;
	       this.time = new TimeInterval(startDate, endDate, startTime, endTime);
	       this.etype = etype;
	   }
	   
	   /**
	    * 
	    * @return
	    */
	   public String getName() {
	       return name;
	   }
	   /**
	    * 
	    * @return
	    */
	   public TimeInterval getTime() {
	       return time;
	   }
	   
	   /**
	    * 
	    * @return
	    */
	   public EventType getEType() {
	       return etype;
	   }
	   
	   /**
	    * 
	    * @param name
	    * @return
	    */
	   public boolean sameEvent(String name) {
	       if (this.getName().equals(name)) {
	           return true;
	       }
	       return false;
	   }
	   
	   /**
	    * 
	    */
	   public int compareTo(Event other) {
	       return this.getTime().compareTo(other.getTime());
	   }
	   
	   /**
	    * 
	    * @param other
	    * @return
	    */
	   public boolean doesOverlap(Event other) {
	       return this.getTime().doesOverlap(other.getTime());
	   }
	   
	   /**
	    * 
	    */
	   public void printEvent() {
	       DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("H:mm");
	       System.out.println(timeformatter.format(getTime().getStartTime()) + " - " + timeformatter.format(getTime().getEndTime()) + " " + getName());

	   }

}



















































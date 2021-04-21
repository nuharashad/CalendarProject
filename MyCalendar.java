/**
 * 
 */
package calendar;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author nuhar
 *
 */
public class MyCalendar {
	
	TreeMap<LocalDate, ArrayList<Event>> events = new TreeMap<LocalDate, ArrayList<Event>>();
	 LocalDate currentCalendarAt = LocalDate.now();
	 
	 /**
	  * 
	  */
	 public void printEventList() {
		 DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("EEEE, MMMM d ");
		 
		 int year = events.firstKey().getYear();
	     System.out.println(year);
	     
	     for (Entry<LocalDate, ArrayList<Event>> entry : events.entrySet()) {
	           Collections.sort(entry.getValue());
	           if (entry.getKey().getYear() != year) {
	               year = entry.getKey().getYear();
	               System.out.println(year);
	     }
	           for (Event e : entry.getValue()) {
	               System.out.print(" " + dateformatter.format(entry.getKey()) + " ");
	               e.printEvent();
	           }
	           
	           System.out.println(" ");
	     }
	 }
	/**
	 * 
	 * @return
	 */
	 public LocalDate getCurrentCalendarAt() {
	         return currentCalendarAt;
	}
	 
	 /**
	  * 
	  * @param currentCalendarAt
	  */
	 public void setCurrentCalendarAt(LocalDate currentCalendarAt) {
	       this.currentCalendarAt = currentCalendarAt;
	 }
	 
	 /**
	  * 
	  * @param days
	  * @param startDate
	  * @param endDate
	  * @return
	  */
	 public ArrayList<LocalDate> getSpecificDates(String days, LocalDate startDate, LocalDate endDate) {
	       ArrayList<DayOfWeek> daysNeeded = new ArrayList<DayOfWeek>();

	       for (char day : days.toCharArray()) {
	           if (day == 'M') {
	               daysNeeded.add(DayOfWeek.MONDAY);
	           } else if (day == 'T') {
	               daysNeeded.add(DayOfWeek.TUESDAY);
	           } else if (day == 'W') {
	               daysNeeded.add(DayOfWeek.WEDNESDAY);
	           } else if (day == 'R') {
	               daysNeeded.add(DayOfWeek.THURSDAY);
	           } else if (day == 'F') {
	               daysNeeded.add(DayOfWeek.FRIDAY);
	           } else if (day == 'S') {
	               daysNeeded.add(DayOfWeek.SATURDAY);
	           } else if (day == 'U') {
	               daysNeeded.add(DayOfWeek.SUNDAY);
	           }
	       }
	       
	       ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	       for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
	           if (daysNeeded.contains(d.getDayOfWeek())) {
	               dates.add(d);
	           }
	       }
	       return dates;
	 }
	 
	 /**
	  * 
	  * @param e
	  */
	 public void addOneTime(Event e) {
	       LocalDate start = e.getTime().getStartDate();
	       if (!doesOverlap(start, e)) {
	           events.putIfAbsent(start, new ArrayList<Event>());
	           events.get(start).add(e);
	           System.out.println("Event " + e.getName() + " has been added!");
	       } else {
	           System.out.println("Time conflict! Could not add " + e.getName());

	       }
	   }
	 
	 /**
	  * 
	  * @param e
	  * @param days
	  */
	 public void addRegular(Event e, String days) {
	       // Get the specific dates between the date range
	       ArrayList<LocalDate> dates = getSpecificDates(days, e.getTime().getStartDate(), e.getTime().getEndDate());
	       for (LocalDate date : dates) {
	           if (!doesOverlap(date, e)) {
	               events.putIfAbsent(date, new ArrayList<Event>());
	               events.get(date).add(e);
	           }
	       }
	   }
	 
	 /**
	  * 
	  * @param date
	  * @param potentialEvent
	  * @return
	  */
	   public boolean doesOverlap(LocalDate date, Event potentialEvent) {
	       ArrayList<Event> dayEvents = events.get(date);
	       if (dayEvents != null) {
	           for (Event event : dayEvents) {
	               if (event.doesOverlap(potentialEvent) == true) {
	                   return true;
	               }
	           }
	       }
	       return false;
	   }
	   
	   /**
	    * 
	    * @param date
	    * @param name
	    */
	   public void remove(LocalDate date, String name) {
	       Event found;
	       int size = events.get(date).size();
	       for (int i = 0; i < size; i++) {
	           found = events.get(date).get(i);
	           if (found.sameEvent(name) == true) {
	               events.get(date).remove(i);
	               break;
	           }
	       }
	       if (events.get(date).size() == size) {
	           throw new IllegalArgumentException("Event is not in calendar");
	       }
	   }
	   
	   /**
	    * 
	    * @param date
	    */
	   public void removeAll(LocalDate date) {
	       if (events.get(date) != null) {
	           events.get(date).clear();
	       }
	   }
	   /**
	    * 
	    * @param numberOfMonths
	    */
	   public void advanceByMonth(int numberOfMonths) {
	       currentCalendarAt = currentCalendarAt.plusMonths(numberOfMonths);
	   }
	   /**
	    * 
	    * @param numberOfDays
	    */
	   public void advanceByDay(int numberOfDays) {
	       currentCalendarAt = currentCalendarAt.plusDays(numberOfDays);
	   }
	 
	   /**
	    * 
	    */
	public void printMonthCalendar() {
		
		  String month = currentCalendarAt.getMonth().toString();
		  month = month.charAt(0) + month.toLowerCase().substring(1, month.length());
		  System.out.println(" " + month + " " + currentCalendarAt.getYear());
		  System.out.println("Su Mo Tu We Th Fr Sa");
		  LocalDate x = LocalDate.of(currentCalendarAt.getYear(), currentCalendarAt.getMonth(), 1);
		  String firstWeekSpace = "";
		  for (int i = 0; i < x.getDayOfWeek().getValue(); i++) {
		       firstWeekSpace += " ";
		  }
		  System.out.print(firstWeekSpace);

		  for (int i = 0; i < currentCalendarAt.lengthOfMonth(); i++) {
		  if (x.equals(LocalDate.now())) {
		       System.out.printf("{%2s} ", x.getDayOfMonth());
		  } 
		  else {
		       System.out.printf("%2s ", x.getDayOfMonth());
		  }
		  x = x.plusDays(1);
		  if (x.getDayOfWeek() == DayOfWeek.SUNDAY) {
		      System.out.print("\n");
		  }
	}
		       System.out.println();
	}
	
	/**
	 * 
	 */
	public void printDayCalendar() {
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
	       System.out.println(" " + formatter.format(currentCalendarAt));
	       ArrayList<Event> dayEvents = events.get(currentCalendarAt);
	       if (dayEvents != null) {
	           Collections.sort(dayEvents);
	           for (Event event : dayEvents) {
	               event.printEvent();
	           }
	       }
	       System.out.println();
	   }
}




























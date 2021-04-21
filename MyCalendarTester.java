package calendar;

import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 
 */


/**
 * @author nuhar
 *
 */
public class MyCalendarTester {

	/**
	 * @param args
	 */
	 public static void main(String[] args) {
	       MyCalendar calendar = new MyCalendar();
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
	       addEvents(calendar, formatter);
	       calendar.printMonthCalendar();
	       Scanner sc = new Scanner(System.in);
	       String strDate = null;
	       LocalDate date = null;

	       String input = "";
	       while (!input.equals("Q")) {
	           System.out.println("\nSelect one of the options:");
	           System.out.println("[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit");
	           input = sc.nextLine().toUpperCase();
	           switch (input) {
	           case "V": 
	               calendar.setCurrentCalendarAt(LocalDate.now());
	               System.out.println("[D]ay view or [M]view ?");
	               String view = sc.nextLine().toUpperCase(); 
	               System.out.println();
	               String option = "";
	               int val = 0; 
	               while (!option.equals("G")) {
	                   if (view.equals("D")) { 
	                       calendar.advanceByDay(val);
	                       calendar.printDayCalendar(); 
	                   } else if (view.equals("M")) { 
	                       calendar.advanceByMonth(val);
	                       calendar.printMonthCalendar(); 
	                                                       
	                   } else {
	                       try {
	                           throw new Exception("Your input isnt recognized");
	                       } catch (Exception e) {
	                           System.out.println(e.getMessage() + " : date not valid");
	                       }
	                   }
	                   System.out.println("\n[P]revious or [N]ext or [G]o back to main menu ?");
	                   option = sc.nextLine().toUpperCase(); 
	                   if (option.equals("P")) { 
	                       val = -1; 
	                   } else if (option.equals("N")) { 
	                       val = 1;
	                   } else if (!option.equals("G")) {
	                       try {
	                           throw new Exception("Your input isnt recognized");
	                       } catch (Exception e) {
	                           System.out.println(e.getMessage() + " : date not valid");
	                       }
	                   }

	               }

	               break;
	           case "C": 
	               System.out.println("Create one time event");
	               System.out.println("Name of event: ");
	               String name = sc.nextLine();

	               try {
	                   System.out.println("Date of event in mm/dd/yyyy: ");
	                   strDate = sc.nextLine();
	                   formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	                   date = LocalDate.parse(strDate, formatter);
	                   formatter = DateTimeFormatter.ofPattern("H:mm");
	                   System.out.println("Start Time of Event in 24 hr clock [format H:M]: ");
	                   String startTime = sc.nextLine();
	                   System.out.println("End Time of Event in 24 hr clock [format H:M]: ");
	                   String endTime = sc.nextLine();
	                   calendar.addOneTime(new Event(name, date, date, LocalTime.parse(startTime, formatter), LocalTime.parse(endTime, formatter), EventType.ONETIME));
	               } catch (Exception e) {
	                   System.out.println(e.getMessage() + " - Invalid date");
	               }

	               break;
	           case "G": 
	               System.out.println("Enter a day in mm/dd/yyyy to see which events are occuring on that day:");
	               strDate = sc.nextLine();
	               formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	               try {
	                   date = LocalDate.parse(strDate, formatter);
	                   calendar.setCurrentCalendarAt(date);
	                   calendar.printDayCalendar();
	               } catch (Exception e) {
	                   System.out.println(e.getMessage() + " : date is not valid");
	               }

	               break;
	           case "E": 
	               calendar.printEventList();
	               break;
	           case "D": 
	               System.out.println("Delete");
	               System.out.println("\n[S]elected or [A]ll ? ");
	               option = sc.nextLine().toUpperCase(); 
	               System.out.println("Enter event in mm/dd/yyyy: ");
	               strDate = sc.nextLine();
	               formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	               try {
	                   date = LocalDate.parse(strDate, formatter);
	                   calendar.setCurrentCalendarAt(date);
	                   if (option.equals("S")) { 
	                       calendar.printDayCalendar();
	                       System.out.print("WHich event would you like to delete?: ");
	                       String evName = sc.nextLine();
	                       try {
	                           calendar.remove(date, evName);
	                           System.out.println("Event has been deleted; here are the other events:");
	                           calendar.printDayCalendar();
	                       } catch (Exception e) {
	                           System.out.println(e.getMessage() + " Can not delete event.");
	                       }
	                   } else if (option.equals("A")) { 
	                       calendar.removeAll(date);
	                       calendar.printDayCalendar();
	                       System.out.println("All events hace been deleted.");
	                   }
	               } catch (Exception e) {
	                   System.out.println(e.getMessage() + " : date is not valid");
	               }

	               break;
	           case "Q":
	               System.out.println("Quit");
	               break;
	           default:
	               System.out.println("Your input can not be recognized");
	               break;
	           }
	       }

	       System.out.println("Bye!");
	       exportEvents(calendar);
	       sc.close();
	   }
	 
	 /**
	  * 
	  * @param calendar
	  * @param formatter
	  */
	
	public static void addEvents(MyCalendar calendar, DateTimeFormatter formatter) {
		String file = "events.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			
			String line;
			
			while ((line = br.readLine()) != null) {
				String name = line;
				line = br.readLine();
				char firstChar = line.charAt(0);
				String elems[] = line.split("\\s+");
				if (firstChar >= 'A' && firstChar <= 'Z') {
					formatter = DateTimeFormatter.ofPattern("M/d/yy");
					LocalDate startDate = LocalDate.parse(elems[3], formatter);
					LocalDate endDate = LocalDate.parse(elems[4], formatter);
					formatter = DateTimeFormatter.ofPattern("H:mm");
					 calendar.addRegular(new Event(name, startDate, endDate, LocalTime.parse(elems[1], formatter), LocalTime.parse(elems[2], formatter), EventType.REGULAR), elems[0]);
				}
				else {
					formatter = DateTimeFormatter.ofPattern("M/d/yy");
					LocalDate startDate, endDate;
					startDate = endDate = LocalDate.parse(elems[0], formatter);
					formatter = DateTimeFormatter.ofPattern("H:mm");
					calendar.addOneTime(new Event(name, startDate, endDate, LocalTime.parse(elems[1], formatter), LocalTime.parse(elems[2], formatter), EventType.ONETIME));
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found" + e.getMessage());
		}
		
		catch (IOException e) {
			System.out.println("Error" + e.getMessage());
		}
	}
	
	/**
	 * @param calendar
	 */
	
	public static void exportEvents(MyCalendar calendar) {
		PrintStream printer = null;
		try {
			printer = new PrintStream(new File("output.txt"));
		}
		catch (FileNotFoundException e) {
			 System.out.println("Error " + e.getMessage());
		}
		
		System.setOut(printer);
	    calendar.printEventList();
	}

}




























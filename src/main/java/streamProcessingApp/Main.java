package streamProcessingApp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
	static List<MonitoredData> list = new ArrayList<MonitoredData>();
	public static Date previousDate;

	public static Date removeTimeFromDate(Date date) {

		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	static long countDayOccurence() {
		previousDate = list.get(0).getStartTime();
		Predicate<MonitoredData> p = param -> {
			if (removeTimeFromDate(param.getStartTime()).after(removeTimeFromDate(previousDate))) {
				previousDate = param.getStartTime();
				return true;
			} else {
				previousDate = param.getStartTime();
				return false;
			}
		};
		return list.stream().filter(p).count() + 1;// funtion::identity->elem curent

	}

	static Map<String, Long> activityOccurence() {
		return list.stream().collect(Collectors.groupingBy(MonitoredData::getActivityType, Collectors.counting()));
	}

	static Map<Date, Map<String, Long>> activityDailyOccurence() {
		return list.stream().collect(Collectors.groupingBy((MonitoredData m) -> removeTimeFromDate(m.getStartTime()),
				Collectors.groupingBy(MonitoredData::getActivityType, Collectors.counting())));
	}

	static Map<MonitoredData, Long> computeActivityDuration() {
		return list.stream().collect(Collectors.toMap(a -> a, s -> TimeUnit.SECONDS
				.convert(s.getEndTime().getTime() - s.getStartTime().getTime(), TimeUnit.MILLISECONDS)));
	}

	static Map<String, Long> computeOverAllActivityDuration() {
		return list.stream().collect(
				Collectors.groupingBy(MonitoredData::getActivityType, Collectors.summingLong(s -> TimeUnit.SECONDS
						.convert(s.getEndTime().getTime() - s.getStartTime().getTime(), TimeUnit.MILLISECONDS))));
	}

	public static void main(String[] args) {
		list = IOProcessing.readFromFile();

		IOProcessing.writeInFile1(countDayOccurence());

		Map<String, Long> map = activityOccurence();
		IOProcessing.writeInFile2(map);

		Map<Date, Map<String, Long>> map4 = activityDailyOccurence();
		IOProcessing.writeInFile3(map4);

		Map<MonitoredData, Long> map3 = computeActivityDuration();
		IOProcessing.writeInFile4(map3);

		Map<String, Long> map2 = computeOverAllActivityDuration();
		IOProcessing.writeInFile5(map2);
	}

}

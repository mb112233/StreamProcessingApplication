package streamProcessingApp;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class IOProcessing {

	static List<MonitoredData> list = new ArrayList<MonitoredData>();

	public static List<MonitoredData> readFromFile() {
		String fileName = "Activities.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(s -> {
				String[] token = s.split("\\s+");
				MonitoredData n = new MonitoredData(token[0] + " " + token[1], token[2] + " " + token[3], token[4]);
				list.add(n);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void writeInFile1(long l) {

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("NumberOfDays.txt")))) {
			writer.write(Long.toString(l));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeInFile2(Map<String, Long> map) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("ActivityOccurences.txt")))) {
			for (Map.Entry<String, Long> entry : map.entrySet()) {
				writer.write(entry.getKey());
				writer.write("->");
				writer.write(Long.toString(entry.getValue()));
				((BufferedWriter) writer).newLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeInFile3(Map<Date, Map<String, Long>> map) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("OccurenceOfEachActivityEachDay.txt")))) {
			for (Map.Entry<Date, Map<String, Long>> entry : map.entrySet()) {
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				String date = df.format(entry.getKey());
				writer.write(date);
				writer.write("  ");
				Map<String, Long> values = entry.getValue();
				for (Map.Entry<String, Long> e : values.entrySet()) {
					writer.write(e.getKey());
					writer.write("->");
					writer.write(Long.toString(e.getValue()));
					writer.write("  ");
				}
				((BufferedWriter) writer).newLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeInFile4(Map<MonitoredData, Long> map) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("EveryDayActivityDuration.txt")))) {
			for (Map.Entry<MonitoredData, Long> entry : map.entrySet()) {
				writer.write(entry.getKey().getActivityType());
				writer.write(":");
				writer.write(Long.toString(entry.getValue()));
				writer.write(" sec");
				((BufferedWriter) writer).newLine();

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeInFile5(Map<String, Long> map) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("OverallActivityDuration.txt")))) {
			for (Map.Entry<String, Long> entry : map.entrySet()) {
				writer.write(entry.getKey());
				writer.write("->");
				writer.write(Long.toString(entry.getValue()));
				writer.write(" sec");
				((BufferedWriter) writer).newLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		readFromFile();
	}

}

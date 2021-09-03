package ssafy;

import org.apache.hadoop.util.ProgramDriver;

public class Driver {

	public static void main(String[] args) {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {

			pgd.addClass("wordcount", WordCount.class,
				"A map/reduce program that performs word counting.");
			pgd.addClass("wordcount1", WordCount1.class,
				"A map/reduce program that counts 1st character of word.");
			pgd.addClass("wordcount-sort", WordCountSort.class,
				"A map/reduce program that word count sort");
			pgd.addClass("inverted-index", InvertedIndex.class,
				"A map/reduce program that word count sort");

			pgd.driver(args);
			exitCode = 0;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.exit(exitCode);
	}
}

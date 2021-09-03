package ssafy;

import java.io.IOException;
import java.util.stream.Stream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MatrixAdd {

	public static class MAddMapper extends Mapper<Object, Text, Text, IntWritable> {

		private Text key = new Text();
		private IntWritable value = new IntWritable();
		private static final String TAB = "\t";

		public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
			// value.toString() : get a line
			String[] tokens = value.toString().split(TAB);
			key.set(tokens[0] + TAB + tokens[1]);
			value.set(Integer.parseInt(tokens[2]));

			context.write(key, value);
		}

		public static class MAddReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

			private IntWritable result = new IntWritable();

			public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {

//				result.set(Stream.of(values).mapToInt(o -> o.get()).sum());
				int sum = 0;
				values.forEach(o -> sum += o.get());
				result.set(sum);

				context.write(key, result);
			}
		}

		public static void main(String[] args) throws Exception {
			Configuration conf = new Configuration();
			String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
			if (otherArgs.length != 2) {    // �hadoop jar jarname.jar ������ � ��� ��� ��
				System.err.println("Usage: <in> <out>");
				System.exit(2);
			}
			FileSystem hdfs = FileSystem.get(conf);
			Path output = new Path(otherArgs[1]);
			if (hdfs.exists(output)) {
				hdfs.delete(output, true);
			}

			Job job = new Job(conf, "matrix addition");
			job.setJarByClass(MatrixAdd.class);        // class � ��
			job.setMapperClass(MAddMapper.class);                   // Map class ��
			job.setReducerClass(MAddReducer.class);                   // Reduce class ��
			job.setOutputKeyClass(Text.class);        // output key type ��
			job.setOutputValueClass(IntWritable.class);        // output value type ��
			job.setNumReduceTasks(2);            // ��� ���� reduce�� ��

			FileInputFormat.addInputPath(job, new Path(otherArgs[0]));    // ���� ���� ��
			FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));    // ���� ���� ��
			System.exit(job.waitForCompletion(true) ? 0 : 1);    // ��
		}
	}
}
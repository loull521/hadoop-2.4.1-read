package loull.hadoop.mr;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {

	public static class WordMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			for (int i=0; i<1; i++) {
				System.out.println("map before sleep 10s");
				Thread.sleep(1*1000);
			}
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}
//			for (int i=0; i<2; i++) {
//				System.out.println("map after sleep 10s");
//				Thread.sleep(1*1000);
//			}
		}
	}
	
	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			for (int i=0; i<1; i++) {
				System.out.println("reduce before sleep 10s");
				Thread.sleep(1*1000);
			}
			
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			context.write(key, result);

//			for (int i=0; i<2; i++) {
//				System.out.println("reduce after sleep 10s");
//				Thread.sleep(10*1000);
//			}
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		System.out.println("args: " + args.length);
		for (String s : args) System.out.println(s);
		if (args.length != 2) {
			System.out.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}
		
		for(int i=0; i<3; i++) {
			System.out.println("main sleep 10s");
			Thread.sleep(1*1000);
		}
		
		Configuration conf = new Configuration();
		Job job = new Job(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(WordMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.out.println("loull ok!");
		System.exit(job.waitForCompletion(true)? 0 : 1);
	}
}
 
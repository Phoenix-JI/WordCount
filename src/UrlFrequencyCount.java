//Published by TANG Haoqing 2022-03-27
//version 1.0

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable.Comparator;
import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.LongWritable.Comparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class UrlFrequencyCount{
    public static void main(String[] args) {
    	
    	try {
    		System.out.println("Processing the HTML...");
    		
	    	String cur_dir = System.getProperty("user.dir");
	    	
	    	System.out.println(cur_dir);
	        
	    	String Input_path = cur_dir +"/input/";
	    	
	    	String Output_path = cur_dir +"/input_txt/";
	    	new File(Output_path).mkdirs();
	    	
	    	HtmlParser.extractUrl(Input_path,Output_path);
    	}catch(Exception e){
            e.printStackTrace();
        }
    	
        //build a configuration object
        Configuration conf = new Configuration();
        try{
        	
        	System.out.println("Job Start...");
        	
            //build a job object
            Job job = Job.getInstance(conf, "url frequency count");
            
            //job.setSortComparator(Sort.class);
            //set the class to run the job
            job.setJarByClass(UrlFrequencyCount.class);
            //set the mapper class
            job.setMapperClass(UrlMapper.class);
            
            //set the reducer class
            job.setReducerClass(UrlReducer.class);
            //set key and value of mapper output
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            
            //set key and value of reducer output
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            
            job.setOutputFormatClass(TextOutputFormat.class);
            //set the input and output path
            Path inpath = new Path("./input_txt/");
            Path outpath = new Path("./output_new/");
          
            FileInputFormat.setInputPaths(job, inpath);
            FileOutputFormat.setOutputPath(job, outpath);
            
            //submit the job
            boolean flag = job.waitForCompletion(true);
            if(!flag){
                System.out.println("Error");
            }else {
            	

	            Job sortjob = Job.getInstance(conf, "sort");
	    
	            Path tempDir = new Path("./output_new/part-r-00000");
	            Path newOutput = new Path("./output_result/"); 
	            
	            sortjob.setNumReduceTasks(1);
	            
	            sortjob.setMapperClass(SwapMap.class);
	            sortjob.setReducerClass(SwapReduce.class);
	
	            sortjob.setMapOutputKeyClass(IntWritable.class);
	            sortjob.setMapOutputValueClass(Text.class);
	           
	            sortjob.setOutputKeyClass(Text.class);
	            sortjob.setOutputValueClass(IntWritable.class);
	           
	            sortjob.setSortComparatorClass(IntWritableDecreasingComparator.class);  
	
	            FileInputFormat.addInputPath(sortjob, tempDir);
	            FileOutputFormat.setOutputPath(sortjob,newOutput);
	            
	            sortjob.waitForCompletion(true);

            }
            
            FileSystem.get(conf).deleteOnExit(outpath); 

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
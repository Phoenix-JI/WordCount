//Published by TANG Haoqing 2022-03-27
//version 1.0

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class UrlReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
    @Override
    protected void reduce(Text key, Iterable<IntWritable> iterable, Context context) throws IOException, InterruptedException{
        int sum = 0;
        // sort and group
        for(IntWritable it:iterable){
            sum = sum+it.get();
        }
        //filter the frequency >= 6
        if(sum>=6){
            //context.write(key, new IntWritable(sum));
        	context.write(key,new IntWritable(sum));
        }
    }
}

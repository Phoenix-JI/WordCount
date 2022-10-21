//Published by TANG Haoqing 2022-03-27
//version 1.0

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class UrlMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        //get the input for each line of data
        String urlline = value.toString();
        StringTokenizer urlst = new StringTokenizer(urlline,"\t\n\r");
        while(urlst.hasMoreTokens()){
            String url = urlst.nextToken();
            //record in <url, 1> (<key, value>) format
            context.write(new Text(url), new IntWritable(1));
        }
    }
}

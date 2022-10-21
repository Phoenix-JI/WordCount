
import java.io.IOException;
 
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.IntWritable.Comparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;



public class SwapReduce extends Reducer<IntWritable,Text,Text,IntWritable>{
    public void reduce(IntWritable key,Iterable<Text>values,Context context) throws IOException, InterruptedException{
        for (Text text : values) {
            context.write(text,key);
        }
    }
}


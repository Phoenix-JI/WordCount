
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



public class SwapMap extends Mapper<Object,Text,IntWritable,Text>{
    public void map(Object key,Text value,Context context) throws NumberFormatException, IOException, InterruptedException{
        String[] split = value.toString().split("\t");
        context.write(new IntWritable(Integer.parseInt(split[1])),new Text(split[0]) );
    }
}



intermedia_txt文件夹: input文件夹中每个html提出到的url

input_txt文件夹: 将intermedia_txt汇总成11个文件
（上述两个文件夹随便一个都可以直接放到代码里跑）

lib文件夹: 运行代码可能需要用到的jar包

output文件夹: 现阶段运行出来的结果
output.txt: output文件夹中part-r-00000文件对应的txt（还缺排序）

src文件夹: 源代码：1. HtmlParser：html中提取url
                              2. UrlFrequencyCount：mapreduce主函数
                              3. UrlMapper：mapper类
                              4. UrlReducer：reducer类
注意：运行mapreduce主函数前，要先修改对应的输入和输出路径



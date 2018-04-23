package com.cmclient.output;

//输出操作数据到三种文件的接口
public interface outputfile {
	void output2txt() throws Exception;
	void output2xml() throws Exception;
	void output2obj() throws Exception;
}

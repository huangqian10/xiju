package com.xiyoukeji.xiju.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;


@Service
public class FileService {
	
	
	Logger logger = Logger.getLogger(FileService.class);

	private String filePath;

	private String baseUrl;

	public String uploadFile(MultipartFile originFile, String uploadName)
			throws IllegalStateException, IOException {
		String originfileName = originFile.getOriginalFilename();
		String subfix = originfileName.substring(originfileName.indexOf("."),
				originfileName.length());
		String fileName = "";
		String url="";
		if(!StringUtils.isEmpty(uploadName)){
			url="xijufile/"+uploadName+"/"+ generateSequenceID()+subfix;
			fileName=filePath + url;

		}else{
			url= "xijufile/"+generateSequenceID()+subfix;
			fileName=filePath +url;
		}

		File file = new File(fileName);
		originFile.transferTo(file);
		return url;
	}
	public String GenerateImage(String imgStr,String uploadName)  
    {   //对字节数组字符串进行Base64解码并生成图片  
		
		//logger.info(imgStr);
		String filename="";
		String url="";
		if(!StringUtils.isEmpty(uploadName)){
			url="xijufile/"+uploadName+"/"+ generateSequenceID()+".png";
			filename=filePath + url;

		}else{
			url= "xijufile/"+generateSequenceID()+".png";
			filename=filePath +url;
		}
        if (!StringUtils.isEmpty(imgStr)){
        	if(imgStr.indexOf("data:image/png;base64,")>=0){
    			imgStr=imgStr.substring("data:image/png;base64,".length(),imgStr.length());
    		}
            BASE64Decoder decoder = new BASE64Decoder();  
            try   
            {  
                //Base64解码  
                byte[] b = decoder.decodeBuffer(imgStr);  
                for(int i=0;i<b.length;++i)  
                {  
                    if(b[i]<0)  
                    {//调整异常数据  
                        b[i]+=256;  
                    }  
                }  
     
                OutputStream out = new FileOutputStream(filename);      
                out.write(b);  
                out.flush();  
                out.close();  
                
            }   
            catch (Exception e)   
            {  
            	e.printStackTrace();
            	logger.info("base64TransToImagError");
            	filename="error";
            }
        }else{
        	filename="error";
        }
        return url;
    }  
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String generateSequenceID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "")
				.toUpperCase();
		String ranEight = String.format("%08d", new Random().nextInt(99999999));
		return uuid + ranEight;
	}

}

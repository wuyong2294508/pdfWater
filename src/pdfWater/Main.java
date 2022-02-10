package pdfWater;

import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.swing.JLabel;
import java.util.ArrayList;

import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Main {
	private static int interval = -5;   
	private static final String fontPath = "/pdfWater/楷体_GB2312.ttf";
	public static void waterMark(String inputFile, String outputFile, String waterMarkName) {    
        try {    
            PdfReader reader = new PdfReader(inputFile);    
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));    
              
            //BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   BaseFont.EMBEDDED);
            BaseFont base = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H,   BaseFont.EMBEDDED); 
              
            Rectangle pageRect = null;  
            PdfGState gs = new PdfGState();  
            gs.setFillOpacity(0.1f);  
            //gs.setStrokeOpacity(0.4f);  
            int total = reader.getNumberOfPages() + 1;   
              
            JLabel label = new JLabel();  
            FontMetrics metrics;  
            int textH = 0;   
            int textW = 0;   
            label.setText(waterMarkName);   
            metrics = label.getFontMetrics(label.getFont());   
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());  
                
            PdfContentByte under;    
            for (int i = 1; i < total; i++) {   
                pageRect = reader.getPageSizeWithRotation(i);    
                under = stamper.getOverContent(i);   
                under.saveState();  
                under.setGState(gs);  
                under.beginText();    
                under.setFontAndSize(base, 100);    
               
                // 水印文字成30度角倾斜  
                //你可以随心所欲的改你自己想要的角度
                for (int height = interval + textH; height < pageRect.getHeight();  
                        height = height + textH*10) {    //垂直方向
                    for (int width = interval + textW; width < pageRect.getWidth() + textW;   
                            width = width + textW*6) {  //水平方向
                under.showTextAligned(Element.ALIGN_LEFT  
                        , waterMarkName, width - textW,  
                        height - textH, 45);  
                    }  
                }  
                // 添加水印文字    
                under.endText();    
            }   
            //说三遍
           //一定不要忘记关闭流
          //一定不要忘记关闭流
          //一定不要忘记关闭流
            stamper.close();  
            reader.close();
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = args[0];
		String inFilePath = args[1];
		String outPath = args[2];
		//String filePath = "F:/Java/2020单位水印名单(1).csv";
		System.out.println("正在从" + filePath + "读取单位名称...");
		File file=new File(filePath);
		//StringBuilder result = new StringBuilder();
		ArrayList<String> results = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                results.add(s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println("读取单位名称完成!");
        for(int i=0;i < results.size();i++) {
        	System.out.print("正在生成" + results.get(i) + "...");
        	waterMark(inFilePath, outPath + i+ results.get(i) +".pdf", results.get(i));
        	System.out.println("完成！");
        }
        
        System.out.print("全部完成，结果请在" + outPath + "中查看！");
		//waterMark("F:/Java/aaa.pdf", "F:/Java/bbb.pdf", "退役军人事务");
        //while(true);
	}

}

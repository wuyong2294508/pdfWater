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
	private static final String fontPath = "/pdfWater/����_GB2312.ttf";
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
               
                // ˮӡ���ֳ�30�Ƚ���б  
                //��������������ĸ����Լ���Ҫ�ĽǶ�
                for (int height = interval + textH; height < pageRect.getHeight();  
                        height = height + textH*10) {    //��ֱ����
                    for (int width = interval + textW; width < pageRect.getWidth() + textW;   
                            width = width + textW*6) {  //ˮƽ����
                under.showTextAligned(Element.ALIGN_LEFT  
                        , waterMarkName, width - textW,  
                        height - textH, 45);  
                    }  
                }  
                // ���ˮӡ����    
                under.endText();    
            }   
            //˵����
           //һ����Ҫ���ǹر���
          //һ����Ҫ���ǹر���
          //һ����Ҫ���ǹر���
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
		//String filePath = "F:/Java/2020��λˮӡ����(1).csv";
		System.out.println("���ڴ�" + filePath + "��ȡ��λ����...");
		File file=new File(filePath);
		//StringBuilder result = new StringBuilder();
		ArrayList<String> results = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                results.add(s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println("��ȡ��λ�������!");
        for(int i=0;i < results.size();i++) {
        	System.out.print("��������" + results.get(i) + "...");
        	waterMark(inFilePath, outPath + i+ results.get(i) +".pdf", results.get(i));
        	System.out.println("��ɣ�");
        }
        
        System.out.print("ȫ����ɣ��������" + outPath + "�в鿴��");
		//waterMark("F:/Java/aaa.pdf", "F:/Java/bbb.pdf", "���۾�������");
        //while(true);
	}

}

package com.liuchao.myspringboot.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
@Controller
public class PdfView {

    @RequestMapping("/pdfView")
    public  void fillTemplate(HttpServletResponse response) {
        // 模板路径
        String templatePath = "E:\\print\\moban\\ylmoban.pdf";
        // 生成的新文件路径
        String newPDFPath = "E:\\print\\测试11.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        int page=1;
        List<PdfReader> list = new ArrayList();
        try {
            //for(int i=0;i<19;i++){
            reader = new PdfReader(templatePath);
            PdfReader pdfReader = null;
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            form.setField("test1","张三");
            form.setField("test2","总经理");
            form.setField("test4","德信养老院");
            form.setField("test5","其它");

            //true代表生成的PDF文件不可编辑
            stamper.setFormFlattening(true);
            stamper.close();
            pdfReader = new PdfReader(bos.toByteArray());
            list.add(pdfReader);

            //}
           // out = new FileOutputStream(newPDFPath);
            ServletOutputStream outputStream = response.getOutputStream();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, outputStream);
            doc.open();
            for (int k = 0; k < list.size(); k++) {
                PdfReader pdfReader1 = list.get(k);
                doc.newPage();
                copy.addDocument(pdfReader1);
            }
            copy.close();
            // PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            // copy.addPage(importPage);
            // doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        //fillTemplate();
    }

}

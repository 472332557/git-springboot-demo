package com.liangzc.example.gitspringbootdemo.export;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("/rest/v1")
public class ExportController {


    @GetMapping("/export")
    @ResponseBody
    public String export(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File("I:\\测试导出excel2020715.xlsx");
        OutputStream outputStream = null;
        InputStream inputStream= null;
        String name = URLEncoder.encode(file.getName(),"UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + name);
//        byte[] bytes = new byte[2048];
        try {
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int i;
            while ((i=inputStream.read()) != -1){
                outputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }

    @GetMapping("/exportExcel")
    @ResponseBody
    public String export1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream= null;
        String name = "测试导出excel2020715";
        name = URLEncoder.encode(name, "UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + name+".xlsx");
        List<String> lists = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("测试导出shht页");
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < lists.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(lists.get(i));
        }
        outputStream = response.getOutputStream();
        workbook.write(outputStream);
        return "SUCCESS";
    }


}

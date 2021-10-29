package com.liangzc.example.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/rest/v1")
public class ExportController {


    @GetMapping("/export")
    @ResponseBody
    public String export(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File("D:/receive-file/DOWNLOAD_PATH/应收账款汇总报表_2021051014125369869.xls");
        OutputStream outputStream = null;
        InputStream inputStream= null;
        String name = URLEncoder.encode(file.getName(),"UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + name);
        response.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("iso8859-1"), "UTF-8"));
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

        List<String> names = Arrays.asList("aa","bb","cc","dd","ee","ff","gg","hh","ii","jj");
        sheet.addMergedRegion(new CellRangeAddress(lists.size()+1,lists.size()+1,0,5));
        row = sheet.createRow(lists.size()+1);

        Cell cell = row.createCell(0);
        cell.setCellValue("合并了");

        Cell cell1 = row.createCell(6);
        cell1.setCellValue("gg");

        Cell cell2 = row.createCell(7);
        cell2.setCellValue("hh");

        Cell cell3 = row.createCell(8);
        cell3.setCellValue("ii");

        Cell cell4 = row.createCell(9);
        cell4.setCellValue("jj");
        outputStream = response.getOutputStream();
        workbook.write(outputStream);
        return "SUCCESS";
    }


    @GetMapping("/exportExcelWithThread")
    @ResponseBody
    public String exportWithThread(HttpServletResponse response) throws IOException {

        int initNum = 2;
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\receive-file\\DOWNLOAD_PATH\\测试导出excel2020715.xlsx");
        String name = "测试导出excel2020715";
        name = URLEncoder.encode(name, "UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + name+".xlsx");
        List<String> lists = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("测试导出shht页");

        int count = (int)Math.ceil((double) lists.size() /  (double) initNum);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                XSSFRow row;
                for (int i = 0; i < count; i++) {
                    for (int j = 0; i < lists.size(); j++) {
                        row = sheet.createRow(j + 1);
                        XSSFCell cell = row.createCell(0);
                        cell.setCellValue(lists.get(0));

                        XSSFCell cell1 = row.createCell(1);
                        cell1.setCellValue(lists.get(1));

                        XSSFCell cell2 = row.createCell(2);
                        cell2.setCellValue(lists.get(2));

                        XSSFCell cell3 = row.createCell(3);
                        cell3.setCellValue(lists.get(3));

                        XSSFCell cell4 = row.createCell(4);
                        cell4.setCellValue(lists.get(4));

                        XSSFCell cell5 = row.createCell(5);
                        cell5.setCellValue(lists.get(5));

                        XSSFCell cell6 = row.createCell(6);
                        cell6.setCellValue(lists.get(6));

                        XSSFCell cell7 = row.createCell(7);
                        cell7.setCellValue(lists.get(7));

                        XSSFCell cell8 = row.createCell(8);
                        cell8.setCellValue(lists.get(8));

                        XSSFCell cell9 = row.createCell(9);
                        cell9.setCellValue(lists.get(9));
                    }
                    try {
                        workbook.write(fileOutputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executorService.shutdown();
        fileOutputStream.close();

        return "success";
    }


}

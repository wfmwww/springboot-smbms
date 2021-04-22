package cn.smbms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import cn.smbms.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//导出excel
public class ExportExcelUtil {
    private final static String excel2003L =".xls";    //2003-
    private final static String excel2007U =".xlsx";   //2007+
    //abc.xls->E:\\.........\WEB-INF\abc.xls
    public static  File getExcelDemoFile(String fileDir) throws Exception{
        String classDir = null;
        String fileBaseDir = null;
        File file = null;
        //       classDir  =   E:\\.........\WEB-INF\classes
        classDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        fileBaseDir = classDir.substring(0, classDir.lastIndexOf("classes"));
        file = new File(fileBaseDir+fileDir);
        if(!file.exists()){
            throw new Exception("文件不存在！");
        }
        return file;
    }
    //返回一个workBook对象
    public static Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(excel2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("其他异常");
        }
        return wb;
    }
    public static Workbook writeNewExcel(File file,String sheetName,List<User> lis) throws Exception{
        Workbook wb = null;
        Row row = null;
        Cell cell = null;

        FileInputStream fis = new FileInputStream(file);

        wb = new ExportExcelUtil().getWorkbook(fis, file.getName());//��ȡ������
        Sheet sheet = wb.getSheet(sheetName);

        //ѭ���������

        CellStyle cs = setSimpleCellStyle(wb);
        int lastRow = sheet.getLastRowNum();    //������ݵ����ROW
        row = sheet.createRow(lastRow);//Excel��Ԫ����ʽ
        cell = row.createCell(0);
        cell.setCellValue("测试");
        cell.setCellStyle(cs);
        row = sheet.createRow(lastRow+1);//Excel��Ԫ����ʽ
        cell = row.createCell(0);
        cell.setCellValue("id");
        cell.setCellStyle(cs);

        cell = row.createCell(1);
        cell.setCellValue("编号");
        cell.setCellStyle(cs);

        cell = row.createCell(2);
        cell.setCellValue("用户名");
        cell.setCellStyle(cs);

        cell = row.createCell(3);
        cell.setCellValue("性别");
        cell.setCellStyle(cs);

        cell = row.createCell(4);
        cell.setCellValue("地址");
        cell.setCellStyle(cs);
        //�ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        for (int i = 0; i < lis.size(); i++) {
            row = sheet.createRow(lastRow+i+2); //�����µ�ROW��������ݲ���
            //����Ŀʵ�������ڸô���������ݲ��뵽Excel��
            User vo  = lis.get(i);
            if(null==vo){
                break;
            }
            //Cell��ֵ��ʼ
            cell = row.createCell(0);
            cell.setCellValue(vo.getId());
            cell.setCellStyle(cs);

            cell = row.createCell(1);
            cell.setCellValue(vo.getUserCode());
            cell.setCellStyle(cs);

            cell = row.createCell(2);
            cell.setCellValue(vo.getUserName());
            cell.setCellStyle(cs);

            cell = row.createCell(3);
            cell.setCellValue(vo.getGender());
            cell.setCellStyle(cs);

            cell = row.createCell(4);
            cell.setCellValue(vo.getAddress());
            cell.setCellStyle(cs);


        }
        return wb;
    }

    //实现导出
    public static  Workbook writeNewExcel(String sheetName,List<User> lis) throws Exception{
        Workbook wb = null;
        Row row = null;
        Cell cell = null;

//		FileInputStream fis = new FileInputStream(file);

//		wb = new ExportExcelUtil().getWorkbook(fis, file.getName());//��ȡ������
        wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
//		Sheet sheet = wb.getSheet(sheetName);

        //ѭ���������

        CellStyle cs = setSimpleCellStyle(wb);
        int lastRow = sheet.getLastRowNum();    //������ݵ����ROW
        row = sheet.createRow(lastRow);//Excel��Ԫ����ʽ
        cell = row.createCell(0);
        cell.setCellValue("测试");
        cell.setCellStyle(cs);
        row = sheet.createRow(lastRow+1);//Excel��Ԫ����ʽ
        cell = row.createCell(0);
        cell.setCellValue("id");
        cell.setCellStyle(cs);

        cell = row.createCell(1);
        cell.setCellValue("编号");
        cell.setCellStyle(cs);

        cell = row.createCell(2);
        cell.setCellValue("用户名");
        cell.setCellStyle(cs);

        cell = row.createCell(3);
        cell.setCellValue("性别");
        cell.setCellStyle(cs);

        cell = row.createCell(4);
        cell.setCellValue("地址");
        cell.setCellStyle(cs);
        //�ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        for (int i = 0; i < lis.size(); i++) {
            row = sheet.createRow(lastRow+i+2); //�����µ�ROW��������ݲ���
            //����Ŀʵ�������ڸô���������ݲ��뵽Excel��
            User vo  = lis.get(i);
            if(null==vo){
                break;
            }
            //Cell��ֵ��ʼ
            cell = row.createCell(0);
            cell.setCellValue(vo.getId());
            cell.setCellStyle(cs);

            cell = row.createCell(1);
            cell.setCellValue(vo.getUserCode());
            cell.setCellStyle(cs);

            cell = row.createCell(2);
            cell.setCellValue(vo.getUserName());
            cell.setCellStyle(cs);

            cell = row.createCell(3);
            cell.setCellValue(vo.getGender());
            cell.setCellStyle(cs);

            cell = row.createCell(4);
            cell.setCellValue(vo.getAddress());
            cell.setCellStyle(cs);


        }
        return wb;
    }


    /**
     * 设置样式
     * @return
     */
    public  static CellStyle setSimpleCellStyle(Workbook wb){
        CellStyle cs = wb.createCellStyle();

        cs.setBorderBottom(CellStyle.BORDER_THIN); //�±߿�
        cs.setBorderLeft(CellStyle.BORDER_THIN);//��߿�
        cs.setBorderTop(CellStyle.BORDER_THIN);//�ϱ߿�
        cs.setBorderRight(CellStyle.BORDER_THIN);//�ұ߿�
        cs.setAlignment(CellStyle.ALIGN_CENTER); // ����

        return cs;
    }

}


package modules.file;

import modules.detect.DetectByPing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Wuhall
 */
public class ExcelData {
    /**
     * 这里只能读取.xlsx文件格式
     * @param filePath
     * @throws IOException
     */
    public static void readExcel(String filePath) throws IOException {
        File xlsFile = new File(filePath);
        if(!xlsFile.exists()){
            throw new RuntimeException("文件不存在");
        }
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        String split = "   ";
        for (int i = 0;i < rows;i++){
            //第i 行
            XSSFRow row = sheet.getRow(i);
            //列数
            short nums = row.getLastCellNum();
            for(int j = 0;j < nums;j++){
                Cell cell = row.getCell(j);
                System.out.println(cell+split);
            }
            System.out.println();
        }
    }

    public static void readExcelPlus(String filePath,int index) throws IOException {
        File xlsFile = new File(filePath);
        String macStr = "";
        if(!xlsFile.exists()){
            throw new RuntimeException("文件不存在");
        }
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        //忽略表头从第2行开始
        for(int i = 1;i < rows;i++){
            macStr = "";
            XSSFRow row = sheet.getRow(i);
            //员工基本信息
            Cell name = row.getCell(0);
            Cell ip = row.getCell(index);
            boolean flag = DetectByPing.ping(ip.toString(),1,1000);
            if(flag){
                macStr = DetectByPing.getMac(ip.toString());
            }
            System.out.println(name+":"+ip+"__ping:"+flag+"__macaddr:"+macStr);
        }
    }

}

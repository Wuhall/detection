import modules.detect.DetectByPing;
import modules.file.ExcelData;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Wuhall
 */
public class Module {
    String ip = "192.168.131.9";

    @Test
    public void test1() throws Exception {
        boolean flag = DetectByPing.ping(ip);
        System.out.println(flag);
    }

    @Test
    public void test2() throws Exception {
        DetectByPing.ping02(ip);
    }

    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        boolean flag = DetectByPing.ping(ip,1,3000);
        long pasttime = System.currentTimeMillis()-start;
        System.out.println(flag+"花费时间"+pasttime/1000);
        BufferedReader in  = null;
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String path1 = "C:\\Users\\87883\\Desktop\\批量关联翔云编号导入模板.xlsx";
    String path2 = "E:\\Chrome_Download\\上线用户_20210720.xls";
    String path3 = "C:\\Users\\87883\\Desktop\\上线用户_20210720.xlsx";
    @Test
    public void test4() throws IOException {
        ExcelData.readExcel(path3);
    }

    @Test
    public void test5() throws IOException {
        ExcelData.readExcelPlus(path3,3);
    }

    @Test
    public void test6() {
        String str = DetectByPing.getMac("192.168.20.15");
        System.out.println(str);
    }

    @Test
    public void test7() {
        String str = "  192.168.20.15         ac-b5-7d-2c-4e-4b     ��̬  ";
        System.out.println(DetectByPing.filterMacAddress(ip,str,"-"));
    }
}

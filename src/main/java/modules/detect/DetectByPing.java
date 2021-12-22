package modules.detect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wuhall
 */
public class DetectByPing {
    public static boolean ping(String ip) {
        int timeOut = 3000;
        boolean status = false;
        try{
            status = InetAddress.getByName(ip).isReachable(timeOut);
        }catch(IOException e){
            e.printStackTrace();
        }
        return status;
    }

    public  static  void ping02(String ipAddress) {
        String line =  "";
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf =  new BufferedReader( new InputStreamReader(
                    pro.getInputStream()));
            while((line = buf.readLine()) !=  null)
                System.out.println(line);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public  static  boolean ping(String ipAddress,int pingTimes,int timeOut) {
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut; // windows格式的命令
        // String pingCommand = "ping" -c " + pingTimes + " -w " + timeOut + ipAddress; // Linux命令如下
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        try{
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in =  new BufferedReader( new InputStreamReader(p.getInputStream()));
            int connectedCount = 0;
            String line =  null;
            while ((line = in.readLine()) != null) {
                connectedCount += checkResult(line);
            }
            // 打印结果出现 ms TTL 字样
            return connectedCount == pingTimes;
        }catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }finally{
            try{
                if(in!=null) {
                    in.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMac(String ip) {
        String pingCmd = "arp -a " + ip; //win 命令
        Runtime r = Runtime.getRuntime();
        try {
            Process p = r.exec(pingCmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                if(line.indexOf(ip) > 0){
                    return filterMacAddress(ip,line,"-");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String filterMacAddress(String ip,String sourceString,String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while(matcher.find()){
            result = matcher.group(1);
            if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break; // 如果有多个IP,只匹配本IP对应的Mac.
            }
        }
        return result;
    }

    /**
     * @param line
     * @return 打印结果出现 ms TTL 字样返回1
     */
    private  static  int checkResult(String line) {
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            return 1;
        }
        return 0;
    }

}

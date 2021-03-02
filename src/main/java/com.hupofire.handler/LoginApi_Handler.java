package com.hupofire.handler;

import com.hupofire.controller.LoginApi_Controller;
import com.hupofire.util.DecodeUnicodeUtil;
import com.hupofire.util.ExcelUtil;
import com.hupofire.util.MobileApiToolsUtil;
import com.hupofire.util.StringUtil;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description;
 * @Author：Derrick
 * @Date：2021/2/28 12:25 下午
 */
public class LoginApi_Handler {
    private static Logger logger = Logger.getLogger(LoginApi_Handler.class);
    //接口用例标题所在行
    static int TITILE_LINE_INDEX= 5;
    //接口所需参数的个数
    static int ArgName_Number = 2;
    //接口需要校验参数个数
    static int Act_Number = 2;

    //接口参数
    static String Param = null;
    static String msg_Act = null;

    /**
     * 获取Excel数据
     */
    public static void GetExcelInstance(){
        ExcelUtil.getInstance().setFilePath("src/main/java/Hupofire.Api.xlsm");
        ExcelUtil.getInstance().setSheetName("Login");
    }

    public static void InitializeExcelData(){
        GetExcelInstance();
        int color = 4;
        System.out.println("开始初始化：" + ExcelUtil.getInstance().getFilePath() + "," + ExcelUtil.getInstance().getSheetName());
        try {
            MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX,""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX,3)+"","",color);
            System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX,3)+"列");
            if(Act_Number < 1){
                for(int i=1;i<Act_Number+6;i++){
                    MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX,""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX,3+ArgName_Number+i)+"","",color);
                    System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX,3+ArgName_Number+Act_Number+i)+"列");
                }
            }else if(Act_Number == 1){
                for(int i=1;i<Act_Number+6;i++){
                    MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX,""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX,3+ArgName_Number+i)+"","",color);
                    System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX,3+ArgName_Number+Act_Number+i)+"列");
                }
            }else if(Act_Number > 1){
                for(int i =1;i<Act_Number+1;i++){
                    if(StringUtil.isEqual(ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+2*i), ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+2*i+2))){
                        System.out.println("效验值："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+2*i)+"重复，请检查后重试！");
                        System.exit(0);
                    }else{
                        MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX, ""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+2*i)+"", "", color);
                        System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+2*i)+"列");
                    }
                }
                MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX, ""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+Act_Number*2+1)+"", "", color);
                System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+Act_Number*2+1)+"列");
                MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX, ""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+Act_Number*2+2)+"", "", color);
                System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+Act_Number*2+2)+"列");
                MobileApiToolsUtil.initializeData(TITILE_LINE_INDEX, ""+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+Act_Number*2+3)+"", "", color);
                System.out.println("正在初始化："+ExcelUtil.getInstance().readExcelCell(TITILE_LINE_INDEX, 3+ArgName_Number+Act_Number*2+3)+"列");
            }
            System.out.println(ExcelUtil.getInstance().getFilePath() + ", " + ExcelUtil.getInstance().getSheetName() + " 初始化完成");
            System.out.println("==================================================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <br>根据用例ID，执行Login相关接口请求，获取Json信息，并写入结果到Excel</br>
     */
    public static String Login(int ID) throws Exception {
        GetExcelInstance();
        boolean ArgName = false;
        List<Map<String, String>> data = null;
        String code = null;
        String ApiUrl = ExcelUtil.getInstance().readExcelCell(1, 2);
        String Act = ExcelUtil.getInstance().readExcelCell(2, 2);
        String Method = ExcelUtil.getInstance().readExcelCell(3, 2);
        ArgName = MobileApiToolsUtil.isArgEquals(4, 2, TITILE_LINE_INDEX);

        if (ApiUrl.equals("") || Act.equals("") || Method.equals("") || !ArgName) {
            logger.error("请检查 Excel 中 ApiUrl、Act、Method、ArgName 是否设置正确...");
            System.out.println("请检查 Excel 中 ApiUrl、Act、Method、ArgName 是否设置正确...");
            System.exit(-1);
        }

        data = ExcelUtil.getInstance().readExcelAllData(TITILE_LINE_INDEX);

        if (data != null) {
            int i = ID;
            //根据Excel列名称,获取单元格内容
            Map<String, String> map = data.get(i-1);//从第一个用例开始，0代表就是第一个
            String CaseID = map.get("CaseID");
            String CaseName = map.get("CaseName");
            String username = map.get("username");
            String password = map.get("password");

            //写入Run列, 执行纪录，Y代表已执行
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "Run", "Y");

            //指定请求的Api地址
            Param = "{\"authRequest\":{\"userName\":\"" + username + "\",\"password\": \"" + password + "\"}}";

            //请求接口，获取LoginInfo数组
            Object[] LoginInfo = LoginApi_Controller.GetAccessToken(ID,ApiUrl,Param);

            //从LoginInfo数组中，读取StatusCode并写入
            code = (String) LoginInfo[1];
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "StatusCode",code);

            //判断StatusCode结果是否等于200，成立则TestResult写入OK并设置单元格颜色为绿色，反则写入NG并设置单元格颜色为红色，并写入失败消息提示
            if ("200".equals(code)){
                ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "StatusCode",TITILE_LINE_INDEX + i, 1);
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "TestResult", "OK");
                ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "TestResult",TITILE_LINE_INDEX + i, 1);
                //从LoginInfo数组中，读取msg_Act结果并写入
                msg_Act = (String) LoginInfo[2];
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "msg_Act", msg_Act);
            }else if ("401".equals(code)){
                ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "StatusCode",TITILE_LINE_INDEX + i, 0);
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "TestResult", "NG");
                ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "TestResult",TITILE_LINE_INDEX + i, 0);
                //从LoginInfo数组中，读取message消息结果并写入
                String message = (String) LoginInfo[0];
                String NewMessage= DecodeUnicodeUtil.decodeUnicode(message);
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "FailHint", NewMessage);
                msg_Act = (String) LoginInfo[2];
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "msg_Act", msg_Act);
            }else{
                ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "StatusCode",TITILE_LINE_INDEX + i, 0);
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "TestResult", "NG");
                ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "TestResult",TITILE_LINE_INDEX + i, 0);
                //从LoginInfo数组中，读取message消息结果并写入
                msg_Act = (String) LoginInfo[2];
                MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "msg_Act", msg_Act);
            }

            //写入执行时间
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "RunningTime", MobileApiToolsUtil.getDate());

            //从LoginInfo数组中，读取JSON结果并编码转换后写入
            String Json = (String) LoginInfo[0];
            String NewJson=DecodeUnicodeUtil.decodeUnicode(Json);
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "Json", NewJson);

            if("login success".equals(msg_Act)){

                Reporter.log("用例ID: "+ CaseID);
                Reporter.log("用例名称: "+ CaseName);
                Reporter.log("请求地址: "+ ApiUrl);
                Reporter.log("请求方式: "+ Method);
                Reporter.log("请求参数: "+ Param);
                Reporter.log("返回结果: "+ NewJson);

            }else{
                Reporter.log("用例ID: "+ CaseID);
                Reporter.log("用例名称: "+ CaseName);
                Reporter.log("请求地址: "+ ApiUrl);
                Reporter.log("请求方式: "+ Method);
                Reporter.log("请求参数: "+ Param);
                Reporter.log("返回结果: "+ NewJson);

                String msg_Exp = map.get("msg_Exp");
                checkEquals(msg_Exp,msg_Act,"msg_Exp","msg_Act",ID);
            }
        }

        return code;
    }

    /**
     * 登录获取access_token
     * @param ID
     * @return
     * @throws Exception
     */
    public static String LoginToAccessToken(int ID) throws Exception {
        GetExcelInstance();
        boolean ArgName = false;
        List<Map<String, String>> data = null;
        String code = null;
        String access_token = "";

        String ApiUrl = ExcelUtil.getInstance().readExcelCell(1, 2);
        String Act = ExcelUtil.getInstance().readExcelCell(2, 2);
        String Method = ExcelUtil.getInstance().readExcelCell(3, 2);
        ArgName = MobileApiToolsUtil.isArgEquals(4, 2, TITILE_LINE_INDEX);

        if (ApiUrl.equals("") || Act.equals("") || Method.equals("") || !ArgName) {
            logger.error("请检查 Excel 中 ApiUrl、Act、Method、ArgName 是否设置正确...");
            System.out.println("请检查 Excel 中 ApiUrl、Act、Method、ArgName 是否设置正确...");
            System.exit(-1);
        }

        data = ExcelUtil.getInstance().readExcelAllData(TITILE_LINE_INDEX);

        if (data != null) {
            int i = ID;
            //根据Excel列名称,获取单元格内容
            Map<String, String> map = data.get(i - 1);//从第一个用例开始，0代表就是第一个
            String CaseID = map.get("CaseID");
            String CaseName = map.get("CaseName");
            String username = map.get("username");
            String password = map.get("password");

            //指定请求的Api地址
            Param = "{\"authRequest\":{\"userName\":\"" + username + "\",\"password\": \"" + password + "\"}}";

            //请求接口，获取LoginInfo数组
            access_token = LoginApi_Controller.GetAccessTokenInfo(ApiUrl, Param);

        }
            return access_token;
    }

    /**
     * <br>根据用例ID，检查预期与实际是否相等，不等则提示错误信息，并写入结果</br>
     */
    public static void checkEquals(String Expected,String Actual,String ExpectedList,String ActualList,int ID) throws Exception{
        int i = ID;
        try {
            Assert.assertEquals(Expected,Actual);
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "TestResult", "OK");
            ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "TestResult",TITILE_LINE_INDEX + i, 1);
            Reporter.log("用例结果: 〖"+ExpectedList.replace("_Exp", "")+"〗=>Expected: " + "【"+Expected+"】" + ", Actual: "+ "【"+Actual+"】");
            System.out.println("用例结果: 【"+ExpectedList.replace("_Exp", "")+"】=>Expected: " + "【"+Expected+"】" + ", Actual: "+ "【"+Actual+"】");
        }catch (Error e)  {
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "TestResult", "NG");
            ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, "TestResult",TITILE_LINE_INDEX + i, 0);
            ExcelUtil.getInstance().setCellBackgroundColor(TITILE_LINE_INDEX, ActualList,TITILE_LINE_INDEX + i, 3);
            MobileApiToolsUtil.writeResult(TITILE_LINE_INDEX, TITILE_LINE_INDEX + i, "FailHint", "【"+ExpectedList+"】和【"+ActualList+"】不一致");
            Assert.fail(""+ExpectedList.replace("_Exp", "")+" =>Expected 【"+ Expected +"】"+" "+"but found 【"+ Actual +"】");
        }
    }

    /**
     * <br>根据用例ID，获取用例名称信息，打印到日志和控制台</br>
     */
    public static void GetCaseInfo(int ID) throws Exception {
        GetExcelInstance();
        List<Map<String, String>> data = null;
        data = ExcelUtil.getInstance().readExcelAllData(TITILE_LINE_INDEX);
        if (data != null) {
            int i = ID;
            //根据Excel列名称,获取单元格内容
            Map<String, String> map = data.get(i-1);
            String CaseID = map.get("CIaseID");
            String CaseName = map.get("CaseName");

            String msg_Exp = map.get("msg_Exp");
            String msg_Act = map.get("msg_Act");

            String StatusCode = map.get("StatusCode");
            String TestResult = map.get("TestResult");
            String FailHint = map.get("FailHint");
            //打印日志
            logger.info("CaseID: " + CaseID + ", CaseName: " + CaseName +
                    ", msg_Exp: " + msg_Exp +", msg_Act: " + msg_Act+
                    ", StatusCode: " + StatusCode +", TestResult: " + TestResult + ", FailHint: " + FailHint + "");
            logger.info("==================================================================");

            System.out.println("CaseID: " + CaseID + ", CaseName: " + CaseName +
                    ", msg_Exp: " + msg_Exp +", msg_Act: " + msg_Act+
                    ", StatusCode: " + StatusCode +", TestResult: " + TestResult + ", FailHint: " + FailHint + "");
            System.out.println("==================================================================");
        }
    }
}

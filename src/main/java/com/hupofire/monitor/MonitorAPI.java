package com.hupofire.monitor;

import com.hupofire.handler.LoginApi_Handler;
import com.hupofire.handler.LogoutApi_Handler;
import com.hupofire.handler.MenuListApi_Handler;
import com.hupofire.util.PropertiesReader;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * @description;
 * @Author：Derrick
 * @Date：2021/3/2 11:47 上午
 */
public class MonitorAPI {

    private static Logger logger = Logger.getLogger(MonitorAPI.class);
    //接口用例标题所在行
    static int TITILE_LINE_INDEX= 5;
    //接口所需参数的个数
    static int ArgName_Number = 0;
    //接口需要校验参数个数
    static int Act_Number = 0;

    static String[] split = null;
    static Properties prop = null;

    static BufferedWriter bufferedWriter = null;
    static {
        try {
            prop = PropertiesReader.readProperties("src/main/java/api.properties");
            String api_name = prop.getProperty("api_name");
            split = api_name.split(",");

            bufferedWriter = new BufferedWriter(new FileWriter("Logs/api_result.csv",true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void monitor() throws Exception {

        for(int i=0;i<split.length;i++){
            int caseNum = Integer.parseInt(prop.getProperty(split[i]+".cases"));

            if("Login".equals(split[i])){

                LoginApi_Handler.InitializeExcelData();
                for(int n=0;n<caseNum;n++){
                    System.out.println("当前Api Name=" + split[i] + "，执行第" + (n+1) + "条用例");
                    String login = LoginApi_Handler.Login(n+1);
                    logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + ",heartTester,login api,"
                            + login
                            + ",v1");
                    bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + ",heartTester,login api,"
                            + login
                            + ",v1");
                    bufferedWriter.flush();
                    bufferedWriter.newLine();

                }
            } else if("Menu".equals(split[i])){
                MenuListApi_Handler.InitializeExcelData();
                String msg_Exp_1 = "{\"breakfast\":[{\"menu_name\":\"Steamed Dumplings\",\"menu_nunber\":\"01\",\"menu_price\":5.5},{\"menu_name\":\"mixed congee\",\"menu_nunber\":\"02\",\"menu_price\":3.0},{\"menu_name\":\"Deep-Fried Dough Sticks\",\"menu_nunber\":\"03\",\"menu_price\":1.5},{\"menu_name\":\"Tea egg\",\"menu_nunber\":\"04\",\"menu_price\":1.0},{\"menu_name\":\"Doubao\",\"menu_nunber\":\"05\",\"menu_price\":1.5},{\"menu_name\":\"Clay oven rolls\",\"menu_nunber\":\"06\",\"menu_price\":2.0}],\"code\":\"200\",\"dinner\":[{\"menu_name\":\"Stir fried pork with chili\",\"menu_nunber\":\"13\",\"menu_price\":21.0},{\"menu_name\":\"Sliced pork soup with peas\",\"menu_nunber\":\"14\",\"menu_price\":15.0},{\"menu_name\":\"Original pork rolls\",\"menu_nunber\":\"15\",\"menu_price\":26.0},{\"menu_name\":\"Sour cabbage\",\"menu_nunber\":\"16\",\"menu_price\":20.0},{\"menu_name\":\"Sauteed Potato, Green Pepper and Eggplant\",\"menu_nunber\":\"17\",\"menu_price\":25.5},{\"menu_name\":\"Spareribs with brown sauce\",\"menu_nunber\":\"18\",\"menu_price\":39.0}],\"lunch\":[{\"menu_name\":\"Kung Pao Chicken\",\"menu_nunber\":\"07\",\"menu_price\":17.0},{\"menu_name\":\"double cooked pork slices\",\"menu_nunber\":\"08\",\"menu_price\":25.0},{\"menu_name\":\"Sweet and Sour Spare Ribs\",\"menu_nunber\":\"09\",\"menu_price\":35.0},{\"menu_name\":\"Fried pork slices with garlic\",\"menu_nunber\":\"10\",\"menu_price\":30.0},{\"menu_name\":\"sour and spicy shredded potatoes\",\"menu_nunber\":\"11\",\"menu_price\":25.5},{\"menu_name\":\"Braised eggplant\",\"menu_nunber\":\"12\",\"menu_price\":32.0}]}";
                String msg_Exp_2 = "[{\"menu_name\":\"Stir fried pork with chili\",\"menu_nunber\":\"13\",\"menu_price\":21.0},{\"menu_name\":\"Sliced pork soup with peas\",\"menu_nunber\":\"14\",\"menu_price\":15.0},{\"menu_name\":\"Original pork rolls\",\"menu_nunber\":\"15\",\"menu_price\":26.0},{\"menu_name\":\"Sour cabbage\",\"menu_nunber\":\"16\",\"menu_price\":20.0},{\"menu_name\":\"Sauteed Potato, Green Pepper and Eggplant\",\"menu_nunber\":\"17\",\"menu_price\":25.5},{\"menu_name\":\"Spareribs with brown sauce\",\"menu_nunber\":\"18\",\"menu_price\":39.0}]";
                String msg_Exp_3 = "null";
                String msg_Exp_4 = "null";
                String msg_Exp_5 = "null";
                String msg_Exp_6 = "Unknownuserinfo,pleasere-login.";
                String msg_Exp_7 = "Unknownuserinfo,pleasere-login.";
                String msg_Exp_8 = "Unknownuserinfo,pleasere-login.";

                ArrayList<String> msg_Exp = new ArrayList<>();
                msg_Exp.add(msg_Exp_1);
                msg_Exp.add(msg_Exp_2);
                msg_Exp.add(msg_Exp_3);
                msg_Exp.add(msg_Exp_4);
                msg_Exp.add(msg_Exp_5);
                msg_Exp.add(msg_Exp_6);
                msg_Exp.add(msg_Exp_7);
                msg_Exp.add(msg_Exp_8);

                for(int n=0;n<caseNum;n++){
                    System.out.println("当前Api Name=" + split[i] + "，执行第" + (n+1) + "条用例");
                    String access_token = "";
                    if (n==5 || n==6 || n==7){
                        access_token = LoginApi_Handler.LoginToAccessToken(2);
                    } else {
                        access_token = LoginApi_Handler.LoginToAccessToken(1);
                    }
                    String menuInfo = MenuListApi_Handler.MenuList(n+1, access_token,msg_Exp.get(n));
                    logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + ",heartTester,menu api,"
                            + menuInfo
                            + ",v1");
                    bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + ",heartTester,menu api,"
                            + menuInfo
                            + ",v1");
                    bufferedWriter.flush();
                    bufferedWriter.newLine();
                }
            } else if("Logout".equals(split[i])){
                LogoutApi_Handler.InitializeExcelData();
                String msg_Exp_1 = "logout success";
                String msg_Exp_2 = "Unknown user info, logout fail.";
                String msg_Exp_3 = "Unknown user info, logout fail.";

                ArrayList<String> msg_Exp = new ArrayList<>();
                msg_Exp.add(msg_Exp_1);
                msg_Exp.add(msg_Exp_2);
                msg_Exp.add(msg_Exp_3);

                for(int m=0;m<caseNum;m++){
                    System.out.println("当前Api Name=" + split[i] + "，执行第" + (m+1) + "条用例");
                    String access_token = "";
                    if(m==1 || m==2){
                        access_token = LoginApi_Handler.LoginToAccessToken(2);
                    } else {
                        access_token = LoginApi_Handler.LoginToAccessToken(1);
                    }
                    String menuInfo = LogoutApi_Handler.LogoutList(m+1, access_token,msg_Exp.get(m));
                    logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + ",heartTester,menu api,"
                            + menuInfo
                            + ",v1");
                    bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            + ",heartTester,menu api,"
                            + menuInfo
                            + ",v1");
                    bufferedWriter.flush();
                    bufferedWriter.newLine();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        while(true){
            monitor();
            Thread.sleep(1000*60*5);
        }
    }
}

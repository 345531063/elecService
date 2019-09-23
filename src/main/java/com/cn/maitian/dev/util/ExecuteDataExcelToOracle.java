package com.cn.maitian.dev.util;

import com.alibaba.druid.util.StringUtils;
import com.cn.maitian.dev.entity.WxUserInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExecuteDataExcelToOracle {
 
        public static void main(String[] args) {
 
            try {
                String filePath = "C:\\Users\\lenovo\\Desktop\\temp\\user_info.xlsx";
                String extString = filePath.substring(filePath.lastIndexOf("."));
                InputStream in = new FileInputStream(filePath);
                List<WxUserInfo> datas = loadExcel(extString,in);//需要替换
                batchInsert(datas);

            } catch (Exception e) {
                e.printStackTrace();
            }
 
        }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            LogUtil.error(ExecuteDataExcelToOracle.class, "文件名不是excel格式");
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
        /**
         * 获取excel中的数据
         * @param filepath
         * @return
         * @throws Exception
         */
        public static List<WxUserInfo> loadExcel(String extString, InputStream is) throws Exception{
            Workbook wb = null;
            if(".xls".equals(extString)){
                 wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                 wb = new XSSFWorkbook(is);
            }else{
                 wb = null;
            }
            Sheet sheet = wb.getSheetAt(0);
            List<WxUserInfo> datas = new ArrayList<>();//将数据添加到数据一行一行的添加到集合中，作为插入数据的入参
            Row row = null;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                System.out.println("i="+i);
                row = sheet.getRow(i);
                if(row == null || StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
                    continue;
                }
                WxUserInfo data = new WxUserInfo();
                data.setId(StrUtils.generate(""));
                //如果公司名称不存在 如果发现员工存在 则删除之前的 保存现在的
                data.setWxNickname(row.getCell(0) == null? "":row.getCell(0).getStringCellValue());
                data.setJobNum(row.getCell(1) == null? "":row.getCell(1).getStringCellValue());
                datas.add(data);
            }
            return datas;
        }
         
        /**
         * 批量执行插入数据
         * @param datas
         */
        public static void batchInsert(List<WxUserInfo> datas){
            long startTime = System.currentTimeMillis();
 
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver"); //如果是oracle就不需要替换，如果是mysql就需要替换
                conn= DriverManager.getConnection("jdbc:mysql://47.108.88.118:3306/dfny", "root","12345678"); //需要替换
                conn.setAutoCommit(false);
 
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO wx_user_info(id,wx_nickname,job_num) VALUES (?,?,?)"
                ); //需要替换

                int num = 0;
                for (WxUserInfo v : datas) {
                    num++;
                    stmt.setString(1,v.getId());
                    stmt.setString(2, v.getWxNickname());
                    stmt.setString(3, v.getJobNum());
                    stmt.addBatch();
                    // 每5万，提交一次
                    if (num > 50000) {
                        stmt.executeBatch();
                        conn.commit();
                        num = 0;
                    }
                }
                stmt.executeBatch();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis();
                System.out.println("方法执行时间：" + (endTime - startTime) + "ms");
            }
        }
}
         
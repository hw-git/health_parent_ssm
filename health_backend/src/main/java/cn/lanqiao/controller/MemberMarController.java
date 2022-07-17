package cn.lanqiao.controller;


import cn.lanqiao.constant.MessageConstant;
import cn.lanqiao.constant.MobileConstant;
import cn.lanqiao.entity.PageResult;
import cn.lanqiao.entity.QueryPageBean;
import cn.lanqiao.entity.Result;
import cn.lanqiao.pojo.Member;
import cn.lanqiao.service.MemberMarService;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.utils.DateUtils;
import cn.lanqiao.utils.MD5Utils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hou
 * @Date: 2021/5/21 10:55
 * @Description:会员管理
 */
@RestController
@RequestMapping("/memberMar")
public class MemberMarController {
    //注入服务接口
    @Autowired
    private MemberMarService memberMarService;
    @Autowired
    private MemberService memberService;

    /**
     * 分页
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = memberMarService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 编辑表单数据回显
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Member member = memberMarService.findById(id);
            return new Result(true, "查询成功", member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "查询失败");
        }
    }

    /**
     * 修改会员数据
     *
     * @param m
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Member m) {
        Member member = memberService.findByTelephone(m.getPhoneNumber());
        if (member != null && !m.getId().equals(member.getId())) {
            return new Result(false, MobileConstant.TELEPHONE_REGISTERED);
        }
        m.setRegTime(new Date());
        String idCard = m.getIdCard();
        Map map = DateUtils.getBirAgeSex(idCard);
        m.setAge(Integer.parseInt((String) map.get("age")));
        m.setSex((String) map.get("sexCode"));
        Date bir = null;
        try {
            bir = DateUtils.parseString2Date((String) map.get("birthday"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setBirthday(bir);
        try {
            memberMarService.edit(m);
            return new Result(true, MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    /**
     * 新增
     *
     * @param m
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Member m) throws Exception {
        Member member = memberService.findByTelephone(m.getPhoneNumber());
        if (member != null) {
            return new Result(false, MobileConstant.TELEPHONE_REGISTERED);
        }
        m.setRegTime(new Date());
        String idCard = m.getIdCard();
        Map map = DateUtils.getBirAgeSex(idCard);
        m.setAge(Integer.parseInt((String) map.get("age")));
        m.setSex((String) map.get("sexCode"));
        Date bir = null;
        try {
            bir = DateUtils.parseString2Date((String) map.get("birthday"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setBirthday(bir);
        String password = m.getPassword();
        if (password != null) {
            //使用md5将明文密码进行加密
            password = MD5Utils.md5(password);
            m.setPassword(password);
        } else {
            //使用md5将明文密码进行加密
            password = MD5Utils.md5("123456");
            m.setPassword(password);
        }
        try {
            memberMarService.add(m);
            return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            memberMarService.delete(id);
            return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_MEMBER_FAIL);
        }
    }

    /**
     * 全部删除
     *
     * @param members
     * @return
     */
    @RequestMapping("/deleteSelect")
    public Result deleteSelect(@RequestBody List<Member> members) {
        List<Integer> ids = new ArrayList<>();
        for (Member member : members) {
            ids.add(member.getId());
        }
        try {
            memberMarService.deleteSelect(ids);
            return new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_MEMBER_FAIL);
        }
    }

    @RequestMapping("/getIds")
    public Result getIds(@RequestBody List<Member> members) {
        try {
            List<Integer> ids = new ArrayList<>();
            for (Member member : members) {
                ids.add(member.getId());
            }
            return new Result(true, "查询成功", ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "查询失败");
        }
    }

    @RequestMapping("/exportMember")
    public Result exportMember(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        XSSFWorkbook workbook = null;
        ServletOutputStream outputStream = null;
        try {
            List<Member> list = null;
            //判断，如果ids为空，则导出所有
            if (ids != null && ids.length > 0) {
                list = memberMarService.findByIds(ids);
            } else {
                list = memberMarService.findAll();
            }

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet sheet = xssfWorkbook.createSheet("会员");

            //创建表头
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("编号");
            row.createCell(1).setCellValue("档案号");
            row.createCell(2).setCellValue("姓名");
            row.createCell(3).setCellValue("性别");
            row.createCell(4).setCellValue("年龄");
            row.createCell(5).setCellValue("电子邮箱");

            int indexRow = 1;
            int index = 1;
            for (Member member : list) {
                XSSFRow row1 = sheet.createRow(indexRow);
                row1.createCell(0).setCellValue(index);//档案号
                row1.createCell(1).setCellValue(member.getFileNumber());//档案号
                row1.createCell(2).setCellValue(member.getName());//姓名
                row1.createCell(3).setCellValue(member.getSex());//性别
                row1.createCell(4).setCellValue(member.getPhoneNumber());//手机
                row1.createCell(5).setCellValue(member.getEmail());//电子邮箱
                indexRow++;
                index++;
            }
            //获取输出流，写出数据
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");

            xssfWorkbook.write(outputStream);

            outputStream.flush();


            return null;


        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


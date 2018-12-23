package cn.doublehh.service;

import cn.doublehh.sport.model.Grade;

import java.util.List;

/**
 * @author 胡昊
 * Description: 微信service
 * Date: 2018/12/24
 * Time: 0:22
 * Create: DoubleH
 */
public interface IWxService {
    /**
     * 上传成绩消息提醒
     *
     * @param gradeList 成绩列表
     * @return 发送失败的列表
     */
    List<Grade> sendUploadGradeMsg(List<Grade> gradeList);
}

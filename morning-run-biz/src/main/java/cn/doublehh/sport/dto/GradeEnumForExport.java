package cn.doublehh.sport.dto;

/**
 * @author 胡昊
 * Description:成绩导出枚举
 * Date: 2019/1/23
 * Time: 10:40
 * Create: DoubleH
 */
public enum GradeEnumForExport {
    JNCJ("技能成绩", 2), LLCJ("理论成绩", 3), ZCFS("早操分数", 4), ZCCS("早操次数", 5), TNCF("体能分数", 6), JTYZ("技体验自", 7),
    TCZF("体测总分", 8), ZCDL("早操锻炼", 9), JNJX("技能教学", 10), TNJX("体能教学", 11), YDTY("运动体验", 12), ZZYD("自主运动", 13),
    GFJY("国防教育", 14), GFJN("国防技能", 15), GFLL("国防理论", 16), SG("身高", 17), TZ("体重", 18), FHL("肺活量", 19),
    RUN50("50米跑", 20), ZWTQQ("坐位体前屈", 21), LDTY("立定跳远", 22), YTXS("引体向上", 23), YWQZ("仰卧起坐", 24),
    RUN800("800米跑", 25), RUN1000("1000米跑", 26), OTHER("未知", 27);
    private String itemName;
    private Integer index;

    GradeEnumForExport(String itemName, int index) {
        this.itemName = itemName;
        this.index = index;
    }

    public static String getItemName(Integer index) {
        for (GradeEnumForExport g : GradeEnumForExport.values()) {
            if (g.getIndex() == index) {
                return g.getItemName();
            }
        }
        return null;
    }

    public static Integer getIndex(String itemName) {
        for (GradeEnumForExport g : GradeEnumForExport.values()) {
            if (itemName.equals(g.getItemName())) {
                return g.index;
            }
        }
        return 27;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getIndex() {
        return index;
    }
}

package cn.doublehh.sport.model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-17
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目编号
     */
    private String itemNumber;

    /**
     * 类别编码
     */
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item{" +
        "id=" + id +
        ", itemName=" + itemName +
        ", itemNumber=" + itemNumber +
        ", type=" + type +
        "}";
    }
}

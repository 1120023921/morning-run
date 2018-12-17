package cn.doublehh.sport.service;

import cn.doublehh.sport.model.Item;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-17
 */
public interface ItemService extends IService<Item> {

    /**
     * 根据项目类型和项目编号查询项目信息
     *
     * @param type
     * @param itemNumber
     * @return
     */
    Item getItem(String type, String itemNumber);
}

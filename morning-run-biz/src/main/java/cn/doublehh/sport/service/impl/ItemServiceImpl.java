package cn.doublehh.sport.service.impl;

import cn.doublehh.sport.model.Item;
import cn.doublehh.sport.dao.ItemMapper;
import cn.doublehh.sport.service.ItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 胡昊
 * @since 2018-12-17
 */
@Service
@Slf4j
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Item getItem(String type, String itemNumber) {
        log.info("ItemServiceImpl [getItem] 根据项目类型和项目编号查询项目信息 type=" + type + " itemNumber=" + itemNumber);
        QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>()
                .eq("type", type)
                .eq("item_number", itemNumber);
        Item item = itemMapper.selectOne(queryWrapper);
        return item;
    }
}

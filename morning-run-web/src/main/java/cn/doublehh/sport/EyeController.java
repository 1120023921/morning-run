package cn.doublehh.sport;


import cn.doublehh.common.annotation.NeedPermission;
import cn.doublehh.common.pojo.ErrorCodeInfo;
import cn.doublehh.common.utils.DesUtil;
import cn.doublehh.sport.model.Eye;
import cn.doublehh.sport.service.EyeService;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 胡昊
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/eye")
public class EyeController {

    @Autowired
    private EyeService eyeService;

    @PostMapping("/insertEye")
    public R insertEye(@RequestBody Eye eye) {
        eye.setUserId(DesUtil.decrypt(eye.getUserId()));
        return R.restResult(eyeService.insertEye(eye), ErrorCodeInfo.SUCCESS);
    }

    @NeedPermission(roleIds = {"admin", "teacher"})
    @PostMapping("/queryEye")
    public R queryEye(Integer pageNum, Integer pageSize, @RequestBody Eye eye) {
        return R.restResult(eyeService.queryEye(pageNum, pageSize, eye), ErrorCodeInfo.SUCCESS);
    }

    @NeedPermission(roleIds = {"admin", "teacher"})
    @PostMapping("/reviewEye")
    public R reviewEye(@RequestBody Eye eye) {
        return R.restResult(eyeService.reviewEye(eye), ErrorCodeInfo.SUCCESS);
    }

    @GetMapping("/getUserEye")
    public R getUserEye(String jobNumber) {
        return R.restResult(eyeService.getUserEye(DesUtil.decrypt(jobNumber)), ErrorCodeInfo.SUCCESS);
    }
}


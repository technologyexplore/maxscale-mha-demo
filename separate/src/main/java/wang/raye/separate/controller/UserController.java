package wang.raye.separate.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wang.raye.separate.config.DynamicDataSource;
import wang.raye.separate.model.User;
import wang.raye.separate.model.WebResult;
import wang.raye.separate.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("add")
    public WebResult add(User user){
        if(service.addUser(user)){
            return WebResult.success();
        }else{
            return WebResult.error("添加失败");
        }
    }

    @RequestMapping("update")
    public WebResult update(User user){
        if(service.updateUser(user)){
            return WebResult.success();
        }else{
            return WebResult.error("更新失败");
        }
    }

    @RequestMapping("delete")
    public WebResult delete(int id){
        if(service.deleteByid(id)){
            return WebResult.success();
        }else{
            return WebResult.error("删除失败");
        }
    }

    @RequestMapping("selectByMaxScale")
    public WebResult selectByMaxScale(){
    	Date startTime = new Date();
    	List<User> list = service.selectAll();
    	Date endTime = new Date();
    	System.out.println("selectByMaxScale:" + (endTime.getTime() - startTime.getTime()));
        return WebResult.success(list);
    }
    
    //敏感查询
    @RequestMapping("selectByMaster")
    public WebResult selectByMaster(){
    	Date startTime = new Date();
    	DynamicDataSource.master();
    	List<User> list = service.selectAll();
    	DynamicDataSource.maxscale();
    	Date endTime = new Date();
    	System.out.println("selectByMaster:" + (endTime.getTime() - startTime.getTime()));
        return WebResult.success(list);
    }
    
    @RequestMapping("selectServerId")
    public WebResult selectServerId(){
    	service.selectServerId();
    	return WebResult.success(service.selectServerId());
    }

    @RequestMapping("test")
    public WebResult test(User user){
        try {
            if(service.insertAndUpdate(user)) {
                return WebResult.success();
            }else {
                return WebResult.error("失败,查看事物");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WebResult.error("失败:"+e.getMessage());
        }
    }
}

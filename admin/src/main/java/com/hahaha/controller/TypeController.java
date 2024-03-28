package com.hahaha.controller;


import com.hahaha.entity.TVideotype;
import com.hahaha.result.Result;
import com.hahaha.service.TVideotypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 视频类型管理
 */
@RestController
@RequestMapping("/video/type")
public class TypeController {


    @Autowired
    private TVideotypeService videotypeService;

    /**
     * 根据类型获取分页类表
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result getVideoTypeList(Integer pageNum, Integer pageSize, TVideotype type) {

        return Result.okResult(videotypeService.getVideoTypeList(pageNum,pageSize,type));
    }

    /**
     * 获取全部分类
     * @return
     */
    @GetMapping("/listAllCategory")
    @PreAuthorize("@ps.hasPermission('content:type:list')")
    public Result getAllVideoTypeList() {

        return Result.okResult(videotypeService.list());
    }

    /**
     * 新增类别
     * @param type
     * @return
     */
    @PostMapping
    public Result add(@RequestBody TVideotype type){
        videotypeService.save(type);
        return Result.okResult();
    }

    /**
     * 删除类别
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        videotypeService.removeById(id);
        return Result.okResult();
    }

    /**
     * 获取指定类别
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable(value = "id")Long id){
        TVideotype type = videotypeService.getById(id);
        return Result.okResult(type);
    }

    /**
     * 修改类别
     * @param type
     * @return
     */
    @PutMapping
    public Result edit(@RequestBody TVideotype type){
        videotypeService.updateById(type);
        return Result.okResult();
    }



}

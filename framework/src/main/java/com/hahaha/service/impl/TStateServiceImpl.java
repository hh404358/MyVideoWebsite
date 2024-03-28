package com.hahaha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.entity.TState;
import com.hahaha.mapper.TStateMapper;
import com.hahaha.service.TStateService;
import org.springframework.stereotype.Service;

/**
 * (TState)表服务实现类
 *
 * @author hahaha
 * @since 2024-03-16 16:12:08
 */
@Service("tStateService")
public class TStateServiceImpl extends ServiceImpl<TStateMapper, TState> implements TStateService {

}

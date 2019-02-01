package com.buildupchao.zns.service.provider.service;

import com.buildupchao.zns.server.annotation.ZnsService;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author buildupchao
 *         Date: 2019/2/1 11:33
 * @since JDK 1.8
 */
@ZnsService
@Service
@Data
public class TestService {

    String name = "test_name";
}

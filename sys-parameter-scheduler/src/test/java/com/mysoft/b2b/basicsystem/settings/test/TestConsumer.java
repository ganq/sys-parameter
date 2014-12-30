package com.mysoft.b2b.basicsystem.settings.test;

import com.mysoft.b2b.basicsystem.settings.api.DictionaryService;
import com.mysoft.b2b.basicsystem.settings.api.ProviderType;
import com.mysoft.b2b.basicsystem.settings.api.SupplierTag;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Think on 2014/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/spring/*.xml" })
public class TestConsumer {

    private static final Logger logger = Logger
            .getLogger(TestConsumer.class);

    @Autowired
    private DictionaryService dictionaryService;


    @Test
    public void ttt() {
        List<SupplierTag> supplierTagTypes = dictionaryService.getSupplierTagTypes();
        List<ProviderType> providerTypes = dictionaryService.getProviderTypes();

        assertTrue(supplierTagTypes.size() > 0);
    }
}

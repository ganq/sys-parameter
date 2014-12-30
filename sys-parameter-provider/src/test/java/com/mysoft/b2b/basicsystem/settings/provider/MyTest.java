package com.mysoft.b2b.basicsystem.settings.provider;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysoft.b2b.basicsystem.settings.test.BaseTestCase;

/**
 * Created by Think on 2014/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest  extends BaseTestCase {

    @Test
    public void genOids() {
        for(int i =0; i < 20; i++) {
            System.out.println(new ObjectId().toString());
        }
    }
}

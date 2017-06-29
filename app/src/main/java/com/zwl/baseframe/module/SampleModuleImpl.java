package com.zwl.baseframe.module;


import com.zwl.baseframe.business.sample.SampleBean;
import com.zwl.baseframe.model.SampleModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * @author velen
 * @date 2017/3/23
 */
public class SampleModuleImpl implements ISampleModule {

    @Inject
    public SampleModuleImpl() {
    }

    @Override
    public Function<SampleBean, Observable<SampleModel>> login() {
       return new Function<SampleBean, Observable<SampleModel>>() {
           @Override
           public Observable<SampleModel> apply(SampleBean sampleBean) throws Exception {
               return null;
           }
       };
    }
}

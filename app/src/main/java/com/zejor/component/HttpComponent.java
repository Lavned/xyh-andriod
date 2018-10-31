package com.zejor.component;

import com.zejor.mvp.activities.AddBankCardActivity;
import com.zejor.mvp.activities.BankPayActivity;
import com.zejor.mvp.activities.CompleteInfoActivity;
import com.zejor.mvp.activities.CompleteAllDataActivity;
import com.zejor.mvp.activities.LoginActivity;
import com.zejor.mvp.activities.RenewalActivity;
import com.zejor.mvp.activities.RepaymentActivity;
import com.zejor.mvp.activities.WithdrawActivity;
import com.zejor.mvp.fragments.HomeFragment;
import com.zejor.mvp.fragments.MyFragment;
import com.zejor.mvp.fragments.OrderFragment;

import dagger.Component;

/**
 * 接口
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {


    void inject(LoginActivity loginActivity);
    void inject(RepaymentActivity repaymentActivity);

    void inject(HomeFragment homeFragment);


    void inject(OrderFragment orderFragment);


    void inject(RenewalActivity renewalActivity);

    void inject(CompleteAllDataActivity completeAllDataActivity);

    void inject(CompleteInfoActivity completeInfoActivity);


    void inject(WithdrawActivity withdrawActivity);

    void inject(AddBankCardActivity addBankCardActivity);


    void inject(MyFragment myFragment);

    void inject(BankPayActivity myFragment);

}

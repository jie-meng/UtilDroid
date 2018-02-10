package com.jmengxy.utildroid.account_hoster;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.models.User;
import com.jmengxy.utildroid.workflows.login.LoginActivity;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class AccountHoster {
    private final AccountManager accountManager;
    private final String accountType;
    private User user;

    public AccountHoster(Context context) {
        this.accountManager = AccountManager.get(context);
        this.accountType = context.getString(R.string.account_type);
    }

    public void startLogin(Activity activity, boolean doRegister) {
        Bundle options = new Bundle();
        options.putBoolean(LoginActivity.ARG_DO_REGISTER, doRegister);
        accountManager.addAccount(accountType, null, null, options, activity, null, null);
    }

    public void saveAccount(User user, boolean persistent) {
        deleteAccount();
        if (persistent) {
            Account account = new Account(user.getUsername(), accountType);
            Bundle userdata = new Bundle();
            userdata.putString(AccountAuthenticator.KEY_ACCESS_TOKEN, user.getAccessToken());
            userdata.putString(AccountAuthenticator.KEY_USERNAME, user.getUsername());
            userdata.putString(AccountAuthenticator.KEY_NICKNAME, user.getNickname());
            userdata.putString(AccountAuthenticator.KEY_EMAIL, user.getEmail());
            userdata.putString(AccountAuthenticator.KEY_MOBILE_NUMBER, user.getMobileNumber());
            accountManager.addAccountExplicitly(account, user.getPassword(), userdata);
        } else {
            this.user = user;
        }
    }

    public void deleteAccount() {
        this.user = null;
        Account[] accounts = accountManager.getAccountsByType(accountType);
        for (Account account : accounts) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                accountManager.removeAccountExplicitly(account);
            } else {
                accountManager.removeAccount(account, null, null);
            }
        }
    }

    public boolean isLoggedIn() {
        return accountManager.getAccountsByType(accountType).length > 0;
    }

    public void updateAccount(User user) {
        if (isLoggedIn()) {
            Account account = accountManager.getAccountsByType(accountType)[0];
            accountManager.setUserData(account, AccountAuthenticator.KEY_USERNAME, user.getUsername());
            accountManager.setUserData(account, AccountAuthenticator.KEY_NICKNAME, user.getNickname());
            accountManager.setUserData(account, AccountAuthenticator.KEY_EMAIL, user.getEmail());
            accountManager.setUserData(account, AccountAuthenticator.KEY_MOBILE_NUMBER, user.getMobileNumber());
        }
    }

    public void updateAccountAccessToken(String accessToken) {
        if (isLoggedIn()) {
            Account account = accountManager.getAccountsByType(accountType)[0];
            accountManager.setUserData(account, AccountAuthenticator.KEY_ACCESS_TOKEN, accessToken);
        } else if (user != null) {
            user.setAccessToken(accessToken);
        }
    }

    public User getAccount() {
        if (isLoggedIn()) {
            User user = new User();
            Account account = accountManager.getAccountsByType(accountType)[0];
            user.setUsername(accountManager.getUserData(account, AccountAuthenticator.KEY_USERNAME));
            user.setNickname(accountManager.getUserData(account, AccountAuthenticator.KEY_NICKNAME));
            user.setEmail(accountManager.getUserData(account, AccountAuthenticator.KEY_EMAIL));
            user.setMobileNumber(accountManager.getUserData(account, AccountAuthenticator.KEY_MOBILE_NUMBER));

            return user;
        } else {
            return null;
        }
    }

    public String getAuthToken() {
        String token = null;
        if (isLoggedIn()) {
            try {
                token = accountManager.getUserData(accountManager.getAccountsByType(accountType)[0], AccountAuthenticator.KEY_ACCESS_TOKEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (user != null) {
            token = user.getAccessToken();
        }
        return token;
    }
}

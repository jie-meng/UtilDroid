package com.jmengxy.utildroid.account_hoster;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utildroid.workflows.login.LoginActivity;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class AccountHoster {
    private final AccountManager accountManager;
    private final String accountType;
    private UserEntity userEntity;

    public AccountHoster(Context context) {
        this.accountManager = AccountManager.get(context);
        this.accountType = context.getString(R.string.account_type);
    }

    public void startLogin(Activity activity, boolean doRegister) {
        Bundle options = new Bundle();
        options.putBoolean(LoginActivity.ARG_DO_REGISTER, doRegister);
        accountManager.addAccount(accountType, null, null, options, activity, null, null);
    }

    public void saveAccount(UserEntity userEntity, boolean persistent) {
        deleteAccount();
        if (persistent) {
            Account account = new Account(userEntity.getUsername(), accountType);
            Bundle userdata = new Bundle();
            userdata.putString(AccountAuthenticator.KEY_ACCESS_TOKEN, userEntity.getAccessToken());
            userdata.putString(AccountAuthenticator.KEY_REFRESH_TOKEN, userEntity.getRefreshToken());
            userdata.putString(AccountAuthenticator.KEY_USERNAME, userEntity.getUsername());
            userdata.putString(AccountAuthenticator.KEY_NICKNAME, userEntity.getNickname());
            userdata.putString(AccountAuthenticator.KEY_EMAIL, userEntity.getEmail());
            userdata.putString(AccountAuthenticator.KEY_MOBILE_NUMBER, userEntity.getMobileNumber());
            userdata.putString(AccountAuthenticator.KEY_CLIENT_ID, userEntity.getClientId());
            accountManager.addAccountExplicitly(account, userEntity.getPassword(), userdata);
        } else {
            this.userEntity = userEntity;
        }
    }

    public void deleteAccount() {
        this.userEntity = null;
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

    public void updateAccount(UserEntity userEntity) {
        if (isLoggedIn()) {
            Account account = accountManager.getAccountsByType(accountType)[0];
            accountManager.setUserData(account, AccountAuthenticator.KEY_USERNAME, userEntity.getUsername());
            accountManager.setUserData(account, AccountAuthenticator.KEY_NICKNAME, userEntity.getNickname());
            accountManager.setUserData(account, AccountAuthenticator.KEY_EMAIL, userEntity.getEmail());
            accountManager.setUserData(account, AccountAuthenticator.KEY_MOBILE_NUMBER, userEntity.getMobileNumber());
            accountManager.setUserData(account, AccountAuthenticator.KEY_CLIENT_ID, userEntity.getClientId());
            accountManager.setUserData(account, AccountAuthenticator.KEY_REFRESH_TOKEN, userEntity.getRefreshToken());
        }
    }

    public void updateAccountAccessToken(String accessToken) {
        if (isLoggedIn()) {
            Account account = accountManager.getAccountsByType(accountType)[0];
            accountManager.setUserData(account, AccountAuthenticator.KEY_ACCESS_TOKEN, accessToken);
        } else if (userEntity != null) {
            userEntity.setAccessToken(accessToken);
        }
    }

    public UserEntity getAccount() {
        if (isLoggedIn()) {
            UserEntity userEntity = new UserEntity();
            Account account = accountManager.getAccountsByType(accountType)[0];
            userEntity.setUsername(accountManager.getUserData(account, AccountAuthenticator.KEY_USERNAME));
            userEntity.setNickname(accountManager.getUserData(account, AccountAuthenticator.KEY_NICKNAME));
            userEntity.setEmail(accountManager.getUserData(account, AccountAuthenticator.KEY_EMAIL));
            userEntity.setMobileNumber(accountManager.getUserData(account, AccountAuthenticator.KEY_MOBILE_NUMBER));
            userEntity.setClientId(accountManager.getUserData(account, AccountAuthenticator.KEY_CLIENT_ID));
            userEntity.setAccessToken(accountManager.getUserData(account, AccountAuthenticator.KEY_ACCESS_TOKEN));
            userEntity.setRefreshToken(accountManager.getUserData(account, AccountAuthenticator.KEY_REFRESH_TOKEN));

            return userEntity;
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
        } else if (userEntity != null) {
            token = userEntity.getAccessToken();
        }
        return token;
    }

    public String getRefreshToken() {
        String token = null;
        if (isLoggedIn()) {
            try {
                token = accountManager.getUserData(accountManager.getAccountsByType(accountType)[0], AccountAuthenticator.KEY_REFRESH_TOKEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (userEntity != null) {
            token = userEntity.getRefreshToken();
        }
        return token;
    }

    public String getClientId() {
        String clientId = null;
        if (isLoggedIn()) {
            clientId = getAccount().getClientId();
        } else if (userEntity != null) {
            clientId = userEntity.getClientId();
        }

        return clientId;
    }
}

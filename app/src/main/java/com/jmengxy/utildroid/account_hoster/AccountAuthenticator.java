package com.jmengxy.utildroid.account_hoster;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jmengxy.utildroid.workflows.login.LoginActivity;

/**
 * Created by jiemeng on 04/02/2018.
 */

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    static final String KEY_ACCESS_TOKEN = "key_access_token";
    static final String KEY_REFRESH_TOKEN = "key_refresh_token";
    static final String KEY_USERNAME = "key_username";
    static final String KEY_NICKNAME = "key_nickname";
    static final String KEY_EMAIL = "key_email";
    static final String KEY_MOBILE_NUMBER = "key_mobile_number";
    static final String KEY_CLIENT_ID = "key_client_id";

    private Context context;

    public AccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Bundle result = new Bundle();

        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_MANAGER_RESPONSE, response);
        intent.putExtra(LoginActivity.ARG_DO_REGISTER, options.getBoolean(LoginActivity.ARG_DO_REGISTER));
        result.putParcelable(AccountManager.KEY_INTENT, intent);
        return result;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Bundle result = new Bundle();
        String token = AccountManager.get(context).getUserData(account, KEY_ACCESS_TOKEN);
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        result.putString(AccountManager.KEY_AUTHTOKEN, token);
        return result;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
}

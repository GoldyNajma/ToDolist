package com.example.to_dolist.modules.login;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.to_dolist.api_responses.LoginResponse;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.SessionRepository;
import com.example.to_dolist.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginInteractor implements LoginContract.Interactor {
    private final SessionRepository<User> userSessionRepository;
    private final SessionRepository<String> tokenSessionRepository;

    public LoginInteractor(SessionRepository<User> userSessionRepository,
                           SessionRepository<String> tokenSessionRepository){
        this.userSessionRepository = userSessionRepository;
        this.tokenSessionRepository = tokenSessionRepository;
    }

    @Override
    public void requestLogin(final String email, final String password,
                             final RequestCallback<LoginResponse> requestCallback) {
        JSONObject loginRequestJSON = createLoginRequestJSON(email, password);

        AndroidNetworking.post(Constants.BASE_URL + "/login")
                .addJSONObjectBody(loginRequestJSON)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.token != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Login failed.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 401) {
                            requestCallback.requestFailed("Invalid login credentials.");
                        } else {
                            requestCallback.requestFailed(anError.getMessage());
                        }
                    }
                });
    }

    @Override
    public void saveToken(String token) {
        tokenSessionRepository.setSessionData(token);
    }

    @Override
    public String getToken() {
        return tokenSessionRepository.getSessionData();
    }

    @Override
    public void saveUser(User user) { userSessionRepository.setSessionData(user); }

    @NonNull
    private JSONObject createLoginRequestJSON(String email, String password){
        JSONObject loginRequestJSON = new JSONObject();

        try{
            loginRequestJSON.put("email", email);
            loginRequestJSON.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return loginRequestJSON;
    }
}
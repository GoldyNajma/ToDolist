package com.example.to_dolist.modules.register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.to_dolist.api_responses.RegisterResponse;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.source.SessionRepository;
import com.example.to_dolist.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterInteractor implements RegisterContract.Interactor {
    private final SessionRepository<String> tokenSessionRepository;

    public RegisterInteractor(SessionRepository<String> tokenSessionRepository){
        this.tokenSessionRepository = tokenSessionRepository;
    }

    @Override
    public void requestRegister(final String name, final String email,
                                final String password, final String passwordConfirmation,
                                final RequestCallback<RegisterResponse> requestCallback) {
        JSONObject registerRequestJSON
                = createRegisterRequestJSON(name, email, password, passwordConfirmation);

        Log.d("abcde", "requestRegister di Interactor");

        AndroidNetworking.post(Constants.BASE_URL + "/register")
                .addJSONObjectBody(registerRequestJSON)
                .build()
                .getAsObject(RegisterResponse.class, new ParsedRequestListener<RegisterResponse>() {
                    @Override
                    public void onResponse(RegisterResponse response) {

                        Log.d("abcde", "onResponse");
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.user != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Registration failed.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("abcde", "onError");
                        if (anError.getErrorCode() == 422) {
                            requestCallback.requestFailed("Invalid registration request.");
                        } else {
                            Log.d("abcde", Integer.toString(anError.getErrorCode()));
                            requestCallback.requestFailed(anError.getMessage());
                        }
                    }
                });
    }

    @Override
    public String getToken() {
        return tokenSessionRepository.getSessionData();
    }

    @NonNull
    private JSONObject createRegisterRequestJSON(String name, String email,
                                                 String password, String passwordConfirmation){
        JSONObject registerRequestJSON = new JSONObject();

        try {
            registerRequestJSON.put("name", name);
            registerRequestJSON.put("email", email);
            registerRequestJSON.put("image_path", null);
            registerRequestJSON.put("password", password);
            registerRequestJSON.put("password_confirmation", passwordConfirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return registerRequestJSON;
    }
}
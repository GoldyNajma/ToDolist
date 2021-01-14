package com.example.to_dolist.modules.home;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.to_dolist.api_responses.IndexTasksResponse;
import com.example.to_dolist.api_responses.LogoutResponse;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.SessionRepository;
import com.example.to_dolist.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeInteractor implements HomeContract.Interactor {
    private final SessionRepository<User> userSessionRepository;
    private final SessionRepository<String> tokenSessionRepository;

    public HomeInteractor(SessionRepository<User> userSessionRepository,
                          SessionRepository<String> tokenSessionRepository){
        this.userSessionRepository = userSessionRepository;
        this.tokenSessionRepository = tokenSessionRepository;
    }

    @Override
    public void requestIndexTasks(final RequestCallback<IndexTasksResponse> requestCallback,
                                  final String option) {
        AndroidNetworking.get(Constants.BASE_URL + Constants.TASKS_PREFIX + "{option}")
                .addPathParameter("option", option)
                .addHeaders("Authorization", this.getToken())
                .build()
                .getAsObject(IndexTasksResponse.class,
                             new ParsedRequestListener<IndexTasksResponse>() {
                    @Override
                    public void onResponse(IndexTasksResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.tasks != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to fetch all tasks.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("Failed to fetch all tasks.");
                    }
                });
    }

    @Override
    public void requestUpdateTaskCompletion(final RequestCallback<TaskResponse> requestCallback,
                                            final int taskId, final boolean completionStatus) {
        AndroidNetworking.put(Constants.BASE_URL + Constants.TASKS_PREFIX + "/{id}/completion")
                .addPathParameter("id", Integer.toString(taskId))
                .addHeaders("Authorization", this.getToken())
                .addJSONObjectBody(createRequestJsonObject(completionStatus))
                .build()
                .getAsObject(IndexTasksResponse.class,
                            new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.task != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to get task.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 404) {
                            String failedMessage = "Task with id \"" + taskId + "\" not found.";

                            requestCallback.requestFailed(failedMessage);
                        } else {
                            requestCallback.requestFailed(anError.getErrorDetail());
                        }
                    }
                });
    }

    @Override
    public String getToken() {
        return tokenSessionRepository.getSessionData();
    }

    @Override
    public void deleteSession() {
        tokenSessionRepository.destroy();
        userSessionRepository.destroy();
    }

    @Override
    public User getUser() {
        return userSessionRepository.getSessionData();
    }

    @Override
    public void requestLogout(final RequestCallback<LogoutResponse> requestCallback) {
        AndroidNetworking.post(Constants.BASE_URL + "/logout")
                .addHeaders("Authorization", this.getToken())
                .build()
                .getAsObject(LogoutResponse.class,
                            new ParsedRequestListener<LogoutResponse>() {
                    @Override
                    public void onResponse(LogoutResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.message != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to logout.");
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("Failed to logout.");
                    }
                });
    }

    @NotNull
    private JSONObject createRequestJsonObject(boolean completionStatus) {
        JSONObject requestJsonObject = new JSONObject();

        try {
            requestJsonObject.put("completed", completionStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestJsonObject;
    }
}

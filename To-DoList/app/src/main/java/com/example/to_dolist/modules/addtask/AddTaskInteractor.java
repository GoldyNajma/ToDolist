package com.example.to_dolist.modules.addtask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.source.SessionRepository;
import com.example.to_dolist.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class AddTaskInteractor implements AddTaskContract.Interactor {
    private final SessionRepository<String> tokenSessionRepository;

    public AddTaskInteractor(SessionRepository<String> tokenSessionRepository) {
        this.tokenSessionRepository = tokenSessionRepository;
    }

    @Override
    public void requestStoreTask(final RequestCallback<TaskResponse> requestCallback, String title,
                                 String description, String imagePath, boolean completed) {
        JSONObject storeTaskRequestJson =
                createStoreTaskRequestJson(title, description, imagePath, completed);

        AndroidNetworking.post(Constants.BASE_URL + Constants.TASKS_PREFIX)
                .addHeaders("Authorization", getToken())
                .addJSONObjectBody(storeTaskRequestJson)
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.task != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to save task.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 409) {
                            requestCallback.requestFailed("Failed to store task");
                        } else {
                            requestCallback.requestFailed(anError.getResponse().message());
                        }
                    }
                });
    }

    @Override
    public String getToken() {
        return tokenSessionRepository.getSessionData();
    }

    @NotNull
    private JSONObject createStoreTaskRequestJson(String title, String description,
                                                  String imagePath, boolean completed) {
        JSONObject storeTaskRequestJSON = new JSONObject();

        try {
            Object imagePathValue = imagePath == null ? JSONObject.NULL : imagePath;
            Object descriptionValue = description == null ? JSONObject.NULL : description;

            storeTaskRequestJSON.put("title", title);
            storeTaskRequestJSON.put("description", descriptionValue);
            storeTaskRequestJSON.put("image_path", imagePathValue);
            storeTaskRequestJSON.put("completed", completed);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return storeTaskRequestJSON;
    }
}

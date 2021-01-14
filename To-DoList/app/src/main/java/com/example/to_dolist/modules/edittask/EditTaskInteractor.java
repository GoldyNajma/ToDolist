package com.example.to_dolist.modules.edittask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.to_dolist.api_responses.DeleteTaskResponse;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.source.SessionRepository;
import com.example.to_dolist.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class EditTaskInteractor implements EditTaskContract.Interactor {
    private final SessionRepository<String> tokenSessionRepository;

    public EditTaskInteractor(SessionRepository<String> tokenSessionRepository) {
        this.tokenSessionRepository = tokenSessionRepository;
    }

    @Override
    public void requestShowTask(final RequestCallback<TaskResponse> requestCallback, int id) {
        AndroidNetworking.get(Constants.BASE_URL + Constants.TASKS_PREFIX + "/{id}")
                .addPathParameter("id", Integer.toString(id))
                .addHeaders("Authorization", getToken())
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.task != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to show task.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 404) {
                            requestCallback.requestFailed("Task not found.");
                        } else {
                            requestCallback.requestFailed(anError.getMessage());
                        }
                    }
                });
    }

    @Override
    public void requestUpdateTask(final RequestCallback<TaskResponse> requestCallback, int id,
                                  String title, String description, String imagePath,
                                  boolean completed) {
        JSONObject upadateTaskRequestJson =
                createUpdateTaskRequestJson(title, description, imagePath, completed);

        AndroidNetworking.put(Constants.BASE_URL + Constants.TASKS_PREFIX + "/{id}")
                .addPathParameter("id", Integer.toString(id))
                .addHeaders("Authorization", getToken())
                .addJSONObjectBody(upadateTaskRequestJson)
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.task != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to edit task.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 404) {
                            requestCallback.requestFailed("Task not found.");
                        } else if (anError.getErrorCode() == 409) {
                            requestCallback.requestFailed("Failed to edit task");
                        } else {
                            requestCallback.requestFailed(anError.getMessage());
                        }
                    }
                });
    }

    @Override
    public void requestDeleteTask(final RequestCallback<DeleteTaskResponse> requestCallback,
                                  final int id, final boolean permanent) {
        String url;

        if (permanent) {
            url = Constants.BASE_URL + Constants.TASKS_PREFIX + "/{id}/force-delete";
        } else {
            url = Constants.BASE_URL + Constants.TASKS_PREFIX + "/{id}";
        }

        AndroidNetworking.delete(url)
                .addPathParameter("id", Integer.toString(id))
                .addHeaders("Authorization", getToken())
                .build()
                .getAsObject(DeleteTaskResponse.class,
                        new ParsedRequestListener<DeleteTaskResponse>() {
                    @Override
                    public void onResponse(DeleteTaskResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if ((!permanent && response.deleted_at != null) ||
                                   (permanent && response.deleted_at == null)) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to delete task.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 404) {
                            requestCallback.requestFailed("Task not found.");
                        } else {
                            requestCallback.requestFailed("Failed to delete task.");
                        }
                    }
                });
    }

    @Override
    public void requestRestoreTask(final RequestCallback<TaskResponse> requestCallback, int id) {
        String url = Constants.BASE_URL + Constants.TASKS_PREFIX + "/{id}/restore";

        AndroidNetworking.patch(url)
                .addPathParameter("id", Integer.toString(id))
                .addHeaders("Authorization", getToken())
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse response) {
                        if (response == null) {
                            requestCallback.requestFailed("Null response.");
                        } else if (response.task != null) {
                            requestCallback.requestSuccess(response);
                        } else {
                            requestCallback.requestFailed("Failed to restore task.");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() == 404) {
                            requestCallback.requestFailed("Task not found.");
                        } else {
                            requestCallback.requestFailed("Failed to restore task. "
                                                          + anError.getErrorDetail());
                        }
                    }
                });
    }

    @NotNull
    private JSONObject createUpdateTaskRequestJson(String title, String description,
                                                   String imagePath, boolean completed) {
        JSONObject updateTaskRequestJSON = new JSONObject();

        try {
            Object imagePathValue = imagePath == null ? JSONObject.NULL : imagePath;
            Object descriptionValue = description == null ? JSONObject.NULL : description;

            updateTaskRequestJSON.put("title", title);
            updateTaskRequestJSON.put("description", descriptionValue);
            updateTaskRequestJSON.put("image_path", imagePathValue);
            updateTaskRequestJSON.put("completed", completed);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return updateTaskRequestJSON;
    }

    @Override
    public String getToken() {
        return tokenSessionRepository.getSessionData();
    }
}

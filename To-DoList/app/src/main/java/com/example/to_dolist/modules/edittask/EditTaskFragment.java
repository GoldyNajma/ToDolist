package com.example.to_dolist.modules.edittask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.source.TokenSessionRepository;
import com.example.to_dolist.modules.login.LoginActivity;
import com.example.to_dolist.modules.home.HomeActivity;

import org.jetbrains.annotations.NotNull;

public class EditTaskFragment extends BaseFragment<EditTaskActivity, EditTaskContract.Presenter>
        implements EditTaskContract.View {
    EditText etTitle;
    EditText etDescription;
    CheckBox cbCompleted;
    Button btnRestore;
    Button btnDelete;
    Button btnSave;
    Button btnCancel;
    ProgressBar pbLoading;
    int currentTaskId;

    public EditTaskFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_task, container, false);

        etTitle = fragmentView.findViewById(R.id.task_et_title);
        etDescription = fragmentView.findViewById(R.id.task_et_description);
        cbCompleted = fragmentView.findViewById(R.id.task_cb_completed);
        btnDelete = fragmentView.findViewById(R.id.task_btn_delete);
        btnRestore = fragmentView.findViewById(R.id.task_btn_restore);
        btnSave = fragmentView.findViewById(R.id.task_btn_save);
        btnCancel = fragmentView.findViewById(R.id.task_btn_cancel);
        pbLoading = fragmentView.findViewById(R.id.task_pb_loading);

        mPresenter = new EditTaskPresenter(this,
                new EditTaskInteractor(
                        new TokenSessionRepository(activity)
                )
        );
        mPresenter.start();

        cbCompleted.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
        btnRestore.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnSaveClick();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnCancelClick();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnDeleteClick(false);
            }
        });
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnRestoreClick();
            }
        });
        btnRestore = fragmentView.findViewById(R.id.task_btn_restore);

        mPresenter.loadTask(this.currentTaskId);

        return fragmentView;
    }

    public void setBtnSaveClick() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String imagePath = null;
        boolean completed = cbCompleted.isChecked();
        boolean isValid = mPresenter.validateFields(title, description, imagePath, completed);

        if (isValid) {
            mPresenter.saveEdit(currentTaskId, title, description, imagePath, completed);
        }
    }

    public void setBtnCancelClick() {
        redirectToHome();
    }

    public void setBtnDeleteClick(boolean permanent) {
        mPresenter.deleteTask(currentTaskId, permanent);
    }

    public void setBtnRestoreClick() {
        mPresenter.restoreTask(currentTaskId);
    }

    @Override
    public void setPresenter(EditTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToHome() {
        Intent intent = new Intent(activity, HomeActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void setSaveButtonEnabled(boolean isEnabled) {
        btnSave.setEnabled(isEnabled);
    }

    @Override
    public void setCancelButtonEnabled(boolean isEnabled) {
        btnCancel.setEnabled(isEnabled);
    }

    @Override
    public void setDeleteButtonEnabled(boolean isEnabled) {
        btnDelete.setEnabled(isEnabled);
    }

    @Override
    public void setCompletedCheckBoxEnabled(boolean isEnabled) {
        cbCompleted.setEnabled(isEnabled);
    }

    @Override
    public void showTask(@NotNull Task task) {
        this.etTitle.setText(task.getTitle());
        if (task.getDescription() != null) {
            this.etDescription.setText(task.getDescription());
        }
        this.cbCompleted.setChecked(task.isCompleted());
        if (task.getDeleted_at() != null) {
            this.etTitle.setEnabled(false);
            this.etDescription.setEnabled(false);
            this.cbCompleted.setEnabled(false);
            this.btnSave.setVisibility(View.GONE);
            this.btnDelete.setText(getString(R.string.task_btn_delete_permanent_text));
            this.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBtnDeleteClick(true);
                }
            });
            this.btnRestore.setVisibility(View.VISIBLE);
        } else {
            this.etTitle.setEnabled(true);
            this.etDescription.setEnabled(true);
            this.cbCompleted.setEnabled(true);
            this.btnSave.setVisibility(View.VISIBLE);
            this.btnDelete.setText(getString(R.string.task_btn_delete_soft_text));
            this.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBtnDeleteClick(false);
                }
            });
            this.btnRestore.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCurrentTaskId(int currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

}

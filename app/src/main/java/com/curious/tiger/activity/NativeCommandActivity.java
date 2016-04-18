package com.curious.tiger.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.curious.tiger.R;
import com.curious.tiger.utils.CmdUtils;

import static com.curious.tiger.R.id.run_btn;

/**
 * Created by lulala on 18/4/16.
 */
public class NativeCommandActivity extends BaseActivity {
    private EditText mCommandLine;
    private Button mRunBtn;

    private TextView mResultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_command);

        mCommandLine = (EditText) findViewById(R.id.command_line);
        mRunBtn = (Button) findViewById(run_btn);
        mRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String command = mCommandLine.getText().toString();
                if (TextUtils.isEmpty(command)) {
                    return;
                }

                mResultView.setText(CmdUtils.exec(command));

            }
        });
        mResultView = (TextView) findViewById(R.id.result);
    }
}

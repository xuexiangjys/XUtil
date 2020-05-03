/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.xutildemo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.file.FileUtils;
import com.xuexiang.xutildemo.R;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xaop.consts.PermissionConsts.STORAGE;

/**
 * @author xuexiang
 * @since 2020/5/3 8:04 PM
 */
@Page(name = "uri转文件路径测试")
public class UriPathFragment extends XPageFragment {

    /**
     * 选择系统文件Request Code
     */
    public static final int REQUEST_FILE = 1000;

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_uri_path;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }


    @SingleClick
    @OnClick(R.id.btn_select)
    public void onViewClicked(View view) {
        selectFile();
    }

    @Permission(STORAGE)
    private void selectFile() {
        startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.ANY), REQUEST_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择系统文件并解析
        if (resultCode == RESULT_OK && requestCode == REQUEST_FILE) {
            if (data != null) {
                Uri uri = data.getData();
                String filePath = PathUtils.getFilePathByUri(getContext(), uri);
                if (StringUtils.isEmptyTrim(filePath)) {
                    filePath = "未解析成功";
                } else {
                    filePath += "\n\n文件是否存在:" + FileUtils.isFileExists(filePath);
                }
                tvContent.setText(filePath);
            }
        }
    }


}

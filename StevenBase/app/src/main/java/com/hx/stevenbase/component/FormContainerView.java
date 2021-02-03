package com.hx.stevenbase.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormContainerView extends LinearLayoutCompat {
    private static final String TYPE_INPUT = "input";
    private static final String TYPE_SELECT = "select";
    private static final String TYPE_LINE = "line";
    private Context context;
    private List<Map<String, String>> initData;

    public FormContainerView(@NonNull Context context) throws Exception {
        super(context);
        this.context = context;
    }

    public FormContainerView(@NonNull Context context, @Nullable AttributeSet attrs) throws Exception {
        super(context, attrs);
        this.context = context;
    }

    public FormContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void getInitObject(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Object object = jsonArray.get(i);
                if (object instanceof JSONArray) {
                    getInitObject((JSONArray) object);
                } else if (object instanceof JSONObject) {
                    Map<String, String> map = new HashMap<>();
                    if (((JSONObject) object).has("name")) {
                        String name = ((JSONObject) object).getString("name");
                        if (name != null) map.put("name", name);
                    }
                    if (((JSONObject) object).has("type")) {
                        String type = ((JSONObject) object).getString("type");
                        if (type != null) map.put("type", type);
                    }
                    if (((JSONObject) object).has("hint")) {
                        String hint = ((JSONObject) object).getString("hint");
                        if (hint != null) map.put("hint", hint);
                    }
                    initData.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void init(String initJson) throws Exception {
        setOrientation(VERTICAL);
        if (initJson == null || initJson.equals("")) throw new Exception("未设置初始化json数组");
        initData = new ArrayList<>();
        getInitObject(new JSONArray(initJson));
        Logger.d(initData);
        for (int i = 0; i < initData.size(); i++) {
            Map<String, String> item = initData.get(i);
            String type = item.get("type");
            switch (type) {
                case TYPE_INPUT:
                    View inputView = LayoutInflater.from(context).inflate(R.layout.form_input_item, this, false);
                    TextView titleView = inputView.findViewById(R.id.input_item_title);
                    titleView.setText(item.get("name"));
                    addView(inputView);
                    break;
                case TYPE_SELECT:
                    View selectView = LayoutInflater.from(context).inflate(R.layout.form_select_item, this, false);
                    TextView selectTitleView = selectView.findViewById(R.id.select_item_title);
                    TextView selectContentView = selectView.findViewById(R.id.select_item_content);
                    selectTitleView.setText(item.get("name"));
                    selectContentView.setText(item.get("hint"));
                    addView(selectView);
                    break;
                default:
                    break;
            }
            if (i != initData.size() - 1) {
                View lineView = LayoutInflater.from(context).inflate(R.layout.form_line_item, this, false);
                addView(lineView);
            }
        }
    }
}

package es.intelygenz.rss.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.intelygenz.rss.R;
import es.intelygenz.rss.presentation.utils.FontStyle;
import es.intelygenz.rss.presentation.utils.Preferences;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class PreferencesActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, PreferencesActivity.class);
        return callingIntent;
    }

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_size_spinner) Spinner textSizeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        overrideOpenTransition();

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        showToolBarActionButton();

        setLayout();
    }

    private void setLayout() {
        setTextSizeSpinner();
    }

    private void setTextSizeSpinner() {
        List<String> list = new ArrayList<String>();
        list.add(FontStyle.Small.getTitle());
        list.add(FontStyle.Medium.getTitle());
        list.add(FontStyle.Large.getTitle());
        list.add(FontStyle.ExtraLarge.getTitle());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textSizeSpinner.setAdapter(adapter);

        final Preferences prefs = new Preferences(this);

        switch (prefs.getFontStyle()) {
            case Small:
                textSizeSpinner.setSelection(0);
                break;

            case Medium:
                textSizeSpinner.setSelection(1);
                break;

            case Large:
                textSizeSpinner.setSelection(2);
                break;

            case ExtraLarge:
                textSizeSpinner.setSelection(3);
                break;
        }

        textSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        prefs.setFontStyle(FontStyle.Small);
                        break;

                    case 1:
                        prefs.setFontStyle(FontStyle.Medium);
                        break;

                    case 2:
                        prefs.setFontStyle(FontStyle.Large);
                        break;

                    case 3:
                        prefs.setFontStyle(FontStyle.ExtraLarge);
                        break;
                }

                // In order to force refresh when going back to the MainActivity
                setResult(RESULT_OK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showToolBarActionButton() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.refresh_button)
    public void refreshButtonClicked(View view) {
        finish();
        startActivity(getIntent());
    }

    private void overrideOpenTransition() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private void overrideCloseTransition() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overrideCloseTransition();
    }
}
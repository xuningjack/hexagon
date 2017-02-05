package com.example.android_hexagon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.widget.SpecailButton;
import com.example.widget.SpecailView;

public class MainActivity extends Activity implements SpecailButton.OnClickListener {

	 private SpecailView layout;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);

	        layout = (SpecailView)findViewById(R.id.specail_view);
	        layout.setOnItemClick(this);
	    }

	    @Override
	    public void onClick(View v, boolean checked) {
	        String text = ((SpecailButton)v).getTextString();
	        Toast.makeText(this, text + checked, Toast.LENGTH_SHORT).show();
	    }

}

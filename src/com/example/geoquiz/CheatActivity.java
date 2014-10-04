package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown";

	private boolean isAnswerTrue;

	private TextView answerTextView;
	private Button showAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cheat_activity);
		isAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		setAnswerShownResult(false);

		answerTextView = (TextView) findViewById(R.id.answer_text_view);
		showAnswer = (Button) findViewById(R.id.show_answer_button);
		showAnswer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isAnswerTrue) {
					answerTextView.setText(R.string.true_button);
				} else {
					answerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
			}
		});

	}

	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
}

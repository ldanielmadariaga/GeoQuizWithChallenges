package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";

	private Button trueButton;
	private Button falseButton;
	private Button cheatButton;
	private ImageButton nextButton;
	private ImageButton previousButton;
	private TextView questionTextView;

	private boolean isCheater;
	private int currentIndex = 0;
	private TrueFalse[] questionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			currentIndex = savedInstanceState.getInt(KEY_INDEX);
		}
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.quiz_activity);

		questionTextView = (TextView) findViewById(R.id.question_text_view);
		questionTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});
		updateQuestionText();

		trueButton = (Button) findViewById(R.id.true_button);
		trueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		falseButton = (Button) findViewById(R.id.false_button);
		falseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		cheatButton = (Button) findViewById(R.id.cheat_button);
		cheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, isAnswerTrue());
				startActivityForResult(intent, 0);
			}
		});

		nextButton = (ImageButton) findViewById(R.id.next_button);
		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextQuestion();
			}

		});

		previousButton = (ImageButton) findViewById(R.id.previous_button);
		previousButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				previousQuestion();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}

	private void nextQuestion() {
		currentIndex = (currentIndex + 1) % questionBank.length;
		notACheater();
		updateQuestionText();
	}

	private void previousQuestion() {
		currentIndex = (currentIndex - 1) % questionBank.length;
		if (currentIndex <= 0) {
			currentIndex = questionBank.length - 1;
		}
		notACheater();
		updateQuestionText();
	}

	private void notACheater() {
		isCheater = false;
	}

	private void updateQuestionText() {
		int question = questionBank[currentIndex].getQuestion();
		questionTextView.setText(question);
	}

	private void checkAnswer(boolean userPressedTrue) {
		int messageResourceId;

		if (isCheater) {
			messageResourceId = R.string.judgment_toast;
		} else {
			if (userPressedTrue == isAnswerTrue()) {
				messageResourceId = R.string.correct_toast;
			} else {
				messageResourceId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show();
	}

	private boolean isAnswerTrue() {
		return questionBank[currentIndex].isTrueQuestion();

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, currentIndex);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on
		// the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}

}

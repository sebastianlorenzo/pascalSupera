package com.grupo10.jatrikandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

	public final static String MESSAGE_USER = "com.grupo10.jatrikandroid.USER";
	public final static String MESSAGE_DATOS = "com.grupo10.jatrikandroid.DATOS";
	private UserLoginTask mAuthTask = null;

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	public Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mUserView = (EditText) findViewById(R.id.EditText_user);

		mPasswordView = (EditText) findViewById(R.id.EditText_psw);
		
		Button mEmailSignInButton = (Button) findViewById(R.id.ButtonLogin);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}

	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);
		
		mUserView = (EditText) findViewById(R.id.EditText_user);
		mPasswordView = (EditText) findViewById(R.id.EditText_psw);
		
		// Store values at the time of the login attempt.
		String user = mUserView.getText().toString();
		String password = mPasswordView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (password.isEmpty()) {
			mPasswordView.setError(getString(R.string.error_psw));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (user.isEmpty()) {
			mUserView.setError(getString(R.string.error_user));
			focusView = mUserView;
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			intent = new Intent(MainActivity.this, ListadoPartidos.class);
			mAuthTask = new UserLoginTask(user, password);
			mAuthTask.execute((Void) null);
		}
	}


	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mUser;
		private final String mPassword;


		UserLoginTask(String user, String password) {
			mUser = user;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			HttpClient httpClient = new DefaultHttpClient();
    		HttpPost post = new HttpPost("http://10.0.3.2:8080/Jatrik/rest/usuarios/login");
    		post.setHeader("content-type", "application/json");
    		JSONObject datoSend = new JSONObject();	 
    		try {
    			datoSend.put("login", mUser);
    			datoSend.put("password", mPassword);
    			StringEntity entitySend = new StringEntity(datoSend.toString());
    			post.setEntity(entitySend);
    			HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONObject datoRcv = new JSONObject(respStr);	 
                if (datoRcv.getBoolean("login")){
                	post = new HttpPost("http://10.0.3.2:8080/Jatrik/rest/equipos/verInfoMobile");
                	post.setHeader("content-type", "application/json");
                	datoSend = new JSONObject();	 
                	datoSend.put("nomUsuario", mUser);
                	entitySend = new StringEntity(datoSend.toString());
                	post.setEntity(entitySend);
                	resp = httpClient.execute(post);
                	respStr = EntityUtils.toString(resp.getEntity());
                	intent.putExtra(MESSAGE_DATOS, respStr);
                }
                return datoRcv.getBoolean("login");
    		} catch (JSONException e) {

    			return false;
    		} catch (UnsupportedEncodingException e) {
    			return false;
    		} catch (ClientProtocolException e) {
    			return false;
    		} catch (IOException e) {
    			return false;
    		}	
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				intent.putExtra(MESSAGE_USER, mUser.toString());
		        startActivity(intent);
				
			} else {
				Toast.makeText(MainActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}

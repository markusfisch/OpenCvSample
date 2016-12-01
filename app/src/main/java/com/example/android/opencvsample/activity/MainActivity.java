package com.example.android.opencvsample.activity;

import com.example.android.opencvsample.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.Toast;

public class MainActivity
		extends AppCompatActivity
		implements CvCameraViewListener2 {
	private static final int REQUEST_CAMERA = 1;

	private final BaseLoaderCallback loaderCallback =
			new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS:
					cameraView.enableView();
					break;
				default:
					super.onManagerConnected(status);
					break;
			}
		}
	};

	private CameraBridgeViewBase cameraView;
	private Mat grayMat;

	@Override
	public void onRequestPermissionsResult(
			int requestCode,
			String permissions[],
			int grantResults[]) {
		switch (requestCode) {
			default:
				break;
			case REQUEST_CAMERA:
				if (grantResults.length > 0 && grantResults[0] !=
						PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(
							this,
							R.string.no_camera_no_fun,
							Toast.LENGTH_SHORT).show();

					finish();
				}
				break;
		}
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		grayMat = new Mat();
	}

	@Override
	public void onCameraViewStopped() {
		grayMat.release();
		grayMat = null;
	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame frame) {
		Imgproc.cvtColor(frame.rgba(), grayMat, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(
				grayMat,
				grayMat,
				0,
				255,
				Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);

		return grayMat;
	}

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.activity_main);

		checkPermissions();

		cameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);
		cameraView.setVisibility(SurfaceView.VISIBLE);
		cameraView.setCvCameraViewListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (cameraView != null) {
			cameraView.disableView();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!OpenCVLoader.initDebug()) {
			OpenCVLoader.initAsync(
					OpenCVLoader.OPENCV_VERSION_3_0_0,
					this,
					loaderCallback);
		} else {
			loaderCallback.onManagerConnected(
					LoaderCallbackInterface.SUCCESS);
		}
	}

	private void checkPermissions() {
		String permission = android.Manifest.permission.CAMERA;

		if (ContextCompat.checkSelfPermission(this, permission) ==
				PackageManager.PERMISSION_GRANTED) {
			return;
		}

		ActivityCompat.requestPermissions(
				this,
				new String[]{permission},
				REQUEST_CAMERA);
	}
}

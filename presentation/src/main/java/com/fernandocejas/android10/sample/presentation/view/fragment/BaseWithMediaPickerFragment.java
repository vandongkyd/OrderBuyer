package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.sample.presentation.dialog.SelectMediaModeDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jerry on Feb-12-18.
 */

public abstract class BaseWithMediaPickerFragment extends BaseFragment {
    private static final int REQUEST_PERMISSION_FROM_REQUEST_LOAD_MEDIA = 101;
    private static final int REQUEST_PERMISSION_FROM_REQUEST_MEDIA_CAPTURE = 102;
    private static final int REQUEST_LOAD_IMAGE = 105;
    private static final int REQUEST_IMAGE_CAPTURE = 106;
    private static final int REQUEST_LOAD_VIDEO = 107;
    private static final int REQUEST_VIDEO_CAPTURE = 108;

    private static final int MEDIA_TYPE_PHOTO = 108;
    private static final int MEDIA_TYPE_VIDEO = 109;

    private SelectMediaModeDialog selectMediaModeDialog;

    private String currentPhotoPath;

    private SelectMediaModeDialog.OnClickListener onSelectMediaModeDialogClickListener = new SelectMediaModeDialog.OnClickListener() {
        @Override
        public void onCameraClicked() {
            BaseWithMediaPickerFragment.this.onCameraClicked();
        }

        @Override
        public void onGalleryClicked() {
            BaseWithMediaPickerFragment.this.onGalleryClicked();
        }

        @Override
        public void onDismissClicked() {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOAD_IMAGE || requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String picturePath = currentPhotoPath;
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                }
                onPhotoPicked(picturePath);
            }
        } else if (requestCode == REQUEST_LOAD_VIDEO || requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri selectedVideo = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedVideo,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String videoPath = cursor.getString(columnIndex);
                    cursor.close();
                    onVideoPicked(videoPath);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERMISSION_FROM_REQUEST_LOAD_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onGalleryClicked();
                }
                break;

            case REQUEST_PERMISSION_FROM_REQUEST_MEDIA_CAPTURE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCameraClicked();
                }
                break;

            default:
                break;
        }
    }

    public void onPhotoClicked() {
        if (this.selectMediaModeDialog == null) {
            this.selectMediaModeDialog = new SelectMediaModeDialog(getActivity());
            this.selectMediaModeDialog.setOnClickListener(onSelectMediaModeDialogClickListener);
        }
        this.selectMediaModeDialog.setMedia_type(MEDIA_TYPE_PHOTO);
        this.selectMediaModeDialog.show();
    }

    public void onVideoClicked() {
        if (this.selectMediaModeDialog == null) {
            this.selectMediaModeDialog = new SelectMediaModeDialog(getActivity());
            this.selectMediaModeDialog.setOnClickListener(onSelectMediaModeDialogClickListener);
        }
        this.selectMediaModeDialog.setMedia_type(MEDIA_TYPE_VIDEO);
        this.selectMediaModeDialog.show();
    }

    public void onCameraClicked() {
        if (requestPermissions(REQUEST_PERMISSION_FROM_REQUEST_MEDIA_CAPTURE,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA})) {
            return;
        }
        if (selectMediaModeDialog.getMedia_type() == MEDIA_TYPE_PHOTO) {
            Intent i = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (i.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                            "com.fernandocejas.android10.fileprovider",
                            photoFile);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else if (selectMediaModeDialog.getMedia_type() == MEDIA_TYPE_VIDEO) {
            Intent i = new Intent(
                    MediaStore.ACTION_VIDEO_CAPTURE);

            if (i.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
                startActivityForResult(i, REQUEST_VIDEO_CAPTURE);
            }
        }
    }

    public void onGalleryClicked() {
        if (requestPermissions(REQUEST_PERMISSION_FROM_REQUEST_LOAD_MEDIA,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            return;
        }
        if (selectMediaModeDialog.getMedia_type() == MEDIA_TYPE_PHOTO) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");

            startActivityForResult(i, REQUEST_LOAD_IMAGE);
        } else if (selectMediaModeDialog.getMedia_type() == MEDIA_TYPE_VIDEO) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, REQUEST_LOAD_VIDEO);
        }
    }

    public boolean requestPermissions(int requestCode, String[] permissions) {
        if (!Utils.verifyPermissions(getActivity(), permissions)) {
            requestPermissions(
                    permissions,
                    requestCode

            );
            return true;
        }
        return false;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public abstract void onPhotoPicked(String path);


    public abstract void onVideoPicked(String path);


}

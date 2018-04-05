package com.androidbootcamp.androidtemplate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ContactPermissionsActivity extends AppCompatActivity {


    public static final String TAG = "ContactPermissions";

    /**
     * Id to identify a contacts permission request.
     */
    private static final int REQUEST_CONTACTS = 1;

    /**
     * Permissions required to read and write contacts. Used by the { ContactsFragment}.
     */
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    /**
     * Root of the layout of this Activity.
     */
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_permissions);

        mLayout = findViewById(R.id.sample_main_layout);

        findViewById(R.id.button_contacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContacts();
            }
        });
    }


    public void showContacts() {
        Log.i(TAG, "Show contacts button pressed. Checking permissions.");

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            Log.i(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
            requestContactsPermissions();

        } else {

            // Contact permissions have been granted. Show the contacts fragment.
            Log.i(TAG,
                    "Contact permissions have already been granted. Displaying contact details.");
            Toast.makeText(this, "Contact permissions have already been granted. Displaying contact details.", Toast.LENGTH_SHORT).show();
            showContactDetails();
        }
    }

    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CONTACTS)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.i(TAG,
                    "Displaying contacts permission rationale to provide additional context.");

            // Display a SnackBar with an explanation and a button to trigger the request.
            Snackbar.make(mLayout, R.string.permission_contacts_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(ContactPermissionsActivity.this, PERMISSIONS_CONTACT,
                                            REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    private void showContactDetails() {
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample_content_fragment, ContactsFragment.newInstance())
                .addToBackStack("contacts")
                .commit();*/

        startActivity(new Intent(this,ContactDetailsActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CONTACTS) {

            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                showContactDetails();
            }else{
                Toast.makeText(this,"Permisos denegados",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }
}

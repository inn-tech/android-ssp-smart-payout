package com.system.itl.ssp_spo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class ListFiles extends AppCompatActivity {

    private ListView fileList;
    private Intent tx;
    private Activity th;
    private byte devHdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        tx = this.getIntent();
        // set the device header from amin activity
        devHdr = getIntent().getByteExtra("deviceCode",(byte)0);
        fileList = (ListView)findViewById(R.id.listFiles);
        // a n array of strings of compatable download files for the connected device
        ArrayList<String> FilesInFolder =
                GetFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),devHdr);
        fileList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FilesInFolder));

        th = this;

        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedFromList = (fileList.getItemAtPosition(position).toString());
                tx.putExtra("filename", selectedFromList);
                th.setResult(RESULT_OK, tx);

                finish();
            }
        });


    }


    /**
     * Build a list of compatible download file stored in the downloads directory
     * @param DirectoryPath
     * @return ArrayList of string file name compatable with teh connected device
     */
    public ArrayList<String> GetFiles(String DirectoryPath, byte hdr) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (File file : files) {
                String flname = file.getName();
                if(flname.length() > 5) {
                    String extension = "";
                    int i = flname.lastIndexOf('.');
                    if (i > 0) {
                        extension = flname.substring(i+1);
                        if(extension.contains("bv1")){
                            if(CheckForDownloadFile(DirectoryPath + "/" + flname,hdr)) {
                                MyFiles.add(flname);
                            }
                        }
                    }
                }
            }
        }

        return MyFiles;
    }

    /**
     * Check the file for compatibility with the connected device
     * @param path the full file path
     * @param hdr the byte header code for the devices
     * @return true = compatible, false is not
     */
    private boolean CheckForDownloadFile(String path, byte hdr)
    {

        File file = new File(path);
        byte[] fileData = new byte[128];
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(file));
            dis.read(fileData,0,128);
            dis.close();
            // fiel header contains compatible info
            if(fileData[0] != 'I' || fileData[1] != 'T' || fileData[2] != 'L'
                    || fileData[5] != hdr || fileData[6] != 0x50){
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;

    }



}

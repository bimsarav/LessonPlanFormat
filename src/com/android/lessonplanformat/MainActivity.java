package com.android.lessonplanformat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int PICKFILE_RESULT_CODE = 1;
	private static final String TAG = "MainActivity";
	
	EditText etSubject;
	EditText etDescription;
	TextView tvResource;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bChoose = (Button) findViewById(R.id.btchoose);
        Button bSave = (Button) findViewById(R.id.btsave);
        
        
        etSubject = (EditText) findViewById(R.id.etsubject);
        etDescription = (EditText) findViewById(R.id.etdescription);
        tvResource = (TextView)findViewById(R.id.tvResource);

        bChoose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				Log.i(TAG, "intentvalue=" + intent);
				if(intent!=null){
					intent.setType("*/*");
					startActivityForResult(intent,PICKFILE_RESULT_CODE);
				}
			}
		});
        
        bSave.setOnClickListener(new OnClickListener() {
        	 
        	@Override
			public void onClick(View arg0) {
				
				 try {
					 
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				 
						// root elements
						Document doc = docBuilder.newDocument();
						Element rootElement = doc.createElement("lesson_plan");
						doc.appendChild(rootElement);
				 
						// lesson elements
						Element lesson = doc.createElement("lesson");
						rootElement.appendChild(lesson);
				 
						// set attribute to lesson element
						Attr attr = doc.createAttribute("id");
						attr.setValue("1");
						lesson.setAttributeNode(attr);
				 
						// shorten way
						// lesson.setAttribute("id", "1");
				 
						// subject elements
						Element subject = doc.createElement("subject");
						subject.appendChild(doc.createTextNode(etSubject.getText().toString()));
						lesson.appendChild(subject);
				 
						// description elements
						Element description = doc.createElement("description");
						description.appendChild(doc.createTextNode(etDescription.getText().toString()));
						lesson.appendChild(description);
						
						// resource elements
						Element resource = doc.createElement("resource");
						resource.appendChild(doc.createTextNode(tvResource.getText().toString()));
						lesson.appendChild(resource);
				 
						// write the content into xml file
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						transformer.setOutputProperty(OutputKeys.INDENT, "yes");
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"filee.txt"));
					
						//StreamResult result = new StreamResult(System.out);
						//String s = result;
						 
						transformer.transform(source, result);
						Log.i(TAG, "Lesson Plan Saved");
						
						
				 } 	catch (ParserConfigurationException pce) {
						pce.printStackTrace();
				 } catch (TransformerException tfe) {
					tfe.printStackTrace();
				 }
				 
//				 -------------------------------------------------------------------------------

//			@Override
//			public void onClick(View arg0) {
//				//create a new file called "new.xml" in the SD card
//		        File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/new1133.txt");
//		        try{
//		        	newxmlfile.createNewFile();
//		        }catch(IOException e){
//		        	Log.e("IOException", "exception in createNewFile() method");
//		        }
//		        //we have to bind the new file with a FileOutputStream
//		        FileOutputStream fileos = null;       	
//		        try{
//		        	fileos = new FileOutputStream(newxmlfile);
//		        }catch(FileNotFoundException e){
//		        	Log.e("FileNotFoundException", "can't create FileOutputStream");
//		        }
//		        //we create a XmlSerializer in order to write xml data
//		        XmlSerializer serializer = Xml.newSerializer();
//		        try {
//		        	//we set the FileOutputStream as output for the serializer, using UTF-8 encoding
//					serializer.setOutput(fileos, "UTF-8");
//					//Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null) 
//					serializer.startDocument(null, Boolean.valueOf(true)); 
//					//set indentation option
//					serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true); 
//					//start a tag called "root"
//					serializer.startTag(null, "lesson"); 
//					//i indent code just to have a view similar to xml-tree
//						serializer.startTag(null, "subject");
//						serializer.text(etSubject.getText().toString());
//						serializer.endTag(null, "subject");
//						
//						serializer.startTag(null, "description");
//						//set an attribute called "attribute" with a "value" for <child2>
//						//serializer.attribute(null, "attribute", "value");
//						serializer.text(etDescription.getText().toString());
//						serializer.endTag(null, "description");
//						
//						String sResource= tvResource.getText().toString();
//						String[] path = sResource.split("\n");
//						
//						for(int i=0; i<path.length; i++){
//							serializer.startTag(null, "path"+i);
//							serializer.text(path[i]);
//							serializer.endTag(null, "path"+i);
//						}
//					
////						serializer.startTag(null, "child3");
////						//write some text inside <child3>
////						serializer.text("some text inside child3");
////						serializer.endTag(null, "child3");
//						
//					serializer.endTag(null, "lesson");
//					serializer.endDocument();
//					
//					//write xml data into the FileOutputStream
//					serializer.flush();
//					//finally we close the file stream
//					fileos.close();
//				} catch (Exception e) {
//					Log.e("Exception","error occurred while creating xml file");
//				}
 
			}
		});
    }
  
    
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode){
		case PICKFILE_RESULT_CODE:
			if(resultCode==RESULT_OK){
				String path = data.getData().getPath();
				Log.i(TAG, "path== " + path);
				tvResource.setText(tvResource.getText() + "\n" + path);
//				String[] fileName = data.getData().getPath().split("/");
//				textFile.setText(textFile.getText() + "\n" + fileName[fileName.length-1]);
			}
			break;
		}

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
}




package com.example.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageInfo;
import android.icu.lang.UScript;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.accessibility.Model.Texts;
import com.example.accessibility.Room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAccessibilityService extends AccessibilityService{
    String appName;
    AppDatabase appDatabase;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source=event.getSource();
        if(event.getEventType()==AccessibilityEvent.TYPE_VIEW_CLICKED ){

            if(event.getPackageName().equals("com.whatsapp")) {
                String chat_name= null;
                appName=event.getPackageName().toString();
                AccessibilityNodeInfo currentNode=getRootInActiveWindow();
                AccessibilityNodeInfo listViewInfo = null;

                for(int i=0;i<currentNode.getChildCount();i++){

                    AccessibilityNodeInfo childNode=currentNode.getChild(i);

                    if(currentNode.getChild(i).getClassName().equals("android.widget.LinearLayout")&&currentNode.getChild(i).getChildCount()>1) {
                        for (int j = 0; j < currentNode.getChild(i).getChildCount(); j++) {
                            if (currentNode.getChild(i).getChild(j) != null) {
                                chat_name=currentNode.getChild(i).getChild(j).getText().toString();
                                break;
                            }
                        }
                    }
                    if(childNode.getClassName().equals("android.widget.ListView")){
                        listViewInfo=childNode;
                        break;
                    }
                }
                if(listViewInfo!=null) {
                    List<AccessibilityNodeInfo> viewGroupInfo = new ArrayList<>();
                    for (int i = 0; i < listViewInfo.getChildCount(); i++) {
                        viewGroupInfo.add(listViewInfo.getChild(i));
                    }
                    if(!viewGroupInfo.isEmpty()){

                        for(int i=0;i<viewGroupInfo.size();i++) {
                            String text = null;
                            for(int j=0;j<viewGroupInfo.get(i).getChildCount();j++) {
                                if (viewGroupInfo.get(i).getChild(j) != null) {
                                    if (viewGroupInfo.get(i).getChild(j).getText() != null) {
                                        if (text == null)
                                            text = viewGroupInfo.get(i).getChild(j).getText().toString();
                                        else {
                                            text = text.concat(" " + viewGroupInfo.get(i).getChild(j).getText().toString());
                                        }
                                    }
                                }
                            }
                            if(text!=null &&chat_name!=null) {
                                Texts texts = new Texts();
                                texts.text = text;
                                texts.app_name = appName;
                                texts.chat_name=chat_name;

                                appDatabase.textsDao().InsertText(texts);
                            }
                        }

                    }
                }

            }



            if(event.getPackageName().equals("com.instagram.android")){

                appName=event.getPackageName().toString();
                AccessibilityNodeInfo rootNode=getRootInActiveWindow();


                AccessibilityNodeInfo viewGroupNode=rootNode.getChild(0);

                AccessibilityNodeInfo frameLayoutInfo=viewGroupNode.getChild(0);
                AccessibilityNodeInfo recyclerViewInfo = null;
                for(int i=0;i<frameLayoutInfo.getChildCount();i++){
                    if(frameLayoutInfo.getChild(i).getClassName().equals("androidx.recyclerview.widget.RecyclerView")){
                        recyclerViewInfo=frameLayoutInfo.getChild(i);
                        break;
                    }
                }

                if(recyclerViewInfo!=null) {
                    for(int i=0;i<recyclerViewInfo.getChildCount();i++){

                        if(recyclerViewInfo.getChild(i).getClassName().equals("android.widget.FrameLayout")){
                            AccessibilityNodeInfo FrameInRecycler=recyclerViewInfo.getChild(i);
                            Texts texts=new Texts();
                                for(int j=0;j<FrameInRecycler.getChildCount();j++){
                                    if(FrameInRecycler.getChild(j).getText()!=null) {
                                        texts.app_name=appName;
                                        if(texts.text==null) {
                                            texts.text = FrameInRecycler.getChild(j).getText().toString();
                                        }
                                        else
                                            texts.text=texts.text.concat(" "+FrameInRecycler.getChild(j).getText().toString());
                                    }
                                }
                            appDatabase.textsDao().InsertText(texts);

                        }
                        if(recyclerViewInfo.getChild(i).getClassName().equals("android.view.ViewGroup")){
                            AccessibilityNodeInfo viewGroupInRecycler=recyclerViewInfo.getChild(i);
                            Texts texts=new Texts();
                            for(int j=0;j<viewGroupInRecycler.getChildCount();j++) {
                                if (viewGroupInRecycler.getChild(j).getText() != null){
                                    texts.app_name=appName;
                                    if(texts.text==null)
                                        texts.text=viewGroupInRecycler.getChild(j).getText().toString();
                                    else
                                        texts.text=texts.text.concat(" "+viewGroupInRecycler.getChild(j).getText().toString());

                                }
                            }
                            appDatabase.textsDao().InsertText(texts);
                        }
                    }
                }

            }

        }
        if (source!=null)
        source.recycle();


    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info=new AccessibilityServiceInfo();

        info.eventTypes=AccessibilityEvent.TYPES_ALL_MASK;
        List<PackageInfo> packages=getPackageManager().getInstalledPackages(0);
        int i=0;
        info.packageNames=new String[packages.size()];
        for (PackageInfo packageInfo:
             packages) {
            info.packageNames[i]=packageInfo.packageName;
            i++;
            if(i==packages.size())
                break;
        }


        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;


        appDatabase=AppDatabase.getInstance(this);

        setServiceInfo(info);

    }

}
